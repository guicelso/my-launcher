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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.mylauncher.AppContainer
import app.mylauncher.core.CrashGuard
import app.mylauncher.data.media.NotificationsSummary
import app.mylauncher.data.location.LocationRepository
import app.mylauncher.data.prefs.AppOrder
import app.mylauncher.data.prefs.LauncherSettings
import app.mylauncher.data.prefs.Recents
import app.mylauncher.nav.NavIntents
import app.mylauncher.nav.NavigationPane
import app.mylauncher.ui.components.AppMenuActions
import app.mylauncher.ui.dashboard.DashboardPanel
import app.mylauncher.ui.dashboard.Suggestion
import app.mylauncher.ui.debug.DebugDialog
import app.mylauncher.ui.drawer.AnimatedDrawer
import app.mylauncher.ui.drawer.AppDrawerScreen
import app.mylauncher.ui.drawer.DrawerAnchor
import app.mylauncher.ui.icons.LauncherIcons
import app.mylauncher.ui.media.MediaCard
import app.mylauncher.ui.media.MediaSources
import app.mylauncher.ui.media.PlayerPanel
import app.mylauncher.ui.rail.Rail
import app.mylauncher.ui.settings.SettingsScreen
import app.mylauncher.ui.theme.LauncherTheme
import app.mylauncher.ui.theme.LocalLauncherColors
import app.mylauncher.ui.theme.ThemeResolver
import app.mylauncher.ui.workspace.Workspace
import java.util.Calendar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull

/** Velocidade máxima considerada "parado" para edições (bloqueio de distração). */
const val PARKED_MAX_KMH = 5

