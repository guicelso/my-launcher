package app.mylauncher.ui.media

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import app.mylauncher.data.apps.AppsRepository
import app.mylauncher.data.media.MediaUiState
import app.mylauncher.data.media.RepeatMode
import app.mylauncher.data.media.formatTime
import app.mylauncher.ui.components.AppIcon
import app.mylauncher.ui.components.PillShape
import app.mylauncher.ui.components.ScreenPanel
import app.mylauncher.ui.components.TouchIconButton
import app.mylauncher.ui.icons.LauncherIcons
import app.mylauncher.ui.theme.LocalLauncherColors

/** Tela de mídia (spec 004, R4.5). Artboard "Mídia". */
@Composable
fun MediaScreen(
    state: MediaUiState,
    sources: List<String>,
    apps: AppsRepository,
    onOpenApp: (String) -> Unit,
    onGrantAccess: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val c = LocalLauncherColors.current
    val np = state.nowPlaying
    var showQueue by rememberSaveable { mutableStateOf(false) }

    ScreenPanel(modifier) {
        Column(Modifier.fillMaxSize().padding(start = 24.dp, top = 16.dp, end = 32.dp, bottom = 24.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(
                    Modifier.weight(1f).horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    sources.forEach { pkg ->
                        val selected = pkg == np?.sourcePackage
                        Row(
                            Modifier
                                .height(64.dp)
                                .clip(PillShape)
                                .background(if (selected) c.onSurface else c.surface2)
                                .clickable(role = Role.Tab) { if (!selected) onOpenApp(pkg) }
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
                if (np != null) {
                    TouchIconButton(LauncherIcons.Search, "Navegar na biblioteca", { onOpenApp(np.sourcePackage) }, iconSize = 32.dp)
                    if (np.queue.isNotEmpty()) {
                        TouchIconButton(
                            LauncherIcons.Queue, "Fila de reprodução", { showQueue = !showQueue },
                            iconSize = 32.dp, background = if (showQueue) c.surface2 else androidx.compose.ui.graphics.Color.Transparent,
                        )
                    }
                }
            }

            if (np == null) {
                Column(
                    Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
                ) {
                    AlbumArt(null, 200.dp, 24.dp, 72.dp)
                    Text(
                        if (state.listenerGranted) "Nada tocando. Escolha uma fonte acima." else "Acesso às notificações necessário",
                        style = MaterialTheme.typography.titleLarge, color = c.onSurfaceVariant,
                    )
                    if (!state.listenerGranted) PillButton("Permitir acesso", onClick = onGrantAccess)
                }
                return@Column
            }

            val controls = state.controls
            val position = rememberPosition(np)
            Row(Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(40.dp)) {
                AlbumArt(np, 440.dp, 24.dp, 96.dp)
                if (showQueue) {
                    LazyColumn(Modifier.weight(1f).fillMaxSize().padding(vertical = 16.dp)) {
                        items(np.queue, key = { it.id }) { item ->
                            Column(
                                Modifier
                                    .fillMaxWidth()
                                    .height(84.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .clickable { controls?.playQueueItem(item.id) }
                                    .padding(horizontal = 16.dp),
                                verticalArrangement = Arrangement.Center,
                            ) {
                                Text(item.title, style = MaterialTheme.typography.titleMedium, color = c.onSurface, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                Text(item.subtitle, style = MaterialTheme.typography.bodyMedium, color = c.onSurfaceVariant, maxLines = 1, overflow = TextOverflow.Ellipsis)
                            }
                        }
                    }
                } else {
                    Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(28.dp)) {
                        Column {
                            Text(
                                np.title.ifBlank { "Sem título" }, style = MaterialTheme.typography.displaySmall, color = c.onSurface,
                                maxLines = 2, overflow = TextOverflow.Ellipsis,
                            )
                            Text(
                                listOf(np.artist, np.album).filter { it.isNotBlank() }.joinToString(" · "),
                                style = MaterialTheme.typography.headlineSmall, color = c.onSurfaceVariant,
                                maxLines = 1, overflow = TextOverflow.Ellipsis, modifier = Modifier.padding(top = 10.dp),
                            )
                        }
                        Column {
                            ProgressBar(np.fraction(position), height = 6.dp)
                            Row(Modifier.fillMaxWidth().padding(top = 12.dp)) {
                                Text(formatTime(position), style = MaterialTheme.typography.bodyMedium, color = c.onSurfaceVariant)
                                Spacer(Modifier.weight(1f))
                                if (np.durationMs > 0) {
                                    Text(formatTime(np.durationMs), style = MaterialTheme.typography.bodyMedium, color = c.onSurfaceVariant)
                                }
                            }
                        }
                        Row(
                            Modifier.widthIn(max = 560.dp).fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            TouchIconButton(
                                LauncherIcons.Shuffle, "Aleatório", { controls?.toggleShuffle() },
                                iconSize = 32.dp, enabled = np.shuffle != null,
                                tint = if (np.shuffle == true) c.primary else c.onSurfaceVariant,
                            )
                            TouchIconButton(LauncherIcons.Previous, "Faixa anterior", { controls?.previous() }, iconSize = 44.dp, enabled = np.canSkip)
                            TouchIconButton(
                                if (np.playing) LauncherIcons.Pause else LauncherIcons.Play,
                                if (np.playing) "Pausar" else "Tocar",
                                { if (np.playing) controls?.pause() else controls?.play() },
                                size = 96.dp, iconSize = 44.dp, background = c.primary, tint = c.onPrimary, shape = CircleShape,
                            )
                            TouchIconButton(LauncherIcons.Next, "Próxima faixa", { controls?.next() }, iconSize = 44.dp, enabled = np.canSkip)
                            Box {
                                TouchIconButton(
                                    LauncherIcons.Repeat, "Repetir", { controls?.cycleRepeat() },
                                    iconSize = 32.dp, enabled = np.repeat != null,
                                    tint = if (np.repeat != null && np.repeat != RepeatMode.OFF) c.primary else c.onSurfaceVariant,
                                )
                                if (np.repeat == RepeatMode.ONE) {
                                    Text("1", style = MaterialTheme.typography.labelSmall, color = c.primary, modifier = Modifier.align(Alignment.TopEnd).padding(top = 14.dp, end = 16.dp))
                                }
                            }
                        }
                    }
                }
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
