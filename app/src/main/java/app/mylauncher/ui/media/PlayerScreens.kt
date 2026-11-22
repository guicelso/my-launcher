package app.mylauncher.ui.media

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import app.mylauncher.data.apps.AppsRepository
import app.mylauncher.data.media.ControlSlots
import app.mylauncher.data.media.MediaAction
import app.mylauncher.data.media.MediaControls
import app.mylauncher.data.media.NowPlaying
import app.mylauncher.ui.components.pressable
import app.mylauncher.ui.icons.LauncherIcons
import app.mylauncher.ui.workspace.sharedMotion

/** O que o player precisa saber do app e da navegação. */
data class PlayerContext(
    val pkg: String,
    val label: String,
    val accent: Color,
    /** O app aceitou a navegação do Lumia (senão: modo só player, R18.2). */
    val hasBrowse: Boolean,
    val searchSupported: Boolean,
    val driving: Boolean,
)

/** Ações do player. */
data class PlayerActions(
    val onBrowse: () -> Unit = {},
    val onSearch: () -> Unit = {},
    val onQueue: () -> Unit = {},
    val onSources: () -> Unit = {},
    val onOpenApp: () -> Unit = {},
    val onSubtitleLink: (String) -> Unit = {},
    val onExpand: () -> Unit = {},
    val onMore: () -> Unit = {},
)

private val Meta = Color(0xFFB8BBB9)
private val SubtitleColor = Color(0xFFD6D9D7)

/** Direção da última troca de faixa, para o slide no sentido certo (R10.11). */
private class SkipDir {
    var value by mutableIntStateOf(1)
}

/**
 * Tocando agora em tela cheia (wireframe MxPlayer e variações): capa desfocada no fundo, capa de
 * 300 dp, título em até 2 linhas, subtítulo com link, indicadores, progresso ondulado e a barra de
 * 5 posições com "Mais" (R18.12–R18.16).
 */
