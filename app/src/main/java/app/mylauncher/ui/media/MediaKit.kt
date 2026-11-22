package app.mylauncher.ui.media

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.mylauncher.data.apps.AppsRepository
import app.mylauncher.data.media.MediaAction
import app.mylauncher.data.media.MediaControls
import app.mylauncher.data.media.NowPlaying
import app.mylauncher.data.media.browse.AppAccent
import app.mylauncher.data.media.browse.MediaArt
import app.mylauncher.ui.components.AppIcon
import app.mylauncher.ui.components.pressable
import app.mylauncher.ui.icons.LauncherIcons
import app.mylauncher.ui.theme.LocalLauncherColors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlin.math.sin

/**
 * Cores das telas de mídia (spec 018), tiradas dos wireframes Mx*. O Android Auto só tem tema
 * escuro; no tema dia do Lumia as telas sem capa usam os equivalentes claros.
 */
@Immutable
data class MediaPalette(
    val card: Color,
    val text: Color,
    val muted: Color,
    val subtle: Color,
    val meta: Color,
    val line: Color,
    val chip: Color,
    val chipStrong: Color,
    val tile: Color,
    val tileActive: Color,
    val mini: Color,
    val track: Color,
    val keyboard: Color,
    val key: Color,
    val keyAlt: Color,
    val field: Color,
    val outline: Color,
    val disabled: Color,
    val railIdle: Color,
    val skeleton: Color,
    val skeleton2: Color,
    val dashed: Color,
    val warnBg: Color,
    val warnFg: Color,
    val play: Color,
    val onPlay: Color,
    val selectedFg: Color,
)

val NightMedia = MediaPalette(
    card = Color(0xFF262625), text = Color(0xFFE3E3E3), muted = Color(0xFFC4C7C5), subtle = Color(0xFFA8ABAE),
    meta = Color(0xFFB8BBB9), line = Color(0xFF3C3E3D), chip = Color.White.copy(alpha = 0.08f), chipStrong = Color.White.copy(alpha = 0.12f),
    tile = Color(0xFF333534), tileActive = Color(0xFF3A3A33), mini = Color(0xFF343635), track = Color(0xFF464A4D),
    keyboard = Color(0xFF1C1D1D), key = Color(0xFF3A3C3B), keyAlt = Color(0xFF2C2E2D), field = Color(0xFF3A3C3B),
    outline = Color(0xFF8E918F), disabled = Color(0xFF8E918F), railIdle = Color(0xFF747775), skeleton = Color(0xFF343635),
    skeleton2 = Color(0xFF2F3130), dashed = Color(0xFF5C5F5E), warnBg = Color(0xFF3B3A2A), warnFg = Color(0xFFF3E7A9),
    play = Color(0xFFCFD6D3), onPlay = Color(0xFF151616), selectedFg = Color(0xFF151616),
)

val DayMedia = NightMedia.copy(
    card = Color(0xFFFFFFFF), text = Color(0xFF1F1F1F), muted = Color(0xFF444746), subtle = Color(0xFF5F6368), meta = Color(0xFF5F6368),
    line = Color(0xFFDADCE0), chip = Color.Black.copy(alpha = 0.05f), chipStrong = Color.Black.copy(alpha = 0.08f), tile = Color(0xFFF1F3F4),
    tileActive = Color(0xFFFFF4E0), mini = Color(0xFFF1F3F4), track = Color(0xFFDADCE0), keyboard = Color(0xFFE8EAED), key = Color(0xFFFFFFFF),
    keyAlt = Color(0xFFDADCE0), field = Color(0xFFF1F3F4), outline = Color(0xFF747775), railIdle = Color(0xFFBDC1C6), skeleton = Color(0xFFE8EAED),
    skeleton2 = Color(0xFFF1F3F4), dashed = Color(0xFFBDC1C6), warnBg = Color(0xFFFEF7E0), warnFg = Color(0xFF5C4300),
    play = Color(0xFF1F1F1F), onPlay = Color(0xFFFFFFFF), selectedFg = Color(0xFFFFFFFF),
)

/** Sobre a capa desfocada o conteúdo é sempre claro (película escura). */
val OnArtMedia = NightMedia.copy(chip = Color.White.copy(alpha = 0.08f), chipStrong = Color.White.copy(alpha = 0.12f))

