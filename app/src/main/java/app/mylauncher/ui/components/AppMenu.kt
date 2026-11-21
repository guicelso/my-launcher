package app.mylauncher.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import app.mylauncher.data.prefs.LauncherSettings
import app.mylauncher.ui.icons.LauncherIcons
import app.mylauncher.ui.theme.LocalLauncherColors

/** Ações do menu de contexto de um app (spec 009). */
data class AppMenuActions(
    val pinned: List<String>,
    val recents: List<String>,
    val label: (String) -> String,
    val onPin: (String) -> Unit,
    val onUnpin: (String) -> Unit,
    val onRemoveRecent: (String) -> Unit,
    val onAppInfo: (String) -> Unit,
)

/**
 * Menu de toque longo (R9.1–R9.5): fixar/desafixar, remover dos recentes, informações do app.
 * Abre ao lado do ícone (deslocamento [offset]).
 */
@Composable
fun AppContextMenu(
    pkg: String,
    expanded: Boolean,
    onDismiss: () -> Unit,
    actions: AppMenuActions,
    offset: DpOffset = DpOffset(8.dp, 0.dp),
    showRemoveRecent: Boolean = true,
) {
    val c = LocalLauncherColors.current
    val pinned = pkg in actions.pinned
    val full = actions.pinned.size >= LauncherSettings.MAX_RAIL_APPS
    // Popup sem foco: não tira o launcher do modo imersivo (as barras do sistema não aparecem).
    BackHandler(enabled = expanded, onBack = onDismiss)
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismiss,
        offset = offset,
        properties = PopupProperties(focusable = false, dismissOnClickOutside = true),
        shape = RoundedCornerShape(24.dp),
        containerColor = c.surface,
        shadowElevation = 12.dp,
        modifier = Modifier.widthIn(min = 320.dp),
    ) {
        Text(
            actions.label(pkg),
            style = MaterialTheme.typography.labelLarge,
            color = c.onSurfaceVariant,
            modifier = Modifier.padding(start = 20.dp, top = 12.dp, end = 20.dp, bottom = 4.dp),
        )
        if (pinned) {
            Item("Desafixar da barra", LauncherIcons.Close) { actions.onUnpin(pkg); onDismiss() }
        } else {
            Item(
                if (full) "Fixar (máximo de ${LauncherSettings.MAX_RAIL_APPS})" else "Fixar na barra",
                LauncherIcons.Pin,
                enabled = !full,
            ) { actions.onPin(pkg); onDismiss() }
        }
        if (showRemoveRecent && !pinned && pkg in actions.recents) {
            Item("Remover dos recentes", LauncherIcons.Minus) { actions.onRemoveRecent(pkg); onDismiss() }
        }
        Item("Informações do app", LauncherIcons.Settings) { actions.onAppInfo(pkg); onDismiss() }
    }
}

@Composable
private fun Item(label: String, icon: ImageVector, enabled: Boolean = true, onClick: () -> Unit) {
    val c = LocalLauncherColors.current
    DropdownMenuItem(
        text = { Text(label, style = MaterialTheme.typography.titleMedium, color = if (enabled) c.onSurface else c.onSurfaceVariant) },
        leadingIcon = { Icon(icon, null, Modifier.size(28.dp), tint = if (enabled) c.primary else c.onSurfaceVariant) },
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier.height(76.dp),
    )
}
