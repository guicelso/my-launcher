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
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.mylauncher.data.apps.AppsRepository
import app.mylauncher.data.status.StatusState
import app.mylauncher.ui.Screen
import app.mylauncher.ui.components.AppContextMenu
import app.mylauncher.ui.components.AppIcon
import app.mylauncher.ui.components.AppMenuActions
import app.mylauncher.ui.components.TouchIconButton
import app.mylauncher.ui.components.pressable
import app.mylauncher.ui.drawer.DrawerAnchor
import app.mylauncher.ui.icons.LauncherIcons
import app.mylauncher.ui.theme.Dimens
import app.mylauncher.ui.theme.LocalLauncherColors
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val ItemShape = RoundedCornerShape(24.dp)

/**
 * Barra lateral (spec 002, R2.1–R2.5): relógio + status, Painel, 3 apps, Apps,
 * espaço, Notificações e Voz. Artboard "Barra lateral" em design/wireframes.
 */
@Composable
fun Rail(
    screen: Screen,
    status: StatusState,
    batteryV: Float?,
    railApps: List<String>,
    activePackage: String?,
    apps: AppsRepository,
    hasNotifications: Boolean,
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
    modifier: Modifier = Modifier,
) {
    val c = LocalLauncherColors.current
    Column(
        modifier
            .width(Dimens.railWidth)
            .fillMaxHeight()
            .background(c.rail)
            .padding(top = 14.dp, bottom = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Clock(status, batteryV, onClockLongPress)

        RailButton(LauncherIcons.Dashboard, "Painel", screen == Screen.DASHBOARD, onDashboard)

        railApps.forEach { pkg ->
            RailApp(pkg, pkg == activePackage, apps, menu, onLaunchApp)
        }

        RailButton(
            LauncherIcons.Apps, "Todos os apps", screen == Screen.APPS, onApps,
            Modifier.onGloballyPositioned { appsAnchor?.bounds = it.boundsInRoot() },
        )

        Spacer(Modifier.weight(1f))

        if (showBell) {
            Box(contentAlignment = Alignment.Center) {
                TouchIconButton(
                    LauncherIcons.Bell, "Notificações", onNotifications,
                    shape = ItemShape, iconSize = 32.dp, tint = c.onSurfaceVariant,
                )
                if (hasNotifications) {
                    Box(
                        Modifier
                            .offset(x = 12.dp, y = (-14).dp)
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(c.primary),
                    )
                }
            }
        }
        if (showVoice) {
            TouchIconButton(
                LauncherIcons.Mic, if (voiceAvailable) "Comando de voz" else "Comando de voz indisponível",
                onVoice, shape = ItemShape, iconSize = 34.dp, enabled = voiceAvailable,
            )
        }
    }
}

/** App da barra: toque abre; toque longo abre o menu (spec 009); ponto = fixado (R9.4). */
@Composable
private fun RailApp(pkg: String, active: Boolean, apps: AppsRepository, menu: AppMenuActions, onLaunchApp: (String) -> Unit) {
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
        AppIcon(apps, pkg, 56.dp)
        if (pkg in menu.pinned) {
            Box(
                Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 2.dp)
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(c.onSurfaceVariant),
            )
        }
        AppContextMenu(pkg, menuOpen, { menuOpen = false }, menu, offset = DpOffset(84.dp, (-76).dp))
    }
}

@Composable
private fun RailButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    active: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val c = LocalLauncherColors.current
    TouchIconButton(
        icon, label, onClick, modifier = modifier,
        shape = ItemShape,
        background = if (active) c.activeBg else Color.Transparent,
        tint = if (active) c.primary else c.onSurfaceVariant,
    )
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