@Composable
fun mediaPalette(): MediaPalette = if (LocalLauncherColors.current.isNight) NightMedia else DayMedia

/** Cor do app de mídia (progresso, "tocando agora", novos), como o Android Auto lê do tema do app. */
@Composable
fun rememberAccent(pkg: String?): Color {
    val context = LocalContext.current
    val fallback = LocalLauncherColors.current.primary
    if (pkg == null) return fallback
    // O app simulado do painel de depuração usa a cor dos wireframes.
    if (pkg == context.packageName) return SonoraAccent
    val c by produceState<Int?>(null, pkg) { value = withContext(Dispatchers.Default) { AppAccent.of(context, pkg) } }
    return c?.let { Color(it) } ?: fallback
}

val SonoraAccent = Color(0xFFF6B26B)

// ---------------------------------------------------------------------------------------------
// Peças pequenas

/** Pílula do app (ícone + nome + seta): toca para trocar de app de mídia (MxPlayer/MxBrowse). */
@Composable
fun AppPill(
    apps: AppsRepository,
    pkg: String,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    height: Dp = 64.dp,
    icon: Dp = 44.dp,
    textSize: TextUnit = 24.sp,
    chevron: Boolean = true,
    background: Color = mediaPalette().chip,
    startPad: Dp = 8.dp,
    endPad: Dp = if (chevron) 16.dp else 14.dp,
    color: Color = mediaPalette().text,
) {
    Row(
        modifier
            .height(height)
            .clip(CircleShape)
            .background(background)
            .pressable(onClickLabel = "Trocar app de mídia ($label)", onClick = onClick)
            .padding(start = startPad, end = endPad),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(if (icon >= 44.dp) 12.dp else 10.dp),
    ) {
        AppIcon(apps, pkg, icon)
        Text(label, fontSize = textSize, color = color, maxLines = 1, overflow = TextOverflow.Ellipsis, modifier = Modifier.widthIn(max = 260.dp))
        if (chevron) Icon(LauncherIcons.ChevronDownSmall, null, Modifier.size(22.dp), tint = color)
    }
}

