package app.mylauncher.ui.rail

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.mylauncher.data.apps.AppsRepository
import app.mylauncher.data.status.StatusState
import app.mylauncher.data.weather.Weather
import app.mylauncher.ui.Screen
import app.mylauncher.ui.components.AppContextMenu
import app.mylauncher.ui.components.AppIcon
import app.mylauncher.ui.components.AppMenuActions
import app.mylauncher.ui.components.PillShape
import app.mylauncher.ui.components.TouchIconButton
import app.mylauncher.ui.components.pressable
import app.mylauncher.ui.drawer.DrawerAnchor
import app.mylauncher.ui.icons.LauncherIcons
import app.mylauncher.ui.theme.Dimens
import app.mylauncher.ui.theme.LocalLauncherColors
import app.mylauncher.ui.widgets.weatherIcon
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val ItemShape = RoundedCornerShape(38.dp)

/** Botão da grade de apps: vira o ícone do Painel quando a grade (ou outra tela expandida) está aberta. */
private fun showsDashboardIcon(screen: Screen) = screen != Screen.DASHBOARD

/**
 * Barra de navegação vertical (spec 002/014), como a do Android Auto em telas largas:
 * status no topo (sinal, Wi-Fi, BT, relógio, bateria, clima e selo de notificações) e, embaixo,
 * os apps (fixados + recentes), o comando de voz e o botão da grade de apps, que vira o ícone
 * do Painel fora do painel. Artboard "Barra vertical" em design/wireframes.
 */
@Composable
fun Rail(
    screen: Screen,
    status: StatusState,
    batteryV: Float?,
    weather: Weather?,
    railApps: List<String>,
    activePackage: String?,
    apps: AppsRepository,
    notificationCount: Int,
    voiceAvailable: Boolean,
    showBell: Boolean,
    showVoice: Boolean,
    menu: AppMenuActions,
    onDashboard: () -> Unit,
    onApps: () -> Unit,
    onLaunchApp: (String) -> Unit,
    onNotifications: () -> Unit,
    onVoice: () -> Unit,
    onClockLongPress: () -> Unit,
    appsAnchor: DrawerAnchor? = null,
    onRight: Boolean = false,
    modifier: Modifier = Modifier,
) {
    val c = LocalLauncherColors.current
    Column(
        modifier
            .width(Dimens.railWidth)
            .fillMaxHeight()
            .background(c.rail)
            .padding(top = 16.dp, bottom = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        StatusBlock(status, batteryV, weather, onClockLongPress)
        if (showBell) {
            NotificationBadge(notificationCount, onNotifications, Modifier.padding(top = 10.dp))
        }

        Spacer(Modifier.weight(1f))

        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(2.dp)) {
            railApps.forEach { pkg ->
                RailApp(pkg, pkg == activePackage, apps, menu, onLaunchApp, menuOffset = if (onRight) DpOffset((-300).dp, (-76).dp) else DpOffset(84.dp, (-76).dp))
            }
            if (showVoice) VoiceButton(voiceAvailable, onVoice)
            LauncherButton(screen, onDashboard, onApps, appsAnchor)
        }
    }
}

/** Grade de apps no painel; ícone do Painel nas outras telas (Android Auto). */
@Composable
private fun LauncherButton(screen: Screen, onDashboard: () -> Unit, onApps: () -> Unit, appsAnchor: DrawerAnchor?) {
    val c = LocalLauncherColors.current
    val dash = showsDashboardIcon(screen)
    TouchIconButton(
        if (dash) LauncherIcons.Dashboard else LauncherIcons.Apps,
        if (dash) "Painel" else "Acesso rápido aos apps",
        if (dash) onDashboard else onApps,
        modifier = Modifier.onGloballyPositioned { appsAnchor?.bounds = it.boundsInRoot() },
        shape = ItemShape, iconSize = 34.dp, tint = c.onSurface,
    )
}

@Composable
private fun VoiceButton(available: Boolean, onVoice: () -> Unit) {
    TouchIconButton(
        LauncherIcons.Mic, if (available) "Comando de voz" else "Comando de voz indisponível",
        onVoice, shape = ItemShape, iconSize = 34.dp, enabled = available,
    )
}

