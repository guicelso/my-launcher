package app.mylauncher.ui.media

import android.util.LruCache
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkVertically
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.palette.graphics.Palette
import app.mylauncher.data.apps.AppsRepository
import app.mylauncher.data.media.MediaControls
import app.mylauncher.data.media.MediaUiState
import app.mylauncher.data.media.NowPlaying
import app.mylauncher.data.media.RepeatMode
import app.mylauncher.data.media.formatTime
import app.mylauncher.ui.components.AppIcon
import app.mylauncher.ui.components.TouchIconButton
import app.mylauncher.ui.components.pressable
import app.mylauncher.ui.icons.LauncherIcons
import app.mylauncher.ui.theme.LocalLauncherColors
import app.mylauncher.ui.workspace.sharedMotion
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/** Cores já extraídas por faixa: evita recalcular (e piscar em cinza) quando o player é recomposto. */
private object ArtTintCache {
    private val cache = LruCache<String, Int>(32)
    fun get(key: String): Int? = cache.get(key)
    fun put(key: String, color: Int) { cache.put(key, color) }
}

/**
 * Cor de fundo do player tirada da capa (R10.10), suavizada sobre a superfície do tema
 * para manter o contraste do texto. Transição de 500 ms entre faixas.
 */
@Composable
fun rememberArtTint(np: NowPlaying?): Color {
    val c = LocalLauncherColors.current
    val key = np?.let { "${it.sourcePackage}|${it.title}|${it.artist}|${c.isNight}" }
    val swatch by produceState(key?.let(ArtTintCache::get), key) {
        val art = np?.art ?: return@produceState
        if (value != null) return@produceState
        value = withContext(Dispatchers.Default) {
            val p = Palette.from(art).maximumColorCount(16).generate()
            if (c.isNight) p.getDarkMutedColor(p.getDarkVibrantColor(p.getDominantColor(0)))
            else p.getLightMutedColor(p.getLightVibrantColor(p.getDominantColor(0)))
        }.also { color -> if (key != null) ArtTintCache.put(key, color) }
    }
    val target = swatch?.takeIf { it != 0 }?.let { lerp(c.surface, Color(it), if (c.isNight) 0.55f else 0.45f) } ?: c.surface
    return animateColorAsState(target, tween(500), label = "artTint").value
}

/** Direção da última troca de faixa, para animar o slide no sentido certo (R10.11). */
private class SkipDirection {
    var value by mutableIntStateOf(1)
}

/**
 * Player completo (spec 010, R10.10–R10.14). `full = false`: painel da tela dividida;
 * `full = true`: tela cheia, com capa maior.
 */