/** Botão circular de ícone (alvo de 76 dp por padrão). */
@Composable
fun RoundIcon(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: Dp = 76.dp,
    iconSize: Dp = 32.dp,
    tint: Color = mediaPalette().text,
    background: Color = Color.Transparent,
    enabled: Boolean = true,
) {
    Box(
        modifier.size(size).clip(CircleShape).background(background)
            .pressable(enabled = enabled, onClickLabel = label, onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Icon(icon, label, Modifier.size(iconSize), tint = if (enabled) tint else tint.copy(alpha = 0.38f))
    }
}

/** Pílula com ícone e texto (Fila, Buscar, Abrir o app, ações de navegação). */
@Composable
fun MediaPill(
    text: String,
    icon: ImageVector?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    height: Dp = 64.dp,
    background: Color = mediaPalette().chipStrong,
    color: Color = mediaPalette().text,
    border: Color? = null,
    iconSize: Dp = 28.dp,
    textSize: TextUnit = 22.sp,
    weight: FontWeight = FontWeight.Normal,
    startPad: Dp = 20.dp,
    endPad: Dp = 26.dp,
    enabled: Boolean = true,
) {
    Row(
        modifier
            .height(height)
            .clip(CircleShape)
            .background(background)
            .then(if (border != null) Modifier.border(2.dp, border, CircleShape) else Modifier)
            .pressable(enabled = enabled, onClickLabel = text, onClick = onClick)
            .padding(start = if (icon != null) startPad else endPad, end = endPad),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        if (icon != null) Icon(icon, null, Modifier.size(iconSize), tint = color)
        Text(text, fontSize = textSize, color = color, fontWeight = weight, maxLines = 1)
    }
}

/** Selo "E" de conteúdo explícito. */
@Composable
fun ExplicitBadge(size: Dp = 26.dp, background: Color = Color(0xFFC4C7C5), color: Color = Color(0xFF151616)) {
    Box(
        Modifier.size(size).clip(RoundedCornerShape(size * 0.19f)).background(background)
            .semantics { contentDescription = "Explícito" },
        contentAlignment = Alignment.Center,
    ) {
        Text("E", fontSize = (size.value * 0.65f).sp, fontWeight = FontWeight.Bold, color = color, lineHeight = (size.value * 0.65f).sp)
    }
}

/** Indicador de faixa tocando (barras). */
@Composable
fun NowPlayingBars(color: Color, size: Dp = 28.dp) {
    Icon(LauncherIcons.Equalizer, "Tocando agora", Modifier.size(size), tint = color)
}

// ---------------------------------------------------------------------------------------------
// Capas

/** Cores de reserva para capas ausentes (as mesmas do Media Center do AOSP). */
private val placeholderColors = listOf(
    Color(0xFF1D5C63) to Color(0xFFF6B26B), Color(0xFF3A2E5C) to Color(0xFFA8C7FA), Color(0xFF5B3A1E) to Color(0xFFFDD663),
    Color(0xFF0F3D2A) to Color(0xFF81C995), Color(0xFF4A1D3B) to Color(0xFFF28B82), Color(0xFF20344F) to Color(0xFF8AB4F8),
)

/** Capa de um item: bitmap, URI (carregada em segundo plano) ou formas coloridas de reserva. */
@Composable
fun ArtBox(
    bitmap: Bitmap?,
    uri: Uri?,
    seed: String,
    size: Dp?,
    radius: Dp,
    modifier: Modifier = Modifier,
    shadow: Boolean = false,
    circle: Boolean = false,
) {
    val context = LocalContext.current
    val px = with(LocalDensity.current) { (size ?: 200.dp).roundToPx() }
    val loaded by produceState<ImageBitmap?>(bitmap?.asImageBitmap() ?: MediaArt.cached(uri)?.asImageBitmap(), bitmap, uri) {
        value = bitmap?.asImageBitmap() ?: MediaArt.load(context, uri, px)?.asImageBitmap()
    }
    val shape = if (circle) CircleShape else RoundedCornerShape(radius)
    val base = (if (size != null) modifier.size(size) else modifier)
        .then(if (shadow) Modifier.shadow(16.dp, shape, ambientColor = Color.Black, spotColor = Color.Black) else Modifier)
        .clip(shape)
    val img = loaded
    if (img != null) {
        Image(img, null, contentScale = ContentScale.Crop, filterQuality = FilterQuality.Medium, modifier = base)
    } else {
        val (a, b) = placeholderColors[Math.floorMod(seed.hashCode(), placeholderColors.size)]
        Canvas(base.background(a)) {
            drawCircle(b, radius = this.size.minDimension * 0.36f, center = Offset(this.size.width * 0.82f, this.size.height * 0.18f))
        }
    }
}

/** Capa do item tocando (bitmap da sessão). */
@Composable
fun NowPlayingArt(np: NowPlaying, size: Dp, radius: Dp, modifier: Modifier = Modifier, shadow: Boolean = true) {
    ArtBox(np.art, null, np.title + np.artist, size, radius, modifier, shadow = shadow)
}

/**
 * Fundo com a capa muito desfocada e película escura (player de 2026, IU-1: uma imagem estática).
 * Desfoque feito na CPU ([FrostedArt.renderStrong]): Android 10 não tem `Modifier.blur`.
 */
@Composable
fun BlurredArtBackdrop(art: Bitmap?, key: String, scrim: Float, modifier: Modifier = Modifier, fallback: Color = mediaPalette().card) {
    val strong by produceState(art?.let { FrostedArt.cachedStrong(key) }, key, art) {
        value = art?.let { a -> FrostedArt.cachedStrong(key) ?: withContext(Dispatchers.Default) { FrostedArt.renderStrong(key, a) } }
    }
    Box(modifier.background(fallback)) {
        Crossfade(strong, animationSpec = tween(500), label = "blurArt") { img ->
            if (img != null) {
                Image(img, null, contentScale = ContentScale.Crop, filterQuality = FilterQuality.High, alpha = 0.9f, modifier = Modifier.fillMaxSize())
            }
        }
        if (strong != null) Box(Modifier.fillMaxSize().background(Color(0xFF0C0D0D).copy(alpha = scrim)))
    }
}

fun NowPlaying.artKey(): String = "$sourcePackage|$title|$artist|${art?.generationId ?: 0}|${art?.let { System.identityHashCode(it) } ?: 0}"

// ---------------------------------------------------------------------------------------------
// Progresso ondulado (Material 3 Expressive), estático

/**
 * Barra de progresso ondulada do player de 2026: a parte tocada ondula, o resto é reto.
 * Estática (sem animação contínua: desempenho na GPU e regra SA-1). Arrastar só com seek (R18.15).
 */
@Composable
fun SquiggleProgress(
    np: NowPlaying,
    controls: MediaControls?,
    accent: Color,
    width: Dp,
    waveHeight: Dp = 28.dp,
    areaHeight: Dp = 40.dp,
    thumbHeight: Dp = 36.dp,
    timeSize: TextUnit = 22.sp,
    timeColor: Color = Color(0xFFB8BBB9),
    timesGap: Dp = 6.dp,
) {
    val position = rememberPosition(np)
    var dragging by remember { mutableStateOf(false) }
    var dragFraction by remember { mutableFloatStateOf(0f) }
    val fraction = (if (dragging) dragFraction else np.fraction(position)).coerceIn(0f, 1f)
    val canSeek = np.canSeek && np.hasProgress && controls != null
    Column(Modifier.width(width)) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(areaHeight)
                .semantics { contentDescription = "Posição ${formatTimeMs(position)} de ${formatTimeMs(np.durationMs)}" }
                .then(
                    if (canSeek) {
                        Modifier
                            .pointerInput(np.durationMs) { detectTapGestures { o -> controls?.seekTo((o.x / size.width * np.durationMs).toLong()) } }
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
        ) {
            val stroke = with(LocalDensity.current) { 5.dp.toPx() }
            Canvas(Modifier.fillMaxWidth().height(waveHeight).align(Alignment.CenterStart)) {
                val mid = size.height / 2
                val x = size.width * fraction
                val amp = with(this) { 5.dp.toPx() }
                val path = Path()
                path.moveTo(3f, mid)
                var i = 3f
                val step = 4f
                while (i <= x - 3f) {
                    path.lineTo(i, mid + amp * sin(i / (7f * density)))
                    i += step
                }
                drawPath(path, accent, style = Stroke(width = stroke, cap = StrokeCap.Round))
                if (x + 10 * density < size.width - 3f) {
                    drawLine(Color.White.copy(alpha = 0.28f), Offset(x + 10 * density, mid), Offset(size.width - 3f, mid), stroke, StrokeCap.Round)
                }
            }
            Box(
                Modifier
                    .align(Alignment.CenterStart)
                    .offset(x = width * fraction - 4.dp)
                    .size(8.dp, thumbHeight)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.White),
            )
        }
        Row(Modifier.fillMaxWidth().padding(top = timesGap)) {
            val shown = if (dragging) (dragFraction * np.durationMs).toLong() else position
            Text(formatTimeMs(shown), fontSize = timeSize, color = timeColor)
            Spacer(Modifier.weight(1f))
            Text("−" + formatTimeMs((np.durationMs - shown).coerceAtLeast(0)), fontSize = timeSize, color = timeColor)
        }
    }
}