@Composable
fun LauncherRoot(container: AppContainer, navigator: Navigator, navPane: NavigationPane) {
    val settings by container.settings.settings.collectAsStateWithLifecycle()
    val vehicle by container.vehicle.collectAsStateWithLifecycle()
    val status by container.status.state.collectAsStateWithLifecycle()
    val allApps by container.apps.apps.collectAsStateWithLifecycle()
    val media by container.media.state.collectAsStateWithLifecycle()
    val notifs by container.messages.summary.collectAsStateWithLifecycle()
    var debugOpen by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val location by container.location.location.collectAsStateWithLifecycle()
    val appsAnchor = remember { DrawerAnchor() }
    // Parado: velocidade da MCU (ou do GPS, se a MCU não informar) até 5 km/h (spec 011, R11.7).
    val speed = vehicle.speedKmh ?: LocationRepository.speedKmh(location) ?: 0
    val parked = speed <= PARKED_MAX_KMH

    val hour = Calendar.getInstance().apply { timeInMillis = status.now }.get(Calendar.HOUR_OF_DAY)
    val night = ThemeResolver.isNight(settings.theme, vehicle, hour)
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

        Row(Modifier.fillMaxSize().background(c.background)) {
            Rail(
                screen = screen,
                status = status,
                batteryV = vehicle.batteryV.takeIf { settings.showBattery },
                railApps = railApps,
                activePackage = media.nowPlaying?.sourcePackage?.takeIf { mediaVisible },
                apps = apps,
                hasNotifications = notifs.count > 0,
                voiceAvailable = container.systemActions.voiceIntent != null,
                showBell = settings.showBell,
                showVoice = settings.showVoice,
                menu = menu,
                onDashboard = { navigator.go(Screen.DASHBOARD) },
                // Segundo toque no botão da gaveta volta à área de trabalho.
                onApps = { if (navigator.overlay == Screen.APPS) navigator.back() else navigator.go(Screen.APPS) },
                onLaunchApp = openApp,
                onNotifications = { container.systemActions.expandNotifications() },
                onVoice = { container.systemActions.startVoice() },
                onClockLongPress = { debugOpen = true },
                appsAnchor = appsAnchor,
            )
            Box(Modifier.weight(1f).fillMaxHeight()) {
                // Área de trabalho: mapa sempre vivo + painel lateral animado (spec 010).
                Workspace(
                    mode = navigator.workspace,
                    active = navigator.overlay == null,
                    navPane = navPane,
                    onExpandMap = { navigator.go(Screen.MAP) },
                    dashboardPanel = { m ->
                        DashboardPanel(
                            suggestions = suggestions(container, settings, notifs) { navigator.go(Screen.SETTINGS) },
                            apps = apps,
                            mediaCard = { cardModifier ->
                                MediaCard(
                                    state = media,
                                    apps = apps,
                                    musicApp = settings.musicApp,
                                    onOpenMedia = { navigator.go(Screen.SPLIT_MEDIA) },
                                    onOpenApp = openApp,
                                    onLaunchApp = { apps.launch(it) },
                                    onGrantAccess = { container.systemActions.openNotificationAccess() },
                                    modifier = cardModifier,
                                )
                            },
                            modifier = m,
                        )
                    },
                    playerPanel = { full, m ->
                        PlayerPanel(
                            state = media,
                            apps = apps,
                            full = full,
                            onToggleSize = { navigator.go(if (full) Screen.SPLIT_MEDIA else Screen.MEDIA) },
                            onOpenApp = { apps.launch(it) },
                            onGrantAccess = { container.systemActions.openNotificationAccess() },
                            sources = MediaSources.list(settings.musicApp, media.nowPlaying?.sourcePackage, apps::isInstalled),
                            onSelectSource = openApp,
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
                        onOpenSettings = { navigator.go(Screen.SETTINGS) },
                        onOpenStockLauncher = { CrashGuard.openFallback(container.context) },
                        onSetPinned = container.settings::setPinned,
                        menu = menu,
                        parked = parked,
                        onCommitOrder = { order ->
                            container.settings.setCustomOrder(order)
                            container.settings.setAppOrder(AppOrder.CUSTOM)
                        },
                        onOpenHidden = { navigator.go(Screen.SETTINGS) },
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
                            onBack = { navigator.back() },
                            onOpenDrawer = { navigator.go(Screen.APPS) },
                            modifier = Modifier.fillMaxSize().background(c.background),
                        )
                    } else {
                        Spacer(Modifier)
                    }
                }
            }
        }
        if (debugOpen) {
            DebugDialog(
                simulate = settings.simulateVehicle,
                vehicle = vehicle,
                fake = container.fakeVehicle,
                player = container.debugPlayer,
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
    if (!navigator.screen.let { it == Screen.MEDIA }) navigator.go(Screen.SPLIT_MEDIA)
    if (pkg in container.media.activePackages.value) return
    mediaApps.sendPlay(pkg)
    scope.launch {
        val appeared = withTimeoutOrNull(3_000) { container.media.activePackages.first { pkg in it } } != null
        if (!appeared) container.apps.launch(pkg)
    }
}

/** Card de sugestões (R2.9): destinos salvos e a última mensagem; sem itens, atalho para os Ajustes. */
private fun suggestions(
    container: AppContainer,
    s: LauncherSettings,
    notifs: NotificationsSummary,
    openSettings: () -> Unit,
): List<Suggestion> {
    val apps = container.apps
    val navLabel = apps.label(s.navApp)
    val list = mutableListOf<Suggestion>()
    s.home?.let { d ->
        list.add(Suggestion("Casa", "Navegar com $navLabel", LauncherIcons.Home) { apps.launchIntent(NavIntents.navigate(s.navApp, d)) })
    }
    s.work?.let { d ->
        list.add(Suggestion("Trabalho", "Navegar com $navLabel", LauncherIcons.Work) { apps.launchIntent(NavIntents.navigate(s.navApp, d)) })
    }
    notifs.lastMessage?.let { m ->
        list.add(
            Suggestion(m.sender.ifBlank { apps.label(m.packageName) }, m.text.ifBlank { "Nova mensagem" }, appPackage = m.packageName) {
                runCatching { m.contentIntent?.send() }.onFailure { apps.launch(m.packageName) }
            },
        )
    }
    return list.ifEmpty {
        listOf(Suggestion("Configurar destinos", "Casa e Trabalho nos Ajustes", LauncherIcons.Settings, onClick = openSettings))
    }
}
