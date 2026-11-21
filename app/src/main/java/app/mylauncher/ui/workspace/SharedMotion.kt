package app.mylauncher.ui.workspace

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.BoundsTransform
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Shape

/**
 * Padrão canônico de movimento da área de trabalho (spec 010): o card cresce a partir de onde
 * está e seus elementos (capa, títulos, controles) viajam até a nova posição.
 */
@OptIn(ExperimentalSharedTransitionApi::class)
val LocalSharedScope = compositionLocalOf<SharedTransitionScope?> { null }
val LocalPanelScope = compositionLocalOf<AnimatedVisibilityScope?> { null }

/** Mesma mola do painel: sem quique, ~350 ms. */
const val MOTION_STIFFNESS = 380f

@OptIn(ExperimentalSharedTransitionApi::class)
val MotionBounds = BoundsTransform { _: Rect, _: Rect ->
    spring(dampingRatio = Spring.DampingRatioNoBouncy, stiffness = MOTION_STIFFNESS)
}

/**
 * Marca um elemento compartilhado entre layouts. `container = true` para o card inteiro
 * (remede os filhos nos limites animados); senão o conteúdo é escalado até os novos limites.
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun Modifier.sharedMotion(key: String, container: Boolean = false, clip: Shape? = null): Modifier {
    val shared = LocalSharedScope.current ?: return this
    val panel = LocalPanelScope.current ?: return this
    return with(shared) {
        val state = rememberSharedContentState(key)
        if (container && clip != null) {
            this@sharedMotion.sharedBounds(
                state, panel,
                boundsTransform = MotionBounds,
                resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds,
                clipInOverlayDuringTransition = OverlayClip(clip),
            )
        } else if (container) {
            this@sharedMotion.sharedBounds(
                state, panel,
                boundsTransform = MotionBounds,
                resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds,
            )
        } else {
            // Re-mede o conteúdo nos limites animados em vez de escalá-lo (texto não estica).
            this@sharedMotion.sharedBounds(
                state, panel,
                boundsTransform = MotionBounds,
                resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds,
            )
        }
    }
}