fun formatTimeMs(ms: Long): String = app.mylauncher.data.media.formatTime(ms)

/** Barra fina de progresso (mini player e card do painel). */
@Composable
fun ThinProgress(fraction: Float, accent: Color, track: Color, modifier: Modifier = Modifier, height: Dp = 4.dp, rounded: Boolean = false) {
    Box(modifier.fillMaxWidth().height(height).then(if (rounded) Modifier.clip(RoundedCornerShape(height / 2)) else Modifier).background(track)) {
        Box(Modifier.fillMaxHeight().fillMaxWidth(fraction.coerceIn(0f, 1f)).then(if (rounded) Modifier.clip(RoundedCornerShape(height / 2)) else Modifier).background(accent))
    }
}

// ---------------------------------------------------------------------------------------------
// Tocar/pausar

/**
 * Tocar/pausar do Android Auto de 2026: quadrado arredondado tocando, mais redondo pausado
 * (a forma muda com o estado). Em buffering vira indicador de carregamento (R18.15).
 */
@Composable
fun ShapePlayButton(
    playing: Boolean,
    buffering: Boolean,
    width: Dp,
    height: Dp,
    playingRadius: Dp,
    pausedRadius: Dp,
    iconSize: Dp,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    background: Color = mediaPalette().play,
    color: Color = mediaPalette().onPlay,
) {
    val radius by animateDpAsState(if (playing) playingRadius else pausedRadius, tween(220), label = "playShape")
    Box(
        modifier
            .size(width, height)
            .clip(RoundedCornerShape(radius))
            .background(background)
            .pressable(onClickLabel = if (buffering) "Carregando" else if (playing) "Pausar" else "Tocar", onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        AnimatedContent(
            targetState = if (buffering) 2 else if (playing) 1 else 0,
            transitionSpec = { (fadeIn(tween(160)) + scaleIn(tween(160), 0.6f)).togetherWith(fadeOut(tween(120)) + scaleOut(tween(120), 0.6f)) },
            label = "playIcon",
        ) { s ->
            when (s) {
                2 -> Spinner(color, iconSize)
                1 -> Icon(LauncherIcons.Pause, "Pausar", Modifier.size(iconSize), tint = color)
                else -> Icon(LauncherIcons.Play, "Tocar", Modifier.size(iconSize), tint = color)
            }
        }
    }
}

/** Indicador de carregamento estático (arco), sem animação contínua. */
@Composable
fun Spinner(color: Color, size: Dp, stroke: Dp = 2.6.dp * (size.value / 24f)) {
    Canvas(Modifier.size(size).semantics { contentDescription = "Carregando" }) {
        val w = stroke.toPx()
        val inset = w / 2 + size.toPx() * 0.125f
        val s = Size(this.size.width - 2 * inset, this.size.height - 2 * inset)
        drawArc(color.copy(alpha = 0.25f), 0f, 360f, false, Offset(inset, inset), s, style = Stroke(w))
        drawArc(color, -90f, 90f, false, Offset(inset, inset), s, style = Stroke(w, cap = StrokeCap.Round))
    }
}

// ---------------------------------------------------------------------------------------------
// Ações do app

/** Como desenhar uma ação: ícone nosso por significado, ícone do app ou texto (velocidade). */
sealed interface ActionGlyph {
    data class Vector(val icon: ImageVector, val filled: Boolean = false) : ActionGlyph
    data class Bitmap(val image: ImageBitmap) : ActionGlyph
    data class Text(val text: String) : ActionGlyph
}

private val speedRegex = Regex("""(\d+(?:[.,]\d+)?)\s*[x×]""")

/**
 * Ícones padronizados por significado (como os `CommandButton.ICON_*` do Media3), para ações
 * conhecidas; o resto usa o ícone do app tingido de branco (R18.14).
 */
fun glyphFor(a: MediaAction): ActionGlyph? {
    val t = (a.id + " " + a.label).lowercase()
    speedRegex.find(a.label)?.let { return ActionGlyph.Text(it.groupValues[1] + "×") }
    return when {
        a.builtin == app.mylauncher.data.media.BuiltinAction.SHUFFLE || "shuffle" in t || "aleat" in t -> ActionGlyph.Vector(LauncherIcons.Shuffle)
        a.builtin != null || "repeat" in t || "repet" in t -> ActionGlyph.Vector(LauncherIcons.Repeat)
        "não gostei" in t || "nao gostei" in t || "dislike" in t || "thumbs down" in t -> ActionGlyph.Vector(LauncherIcons.ThumbDown)
        ("remover" in t || "remove" in t) && ("gostei" in t || "like" in t) -> ActionGlyph.Vector(LauncherIcons.ThumbUpFilled, filled = true)
        "gostei" in t || "thumbs up" in t -> ActionGlyph.Vector(LauncherIcons.ThumbUp)
        "curtid" in t || "liked" in t -> ActionGlyph.Vector(LauncherIcons.HeartFilled, filled = true)
        "coleção" in t || "colecao" in t || "biblioteca" in t -> ActionGlyph.Vector(LauncherIcons.Heart)
        "curtir" in t || "like" in t || "heart" in t -> ActionGlyph.Vector(LauncherIcons.Heart)
        "favorit" in t && "estação" in t -> ActionGlyph.Vector(LauncherIcons.StarFilled, filled = true)
        "favorit" in t || "star" in t -> ActionGlyph.Vector(LauncherIcons.Star)
        "15" in t && ("voltar" in t || "back" in t || "rewind" in t || "replay" in t) -> ActionGlyph.Vector(LauncherIcons.Replay15)
        ("30" in t || "15" in t) && ("avan" in t || "forward" in t || "skip" in t) -> ActionGlyph.Vector(LauncherIcons.Forward30)
        "rádio" in t || "radio" in t -> ActionGlyph.Vector(LauncherIcons.Radio)
        "playlist" in t || "adicionar" in t -> ActionGlyph.Vector(LauncherIcons.PlaylistAdd)
        "ouvido" in t || "played" in t || "concluí" in t -> ActionGlyph.Vector(LauncherIcons.Check)
        "baix" in t || "download" in t -> ActionGlyph.Vector(LauncherIcons.Download)
        "deslig" in t || "timer" in t || "sleep" in t -> ActionGlyph.Vector(LauncherIcons.Timer)
        "lista" in t || "estações" in t -> ActionGlyph.Vector(LauncherIcons.ListBullets)
        else -> null
    }
}

/** Número sob o ícone de voltar/avançar (15 / 30). */
fun skipNumber(a: MediaAction): String? {
    val t = (a.id + " " + a.label).lowercase()
    return when {
        "15" in t && ("voltar" in t || "back" in t || "replay" in t || "rewind" in t) -> "15"
        "30" in t && ("avan" in t || "forward" in t) -> "30"
        "15" in t && ("avan" in t || "forward" in t) -> "15"
        else -> null
    }
}

@Composable
fun rememberActionGlyph(a: MediaAction): ActionGlyph {
    val context = LocalContext.current
    glyphFor(a)?.let { return it }
    val bmp by produceState<ImageBitmap?>(null, a.pkg, a.iconRes) {
        value = if (a.iconRes == 0 || a.pkg.isEmpty()) null else withContext(Dispatchers.IO) {
            runCatching {
                val res = context.packageManager.getResourcesForApplication(a.pkg)
                @Suppress("DEPRECATION")
                val d = res.getDrawable(a.iconRes)
                val px = 96
                val b = Bitmap.createBitmap(px, px, Bitmap.Config.ARGB_8888)
                val cv = android.graphics.Canvas(b)
                d.setBounds(0, 0, px, px)
                d.draw(cv)
                b.asImageBitmap()
            }.getOrNull()
        }
    }
    return bmp?.let { ActionGlyph.Bitmap(it) } ?: ActionGlyph.Vector(LauncherIcons.MoreVert)
}

/** Conteúdo de um botão de ação (ícone, ícone do app ou texto em caixa). */
@Composable
fun ActionGlyphView(a: MediaAction, iconSize: Dp, color: Color, textSize: TextUnit = 24.sp) {
    when (val g = rememberActionGlyph(a)) {
        is ActionGlyph.Vector -> Box(contentAlignment = Alignment.Center) {
            Icon(g.icon, a.label, Modifier.size(iconSize), tint = color)
            skipNumber(a)?.let { Text(it, fontSize = (iconSize.value * 0.3f).sp, fontWeight = FontWeight.Bold, color = color, modifier = Modifier.padding(top = iconSize * 0.09f)) }
        }
        is ActionGlyph.Bitmap -> Image(g.image, a.label, Modifier.size(iconSize), colorFilter = ColorFilter.tint(color))
        is ActionGlyph.Text -> Box(
            Modifier.height(iconSize).border(2.5.dp, color, RoundedCornerShape(12.dp)).padding(horizontal = 10.dp),
            contentAlignment = Alignment.Center,
        ) { Text(g.text, fontSize = textSize, fontWeight = FontWeight.Bold, color = color) }
    }
}

// ---------------------------------------------------------------------------------------------
// Trilho de rolagem (botões para as teclas do volante) e mini player

@Composable
fun ScrollRail(
    canUp: Boolean,
    canDown: Boolean,
    thumbStart: Float,
    thumbSize: Float,
    onUp: () -> Unit,
    onDown: () -> Unit,
    modifier: Modifier = Modifier,
    az: (() -> Unit)? = null,
) {
    val m = mediaPalette()
    Column(modifier.width(76.dp).fillMaxHeight().padding(bottom = 8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        RoundIcon(LauncherIcons.ChevronUp, "Rolar para cima", onUp, tint = if (canUp) m.text else m.railIdle)
        Box(Modifier.weight(1f).width(4.dp).clip(RoundedCornerShape(2.dp)).background(m.line)) {
            androidx.compose.foundation.layout.BoxWithConstraints(Modifier.fillMaxSize()) {
                Box(
                    Modifier
                        .offset(y = maxHeight * thumbStart.coerceIn(0f, 1f))
                        .width(4.dp)
                        .height(maxHeight * thumbSize.coerceIn(0.05f, 1f))
                        .clip(RoundedCornerShape(2.dp))
                        .background(m.muted),
                )
            }
        }
        if (az != null) {
            Box(
                Modifier.padding(vertical = 8.dp).size(64.dp).clip(CircleShape).background(m.chip.copy(alpha = 0.1f / 0.08f * m.chip.alpha))
                    .pressable(onClickLabel = "Pular por letra", onClick = az),
                contentAlignment = Alignment.Center,
            ) { Text("A–Z", fontSize = 20.sp, fontWeight = FontWeight.Medium, color = m.text) }
        }
        RoundIcon(LauncherIcons.ChevronDown, "Rolar para baixo", onDown, tint = if (canDown) m.text else m.railIdle)
    }
}

/** Mini player fixo embaixo da navegação (R18.10), no lugar do botão flutuante do Android Auto. */
@Composable
fun MiniPlayer(np: NowPlaying, controls: MediaControls?, accent: Color, onOpen: () -> Unit, modifier: Modifier = Modifier) {
    val m = mediaPalette()
    val position = rememberPosition(np)
    Box(modifier.fillMaxWidth().height(104.dp).clip(RoundedCornerShape(20.dp)).background(m.mini)) {
        if (np.hasProgress) ThinProgress(np.fraction(position), accent, m.track, Modifier.align(Alignment.TopStart))
        Row(
            Modifier.fillMaxSize().padding(start = 12.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Row(
                Modifier.weight(1f).fillMaxHeight().pressable(onClickLabel = "Abrir tocando agora", onClick = onOpen),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(18.dp),
            ) {
                NowPlayingArt(np, 72.dp, 12.dp, shadow = false)
                Column(Modifier.weight(1f)) {
                    Text(np.title.ifBlank { "Sem título" }, fontSize = 24.sp, color = m.text, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    Text(np.artist, fontSize = 20.sp, color = m.subtle, maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
            }
            RoundIcon(LauncherIcons.Previous, "Faixa anterior", { controls?.previous() }, iconSize = 36.dp, enabled = np.canPrevious)
            ShapePlayButton(
                np.playing, np.buffering, 88.dp, 76.dp, 20.dp, 38.dp, 36.dp,
                onClick = { if (np.playing) controls?.pause() else controls?.play() },
            )
            RoundIcon(LauncherIcons.Next, "Próxima faixa", { controls?.next() }, iconSize = 36.dp, enabled = np.canNext)
        }
    }
}

/** Aviso curto (resultado de ação de navegação), some sozinho. */
@Composable
fun BoxScope.MediaToast(message: String?, onDone: () -> Unit) {
    if (message == null) return
    LaunchedEffect(message) { delay(2_500); onDone() }
    Box(
        Modifier.align(Alignment.BottomCenter).padding(bottom = 128.dp).height(56.dp).clip(CircleShape)
            .background(Color(0xFF3A3C3B)).padding(horizontal = 24.dp),
        contentAlignment = Alignment.Center,
    ) { Text(message, fontSize = 20.sp, color = Color(0xFFE3E3E3)) }
}

/** Linha tracejada (limite em movimento). */
fun Modifier.dashedBorder(color: Color, radius: Dp, width: Dp = 2.dp): Modifier = this.then(
    Modifier.drawBehindDashed(color, radius, width),
)

private fun Modifier.drawBehindDashed(color: Color, radius: Dp, width: Dp): Modifier = this.drawBehind {
    val w = width.toPx()
    drawRoundRect(
        color = color,
        topLeft = Offset(w / 2, w / 2),
        size = Size(size.width - w, size.height - w),
        cornerRadius = CornerRadius(radius.toPx()),
        style = Stroke(width = w, pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 8f))),
    )
}

/** Linha de botões centrada com distribuição igual (barra de 5 posições). */
@Composable
fun SlotsRow(width: Dp, modifier: Modifier = Modifier, content: @Composable RowScope.() -> Unit) {
    Row(modifier.width(width), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically, content = content)
}

val MediaTitleStyle = TextStyle(fontWeight = FontWeight.Normal)
