package app.mylauncher.ui.rail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.semantics.Role
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
            RailApp(pkg, apps, menu, onLaunchApp)
        }

        RailButton(LauncherIcons.Apps, "Todos os apps", screen == Screen.APPS, onApps)

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
@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun RailApp(pkg: String, apps: AppsRepository, menu: AppMenuActions, onLaunchApp: (String) -> Unit) {
    val c = LocalLauncherColors.current
    var menuOpen by remember { mutableStateOf(false) }
    val label = apps.label(pkg)
    Box(
        Modifier
            .size(Dimens.touch)
            .clip(ItemShape)
            .combinedClickable(
                role = Role.Button,
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
private fun RailButton(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, active: Boolean, onClick: () -> Unit) {
    val c = LocalLauncherColors.current
    TouchIconButton(
        icon, label, onClick,
        shape = ItemShape,
        background = if (active) c.activeBg else Color.Transparent,
        tint = if (active) c.primary else c.onSurfaceVariant,
    )
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
            if (status.cellular) Icon(LauncherIcons.Signal, "Rede móvel", Modifier.size(16.dp), tint = c.onSurfaceVariant)
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
