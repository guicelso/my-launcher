package app.mylauncher.ui

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.mylauncher.AppContainer
import app.mylauncher.core.CrashGuard
import app.mylauncher.data.prefs.LauncherSettings
import app.mylauncher.data.prefs.Recents
import app.mylauncher.nav.NavIntents
import app.mylauncher.nav.NavigationPane
import app.mylauncher.nav.PaneMode
import app.mylauncher.ui.components.AppMenuActions
import app.mylauncher.ui.components.ScreenPanel
import app.mylauncher.ui.dashboard.DashboardScreen
import app.mylauncher.ui.dashboard.Suggestion
import app.mylauncher.ui.debug.DebugDialog
import app.mylauncher.ui.drawer.AppDrawerScreen
import app.mylauncher.ui.icons.LauncherIcons
import app.mylauncher.ui.media.MediaCard
import app.mylauncher.ui.media.MediaScreen
import app.mylauncher.ui.media.MediaSources
import app.mylauncher.data.media.NotificationsSummary
import app.mylauncher.ui.rail.Rail
import app.mylauncher.ui.settings.SettingsScreen
import app.mylauncher.ui.theme.LauncherTheme
import app.mylauncher.ui.theme.LocalLauncherColors
import app.mylauncher.ui.theme.ThemeResolver
import java.util.Calendar

@Composable
fun LauncherRoot(container: AppContainer, navigator: Navigator, navPane: NavigationPane) {
    val settings by container.settings.settings.collectAsStateWithLifecycle()
    val vehicle by container.vehicle.collectAsStateWithLifecycle()
    val status by container.status.state.collectAsStateWithLifecycle()
    val allApps by container.apps.apps.collectAsStateWithLifecycle()
    val media by container.media.state.collectAsStateWithLifecycle()
    val notifs by container.messages.summary.collectAsStateWithLifecycle()
    var debugOpen by remember { mutableStateOf(false) }

    val hour = Calendar.getInstance().apply { timeInMillis = status.now }.get(Calendar.HOUR_OF_DAY)
    val night = ThemeResolver.isNight(settings.theme, vehicle, hour)
    val apps = container.apps

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
        )
        Row(Modifier.fillMaxSize().background(c.background)) {
            Rail(
                screen = navigator.screen,
                status = status,
                batteryV = vehicle.batteryV.takeIf { settings.showBattery },
                railApps = railApps,
                apps = apps,
                hasNotifications = notifs.count > 0,
                voiceAvailable = container.systemActions.voiceIntent != null,
                showBell = settings.showBell,
                showVoice = settings.showVoice,
                menu = menu,
                onDashboard = { navigator.go(Screen.DASHBOARD) },
                onApps = { navigator.go(Screen.APPS) },
                onLaunchApp = { apps.launch(it) },
                onNotifications = { container.systemActions.expandNotifications() },
                onVoice = { container.systemActions.startVoice() },
                onClockLongPress = { debugOpen = true },
            )
            Box(Modifier.weight(1f).fillMaxHeight()) {
                Crossfade(navigator.screen, animationSpec = tween(200), label = "screen") { screen ->
                    when (screen) {
                        Screen.DASHBOARD -> DashboardScreen(
                            navPane = navPane,
                            suggestions = suggestions(container, settings, notifs) { navigator.go(Screen.SETTINGS) },
                            apps = apps,
                            onOpenMap = { navigator.go(Screen.MAP) },
                            mediaCard = { m ->
                                MediaCard(
                                    state = media,
                                    apps = apps,
                                    musicApp = settings.musicApp,
                                    onOpenMedia = { navigator.go(Screen.MEDIA) },
                                    onOpenApp = { apps.launch(it) },
                                    onGrantAccess = { container.systemActions.openNotificationAccess() },
                                    modifier = m,
                                )
                            },
                        )
                        Screen.MAP -> ScreenPanel(Modifier.fillMaxSize()) {
                            navPane.Content(PaneMode.FULL, Modifier.fillMaxSize(), onExpand = {})
                        }
                        Screen.APPS -> AppDrawerScreen(
                            apps = apps,
                            allApps = allApps,
                            settings = settings,
                            onLaunch = { apps.launch(it) },
                            onOpenSettings = { navigator.go(Screen.SETTINGS) },
                            onOpenStockLauncher = { CrashGuard.openFallback(container.context) },
                            onSetPinned = container.settings::setPinned,
                            menu = menu,
                            modifier = Modifier.fillMaxSize(),
                        )
                        Screen.MEDIA -> MediaScreen(
                            state = media,
                            sources = MediaSources.list(settings.musicApp, media.nowPlaying?.sourcePackage, apps::isInstalled),
                            apps = apps,
                            onOpenApp = { apps.launch(it) },
                            onGrantAccess = { container.systemActions.openNotificationAccess() },
                            modifier = Modifier.fillMaxSize(),
                        )
                        Screen.SETTINGS -> SettingsScreen(
                            container = container,
                            onBack = { navigator.go(Screen.DASHBOARD) },
                            onOpenDrawer = { navigator.go(Screen.APPS) },
                            modifier = Modifier.fillMaxSize(),
                        )
                    }
                }
            }
        }
        if (debugOpen) {
            DebugDialog(
                simulate = settings.simulateVehicle,
                vehicle = vehicle,
                fake = container.fakeVehicle,
                onSimulate = container.settings::setSimulateVehicle,
                onDismiss = { debugOpen = false },
            )
        }
    }
}

/** Card de sugestões (R2.9): destinos salvos; sem itens, atalho para os Ajustes. */
private fun suggestions(
    container: AppContainer,
    s: LauncherSettings,
    notifs: NotificationsSummary,
    openSettings: () -> Unit,
): List<Suggestion> {
    val apps = container.apps
    val navLabel = apps.label(s.navApp)
    val list = mutableListOf<Suggestion>().apply {
        s.home?.let { d ->
            add(Suggestion("Casa", "Navegar com $navLabel", LauncherIcons.Home) { apps.launchIntent(NavIntents.navigate(s.navApp, d)) })
        }
        s.work?.let { d ->
            add(Suggestion("Trabalho", "Navegar com $navLabel", LauncherIcons.Work) { apps.launchIntent(NavIntents.navigate(s.navApp, d)) })
        }
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