@Composable
fun FullPlayer(
    np: NowPlaying,
    controls: MediaControls?,
    apps: AppsRepository,
    ctx: PlayerContext,
    actions: PlayerActions,
    moreOpen: Boolean,
    onMoreChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val layout = ControlSlots.layout(np)
    val open = moreOpen && layout.secondary.isNotEmpty()
    val dir = remember { SkipDir() }
    CompositionLocalProvider(androidx.compose.material3.LocalContentColor provides OnArtMedia.text) {
        Box(modifier.clip(RoundedCornerShape(24.dp)).sharedMotion("media-card", container = true, clip = RoundedCornerShape(24.dp))) {
            BlurredArtBackdrop(np.art, np.artKey(), scrim = 0.62f, modifier = Modifier.fillMaxSize(), fallback = NightMedia.card)
            Column(Modifier.fillMaxSize().padding(start = 12.dp, top = 12.dp, end = 24.dp, bottom = 20.dp)) {
                Row(Modifier.height(88.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                    if (ctx.hasBrowse) {
                        RoundIcon(LauncherIcons.ChevronDown, "Voltar à navegação", actions.onBrowse, iconSize = 34.dp, tint = OnArtMedia.text)
                    } else {
                        Spacer(Modifier.width(0.dp))
                    }
                    AppPill(apps, ctx.pkg, ctx.label, actions.onSources, background = OnArtMedia.chip, color = OnArtMedia.text)
                    Spacer(Modifier.weight(1f))
                    if (ctx.hasBrowse && ctx.searchSupported) {
                        RoundIcon(LauncherIcons.Search, "Buscar", actions.onSearch, iconSize = 32.dp, tint = OnArtMedia.text)
                    }
                    if (!ctx.hasBrowse) {
                        MediaPill("Abrir o app", LauncherIcons.External, actions.onOpenApp, background = Color.Transparent, border = OnArtMedia.outline, color = OnArtMedia.text, iconSize = 26.dp)
                    }
                    if (np.queue.isNotEmpty()) {
                        MediaPill("Fila", LauncherIcons.Queue, actions.onQueue, background = OnArtMedia.chipStrong, color = OnArtMedia.text)
                    }
                }
                if (!ctx.hasBrowse) {
                    Row(
                        Modifier.padding(start = 100.dp).height(48.dp).clip(RoundedCornerShape(24.dp)).background(Color.White.copy(alpha = 0.1f)).padding(horizontal = 20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        Icon(LauncherIcons.Info, null, Modifier.size(22.dp), tint = OnArtMedia.text)
                        Text("Navegação e busca indisponíveis neste app. Os controles funcionam normalmente.", fontSize = 20.sp, color = OnArtMedia.text)
                    }
                }

                val error = np.error
                if (error != null) {
                    MediaMessage(
                        state = errorState(error, ctx.label),
                        accent = ctx.accent,
                        driving = ctx.driving,
                        onPrimary = { runCatching { error.resolutionIntent?.send() } },
                        onSecondary = actions.onSources,
                        modifier = Modifier.weight(1f).fillMaxWidth(),
                        onArt = true,
                    )
                } else {
                    Row(
                        Modifier.weight(1f).fillMaxWidth().padding(start = 64.dp).alpha(if (open) 0.35f else 1f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(48.dp),
                    ) {
                        SlideArt(np, dir, 300.dp, 24.dp, Modifier.sharedMotion("media-art"))
                        BoxWithConstraints(Modifier.weight(1f).padding(end = 40.dp)) {
                            val avail = maxWidth
                            Column(Modifier.sharedMotion("media-titles")) {
                                SlideTitles(np, dir, 40.sp, 48.sp)
                                Subtitle(np, 28.sp, 24.dp, Modifier.padding(top = 10.dp), actions.onSubtitleLink)
                                Indicators(np, 26.dp, 22.sp, Modifier.padding(top = 12.dp), gap = 12.dp)
                                if (np.hasProgress) {
                                    Box(Modifier.padding(top = 28.dp).sharedMotion("media-progress")) {
                                        SquiggleProgress(np, controls, ctx.accent, width = min(640.dp, avail))
                                    }
                                }
                            }
                        }
                    }
                }

                if (open) {
                    Row(
                        Modifier.align(Alignment.CenterHorizontally).padding(bottom = 12.dp).clip(RoundedCornerShape(48.dp))
                            .background(Color.White.copy(alpha = 0.1f)).padding(horizontal = 20.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(24.dp),
                    ) {
                        layout.secondary.forEach { a ->
                            val bg = if (a.active) OnArtMedia.text else Color.Transparent
                            val fg = if (a.active) OnArtMedia.selectedFg else OnArtMedia.text
                            Box(
                                Modifier.size(88.dp).clip(CircleShape).background(bg).pressable(onClickLabel = a.label) { controls?.customAction(a) },
                                contentAlignment = Alignment.Center,
                            ) { ActionGlyphView(a, 36.dp, fg) }
                        }
                    }
                }

                if (error == null) {
                    SlotsRow(820.dp, Modifier.align(Alignment.CenterHorizontally)) {
                        Slot(layout.first, np, controls, dir, 96.dp, 44.dp, open) { onMoreChange(!open) }
                        Slot(layout.previous, np, controls, dir, 96.dp, 44.dp, open) { onMoreChange(!open) }
                        ShapePlayButton(
                            np.playing, np.buffering, 136.dp, 112.dp, 28.dp, 56.dp, 52.dp,
                            onClick = { if (np.playing) controls?.pause() else controls?.play() },
                            modifier = Modifier.sharedMotion("media-play"),
                            background = NightMedia.play, color = NightMedia.onPlay,
                        )
                        Slot(layout.next, np, controls, dir, 96.dp, 44.dp, open) { onMoreChange(!open) }
                        Slot(layout.last, np, controls, dir, 96.dp, 44.dp, open) { onMoreChange(!open) }
                    }
                }
            }
        }
    }
}

/**
 * Player dividido com o mapa (wireframe MxSplit, 600 dp): mesmas regras da barra de 5 posições
 * (R18.20). "Mais" e "Fila" abrem a tela cheia.
 */
@Composable
fun SplitPlayer(
    np: NowPlaying,
    controls: MediaControls?,
    apps: AppsRepository,
    ctx: PlayerContext,
    actions: PlayerActions,
    modifier: Modifier = Modifier,
) {
    val layout = ControlSlots.layout(np)
    val dir = remember { SkipDir() }
    Box(modifier.clip(RoundedCornerShape(24.dp)).sharedMotion("media-card", container = true, clip = RoundedCornerShape(24.dp))) {
        BlurredArtBackdrop(np.art, np.artKey(), scrim = 0.6f, modifier = Modifier.fillMaxSize(), fallback = NightMedia.card)
        Column(Modifier.fillMaxSize().padding(start = 20.dp, top = 14.dp, end = 20.dp, bottom = 18.dp)) {
            Row(Modifier.height(76.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                AppPill(
                    apps, ctx.pkg, ctx.label, actions.onSources, height = 60.dp, icon = 40.dp, textSize = 22.sp, chevron = false,
                    background = OnArtMedia.chip, startPad = 6.dp, endPad = 14.dp, color = OnArtMedia.text,
                )
                Spacer(Modifier.weight(1f))
                if (np.queue.isNotEmpty()) RoundIcon(LauncherIcons.Queue, "Fila", actions.onQueue, iconSize = 30.dp, tint = OnArtMedia.text)
                RoundIcon(LauncherIcons.Expand, "Expandir player", actions.onExpand, iconSize = 28.dp, tint = OnArtMedia.text)
            }
            val error = np.error
            if (error != null) {
                MediaMessage(
                    state = errorState(error, ctx.label), accent = ctx.accent, driving = ctx.driving,
                    onPrimary = { runCatching { error.resolutionIntent?.send() } }, onSecondary = actions.onSources,
                    modifier = Modifier.weight(1f).fillMaxWidth(), compact = true, onArt = true,
                )
                return@Column
            }
            Row(Modifier.weight(1f).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                SlideArt(np, dir, 210.dp, 20.dp, Modifier.sharedMotion("media-art"))
                Column(Modifier.weight(1f).sharedMotion("media-titles")) {
                    SlideTitles(np, dir, 30.sp, 36.sp)
                    Subtitle(np, 23.sp, 20.dp, Modifier.padding(top = 6.dp), actions.onSubtitleLink)
                    Indicators(np, 22.dp, 20.sp, Modifier.padding(top = 10.dp), gap = 10.dp, showExtra = false)
                }
            }
            if (np.hasProgress) {
                Box(Modifier.sharedMotion("media-progress")) {
                    SquiggleProgress(np, controls, ctx.accent, width = 560.dp, waveHeight = 24.dp, areaHeight = 36.dp, thumbHeight = 32.dp, timeSize = 20.sp, timesGap = 0.dp)
                }
            }
            Row(Modifier.fillMaxWidth().padding(top = 10.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Slot(layout.first, np, controls, dir, 80.dp, 36.dp, false, actions.onMore)
                Slot(layout.previous, np, controls, dir, 80.dp, 40.dp, false, actions.onMore)
                ShapePlayButton(
                    np.playing, np.buffering, 120.dp, 96.dp, 26.dp, 48.dp, 46.dp,
                    onClick = { if (np.playing) controls?.pause() else controls?.play() },
                    modifier = Modifier.sharedMotion("media-play"),
                    background = NightMedia.play, color = NightMedia.onPlay,
                )
                Slot(layout.next, np, controls, dir, 80.dp, 40.dp, false, actions.onMore)
                Slot(layout.last, np, controls, dir, 80.dp, 36.dp, false, actions.onMore)
            }
        }
    }
}

@Composable
private fun Slot(
    slot: ControlSlots.Slot,
    np: NowPlaying,
    controls: MediaControls?,
    dir: SkipDir,
    size: Dp,
    iconSize: Dp,
    moreOpen: Boolean,
    onMore: () -> Unit,
) {
    val fg = OnArtMedia.text
    when (slot) {
        ControlSlots.Slot.Empty -> Spacer(Modifier.size(size))
        ControlSlots.Slot.Previous -> RoundIcon(LauncherIcons.Previous, "Faixa anterior", { dir.value = -1; controls?.previous() }, size = size, iconSize = iconSize, tint = fg, enabled = np.canPrevious)
        ControlSlots.Slot.Next -> RoundIcon(LauncherIcons.Next, "Próxima faixa", { dir.value = 1; controls?.next() }, size = size, iconSize = iconSize, tint = fg, enabled = np.canNext)
        ControlSlots.Slot.More -> RoundIcon(
            LauncherIcons.MoreVert, "Mais ações", onMore, size = size, iconSize = iconSize, tint = fg,
            background = if (moreOpen) Color.White.copy(alpha = 0.16f) else Color.Transparent,
        )
        is ControlSlots.Slot.Action -> ActionButton(slot.action, controls, size, iconSize, fg)
    }
}

@Composable
private fun ActionButton(a: MediaAction, controls: MediaControls?, size: Dp, iconSize: Dp, color: Color) {
    Box(
        Modifier.size(size).clip(CircleShape).pressable(onClickLabel = a.label) { controls?.customAction(a) },
        contentAlignment = Alignment.Center,
    ) { ActionGlyphView(a, iconSize, color) }
}

@Composable
private fun SlideArt(np: NowPlaying, dir: SkipDir, size: Dp, radius: Dp, modifier: Modifier) {
    AnimatedContent(
        targetState = np,
        contentKey = { it.title + "|" + it.artist + "|" + (it.art?.generationId ?: 0) },
        transitionSpec = {
            val d = dir.value
            (slideInHorizontally(tween(320)) { it / 4 * d } + fadeIn(tween(320))).togetherWith(slideOutHorizontally(tween(220)) { -it / 4 * d } + fadeOut(tween(180)))
        },
        modifier = modifier,
        label = "art",
    ) { NowPlayingArt(it, size, radius) }
}

@Composable
private fun SlideTitles(np: NowPlaying, dir: SkipDir, size: TextUnit, lineHeight: TextUnit) {
    AnimatedContent(
        targetState = np.title,
        transitionSpec = {
            val d = dir.value
            (slideInHorizontally(tween(320)) { it / 6 * d } + fadeIn(tween(320))).togetherWith(slideOutHorizontally(tween(200)) { -it / 6 * d } + fadeOut(tween(160)))
        },
        label = "title",
    ) { t ->
        Text(t.ifBlank { "Sem título" }, fontSize = size, lineHeight = lineHeight, color = OnArtMedia.text, maxLines = 2, overflow = TextOverflow.Ellipsis)
    }
}

@Composable
private fun Subtitle(np: NowPlaying, size: TextUnit, chevron: Dp, modifier: Modifier, onLink: (String) -> Unit) {
    if (np.artist.isBlank()) return
    val link = np.subtitleLinkId
    Row(
        modifier.then(if (link != null) Modifier.clip(RoundedCornerShape(12.dp)).pressable(onClickLabel = "Abrir ${np.artist}") { onLink(link) } else Modifier),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Text(np.artist, fontSize = size, color = SubtitleColor, maxLines = 1, overflow = TextOverflow.Ellipsis, modifier = Modifier.weight(1f, fill = false))
        if (link != null) Icon(LauncherIcons.Chevron, null, Modifier.size(chevron), tint = SubtitleColor)
    }
}

@Composable
private fun Indicators(np: NowPlaying, size: Dp, text: TextUnit, modifier: Modifier, gap: Dp, showExtra: Boolean = true) {
    val extra = np.album.takeIf { showExtra && it.isNotBlank() }
    if (!np.explicit && !np.downloaded && !np.live && extra == null) return
    Row(modifier, verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(gap)) {
        if (np.explicit) ExplicitBadge(size)
        if (np.downloaded) Icon(LauncherIcons.Download, "Baixado", Modifier.size(size), tint = Meta)
        if (np.live) {
            Row(
                Modifier.height(32.dp).clip(CircleShape).background(Color(0xFFF2B8B5)).padding(horizontal = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Box(Modifier.size(10.dp).clip(CircleShape).background(Color(0xFF601410)))
                Text("AO VIVO", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF601410), letterSpacing = 0.6.sp)
            }
        }
        if (extra != null) Text(extra, fontSize = text, color = Meta, maxLines = 1, overflow = TextOverflow.Ellipsis)
    }
}
