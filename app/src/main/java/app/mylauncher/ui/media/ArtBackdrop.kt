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

    fun render(key: String, src: Bitmap): ImageBitmap = cache.get(key) ?: blurred(src, SIZE, RADIUS, 2).also { cache.put(key, it) }

    /**
     * Desfoque forte (fundo de capa do player de 2026, spec 018): a capa vira manchas de cor,
     * como o `blur(60px)` dos wireframes. Reduzir para 40 px já faz quase todo o trabalho.
     */
    fun renderStrong(key: String, src: Bitmap): ImageBitmap = cache.get("s|$key") ?: blurred(src, STRONG_SIZE, 3, 3).also { cache.put("s|$key", it) }

    fun cachedStrong(key: String): ImageBitmap? = cache.get("s|$key")

    private fun blurred(src: Bitmap, size: Int, radius: Int, passes: Int): ImageBitmap {
        val small = Bitmap.createScaledBitmap(squareCrop(trimDarkBars(src)), size, size, true)
        val px = IntArray(size * size)
        small.getPixels(px, 0, size, 0, 0, size, size)
        repeat(passes) { boxBlur(px, size, size, radius) }
        val out = Bitmap.createBitmap(px, size, size, Bitmap.Config.ARGB_8888).asImageBitmap()
        if (small !== src) small.recycle()
        return out
    }

    private const val STRONG_SIZE = 40

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
