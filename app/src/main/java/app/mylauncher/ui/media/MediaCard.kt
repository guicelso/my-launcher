package app.mylauncher.ui.media

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.mylauncher.data.apps.AppsRepository
import app.mylauncher.data.media.MediaUiState
import app.mylauncher.data.media.browse.BrowseItem
import app.mylauncher.ui.components.AppIcon
import app.mylauncher.ui.components.LauncherCard
import app.mylauncher.ui.components.PillShape
import app.mylauncher.ui.components.pressable
import app.mylauncher.ui.icons.LauncherIcons
import app.mylauncher.ui.theme.Dimens
import app.mylauncher.ui.theme.LocalLauncherColors
import app.mylauncher.ui.workspace.sharedMotion

private val OnCardMuted = Color(0xFFD0D3D1)

/**
 * Card de mídia do painel (wireframes MxHome e MxHomeSuggest, spec 018 R18.19): capa desfocada no
 * fundo, capa de 128 dp, título em até 2 linhas, barra fina na cor do app, controles com o tocar que
 * muda de forma e as ações do app (aleatório, repetir, curtir). Sem nada tocando, as sugestões do
 * app tocam sem abrir o app. Adapta-se à altura (pilhas de widgets, spec 017).
 */
@Composable
fun MediaCard(
    state: MediaUiState,
    apps: AppsRepository,
    musicApp: String,
    label: (String) -> String,
    suggestions: List<BrowseItem>,
    onOpenMedia: () -> Unit,
    onOpenSources: () -> Unit,
    onOpenApp: (String) -> Unit,
    onPlaySuggestion: (BrowseItem) -> Unit,
    onGrantAccess: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val c = LocalLauncherColors.current
    val np = state.nowPlaying
    val shape = RoundedCornerShape(Dimens.cardRadius)
    if (np == null && suggestions.isEmpty()) {
        EmptyCard(state.listenerGranted, apps, musicApp, onOpenApp, onGrantAccess, modifier.sharedMotion("media-card", container = true, clip = shape))
        return
    }
    val pkg = np?.sourcePackage ?: musicApp
    val accent = rememberAccent(pkg)
    var index by remember(suggestions) { mutableIntStateOf(0) }
    val suggestion = suggestions.getOrNull(index)
    val art = np?.art
    val key = np?.artKey() ?: "sug|${suggestion?.id}"
    Box(modifier.clip(shape).sharedMotion("media-card", container = true, clip = shape)) {
        BlurredArtBackdrop(art ?: suggestion?.iconBitmap, key, scrim = 0.6f, modifier = Modifier.fillMaxSize(), fallback = c.surface)
        BoxWithConstraints(Modifier.fillMaxSize()) {
            val tall = maxHeight >= 400.dp
            val medium = maxHeight >= 300.dp
            Column(Modifier.fillMaxSize().padding(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(Modifier.height(48.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Row(
                        Modifier.height(48.dp).clip(CircleShape).pressable(onClickLabel = "Trocar app de mídia", onClick = onOpenSources).padding(start = 4.dp, end = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        AppIcon(apps, pkg, 36.dp)
                        Text(label(pkg), fontSize = 22.sp, color = OnArtMedia.text, maxLines = 1, overflow = TextOverflow.Ellipsis, modifier = Modifier.width(220.dp))
                    }
                    Spacer(Modifier.weight(1f))
                    RoundIcon(LauncherIcons.Expand, "Abrir o player", onOpenMedia, size = 64.dp, iconSize = 28.dp, tint = OnArtMedia.text)
                }
                if (np != null) {
                    Row(
                        Modifier.fillMaxWidth().clip(RoundedCornerShape(18.dp)).pressable(onClickLabel = "Abrir o player", onClick = onOpenMedia),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(18.dp),
                    ) {
                        Box(Modifier.sharedMotion("media-art")) { NowPlayingArt(np, if (medium) 128.dp else 96.dp, 18.dp) }
                        Column(Modifier.weight(1f).sharedMotion("media-titles"), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(np.title.ifBlank { "Sem título" }, fontSize = 26.sp, lineHeight = 31.sp, color = OnArtMedia.text, maxLines = 2, overflow = TextOverflow.Ellipsis)
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Text(np.artist, fontSize = 21.sp, color = OnCardMuted, maxLines = 1, overflow = TextOverflow.Ellipsis, modifier = Modifier.weight(1f, fill = false))
                                if (np.explicit) ExplicitBadge(20.dp)
                            }
                        }
                    }
                    val position = rememberPosition(np)
                    Box(Modifier.padding(vertical = 4.dp).sharedMotion("media-progress")) {
                        if (np.hasProgress) ThinProgress(np.fraction(position), accent, Color.White.copy(alpha = 0.28f), rounded = true) else Spacer(Modifier.height(4.dp))
                    }
                    val controls = state.controls
                    Row(Modifier.fillMaxWidth().padding(horizontal = 8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        RoundIcon(LauncherIcons.Previous, "Faixa anterior", { controls?.previous() }, size = 80.dp, iconSize = 40.dp, tint = OnArtMedia.text, enabled = np.canPrevious)
                        ShapePlayButton(
                            np.playing, np.buffering, 112.dp, 88.dp, 24.dp, 44.dp, 44.dp,
                            onClick = { if (np.playing) controls?.pause() else controls?.play() },
                            modifier = Modifier.sharedMotion("media-play"),
                            background = NightMedia.play, color = NightMedia.onPlay,
                        )
                        RoundIcon(LauncherIcons.Next, "Próxima faixa", { controls?.next() }, size = 80.dp, iconSize = 40.dp, tint = OnArtMedia.text, enabled = np.canNext)
                    }
                    if (tall) {
                        // Ações do app no card (redesenho de 2026): até 3, aleatório/repetir/curtir primeiro.
                        val extras = np.customActions.sortedBy { a ->
                            val t = (a.id + a.label).lowercase()
                            when {
                                "aleat" in t || "shuffle" in t -> 0
                                "repet" in t || "repeat" in t -> 1
                                "curt" in t || "like" in t -> 2
                                else -> 3
                            }
                        }.take(3)
                        if (extras.isNotEmpty()) {
                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                                extras.forEach { a ->
                                    Box(
                                        Modifier.size(76.dp, 64.dp).clip(CircleShape).background(if (a.active) Color.White.copy(alpha = 0.16f) else Color.Transparent)
                                            .pressable(onClickLabel = a.label) { controls?.customAction(a) },
                                        contentAlignment = Alignment.Center,
                                    ) { ActionGlyphView(a, 30.dp, OnArtMedia.text, 20.sp) }
                                }
                            }
                        }
                    }
                } else if (suggestion != null) {
                    Text("Continue ouvindo", fontSize = 20.sp, color = OnCardMuted)
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(18.dp)) {
                        ArtBox(suggestion.iconBitmap, suggestion.iconUri, suggestion.title, if (medium) 150.dp else 96.dp, 18.dp, shadow = true)
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(suggestion.title, fontSize = 26.sp, color = OnArtMedia.text, maxLines = 2, overflow = TextOverflow.Ellipsis)
                            if (suggestion.subtitle.isNotBlank()) Text(suggestion.subtitle, fontSize = 20.sp, color = OnCardMuted, maxLines = 1, overflow = TextOverflow.Ellipsis)
                        }
                    }
                    Row(Modifier.fillMaxWidth().padding(top = 4.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Row(
                            Modifier.weight(1f).height(76.dp).clip(CircleShape).background(NightMedia.play).pressable(onClickLabel = "Tocar ${suggestion.title}") { onPlaySuggestion(suggestion) },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
                        ) {
                            Icon(LauncherIcons.Play, null, Modifier.size(30.dp), tint = NightMedia.onPlay)
                            Text("Tocar", fontSize = 24.sp, fontWeight = FontWeight.Medium, color = NightMedia.onPlay)
                        }
                        if (suggestions.size > 1) {
                            RoundIcon(LauncherIcons.Chevron, "Próxima sugestão", { index = (index + 1) % suggestions.size }, iconSize = 30.dp, tint = OnArtMedia.text, background = Color.White.copy(alpha = 0.14f))
                        }
                    }
                    if (suggestions.size > 1) {
                        Spacer(Modifier.weight(1f))
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)) {
                            suggestions.indices.take(MAX_DOTS).forEach { i ->
                                Box(Modifier.size(if (i == index % MAX_DOTS) 22.dp else 8.dp, 8.dp).clip(CircleShape).background(if (i == index % MAX_DOTS) Color.White else Color.White.copy(alpha = 0.4f)))
                            }
                        }
                    }
                }
            }
        }
    }
}

