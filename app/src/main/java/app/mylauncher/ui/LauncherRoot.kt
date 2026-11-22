package app.mylauncher.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding
import app.mylauncher.ui.rail.BOTTOM_BAR_HEIGHT
import app.mylauncher.ui.theme.Dimens
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.mylauncher.AppContainer
import app.mylauncher.core.CrashGuard
import app.mylauncher.data.media.NotificationsSummary
import app.mylauncher.data.destinations.LearnedPlace
import app.mylauncher.data.destinations.PlaceKind
import app.mylauncher.data.destinations.SharedPlace
import app.mylauncher.data.location.LocationRepository
import app.mylauncher.data.prefs.Destination
import app.mylauncher.data.prefs.AppOrder
import app.mylauncher.data.prefs.LauncherSettings
import app.mylauncher.data.prefs.Recents
import app.mylauncher.nav.NavIntents
import app.mylauncher.nav.NavigationPane
import app.mylauncher.ui.components.AppMenuActions
import app.mylauncher.data.prefs.WidgetType
import app.mylauncher.ui.dashboard.DashboardPanel
import app.mylauncher.ui.dashboard.SuggestionsCard
import app.mylauncher.ui.widgets.ClockWeatherWidget
import app.mylauncher.ui.widgets.ClockWidget
import app.mylauncher.ui.widgets.NotificationsWidget
import app.mylauncher.ui.widgets.VehicleWidget
import app.mylauncher.ui.widgets.WeatherDailyWidget
import app.mylauncher.ui.widgets.WeatherDetailedWidget
import app.mylauncher.ui.widgets.WeatherHourlyWidget
import app.mylauncher.ui.widgets.WeatherNowWidget
import app.mylauncher.ui.dashboard.Suggestion
import app.mylauncher.ui.debug.DebugDialog
import app.mylauncher.ui.destinations.SaveDestinationSheet
import app.mylauncher.ui.drawer.AnimatedDrawer
import app.mylauncher.ui.drawer.AppDrawerScreen
import app.mylauncher.ui.drawer.DrawerAnchor
import app.mylauncher.ui.icons.LauncherIcons
import app.mylauncher.ui.media.MediaCard
import app.mylauncher.ui.media.MediaHost
import app.mylauncher.ui.media.MediaHostCallbacks
import app.mylauncher.ui.media.MediaPage
import app.mylauncher.data.prefs.RailPosition
import app.mylauncher.data.prefs.DriverSide
import app.mylauncher.data.media.MediaListenerService
import app.mylauncher.ui.notifications.NotificationCenter
import app.mylauncher.ui.rail.BottomBar
import app.mylauncher.ui.rail.Rail
import app.mylauncher.ui.settings.SettingsScreen
import app.mylauncher.ui.settings.SettingsSection
import app.mylauncher.ui.theme.LauncherTheme
import app.mylauncher.ui.theme.LocalLauncherColors
import app.mylauncher.ui.theme.ThemeResolver
import app.mylauncher.ui.workspace.Workspace
import app.mylauncher.ui.dashboard.StackEditRequest
import app.mylauncher.ui.dashboard.StackEditor
import app.mylauncher.ui.dashboard.StackEvent
import app.mylauncher.ui.dashboard.stackNames
import java.util.Calendar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull

/** Velocidade máxima considerada "parado" para edições (bloqueio de distração). */
const val PARKED_MAX_KMH = 5

/** Bateria fraca para as sugestões das pilhas (spec 017, R17.7). */
private const val LOW_BATTERY_V = 11.8f
private const val LOW_BATTERY_HOLD_MS = 30_000L

