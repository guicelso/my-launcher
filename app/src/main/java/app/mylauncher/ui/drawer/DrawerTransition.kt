package app.mylauncher.ui.drawer

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterExitState
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.lerp
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import app.mylauncher.data.prefs.DrawerAnimation
import app.mylauncher.ui.workspace.MOTION_STIFFNESS
import kotlin.math.hypot

/** Posição do botão da gaveta na tela, usada como origem das animações que "nascem" dele. */
class DrawerAnchor {
    var bounds by mutableStateOf<Rect?>(null)
}

/**
 * Abertura/fechamento da gaveta no estilo escolhido nos Ajustes. CONTAINER é o padrão canônico do
 * launcher: o card cresce a partir do botão (mesma mola da área de trabalho, spec 010).
 */
@Composable
fun AnimatedDrawer(
    visible: Boolean,
    style: DrawerAnimation,
    anchor: DrawerAnchor,
    content: @Composable () -> Unit,
) {
    var hostOrigin by remember { mutableStateOf(Offset.Zero) }
    Box(Modifier.fillMaxSize().onGloballyPositioned { hostOrigin = it.positionInRoot() }) {
        when (style) {
            DrawerAnimation.NONE -> if (visible) content()
            DrawerAnimation.FADE_SCALE -> AnimatedVisibility(
                visible,
                enter = fadeIn(tween(220)) + scaleIn(tween(220), initialScale = 0.96f),
                exit = fadeOut(tween(160)) + scaleOut(tween(160), targetScale = 0.98f),
            ) { content() }
            DrawerAnimation.SLIDE_SIDE -> AnimatedVisibility(
                visible,
                enter = slideInHorizontally(spring(Spring.DampingRatioNoBouncy, MOTION_STIFFNESS)) { -it / 3 } + fadeIn(tween(200)),
                exit = slideOutHorizontally(tween(200)) { -it / 4 } + fadeOut(tween(160)),
            ) { content() }
            DrawerAnimation.SLIDE_UP -> AnimatedVisibility(
                visible,
                enter = slideInVertically(spring(Spring.DampingRatioNoBouncy, MOTION_STIFFNESS)) { it / 3 } + fadeIn(tween(200)),
                exit = slideOutVertically(tween(200)) { it / 4 } + fadeOut(tween(160)),
            ) { content() }
            DrawerAnimation.CONTAINER, DrawerAnimation.CIRCULAR -> AnimatedVisibility(
                visible,
                enter = EnterTransition.None,
                exit = ExitTransition.None,
            ) {
                val progress by transition.animateFloat(
                    transitionSpec = {
                        if (targetState == EnterExitState.Visible) {
                            spring(Spring.DampingRatioNoBouncy, MOTION_STIFFNESS)
                        } else {
                            spring(Spring.DampingRatioNoBouncy, MOTION_STIFFNESS * 1.6f)
                        }
                    },
                    label = "drawerReveal",
                ) { if (it == EnterExitState.Visible) 1f else 0f }
                val origin = anchor.bounds?.translate(-hostOrigin)
                val density = LocalDensity.current
                val shape = remember(origin, style) { RevealShape(origin, style == DrawerAnimation.CIRCULAR, density) }
                Box(
                    Modifier
                        .fillMaxSize()
                        .graphicsLayer {
                            shape.progress = progress
                            this.shape = shape
                            clip = true
                        },
                ) {
                    // O conteúdo acompanha: leve escala a partir da origem e surge no início do movimento.
                    Box(
                        Modifier
                            .fillMaxSize()
                            .graphicsLayer {
                                val p = progress
                                alpha = ((p - 0.05f) / 0.45f).coerceIn(0f, 1f)
                                val s = 0.94f + 0.06f * p
                                scaleX = s
                                scaleY = s
                                if (origin != null && size.width > 0f) {
                                    transformOrigin = TransformOrigin(origin.center.x / size.width, origin.center.y / size.height)
                                }
                            },
                    ) { content() }
                }
            }
        }
    }
}

/**
 * Recorte animado: retângulo arredondado que vai do botão até a tela inteira (CONTAINER) ou
 * círculo que cresce a partir do botão (CIRCULAR). [progress] é lido no desenho, sem recompor.
 */
private class RevealShape(private val origin: Rect?, private val circular: Boolean, density: Density) : Shape {
    var progress by mutableStateOf(0f)
    private val startRadius = with(density) { 24.dp.toPx() }
    private val endRadius = with(density) { 28.dp.toPx() }

    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
        val full = Rect(Offset.Zero, size)
        val from = origin ?: Rect(Offset(0f, size.height / 2f), Size(0f, 0f))
        val p = progress.coerceIn(0f, 1f)
        return if (circular) {
            val c = from.center
            val maxR = maxOf(
                hypot(c.x, c.y), hypot(size.width - c.x, c.y),
                hypot(c.x, size.height - c.y), hypot(size.width - c.x, size.height - c.y),
            )
            val r = from.minDimension / 2f + (maxR - from.minDimension / 2f) * p
            Outline.Generic(Path().apply { addOval(Rect(c, r)) })
        } else {
            val rect = lerp(from, full, p)
            val radius = startRadius + (endRadius - startRadius) * p
            Outline.Rounded(RoundRect(rect, CornerRadius(radius)))
        }
    }
}