@Composable
fun PlayerPanel(
    state: MediaUiState,
    apps: AppsRepository,
    full: Boolean,
    onToggleSize: () -> Unit,
    onOpenApp: (String) -> Unit,
    onGrantAccess: () -> Unit,
    modifier: Modifier = Modifier,
    sources: List<String> = emptyList(),
    onSelectSource: (String) -> Unit = {},
) {
    val c = LocalLauncherColors.current
    val np = state.nowPlaying
    val tint = rememberArtTint(np)
    var showQueue by rememberSaveable { mutableStateOf(false) }
    val direction = remember { SkipDirection() }

    val cardShape = RoundedCornerShape(28.dp)
    FrostedArtCard(np, cardShape, fallback = tint, modifier = modifier.sharedMotion("media-card", container = true, clip = cardShape)) { _ ->
        val c = LocalLauncherColors.current
        Column(Modifier.fillMaxSize().padding(horizontal = 24.dp, vertical = 18.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                if (np != null) {
                    Row(
                        Modifier.weight(1f).clip(RoundedCornerShape(20.dp)).pressable { onOpenApp(np.sourcePackage) }.padding(end = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        AppIcon(apps, np.sourcePackage, 32.dp)
                        Text(np.sourceLabel, style = MaterialTheme.typography.labelLarge, color = c.onSurfaceVariant, maxLines = 1)
                    }
                } else {
                    Spacer(Modifier.weight(1f))
                }
                if (np != null) {
                    TouchIconButton(LauncherIcons.External, "Abrir ${np.sourceLabel}", { onOpenApp(np.sourcePackage) }, iconSize = 28.dp)
                }
                if (np != null && np.queue.isNotEmpty()) {
                    TouchIconButton(
                        LauncherIcons.Queue, if (showQueue) "Fechar fila" else "Fila de reprodução", { showQueue = !showQueue },
                        iconSize = 30.dp, background = if (showQueue) c.surface2 else Color.Transparent,
                    )
                }
                TouchIconButton(
                    if (full) LauncherIcons.Close else LauncherIcons.Expand,
                    if (full) "Voltar ao mapa" else "Player em tela cheia", onToggleSize, iconSize = 30.dp,
                )
            }

            if (np == null) {
                EmptyPlayer(state.listenerGranted, onGrantAccess, Modifier.weight(1f))
                return@Column
            }

            val controls = state.controls
            if (showQueue) {
                Queue(np, controls, Modifier.weight(1f))
                return@Column
            }
            AnimatedVisibility(full && sources.size > 1, enter = fadeIn() + expandVertically(), exit = fadeOut() + shrinkVertically()) {
                SourceTabs(sources, np.sourcePackage, apps, onSelectSource, Modifier.padding(top = 4.dp))
            }
            // Um único layout que se reorganiza conforme a largura do card (spec 010): ao crescer,
            // a capa e o título aumentam e os botões extras aparecem, quadro a quadro.
            BoxWithConstraints(Modifier.weight(1f).fillMaxWidth()) {
                val t = ((maxWidth - COMPACT_WIDTH) / (WIDE_WIDTH - COMPACT_WIDTH)).coerceIn(0f, 1f)
                val art = lerp(200.dp, 400.dp, t).coerceAtMost(maxHeight - 16.dp)
                val showExtras = maxWidth > EXTRAS_WIDTH
                Row(
                    Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(lerp(24.dp, 40.dp, t)),
                ) {
                    Box(Modifier.sharedMotion("media-art")) { AnimatedArt(np, art, direction) }
                    Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(lerp(12.dp, 24.dp, t))) {
                        Box(Modifier.sharedMotion("media-titles")) {
                            AnimatedTitles(np, direction, titleSize = lerp(26.sp, 40.sp, t), subtitleSize = lerp(18.sp, 26.sp, t))
                        }
                        Box(Modifier.sharedMotion("media-progress")) { SeekBar(np, controls) }
                        Controls(np, controls, direction, playSize = lerp(84.dp, 104.dp, t), showExtras = showExtras)
                    }
                }
            }
        }
    }
}

/** Larguras de referência do layout responsivo do player. */
private val COMPACT_WIDTH = 520.dp
private val WIDE_WIDTH = 1100.dp
private val EXTRAS_WIDTH = 680.dp

@Composable
private fun EmptyPlayer(granted: Boolean, onGrantAccess: () -> Unit, modifier: Modifier) {
    val c = LocalLauncherColors.current
    Column(
        modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
    ) {
        AlbumArt(null, 160.dp, 24.dp, 64.dp)
        Text(
            if (granted) "Nada tocando" else "Acesso às notificações necessário",
            style = MaterialTheme.typography.titleLarge, color = c.onSurfaceVariant,
        )
        if (!granted) PillButton("Permitir acesso", onClick = onGrantAccess)
    }
}

@Composable
private fun AnimatedArt(np: NowPlaying, size: Dp, direction: SkipDirection) {
    AnimatedContent(
        targetState = np,
        contentKey = { it.title + "|" + it.artist + "|" + (it.art?.generationId ?: 0) },
        transitionSpec = {
            val d = direction.value
            (slideInHorizontally(tween(320)) { it / 4 * d } + fadeIn(tween(320)))
                .togetherWith(slideOutHorizontally(tween(220)) { -it / 4 * d } + fadeOut(tween(180)))
        },
        label = "art",
    ) { AlbumArt(it, size, 20.dp, size / 3) }
}