@Composable
fun LauncherRoot(container: AppContainer, navigator: Navigator, navPane: NavigationPane) {
    val settings by container.settings.settings.collectAsStateWithLifecycle()
    val vehicle by container.vehicle.collectAsStateWithLifecycle()
    val status by container.status.state.collectAsStateWithLifecycle()
    val allApps by container.apps.apps.collectAsStateWithLifecycle()
    val media by container.media.state.collectAsStateWithLifecycle()
    val activeMedia by container.media.activePackages.collectAsStateWithLifecycle()
    // Sugestões do app de música para o card quando nada toca (spec 018, R18.19).
    val nothingPlaying = media.nowPlaying == null
    val mediaSuggestions by androidx.compose.runtime.produceState(emptyList<app.mylauncher.data.media.browse.BrowseItem>(), settings.musicApp, nothingPlaying) {
        value = if (nothingPlaying) runCatching { container.mediaLibrary.suggestions(settings.musicApp) }.getOrDefault(emptyList()) else emptyList()
    }
    val notifs by container.messages.summary.collectAsStateWithLifecycle()
    var debugOpen by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val location by container.location.location.collectAsStateWithLifecycle()
    val appsAnchor = remember { DrawerAnchor() }
    val pendingSave by container.destinationRequests.pending.collectAsStateWithLifecycle()
    val learned by container.placesLearner.suggestion.collectAsStateWithLifecycle()
    val weather by container.weather.weather.collectAsStateWithLifecycle()
    // Parado: velocidade da MCU (ou do GPS, se a MCU não informar) até 5 km/h (spec 011, R11.7).
    val speed = vehicle.speedKmh ?: LocationRepository.speedKmh(location) ?: 0
    val parked = speed <= PARKED_MAX_KMH

    // Chave ligada: volta ao painel (spec 016, R16.3).
    androidx.compose.runtime.LaunchedEffect(Unit) { container.wake.collect { navigator.home() } }

    // Pilhas de widgets (spec 017): eventos que trazem o widget certo para a frente (R17.7).
    val stackEvents = remember { MutableSharedFlow<StackEvent>(extraBufferCapacity = 8) }
    var stackEdit by remember { mutableStateOf<StackEditRequest?>(null) }
    // Editar pilha só com o carro parado (R17.5).
    androidx.compose.runtime.LaunchedEffect(parked) { if (!parked) stackEdit = null }
    androidx.compose.runtime.LaunchedEffect(Unit) {
        launch { container.wake.collect { stackEvents.emit(StackEvent.Wake) } }
        launch {
            container.media.state.map { it.nowPlaying?.playing == true }.distinctUntilChanged().drop(1)
                .collect { if (it) stackEvents.emit(StackEvent.Suggest(WidgetType.MEDIA, "Música tocando")) }
        }
        launch {
            // 30 s abaixo do limite: a queda de tensão da partida não conta.
            container.vehicle.map { v -> v.batteryV?.let { it < LOW_BATTERY_V } == true }.distinctUntilChanged()
                .collectLatest { low ->
                    if (low) {
                        delay(LOW_BATTERY_HOLD_MS)
                        stackEvents.emit(StackEvent.Suggest(WidgetType.VEHICLE, "Bateria fraca"))
                    }
                }
        }
        launch {
            container.messages.summary.map { s -> s.recent.firstOrNull { it.isMessage }?.key }.distinctUntilChanged().drop(1)
                .collect { if (it != null) stackEvents.emit(StackEvent.Suggest(WidgetType.NOTIFICATIONS, "Mensagem nova")) }
        }
    }

    val hour = Calendar.getInstance().apply { timeInMillis = status.now }.get(Calendar.HOUR_OF_DAY)
    val night = ThemeResolver.isNight(settings.theme, vehicle, hour)
    // O sistema acompanha o tema do Lumia, para os apps que seguem o sistema (Maps embutido, spec 008).
    LaunchedEffect(night) { withContext(Dispatchers.IO) { app.mylauncher.data.root.SystemNightMode.apply(night) } }
    val apps = container.apps
    val openApp: (String) -> Unit = { pkg -> openApp(container, navigator, scope, settings, pkg) }

    LauncherTheme(isNight = night, fontScale = settings.textScale.factor) {
        val c = LocalLauncherColors.current
        val railApps = remember(settings.pinned, settings.recents, settings.navApp, settings.musicApp, allApps) {
            Recents.railApps(
                pinned = settings.pinned,
                recents = settings.recents,
                defaults = listOf(settings.navApp, settings.musicApp, LauncherSettings.PHONE_APP),
                isInstalled = apps::isInstalled,
            )
        }
        val menu = AppMenuActions(
            pinned = settings.pinned,
            recents = settings.recents,
            label = apps::label,
            onPin = container.settings::pin,
            onUnpin = container.settings::unpin,
            onRemoveRecent = container.settings::removeRecent,
            onAppInfo = { container.systemActions.openAppInfo(it) },
            onHide = container.settings::hide,
            canUninstall = apps::canUninstall,
            onUninstall = apps::uninstall,
        )
        val screen = navigator.screen
        val mediaVisible = screen == Screen.SPLIT_MEDIA || screen == Screen.MEDIA

        // A área de trabalho fica sempre na mesma posição da árvore (o mapa não é recriado);
        // só a barra muda entre vertical e horizontal, com o recuo correspondente (R14.3).
        val bottom = settings.railPosition == RailPosition.BOTTOM
        // Motorista à direita: a barra vertical vai para a direita e a horizontal se inverte.
        val railRight = settings.driverSide == DriverSide.RIGHT
        val barWeather = weather.takeIf { settings.showWeatherInBar }
        val onNotifications: () -> Unit = {
            if (navigator.overlay == Screen.NOTIFICATIONS) navigator.back() else navigator.go(Screen.NOTIFICATIONS)
        }
        // Desenha um widget do catálogo; usado nas pilhas do painel e nas prévias do editor (spec 017).
        val renderWidget: @Composable (WidgetType, Modifier) -> Unit = { type, wm ->
            when (type) {
                WidgetType.MEDIA -> MediaCard(
                    state = media,
                    apps = apps,
                    musicApp = settings.musicApp,
                    label = container.mediaLibrary::label,
                    suggestions = mediaSuggestions,
                    onOpenMedia = { navigator.go(Screen.SPLIT_MEDIA) },
                    onOpenSources = { navigator.media.reset(MediaPage.Sources); navigator.go(Screen.MEDIA) },
                    onOpenApp = openApp,
                    onPlaySuggestion = { item ->
                        container.media.prefer(settings.musicApp)
                        scope.launch { container.mediaLibrary.playSuggestion(settings.musicApp, item) }
                        navigator.go(Screen.SPLIT_MEDIA)
                    },
                    onGrantAccess = { container.systemActions.openNotificationAccess() },
                    modifier = wm,
                )
                WidgetType.SUGGESTIONS -> SuggestionsCard(
                    suggestions(container, settings, notifs, learned, location.takeIf { parked }) { navigator.go(Screen.SETTINGS) },
                    apps, wm,
                )
                WidgetType.WEATHER -> WeatherNowWidget(weather, wm)
                WidgetType.WEATHER_DETAILED -> WeatherDetailedWidget(weather, wm)
                WidgetType.WEATHER_HOURLY -> WeatherHourlyWidget(weather, wm)
                WidgetType.WEATHER_DAILY -> WeatherDailyWidget(weather, wm)
                WidgetType.VEHICLE -> VehicleWidget(vehicle, wm)
                WidgetType.CLOCK -> ClockWidget(status.now, wm)
                WidgetType.CLOCK_WEATHER -> ClockWeatherWidget(status.now, weather, wm)
                WidgetType.NOTIFICATIONS -> NotificationsWidget(
                    notifs, apps,
                    onOpen = { pkg, intent -> runCatching { intent?.send() ?: apps.launch(pkg) }.onFailure { apps.launch(pkg) } },
                    modifier = wm,
                )
            }
        }
        Box(Modifier.fillMaxSize().background(c.background)) {
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(
                        start = if (bottom || railRight) 0.dp else Dimens.railWidth,
                        end = if (!bottom && railRight) Dimens.railWidth else 0.dp,
                        bottom = if (bottom) BOTTOM_BAR_HEIGHT else 0.dp,
                    ),
            ) {
                // Área de trabalho: mapa sempre vivo + painel lateral animado (spec 010).
                Workspace(
                    mode = navigator.workspace,
                    active = navigator.overlay == null,
                    navPane = navPane,
                    // Alterna: no card abre a tela cheia; na tela cheia volta ao painel.
                    onExpandMap = { if (navigator.workspace == Screen.MAP) navigator.back() else navigator.go(Screen.MAP) },
                    startInset = if (bottom || railRight) Dimens.gap else 8.dp,
                    dashboardPanel = { m ->
                        DashboardPanel(
                            layout = settings.dashboardLayout,
                            stacks = settings.stacks,
                            parked = parked,
                            smart = settings.smartStacks,
                            events = stackEvents,
                            onEditStacks = container.settings::editStacks,
                            onOpenEditor = { stackEdit = it },
                            widget = renderWidget,
                            modifier = m,
                        )
                    },
                    playerPanel = { full, m ->
                        MediaHost(
                            full = full,
                            state = media,
                            nav = navigator.media,
                            apps = apps,
                            library = container.mediaLibrary,
                            musicApp = settings.musicApp,
                            driving = !parked,
                            activePackages = activeMedia,
                            cb = MediaHostCallbacks(
                                onExpand = { navigator.go(Screen.MEDIA) },
                                onCollapse = { navigator.go(Screen.SPLIT_MEDIA) },
                                onLaunchApp = { apps.launch(it) },
                                onPickApp = { pkg ->
                                    container.settings.recordLaunch(pkg)
                                    container.media.prefer(pkg)
                                    if (pkg !in container.media.activePackages.value) container.mediaApps.sendPlay(pkg)
                                },
                                onPrefer = { container.media.prefer(it) },
                                onGrantAccess = { container.systemActions.openNotificationAccess() },
                            ),
                            modifier = m,
                        )
                    },
                )

                // Gaveta de apps: abre no estilo escolhido nos Ajustes (padrão: cresce do botão).
                AnimatedDrawer(
                    visible = navigator.overlay == Screen.APPS,
                    style = settings.drawerAnimation,
                    anchor = appsAnchor,
                ) {
                    AppDrawerScreen(
                        apps = apps,
                        allApps = allApps,
                        settings = settings,
                        onLaunch = openApp,
                        onOpenSettings = { navigator.openSettings() },
                        onOpenStockLauncher = { CrashGuard.openFallback(container.context) },
                        onSetPinned = container.settings::setPinned,
                        menu = menu,
                        parked = parked,
                        onCommitOrder = { order ->
                            container.settings.setCustomOrder(order)
                            container.settings.setAppOrder(AppOrder.CUSTOM)
                        },
                        onOpenHidden = { navigator.openSettings(SettingsSection.APPS.name) },
                        modifier = Modifier.fillMaxSize().background(c.background),
                    )
                }

                // Ajustes: por cima da área de trabalho, com fade + escala sutil (R10.3).
                AnimatedContent(
                    targetState = navigator.overlay == Screen.SETTINGS,
                    transitionSpec = {
                        (fadeIn(tween(220)) + scaleIn(tween(220), initialScale = 0.96f))
                            .togetherWith(fadeOut(tween(160)) + scaleOut(tween(160), targetScale = 0.98f))
                    },
                    label = "settings",
                ) { open ->
                    if (open) {
                        SettingsScreen(
                            container = container,
                            onEditStack = { stackEdit = StackEditRequest(it) },
                            onBack = { navigator.back() },
                            onOpenDrawer = { navigator.go(Screen.APPS) },
                            modifier = Modifier.fillMaxSize().background(c.background),
                            initialSection = navigator.settingsSection
                                ?.let { runCatching { SettingsSection.valueOf(it) }.getOrNull() } ?: SettingsSection.APPEARANCE,
                        )
                    } else {
                        Spacer(Modifier)
                    }
                }

                // Central de notificações (Android Auto): abre pelo selo da barra.
                AnimatedContent(
                    targetState = navigator.overlay == Screen.NOTIFICATIONS,
                    transitionSpec = {
                        (fadeIn(tween(220)) + scaleIn(tween(220), initialScale = 0.96f))
                            .togetherWith(fadeOut(tween(160)) + scaleOut(tween(160), targetScale = 0.98f))
                    },
                    label = "notifications",
                ) { open ->
                    if (open) {
                        NotificationCenter(
                            summary = notifs,
                            apps = apps,
                            now = status.now,
                            onBack = { navigator.back() },
                            onOpen = { pkg, intent ->
                                runCatching { intent?.send() ?: apps.launch(pkg) }.onFailure { apps.launch(pkg) }
                            },
                            onDismiss = { MediaListenerService.dismiss(it) },
                            onDismissAll = { MediaListenerService.dismissAll() },
                            onSystemShade = { container.systemActions.expandNotifications() },
                            modifier = Modifier.fillMaxSize().background(c.background),
                        )
                    } else {
                        Spacer(Modifier)
                    }
                }

                // Editar pilha (spec 017): por cima do painel ou dos Ajustes.
                stackEdit?.let { req ->
                    StackEditor(
                        stacks = settings.stacks,
                        request = req,
                        names = stackNames(settings.dashboardLayout),
                        visible = settings.dashboardLayout.slots,
                        smart = settings.smartStacks,
                        onEdit = container.settings::editStacks,
                        onSmart = container.settings::setSmartStacks,
                        onClose = { stackEdit = null },
                        preview = renderWidget,
                    )
                }
            }
            if (bottom) {
                BottomBar(
                    screen = screen,
                    status = status,
                    batteryV = vehicle.batteryV.takeIf { settings.showBattery },
                    weather = barWeather,
                    railApps = railApps,
                    activePackage = media.nowPlaying?.sourcePackage?.takeIf { mediaVisible },
                    apps = apps,
                    notificationCount = notifs.count,
                    voiceAvailable = container.systemActions.voiceIntent != null,
                    showBell = settings.showBell,
                    showVoice = settings.showVoice,
                    menu = menu,
                    onDashboard = { navigator.go(Screen.DASHBOARD) },
                    // Segundo toque no botão da gaveta volta à área de trabalho.
                    onApps = { if (navigator.overlay == Screen.APPS) navigator.back() else navigator.go(Screen.APPS) },
                    onLaunchApp = openApp,
                    onNotifications = onNotifications,
                    onVoice = { container.systemActions.startVoice() },
                    onClockLongPress = { debugOpen = true },
                    appsAnchor = appsAnchor,
                    mirrored = railRight,
                    modifier = Modifier.align(Alignment.BottomCenter),
                )
            } else {
                Rail(
                    screen = screen,
                    status = status,
                    batteryV = vehicle.batteryV.takeIf { settings.showBattery },
                    weather = barWeather,
                    railApps = railApps,
                    activePackage = media.nowPlaying?.sourcePackage?.takeIf { mediaVisible },
                    apps = apps,
                    notificationCount = notifs.count,
                    voiceAvailable = container.systemActions.voiceIntent != null,
                    showBell = settings.showBell,
                    showVoice = settings.showVoice,
                    menu = menu,
                    onDashboard = { navigator.go(Screen.DASHBOARD) },
                    // Segundo toque no botão da gaveta volta à área de trabalho.
                    onApps = { if (navigator.overlay == Screen.APPS) navigator.back() else navigator.go(Screen.APPS) },
                    onLaunchApp = openApp,
                    onNotifications = onNotifications,
                    onVoice = { container.systemActions.startVoice() },
                    onClockLongPress = { debugOpen = true },
                    appsAnchor = appsAnchor,
                    onRight = railRight,
                    modifier = Modifier.align(if (railRight) Alignment.CenterEnd else Alignment.CenterStart),
                )
            }

            // "Salvar como…" (spec 012): por cima de tudo, dentro da janela (sem barras do sistema).
            pendingSave?.let { place ->
                SaveDestinationSheet(
                    place = place,
                    onSaveHome = { container.settings.setHome(it); container.destinationRequests.dismiss() },
                    onSaveWork = { container.settings.setWork(it); container.destinationRequests.dismiss() },
                    onSaveFavorite = { container.settings.addFavorite(it); container.destinationRequests.dismiss() },
                    onDismiss = { container.destinationRequests.dismiss() },
                )
            }
        }
        if (debugOpen) {
            DebugDialog(
                simulate = settings.simulateVehicle,
                vehicle = vehicle,
                fake = container.fakeVehicle,
                player = container.debugPlayer,
                weather = container.weather,
                onSimulate = container.settings::setSimulateVehicle,
                onDismiss = { debugOpen = false },
            )
        }
    }
}

