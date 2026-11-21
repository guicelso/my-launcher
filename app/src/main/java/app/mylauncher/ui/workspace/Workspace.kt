package app.mylauncher.ui.workspace

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import app.mylauncher.nav.LocalMapCanvasSize
import app.mylauncher.nav.NavigationPane
import app.mylauncher.nav.PaneMode
import app.mylauncher.ui.Screen
import app.mylauncher.ui.theme.Dimens

/** Larguras do painel lateral por modo (spec 010). */
object PanelWidths {
    val dashboard = 408.dp
    val split = 600.dp
}

private fun <T> panelSpring() = spring<T>(dampingRatio = Spring.DampingRatioNoBouncy, stiffness = MOTION_STIFFNESS)

/**
 * Área de trabalho (spec 010): o mapa é composto uma única vez e o painel lateral muda de
 * largura com mola. Cards crescem a partir de onde estão (elementos compartilhados) e o vizinho
 * encolhe: Painel ⇄ Dividido ⇄ Mídia em tela cheia, e Painel ⇄ Mapa em tela cheia.
 * [active] = false quando há uma tela por cima ou o mapa está oculto (renderização pausada).
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun Workspace(
    mode: Screen,
    active: Boolean,
    navPane: NavigationPane,
    onExpandMap: () -> Unit,
    dashboardPanel: @Composable (Modifier) -> Unit,
    playerPanel: @Composable (full: Boolean, Modifier) -> Unit,
    modifier: Modifier = Modifier,
    /** Recuo à esquerda: pequeno colado na barra vertical; o espaço padrão nos outros casos. */
    startInset: Dp = 8.dp,
) {
    val mapFull = mode == Screen.MAP
    val mediaFull = mode == Screen.MEDIA

    BoxWithConstraints(modifier.fillMaxSize()) {
        val start = if (mapFull) 0.dp else startInset
        val outerTarget = if (mapFull) 0.dp else Dimens.gap
        val available = maxWidth - start - outerTarget
        val target: Dp = when (mode) {
            Screen.SPLIT_MEDIA -> PanelWidths.split
            Screen.MEDIA -> available
            Screen.MAP -> 0.dp
            else -> PanelWidths.dashboard
        }
        val panelWidth by animateDpAsState(target, panelSpring(), label = "panel")
        val outer by animateDpAsState(outerTarget, panelSpring(), label = "outer")
        val gap by animateDpAsState(if (mapFull || mediaFull) 0.dp else Dimens.gap, panelSpring(), label = "gap")
        val endRadius by animateDpAsState(if (mapFull) 0.dp else Dimens.cardRadius, panelSpring(), label = "radius")

        val mapCanvas = DpSize(maxWidth, maxHeight)
        SharedTransitionLayout {
            CompositionLocalProvider(LocalSharedScope provides this, LocalMapCanvasSize provides mapCanvas) {
                Row(Modifier.fillMaxSize().padding(start = start, top = outer, end = outer, bottom = outer)) {
                    Box(
                        Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(topStart = Dimens.cardRadius, bottomStart = Dimens.cardRadius, topEnd = endRadius, bottomEnd = endRadius)),
                        contentAlignment = Alignment.Center,
                    ) {
                        navPane.Content(
                            mode = if (mapFull) PaneMode.FULL else PaneMode.CARD,
                            modifier = Modifier.fillMaxSize(),
                            active = active && !mediaFull,
                            onExpand = onExpandMap,
                        )
                    }
                    Spacer(Modifier.width(gap))
                    Box(Modifier.width(panelWidth).fillMaxHeight(), contentAlignment = Alignment.CenterStart) {
                        AnimatedContent(
                            targetState = mode,
                            // Dividido e tela cheia são o MESMO player: ele só cresce e se reorganiza.
                            contentKey = { if (it == Screen.SPLIT_MEDIA || it == Screen.MEDIA) Screen.SPLIT_MEDIA else it },
                            // Os elementos compartilhados fazem o movimento; o resto só esmaece.
                            transitionSpec = { fadeIn(tween(220, delayMillis = 80)).togetherWith(fadeOut(tween(120))) },
                            contentAlignment = Alignment.CenterStart,
                            label = "panelContent",
                        ) { m ->
                            CompositionLocalProvider(LocalPanelScope provides this) {
                                when (m) {
                                    Screen.SPLIT_MEDIA -> playerPanel(false, Modifier.fillMaxSize())
                                    Screen.MEDIA -> playerPanel(true, Modifier.fillMaxSize())
                                    Screen.MAP -> Box(Modifier.fillMaxSize())
                                    else -> dashboardPanel(Modifier.requiredWidth(PanelWidths.dashboard).fillMaxHeight())
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