@Composable
private fun AnimatedTitles(np: NowPlaying, direction: SkipDirection, titleSize: TextUnit, subtitleSize: TextUnit) {
    val c = LocalLauncherColors.current
    AnimatedContent(
        targetState = np,
        contentKey = { it.title + "|" + it.artist },
        transitionSpec = {
            val d = direction.value
            (slideInHorizontally(tween(320)) { it / 6 * d } + fadeIn(tween(320)))
                .togetherWith(slideOutHorizontally(tween(200)) { -it / 6 * d } + fadeOut(tween(160)))
        },
        label = "titles",
    ) { t ->
        Column {
            Text(
                t.title.ifBlank { "Sem título" },
                style = MaterialTheme.typography.headlineSmall.copy(fontSize = titleSize, lineHeight = titleSize * 1.15f),
                color = c.onSurface, maxLines = 2, overflow = TextOverflow.Ellipsis,
            )
            Text(
                listOf(t.artist, t.album).filter { it.isNotBlank() }.joinToString(" · "),
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = subtitleSize, lineHeight = subtitleSize * 1.2f),
                color = c.onSurfaceVariant, maxLines = 1, overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 6.dp),
            )
        }
    }
}

/**
 * Progresso contínuo e arrastável (R10.12): alvo de toque de 76 dp de altura,
 * trilho de 6 dp. Só busca quando a sessão permite.
 */
@Composable
fun SeekBar(np: NowPlaying, controls: MediaControls?) {
    val c = LocalLauncherColors.current
    val position = rememberPosition(np)
    var dragging by remember { mutableStateOf(false) }
    var dragFraction by remember { mutableFloatStateOf(0f) }
    val live = np.fraction(position)
    // Avanço contínuo: anima linearmente entre os ticks de 500 ms.
    val smooth by animateFloatAsState(live, tween(if (np.playing) 500 else 150, easing = LinearEasing), label = "progress")
    val shown = if (dragging) dragFraction else smooth
    val canSeek = np.canSeek && np.durationMs > 0 && controls != null

    Column {
        BoxWithConstraints(
            Modifier
                .fillMaxWidth()
                .height(if (canSeek) 76.dp else 24.dp)
                .semantics { contentDescription = "Progresso ${formatTime(position)} de ${formatTime(np.durationMs)}" }
                .then(
                    if (canSeek) {
                        Modifier
                            .pointerInput(np.durationMs) {
                                detectTapGestures { o -> controls?.seekTo((o.x / size.width * np.durationMs).toLong()) }
                            }
                            .pointerInput(np.durationMs) {
                                detectHorizontalDragGestures(
                                    onDragStart = { o -> dragging = true; dragFraction = (o.x / size.width).coerceIn(0f, 1f) },
                                    onDragEnd = { controls?.seekTo((dragFraction * np.durationMs).toLong()); dragging = false },
                                    onDragCancel = { dragging = false },
                                ) { change, _ -> dragFraction = (change.position.x / size.width).coerceIn(0f, 1f) }
                            }
                    } else {
                        Modifier
                    },
                ),
            contentAlignment = Alignment.CenterStart,
        ) {
            val w = maxWidth
            Box(Modifier.fillMaxWidth().height(6.dp).clip(CircleShape).background(c.outline)) {
                Box(Modifier.fillMaxHeight().fillMaxWidth(shown.coerceIn(0f, 1f)).clip(CircleShape).background(c.onSurface))
            }
            if (canSeek) {
                val thumb = if (dragging) 24.dp else 16.dp
                Box(
                    Modifier
                        .offset(x = w * shown.coerceIn(0f, 1f) - thumb / 2)
                        .size(thumb)
                        .clip(CircleShape)
                        .background(c.onSurface),
                )
            }
        }
        Row(Modifier.fillMaxWidth()) {
            val shownMs = if (dragging) (dragFraction * np.durationMs).toLong() else position
            Text(formatTime(shownMs), style = MaterialTheme.typography.bodyMedium, color = c.onSurfaceVariant)
            Spacer(Modifier.weight(1f))
            if (np.durationMs > 0) Text(formatTime(np.durationMs), style = MaterialTheme.typography.bodyMedium, color = c.onSurfaceVariant)
        }
    }
}