/**
 * Abrir um app pela barra/gaveta (spec 010, R10.6–R10.8): apps de mídia abrem o player ao lado do
 * mapa e começam a tocar sem abrir o app; se em 3 s não surgir sessão, o app é aberto.
 */
private fun openApp(container: AppContainer, navigator: Navigator, scope: CoroutineScope, settings: LauncherSettings, pkg: String) {
    val mediaApps = container.mediaApps
    if (!settings.splitMedia || !mediaApps.isMediaApp(pkg)) {
        container.apps.launch(pkg)
        return
    }
    container.settings.recordLaunch(pkg)
    container.media.prefer(pkg)
    navigator.media.app = pkg
    if (!navigator.screen.let { it == Screen.MEDIA }) navigator.go(Screen.SPLIT_MEDIA)
    if (pkg in container.media.activePackages.value) return
    mediaApps.sendPlay(pkg)
    scope.launch {
        val appeared = withTimeoutOrNull(3_000) { container.media.activePackages.first { pkg in it } } != null
        if (!appeared) container.apps.launch(pkg)
    }
}

/**
 * Card de sugestões (R2.9 + spec 012): destinos salvos, lugar aprendido, última mensagem e
 * "Salvar local atual" (só parado). Sem itens, atalho para os Ajustes.
 */
