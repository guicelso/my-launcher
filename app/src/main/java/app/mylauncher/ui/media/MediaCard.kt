package app.mylauncher.ui.media

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import app.mylauncher.data.apps.AppsRepository
import app.mylauncher.data.media.MediaUiState
import app.mylauncher.ui.components.AppIcon
import app.mylauncher.ui.components.LauncherCard
import app.mylauncher.ui.components.PillShape
import app.mylauncher.ui.components.pressable
import app.mylauncher.ui.components.TouchIconButton
import app.mylauncher.ui.icons.LauncherIcons
import app.mylauncher.ui.theme.LocalLauncherColors
import app.mylauncher.ui.workspace.sharedMotion

/** Card de mídia do painel (spec 004, R4.3/R4.7). */
@Composable
fun MediaCard(
    state: MediaUiState,
    apps: AppsRepository,
    musicApp: String,
    onOpenMedia: () -> Unit,
    onOpenApp: (String) -> Unit,
    onLaunchApp: (String) -> Unit,
    onGrantAccess: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val c = LocalLauncherColors.current
    val np = state.nowPlaying
    val shape = RoundedCornerShape(28.dp)
    if (np == null) {
        LauncherCard(modifier.sharedMotion("media-card", container = true, clip = shape)) {
            Column(
                Modifier.fillMaxSize().padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            ) {
                Icon(LauncherIcons.Music, null, Modifier.size(44.dp), tint = c.onSurfaceVariant)
                if (!state.listenerGranted) {
                    Text(
                        "Permita o acesso às notificações para ver o que está tocando",
                        style = MaterialTheme.typography.bodyLarge, color = c.onSurfaceVariant, textAlign = TextAlign.Center,
                    )
                    PillButton("Permitir acesso", onClick = onGrantAccess)
                } else {
                    Text("Nada tocando", style = MaterialTheme.typography.titleLarge, color = c.onSurfaceVariant)
                    if (apps.isInstalled(musicApp)) {
                        PillButton("Abrir ${apps.label(musicApp)}") { onOpenApp(musicApp) }
                    }
                }
            }
        }
        return
    }
    val controls = state.controls
    val position = rememberPosition(np)
    // Capa cobrindo o card, em vidro fosco (estilo Android Auto); título e controles embaixo.
    FrostedArtCard(np, shape, fallback = c.surface, modifier = modifier.sharedMotion("media-card", container = true, clip = shape)) { hasArt ->
        val cc = LocalLauncherColors.current
        Column(Modifier.fillMaxSize().padding(horizontal = 22.dp, vertical = 16.dp)) {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Row(
                    Modifier.weight(1f).clip(PillShape).pressable(onClick = onOpenMedia).padding(end = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    AppIcon(apps, np.sourcePackage, 28.dp)
                    Text(np.sourceLabel, style = MaterialTheme.typography.labelMedium, color = cc.onSurfaceVariant, maxLines = 1)
                }
                // Atalho direto para o app que está tocando.
                TouchIconButton(
                    LauncherIcons.External, "Abrir ${np.sourceLabel}", { onLaunchApp(np.sourcePackage) },
                    size = 64.dp, iconSize = 26.dp, tint = cc.onSurfaceVariant,
                )
            }
            // Área central tocável: abre o player ao lado do mapa.
            Row(
                Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .pressable(onClickLabel = "Abrir player", onClick = onOpenMedia),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                if (!hasArt) {
                    Box(Modifier.sharedMotion("media-art")) { AlbumArt(np, 96.dp, 16.dp, 40.dp) }
                }
                Column(Modifier.weight(1f).sharedMotion("media-titles").padding(bottom = 4.dp)) {
                    Text(
                        np.title.ifBlank { "Sem título" }, style = MaterialTheme.typography.headlineSmall,
                        color = cc.onSurface, maxLines = 2, overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        np.artist, style = MaterialTheme.typography.bodyLarge, color = cc.onSurfaceVariant,
                        maxLines = 1, overflow = TextOverflow.Ellipsis, modifier = Modifier.padding(top = 4.dp),
                    )
                }
            }
            Box(Modifier.padding(vertical = 10.dp).sharedMotion("media-progress")) { ProgressBar(np.fraction(position)) }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                TouchIconButton(LauncherIcons.Previous, "Faixa anterior", { controls?.previous() }, enabled = np.canSkip, tint = cc.onSurface)
                PlayPauseButton(np.playing, size = 76.dp, modifier = Modifier.sharedMotion("media-play")) {
                    if (np.playing) controls?.pause() else controls?.play()
                }
                TouchIconButton(LauncherIcons.Next, "Próxima faixa", { controls?.next() }, enabled = np.canSkip, tint = cc.onSurface)
            }
        }
    }
}

@Composable
fun PillButton(label: String, primary: Boolean = true, onClick: () -> Unit) {
    val c = LocalLauncherColors.current
    Box(
        Modifier
            .height(64.dp)
            .clip(PillShape)
            .background(if (primary) c.primary else c.surface2)
            .pressable(onClick = onClick)
            .padding(horizontal = 26.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(label, style = MaterialTheme.typography.labelLarge, color = if (primary) c.onPrimary else c.onSurface)
    }
}