@Composable
private fun Clock(status: StatusState, batteryV: Float?, onLongPress: () -> Unit) {
    val c = LocalLauncherColors.current
    val time = SimpleDateFormat("HH:mm", Locale("pt", "BR")).format(Date(status.now))
    Column(
        Modifier
            .height(if (batteryV != null) 84.dp else 64.dp)
            .pointerInput(Unit) { detectTapGestures(onLongPress = { onLongPress() }) },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(time, color = c.onSurface, fontSize = 24.sp, fontWeight = FontWeight.Medium)
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.padding(top = 4.dp)) {
            if (status.hasSim || status.cellular) SignalIndicator(status.signalLevel, status.networkLabel)
            if (status.wifi) Icon(LauncherIcons.Wifi, "Wi-Fi", Modifier.size(16.dp), tint = c.onSurfaceVariant)
            if (status.bluetooth) Icon(LauncherIcons.Bluetooth, "Bluetooth", Modifier.size(16.dp), tint = c.onSurfaceVariant)
        }
        if (batteryV != null) {
            Text(
                String.format(Locale("pt", "BR"), "%.1f V", batteryV),
                color = c.onSurfaceVariant, fontSize = 14.sp, modifier = Modifier.padding(top = 4.dp),
            )
        }
    }
}

/**
 * Barra horizontal embaixo (spec 014), no estilo da taskbar do Android Auto: gaveta e voz à
 * esquerda, painel e apps no centro, notificações, sinal e relógio à direita. 96 dp de altura.
 */
@Composable
fun BottomBar(
    screen: Screen,
    status: StatusState,
    batteryV: Float?,
    railApps: List<String>,
    activePackage: String?,
    apps: AppsRepository,
    hasNotifications: Boolean,
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
    modifier: Modifier = Modifier,
) {
    val c = LocalLauncherColors.current
    Row(
        modifier
            .fillMaxWidth()
            .height(BOTTOM_BAR_HEIGHT)
            .background(c.rail)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        RailButton(
            LauncherIcons.Apps, "Todos os apps", screen == Screen.APPS, onApps,
            Modifier.onGloballyPositioned { appsAnchor?.bounds = it.boundsInRoot() },
        )
        if (showVoice) {
            TouchIconButton(
                LauncherIcons.Mic, if (voiceAvailable) "Comando de voz" else "Comando de voz indisponível",
                onVoice, shape = ItemShape, iconSize = 34.dp, enabled = voiceAvailable,
            )
        }
        Spacer(Modifier.weight(1f))
        RailButton(LauncherIcons.Dashboard, "Painel", screen == Screen.DASHBOARD, onDashboard)
        railApps.forEach { pkg -> RailApp(pkg, pkg == activePackage, apps, menu, onLaunchApp) }
        Spacer(Modifier.weight(1f))
        if (showBell) {
            Box(contentAlignment = Alignment.Center) {
                TouchIconButton(LauncherIcons.Bell, "Notificações", onNotifications, shape = ItemShape, iconSize = 32.dp, tint = c.onSurfaceVariant)
                if (hasNotifications) {
                    Box(Modifier.offset(x = 12.dp, y = (-14).dp).size(10.dp).clip(CircleShape).background(c.primary))
                }
            }
        }
        Column(
            Modifier.pointerInput(Unit) { detectTapGestures(onLongPress = { onClockLongPress() }) }.padding(start = 8.dp),
            horizontalAlignment = Alignment.End,
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
                if (status.hasSim || status.cellular) SignalIndicator(status.signalLevel, status.networkLabel)
                if (status.wifi) Icon(LauncherIcons.Wifi, "Wi-Fi", Modifier.size(16.dp), tint = c.onSurfaceVariant)
                if (status.bluetooth) Icon(LauncherIcons.Bluetooth, "Bluetooth", Modifier.size(16.dp), tint = c.onSurfaceVariant)
                if (batteryV != null) Text(String.format(Locale("pt", "BR"), "%.1f V", batteryV), color = c.onSurfaceVariant, fontSize = 13.sp)
            }
            Text(SimpleDateFormat("HH:mm", Locale("pt", "BR")).format(Date(status.now)), color = c.onSurface, fontSize = 24.sp, fontWeight = FontWeight.Medium)
        }
    }
}

val BOTTOM_BAR_HEIGHT = 96.dp