private fun suggestions(
    container: AppContainer,
    s: LauncherSettings,
    notifs: NotificationsSummary,
    learned: LearnedPlace?,
    parkedAt: android.location.Location?,
    openSettings: () -> Unit,
): List<Suggestion> {
    val apps = container.apps
    val navLabel = if (apps.isInstalled(s.navApp)) apps.label(s.navApp) else "o app de navegação"
    val list = mutableListOf<Suggestion>()
    learned?.let { place ->
        val label = if (place.kind == PlaceKind.HOME) "Casa" else "Trabalho"
        list.add(
            Suggestion("Salvar como $label?", "Lugar frequente (${place.visits} paradas) · toque para salvar", LauncherIcons.Pin) {
                val d = Destination(label, place.query)
                if (place.kind == PlaceKind.HOME) container.settings.setHome(d) else container.settings.setWork(d)
            },
        )
    }
    s.destinations.forEach { d ->
        val icon = when (d.label) {
            "Casa" -> LauncherIcons.Home
            "Trabalho" -> LauncherIcons.Work
            else -> LauncherIcons.Pin
        }
        list.add(Suggestion(d.label, "Navegar com $navLabel", icon) { apps.launchIntent(NavIntents.navigate(s.navApp, d)) })
    }
    notifs.lastMessage?.let { m ->
        list.add(
            Suggestion(m.sender.ifBlank { apps.label(m.packageName) }, m.text.ifBlank { "Nova mensagem" }, appPackage = m.packageName) {
                runCatching { m.contentIntent?.send() }.onFailure { apps.launch(m.packageName) }
            },
        )
    }
    if (parkedAt != null && list.size < 3) {
        list.add(
            Suggestion("Salvar local atual", "Casa, Trabalho ou favorito, sem digitar", LauncherIcons.Locate) {
                container.destinationRequests.request(
                    SharedPlace("%.6f,%.6f".format(java.util.Locale.US, parkedAt.latitude, parkedAt.longitude), null),
                )
            },
        )
    }
    return list.ifEmpty {
        listOf(Suggestion("Configurar destinos", "Casa e Trabalho nos Ajustes", LauncherIcons.Settings, onClick = openSettings))
    }
}