private const val MAX_DOTS = 5

@Composable
private fun EmptyCard(granted: Boolean, apps: AppsRepository, musicApp: String, onOpenApp: (String) -> Unit, onGrantAccess: () -> Unit, modifier: Modifier) {
    val c = LocalLauncherColors.current
    LauncherCard(modifier) {
        Column(
            Modifier.fillMaxSize().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        ) {
            Icon(LauncherIcons.Music, null, Modifier.size(44.dp), tint = c.onSurfaceVariant)
            if (!granted) {
                Text(
                    "Permita o acesso às notificações para ver o que está tocando",
                    style = MaterialTheme.typography.bodyLarge, color = c.onSurfaceVariant, textAlign = TextAlign.Center,
                )
                PillButton("Permitir acesso", onClick = onGrantAccess)
            } else {
                Text("Nada tocando", style = MaterialTheme.typography.titleLarge, color = c.onSurfaceVariant)
                if (apps.isInstalled(musicApp)) PillButton("Abrir ${apps.label(musicApp)}") { onOpenApp(musicApp) }
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
            .background(if (primary) c.primaryContainer else c.surface2)
            .pressable(onClick = onClick)
            .padding(horizontal = 26.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(label, style = MaterialTheme.typography.labelLarge, color = if (primary) c.onPrimaryContainer else c.onSurface)
    }
}
