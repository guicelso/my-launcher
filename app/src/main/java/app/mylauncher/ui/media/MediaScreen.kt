package app.mylauncher.ui.media

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import app.mylauncher.data.apps.AppsRepository
import app.mylauncher.data.media.MediaUiState
import app.mylauncher.ui.components.AppIcon
import app.mylauncher.ui.components.PillShape
import app.mylauncher.ui.components.ScreenPanel
import app.mylauncher.ui.components.pressable
import app.mylauncher.ui.theme.LocalLauncherColors

/** Mídia em tela cheia (spec 004 R4.5 + spec 010): abas de fonte + player grande. */
@Composable
fun MediaScreen(
    state: MediaUiState,
    sources: List<String>,
    apps: AppsRepository,
    onSelectSource: (String) -> Unit,
    onOpenApp: (String) -> Unit,
    onGrantAccess: () -> Unit,
    onCollapse: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ScreenPanel(modifier) {
        Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            SourceTabs(sources, state.nowPlaying?.sourcePackage, apps, onSelectSource)
            PlayerPanel(
                state = state,
                apps = apps,
                full = true,
                onToggleSize = onCollapse,
                onOpenApp = onOpenApp,
                onGrantAccess = onGrantAccess,
                modifier = Modifier.fillMaxWidth().weight(1f),
            )
        }
    }
}

@Composable
private fun SourceTabs(sources: List<String>, current: String?, apps: AppsRepository, onSelect: (String) -> Unit) {
    val c = LocalLauncherColors.current
    Row(
        Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        sources.forEach { pkg ->
            val selected = pkg == current
            Row(
                Modifier
                    .height(64.dp)
                    .clip(PillShape)
                    .background(if (selected) c.onSurface else c.surface2)
                    .pressable(role = Role.Tab) { if (!selected) onSelect(pkg) }
                    .semantics { this.selected = selected }
                    .padding(start = 10.dp, end = 22.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                AppIcon(apps, pkg, 40.dp)
                Text(apps.label(pkg), style = MaterialTheme.typography.labelLarge, color = if (selected) c.background else c.onSurface)
            }
        }
    }
}

/** Fontes exibidas no seletor: app de música padrão + apps de mídia conhecidos instalados. */
object MediaSources {
    val KNOWN = listOf(
        "com.spotify.music",
        "com.google.android.apps.youtube.music",
        "com.soundcloud.android",
        "com.tw.radio",
        "com.tw.music",
        "com.tw.bt",
    )

    fun list(musicApp: String, current: String?, isInstalled: (String) -> Boolean): List<String> =
        (listOfNotNull(current, musicApp) + KNOWN).distinct().filter(isInstalled)
}
