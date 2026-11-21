package app.mylauncher.ui.media

import android.os.SystemClock
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import app.mylauncher.data.media.NowPlaying
import app.mylauncher.ui.icons.LauncherIcons
import app.mylauncher.ui.theme.LocalLauncherColors
import kotlinx.coroutines.delay

/**
 * Posição atual, interpolada localmente uma vez por segundo enquanto toca (R4.8).
 * Uma vez por segundo, como no Android Auto: evita redesenhar a interface continuamente.
 */
@Composable
fun rememberPosition(np: NowPlaying): Long {
    var now by remember { mutableLongStateOf(SystemClock.elapsedRealtime()) }
    LaunchedEffect(np.playing, np.positionMs, np.positionUpdatedAt) {
        while (true) {
            now = SystemClock.elapsedRealtime()
            if (!np.playing) break
            delay(1_000)
        }
    }
    return np.positionAt(now)
}

@Composable
fun AlbumArt(np: NowPlaying?, size: Dp, radius: Dp, iconSize: Dp) {
    val c = LocalLauncherColors.current
    val bitmap = remember(np?.art) { np?.art?.asImageBitmap() }
    Box(
        Modifier.size(size).clip(RoundedCornerShape(radius)).background(c.surface2),
        contentAlignment = Alignment.Center,
    ) {
        if (bitmap != null) {
            Image(bitmap, contentDescription = "Capa", contentScale = ContentScale.Crop, modifier = Modifier.size(size))
        } else {
            Icon(LauncherIcons.Music, null, Modifier.size(iconSize), tint = c.onSurfaceVariant)
        }
    }
}

@Composable
fun ProgressBar(fraction: Float, height: Dp = 4.dp, modifier: Modifier = Modifier) {
    val c = LocalLauncherColors.current
    Box(modifier.fillMaxWidth().height(height).clip(RoundedCornerShape(height)).background(c.outline)) {
        Box(
            Modifier
                .fillMaxHeight()
                .fillMaxWidth(fraction.coerceIn(0f, 1f))
                .clip(RoundedCornerShape(height))
                .background(c.onSurface),
        )
    }
}

fun NowPlaying.fraction(position: Long): Float = if (durationMs > 0) position.toFloat() / durationMs else 0f
