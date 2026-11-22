package app.mylauncher.ui.media

import android.util.LruCache
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.palette.graphics.Palette
import app.mylauncher.data.media.NowPlaying
import app.mylauncher.ui.theme.LocalLauncherColors
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
    // Inclui a identidade da capa (chega depois do título) e, como no fundo fosco, nunca reaproveita
    // o valor da chave anterior que o produceState mantém ao trocar de faixa.
    val key = np?.art?.let { art -> "${np.sourcePackage}|${np.title}|${np.artist}|${System.identityHashCode(art)}|${art.generationId}|${c.isNight}" }
    val swatch by produceState(key?.let(ArtTintCache::get), key) {
        val art = np?.art
        if (key == null || art == null) {
            value = null
            return@produceState
        }
        ArtTintCache.get(key)?.let { value = it; return@produceState }
        value = withContext(Dispatchers.Default) {
            val p = Palette.from(art).maximumColorCount(16).generate()
            if (c.isNight) p.getDarkMutedColor(p.getDarkVibrantColor(p.getDominantColor(0)))
            else p.getLightMutedColor(p.getLightVibrantColor(p.getDominantColor(0)))
        }.also { color -> if (key != null) ArtTintCache.put(key, color) }
    }
    val target = swatch?.takeIf { it != 0 }?.let { lerp(c.surface, Color(it), if (c.isNight) 0.55f else 0.45f) } ?: c.surface
    return animateColorAsState(target, tween(500), label = "artTint").value
}