/**
 * Selo da Central de notificações: pílula verde com a contagem, como no Android Auto. Sem
 * notificações, um sino discreto (continua abrindo a Central).
 */
@Composable
private fun NotificationBadge(count: Int, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val c = LocalLauncherColors.current
    if (count > 0) {
        Box(
            modifier
                .size(width = Dimens.touch, height = 56.dp)
                .clip(PillShape)
                .pressable(onClickLabel = "Central de notificações", onClick = onClick)
                .semantics { contentDescription = "Central de notificações: $count" },
            contentAlignment = Alignment.Center,
        ) {
            Box(
                Modifier
                    .height(36.dp)
                    .clip(PillShape)
                    .background(c.badge)
                    .padding(horizontal = 12.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(if (count > 9) "9+" else count.toString(), color = c.onBadge, fontSize = 20.sp, fontWeight = FontWeight.Medium)
            }
        }
    } else {
        TouchIconButton(
            LauncherIcons.Bell, "Central de notificações", onClick, modifier = modifier,
            size = 56.dp, shape = ItemShape, iconSize = 26.dp, tint = c.onSurfaceVariant,
        )
    }
}

/** App da barra: toque abre; toque longo abre o menu (spec 009); ponto = fixado (R9.4). */
@Composable
private fun RailApp(
    pkg: String,
    active: Boolean,
    apps: AppsRepository,
    menu: AppMenuActions,
    onLaunchApp: (String) -> Unit,
    menuOffset: DpOffset = DpOffset(84.dp, (-76).dp),
) {
    val c = LocalLauncherColors.current
    var menuOpen by remember { mutableStateOf(false) }
    val label = apps.label(pkg)
    val bg by animateColorAsState(if (active) c.activeBg else Color.Transparent, tween(200), label = "railActive")
    Box(
        Modifier
            .size(Dimens.touch)
            .clip(ItemShape)
            .background(bg)
            .pressable(
                onClickLabel = label,
                onLongClickLabel = "Opções de $label",
                onLongClick = { menuOpen = true },
                onClick = { onLaunchApp(pkg) },
            )
            .semantics { contentDescription = label },
        contentAlignment = Alignment.Center,
    ) {
        AppIcon(apps, pkg, Dimens.dockIcon)
        if (pkg in menu.pinned) {
            Box(
                Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 2.dp)
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(c.onSurface),
            )
        }
        AppContextMenu(pkg, menuOpen, { menuOpen = false }, menu, offset = menuOffset)
    }
}

/** Barras de sinal (0–4) + tipo de rede, como na barra de status do Android. */
@Composable
private fun SignalIndicator(level: Int?, label: String?) {
    val c = LocalLauncherColors.current
    val on = c.onSurfaceVariant
    val off = c.onSurfaceVariant.copy(alpha = 0.3f)
    val lvl = level ?: 0
    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        modifier = Modifier.semantics { contentDescription = "Rede móvel ${label ?: ""}, sinal $lvl de 4" },
    ) {
        if (label != null) {
            Text(label, color = on, fontSize = 10.sp, fontWeight = FontWeight.Bold, lineHeight = 10.sp)
        }
        Canvas(Modifier.size(width = 15.dp, height = 13.dp)) {
            val barW = size.width / 4.6f
            for (i in 0 until 4) {
                val h = size.height * (i + 1) / 4f
                drawRoundRect(
                    color = if (i < lvl) on else off,
                    topLeft = Offset(i * barW * 1.2f, size.height - h),
                    size = Size(barW, h),
                    cornerRadius = CornerRadius(1.5f, 1.5f),
                )
            }
        }
    }
}

/** Elementos de status no topo da barra vertical; toque longo abre o painel de depuração. */
@Composable
private fun StatusBlock(status: StatusState, batteryV: Float?, weather: Weather?, onLongPress: () -> Unit) {
    val c = LocalLauncherColors.current
    Column(
        Modifier
            .width(Dimens.railWidth)
            .pointerInput(Unit) { detectTapGestures(onLongPress = { onLongPress() }) },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        ConnectivityIcons(status)
        Text(clock(status.now), color = c.onSurface, fontSize = 26.sp, fontWeight = FontWeight.Normal, lineHeight = 30.sp)
        if (batteryV != null) {
            Text(volts(batteryV), color = c.onSurfaceVariant, fontSize = 15.sp)
        }
        if (weather != null) WeatherChip(weather)
    }
}

