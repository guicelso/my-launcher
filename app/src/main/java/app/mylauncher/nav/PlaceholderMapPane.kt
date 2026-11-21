package app.mylauncher.nav

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.mylauncher.ui.icons.LauncherIcons
import app.mylauncher.ui.theme.LocalLauncherColors

/** Painel provisório até o mapa próprio (spec 005). */
class PlaceholderMapPane : NavigationPane {
    @Composable
    override fun Content(mode: PaneMode, modifier: Modifier, onExpand: () -> Unit) {
        val c = LocalLauncherColors.current
        Box(modifier.background(c.surface2).clickable(onClick = onExpand), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(LauncherIcons.Locate, null, Modifier.size(44.dp), tint = c.primary)
                Text("Mapa", style = MaterialTheme.typography.titleLarge, color = c.onSurfaceVariant)
            }
        }
    }
}
