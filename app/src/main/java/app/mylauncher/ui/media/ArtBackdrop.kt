package app.mylauncher.ui.media

import android.graphics.Bitmap
import android.util.LruCache
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import app.mylauncher.data.media.NowPlaying
import app.mylauncher.ui.theme.LauncherColors
import app.mylauncher.ui.theme.LocalLauncherColors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Desfoque da capa feito na CPU: Modifier.blur só existe a partir do Android 12 e a
 * multimídia é Android 10. Reduz a capa e aplica box blur (3 passadas ≈ gaussiano).
 */
object FrostedArt {
    // Desfoque leve: a capa continua reconhecível, com aspecto de vidro fosco.
    private const val SIZE = 160
    private const val RADIUS = 3
    private val cache = LruCache<String, ImageBitmap>(16)

    fun cached(key: String): ImageBitmap? = cache.get(key)

    fun render(key: String, src: Bitmap): ImageBitmap = cache.get(key) ?: run {
        val small = Bitmap.createScaledBitmap(squareCrop(trimDarkBars(src)), SIZE, SIZE, true)
        val px = IntArray(SIZE * SIZE)
        small.getPixels(px, 0, SIZE, 0, 0, SIZE, SIZE)
        repeat(2) { boxBlur(px, SIZE, SIZE, RADIUS) }
        val out = Bitmap.createBitmap(px, SIZE, SIZE, Bitmap.Config.ARGB_8888).asImageBitmap()
        if (small !== src) small.recycle()
        cache.put(key, out)
        out
    }

    /**
     * Remove faixas pretas (miniaturas 16:9 de vídeos, como no YouTube Music) comparando o
     * brilho médio de linhas/colunas das bordas.
     */
    internal fun trimDarkBars(src: Bitmap): Bitmap {
        val w = src.width
        val h = src.height
        if (w < 8 || h < 8) return src
        fun rowDark(y: Int): Boolean = (0 until w step maxOf(1, w / 32)).all { x -> luma(src.getPixel(x, y)) < 18 }
        fun colDark(x: Int): Boolean = (0 until h step maxOf(1, h / 32)).all { y -> luma(src.getPixel(x, y)) < 18 }
        var top = 0
        while (top < h / 3 && rowDark(top)) top++
        var bottom = h - 1
        while (bottom > h * 2 / 3 && rowDark(bottom)) bottom--
        var left = 0
        while (left < w / 3 && colDark(left)) left++
        var right = w - 1
        while (right > w * 2 / 3 && colDark(right)) right--
        val cw = right - left + 1
        val ch = bottom - top + 1
        return if (cw == w && ch == h) src else Bitmap.createBitmap(src, left, top, cw, ch)
    }

    /** Recorte quadrado central (sem esticar). */
    internal fun squareCrop(src: Bitmap): Bitmap {
        val side = minOf(src.width, src.height)
        if (src.width == src.height) return src
        return Bitmap.createBitmap(src, (src.width - side) / 2, (src.height - side) / 2, side, side)
    }

    private fun luma(c: Int): Int = (((c shr 16) and 0xFF) * 3 + ((c shr 8) and 0xFF) * 6 + (c and 0xFF)) / 10

    /** Box blur separável (horizontal + vertical) in-place. */
    private fun boxBlur(px: IntArray, w: Int, h: Int, r: Int) {
        val tmp = IntArray(px.size)
        blurPass(px, tmp, w, h, r, horizontal = true)
        blurPass(tmp, px, w, h, r, horizontal = false)
    }

    private fun blurPass(src: IntArray, dst: IntArray, w: Int, h: Int, r: Int, horizontal: Boolean) {
        val lines = if (horizontal) h else w
        val len = if (horizontal) w else h
        val div = 2 * r + 1
        for (line in 0 until lines) {
            var a = 0; var rr = 0; var g = 0; var b = 0
            fun idx(i: Int): Int {
                val p = i.coerceIn(0, len - 1)
                return if (horizontal) line * w + p else p * w + line
            }
            for (i in -r..r) {
                val c = src[idx(i)]
                a += c ushr 24; rr += (c shr 16) and 0xFF; g += (c shr 8) and 0xFF; b += c and 0xFF
            }
            for (i in 0 until len) {
                dst[idx(i)] = ((a / div) shl 24) or ((rr / div) shl 16) or ((g / div) shl 8) or (b / div)
                val outC = src[idx(i - r)]
                val inC = src[idx(i + r + 1)]
                a += (inC ushr 24) - (outC ushr 24)
                rr += ((inC shr 16) and 0xFF) - ((outC shr 16) and 0xFF)
                g += ((inC shr 8) and 0xFF) - ((outC shr 8) and 0xFF)
                b += (inC and 0xFF) - (outC and 0xFF)
            }
        }
    }
}

private fun artKey(np: NowPlaying) = "${np.sourcePackage}|${np.title}|${np.artist}"

@Composable
private fun rememberFrostedArt(np: NowPlaying?): ImageBitmap? {
    val key = np?.let(::artKey)
    val bmp by produceState(key?.let(FrostedArt::cached), key) {
        val art = np?.art ?: run { value = null; return@produceState }
        if (value == null) value = withContext(Dispatchers.Default) { FrostedArt.render(key!!, art) }
    }
    return bmp
}

/** Cores do conteúdo sobre a capa: sempre claras, sobre película escura. */
private fun LauncherColors.onArt() = copy(
    onSurface = Color(0xFFF5F6F7),
    onSurfaceVariant = Color(0xFFD7DADD),
    outline = Color.White.copy(alpha = 0.28f),
    surface2 = Color.White.copy(alpha = 0.14f),
    primary = Color(0xFFF5F6F7),
    onPrimary = Color(0xFF16181B),
)

/**
 * Fundo de vidro fosco com a capa (estilo Android Auto): capa desfocada cobrindo o card,
 * película escura em degradê para legibilidade e um fio de luz na borda. Sem capa, usa [fallback].
 */
@Composable
fun FrostedArtCard(
    np: NowPlaying?,
    shape: Shape,
    fallback: Color,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.(hasArt: Boolean) -> Unit,
) {
    val c = LocalLauncherColors.current
    val frosted = rememberFrostedArt(np)
    Box(modifier.clip(shape).background(fallback)) {
        Crossfade(frosted, animationSpec = tween(500), label = "frostedArt") { img ->
            if (img != null) {
                Box(Modifier.fillMaxSize()) {
                    Image(
                        img, contentDescription = null, contentScale = ContentScale.Crop,
                        filterQuality = FilterQuality.High, modifier = Modifier.fillMaxSize(),
                    )
                    Box(
                        Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    0f to Color.Black.copy(alpha = 0.22f),
                                    0.45f to Color.Black.copy(alpha = 0.42f),
                                    1f to Color.Black.copy(alpha = 0.74f),
                                ),
                            ),
                    )
                }
            }
        }
        if (frosted != null) {
            Box(Modifier.fillMaxSize().border(1.dp, Color.White.copy(alpha = 0.10f), shape))
        }
        CompositionLocalProvider(LocalLauncherColors provides if (frosted != null) c.onArt() else c) {
            content(frosted != null)
        }
    }
}
