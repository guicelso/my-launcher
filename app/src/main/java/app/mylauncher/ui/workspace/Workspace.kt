package app.mylauncher.ui.workspace

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import app.mylauncher.nav.NavigationPane
import app.mylauncher.nav.PaneMode
import app.mylauncher.ui.Screen
import app.mylauncher.ui.theme.Dimens

/** Larguras do painel lateral por modo (spec 010). */
object PanelWidths {
    val dashboard = 408.dp
    val split = 600.dp
}

/** Spring sem quique, ~350 ms (R10.2). */
private fun <T> panelSpring() = spring<T>(dampingRatio = Spring.DampingRatioNoBouncy, stiffness = 380f)

/**
 * Área de trabalho (spec 010, R10.1–R10.5): o mapa é composto uma única vez e o painel lateral
 * muda de largura com animação. [active] = false quando há uma tela por cima (mapa pausado).
 */
@Composable
fun Workspace(
    mode: Screen,
    active: Boolean,
    navPane: NavigationPane,
    onExpandMap: () -> Unit,
    dashboardPanel: @Composable (Modifier) -> Unit,
    splitPanel: @Composable (Modifier) -> Unit,
    modifier: Modifier = Modifier,
) {
    val full = mode == Screen.MAP
    val target: Dp = when (mode) {
        Screen.SPLIT_MEDIA -> PanelWidths.split
        Screen.MAP -> 0.dp
        else -> PanelWidths.dashboard
    }
    val panelWidth by animateDpAsState(target, panelSpring(), label = "panel")
    val outer by animateDpAsState(if (full) 0.dp else Dimens.gap, panelSpring(), label = "outer")
    val gap by animateDpAsState(if (full) 0.dp else Dimens.gap, panelSpring(), label = "gap")
    val endRadius by animateDpAsState(if (full) 0.dp else Dimens.cardRadius, panelSpring(), label = "radius")

    Row(modifier.fillMaxSize().padding(start = if (full) 0.dp else 8.dp, top = outer, end = outer, bottom = outer)) {
        Box(
            Modifier
                .weight(1f)
                .fillMaxHeight()
                .clip(RoundedCornerShape(topStart = Dimens.cardRadius, bottomStart = Dimens.cardRadius, topEnd = endRadius, bottomEnd = endRadius)),
        ) {
            navPane.Content(
                mode = if (full) PaneMode.FULL else PaneMode.CARD,
                modifier = Modifier.fillMaxSize(),
                active = active,
                onExpand = onExpandMap,
            )
        }
        Spacer(Modifier.width(gap))
        Box(Modifier.width(panelWidth).fillMaxHeight().clipToBounds(), contentAlignment = Alignment.CenterStart) {
            AnimatedContent(
                targetState = mode,
                transitionSpec = {
                    (fadeIn(tween(260, delayMillis = 60)) + scaleIn(tween(260, delayMillis = 60), initialScale = 0.96f))
                        .togetherWith(fadeOut(tween(140)))
                },
                contentAlignment = Alignment.CenterStart,
                label = "panelContent",
            ) { m ->
                when (m) {
                    Screen.SPLIT_MEDIA -> splitPanel(Modifier.requiredWidth(PanelWidths.split).fillMaxHeight())
                    Screen.MAP -> Box(Modifier.fillMaxSize())
                    else -> dashboardPanel(Modifier.requiredWidth(PanelWidths.dashboard).fillMaxHeight())
                }
            }
        }
    }
}