@Composable
private fun ConnectivityIcons(status: StatusState) {
    val c = LocalLauncherColors.current
    Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
        if (status.hasSim || status.cellular) SignalIndicator(status.signalLevel, status.networkLabel)
        if (status.wifi) Icon(LauncherIcons.Wifi, "Wi-Fi", Modifier.size(16.dp), tint = c.onSurface)
        if (status.bluetooth) Icon(LauncherIcons.Bluetooth, "Bluetooth", Modifier.size(16.dp), tint = c.onSurface)
    }
}

@Composable
private fun WeatherChip(weather: Weather) {
    val c = LocalLauncherColors.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.semantics { contentDescription = "Clima: ${weather.temperature} graus" },
    ) {
        Icon(weatherIcon(weather.kind, weather.isDay), null, Modifier.size(20.dp), tint = c.onSurfaceVariant)
        Text("${weather.temperature}°", color = c.onSurfaceVariant, fontSize = 18.sp)
    }
}

private fun clock(now: Long) = SimpleDateFormat("HH:mm", Locale("pt", "BR")).format(Date(now))
private fun volts(v: Float) = String.format(Locale("pt", "BR"), "%.1f V", v)

/**
 * Barra horizontal embaixo (spec 014), como a do Android Auto: grade de apps e voz à esquerda,
 * apps no centro, selo de notificações e status à direita. Com o motorista à direita, a ordem
 * se inverte (os itens importantes ficam perto dele). 88 dp de altura.
 */
@Composable
fun BottomBar(
    screen: Screen,
    status: StatusState,
    batteryV: Float?,
    weather: Weather?,
    railApps: List<String>,
    activePackage: String?,
    apps: AppsRepository,
    notificationCount: Int,
    voiceAvailable: Boolean,
    showBell: Boolean,
    showVoice: Boolean,
    menu: AppMenuActions,
    onDashboard: () -> Unit,
    onApps: () -> Unit,
    onLaunchApp: (String) -> Unit,
    onNotifications: () -> Unit,
    onVoice: () -> Unit,
    onClockLongPress: () -> Unit,
    appsAnchor: DrawerAnchor? = null,
    mirrored: Boolean = false,
    modifier: Modifier = Modifier,
) {
    val c = LocalLauncherColors.current
    val outer = LocalLayoutDirection.current
    CompositionLocalProvider(LocalLayoutDirection provides if (mirrored) LayoutDirection.Rtl else LayoutDirection.Ltr) {
        Row(
            modifier
                .fillMaxWidth()
                .height(BOTTOM_BAR_HEIGHT)
                .background(c.rail)
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                LauncherButton(screen, onDashboard, onApps, appsAnchor)
                if (showVoice) VoiceButton(voiceAvailable, onVoice)
            }
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                railApps.forEach { pkg -> RailApp(pkg, pkg == activePackage, apps, menu, onLaunchApp, menuOffset = DpOffset(0.dp, (-380).dp)) }
            }
            Row(
                Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.End),
            ) {
                if (showBell) NotificationBadge(notificationCount, onNotifications)
                // O texto do status mantém a direção normal mesmo com a barra espelhada.
                CompositionLocalProvider(LocalLayoutDirection provides outer) {
                    Column(
                        Modifier.pointerInput(Unit) { detectTapGestures(onLongPress = { onClockLongPress() }) },
                        horizontalAlignment = if (mirrored) Alignment.Start else Alignment.End,
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
                            if (weather != null) WeatherChip(weather)
                            ConnectivityIcons(status)
                            if (batteryV != null) Text(volts(batteryV), color = c.onSurfaceVariant, fontSize = 14.sp)
                        }
                        Text(clock(status.now), color = c.onSurface, fontSize = 28.sp, fontWeight = FontWeight.Normal)
                    }
                }
            }
        }
    }
}

val BOTTOM_BAR_HEIGHT = 88.dp