@Composable
private fun Controls(np: NowPlaying, controls: MediaControls?, direction: SkipDirection, playSize: Dp, showExtras: Boolean) {
    val c = LocalLauncherColors.current
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        AnimatedVisibility(showExtras, enter = fadeIn() + expandHorizontally(), exit = fadeOut() + shrinkHorizontally()) {
            TouchIconButton(
                LauncherIcons.Shuffle, "Aleatório", { controls?.toggleShuffle() }, iconSize = 30.dp,
                enabled = np.shuffle != null, tint = if (np.shuffle == true) c.primary else c.onSurfaceVariant,
            )
        }
        TouchIconButton(
            LauncherIcons.Previous, "Faixa anterior",
            { direction.value = -1; controls?.previous() }, iconSize = 40.dp, enabled = np.canSkip,
        )
        PlayPauseButton(np.playing, size = playSize, modifier = Modifier.sharedMotion("media-play")) {
            if (np.playing) controls?.pause() else controls?.play()
        }
        TouchIconButton(
            LauncherIcons.Next, "Próxima faixa",
            { direction.value = 1; controls?.next() }, iconSize = 40.dp, enabled = np.canSkip,
        )
        AnimatedVisibility(showExtras, enter = fadeIn() + expandHorizontally(), exit = fadeOut() + shrinkHorizontally()) {
            Box(contentAlignment = Alignment.Center) {
                TouchIconButton(
                    LauncherIcons.Repeat, "Repetir", { controls?.cycleRepeat() }, iconSize = 30.dp,
                    enabled = np.repeat != null,
                    tint = if (np.repeat != null && np.repeat != RepeatMode.OFF) c.primary else c.onSurfaceVariant,
                )
                if (np.repeat == RepeatMode.ONE) {
                    Text("1", style = MaterialTheme.typography.labelSmall, color = c.primary, modifier = Modifier.offset(x = 14.dp, y = (-14).dp))
                }
            }
        }
    }
}

/** Tocar/pausar com troca animada do ícone (R10.13). */
@Composable
fun PlayPauseButton(playing: Boolean, size: Dp, modifier: Modifier = Modifier, onClick: () -> Unit) {
    val c = LocalLauncherColors.current
    Box(
        modifier
            .size(size)
            .clip(CircleShape)
            .background(c.primary)
            .pressable(onClickLabel = if (playing) "Pausar" else "Tocar", onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        AnimatedContent(
            targetState = playing,
            transitionSpec = { (fadeIn(tween(160)) + scaleIn(tween(160), 0.6f)).togetherWith(fadeOut(tween(120)) + scaleOut(tween(120), 0.6f)) },
            label = "playPause",
        ) { p ->
            Icon(if (p) LauncherIcons.Pause else LauncherIcons.Play, if (p) "Pausar" else "Tocar", Modifier.size(size * 0.42f), tint = c.onPrimary)
        }
    }
}

@Composable
private fun Queue(np: NowPlaying, controls: MediaControls?, modifier: Modifier) {
    val c = LocalLauncherColors.current
    LazyColumn(modifier.fillMaxWidth().padding(top = 8.dp)) {
        items(np.queue, key = { it.id }) { item ->
            Column(
                Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .pressable { controls?.playQueueItem(item.id) }
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(item.title, style = MaterialTheme.typography.titleMedium, color = c.onSurface, maxLines = 1, overflow = TextOverflow.Ellipsis)
                if (item.subtitle.isNotBlank()) {
                    Text(item.subtitle, style = MaterialTheme.typography.bodyMedium, color = c.onSurfaceVariant, maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
            }
        }
    }
}
