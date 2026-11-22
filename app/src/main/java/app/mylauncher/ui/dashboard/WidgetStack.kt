@file:OptIn(androidx.compose.animation.ExperimentalSharedTransitionApi::class)

package app.mylauncher.ui.dashboard

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import app.mylauncher.data.prefs.WidgetType
import app.mylauncher.ui.components.interceptLongPress
import app.mylauncher.ui.icons.LauncherIcons
import app.mylauncher.ui.theme.Dimens
import app.mylauncher.ui.theme.LocalLauncherColors
import app.mylauncher.ui.workspace.LocalSharedScope
import kotlin.math.abs
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/** Evento que pode trazer um widget para a frente da pilha (spec 017, R17.7). */
sealed interface StackEvent {
    /** Algo aconteceu e [type] é o widget que mostra isso; [label] vai no selo. */
    data class Suggest(val type: WidgetType, val label: String) : StackEvent

    /** Chave ligada: todas as pilhas voltam ao widget principal. */
    data object Wake : StackEvent
}

/** Voltas virtuais do pager circular: o motorista nunca chega ao fim. */
private const val LOOPS = 1000

/** Tempo dos pontos na tela depois de soltar (R17.3) e do selo da sugestão (R17.7). */
private const val DOTS_HOLD_MS = 1500L
private const val HINT_MS = 5000L
private const val WARN_MS = 2500L
private const val SWAP_THRESHOLD = 0.2f

/**
 * Pilha de widgets (spec 017): um espaço do painel com 1 a 7 widgets. Deslizar para os lados
 * troca o widget (rolagem circular), as teclas do volante também, e os pontos indicadores só
 * aparecem enquanto o motorista toca ou arrasta. Artboards "Painel com 2 pilhas" e
 * "Transição ao arrastar".
 */
@Composable
fun WidgetStack(
    types: List<WidgetType>,
    parked: Boolean,
    smart: Boolean,
    events: Flow<StackEvent>,
    onLongPress: (current: WidgetType) -> Unit,
    widget: @Composable (WidgetType, Modifier) -> Unit,
    modifier: Modifier = Modifier,
) {
    val c = LocalLauncherColors.current
    // Widget na tela; sobrevive a mudanças na lista (adicionar, remover, reordenar).
    var shown by remember { mutableStateOf<WidgetType?>(null) }
    var touching by remember { mutableStateOf(false) }
    var focused by remember { mutableStateOf(false) }
    var hint by remember { mutableStateOf<String?>(null) }
    var warn by remember { mutableStateOf(false) }
    // R17.8: depois que o motorista desliza, a sugestão que estava valendo fica muda.
    var lastSuggested by remember { mutableStateOf<WidgetType?>(null) }
    var muted by remember { mutableStateOf<WidgetType?>(null) }
    val currentSmart by rememberUpdatedState(smart)
    val currentParked by rememberUpdatedState(parked)
    val scope = rememberCoroutineScope()
    val shared = LocalSharedScope.current

    LaunchedEffect(hint) { if (hint != null) { delay(HINT_MS); hint = null } }
    LaunchedEffect(warn) { if (warn) { delay(WARN_MS); warn = false } }

    key(types) {
        val n = types.size
        val start = types.indexOf(shown).coerceAtLeast(0)
        val pager = rememberPagerState(initialPage = if (n > 1) n * (LOOPS / 2) + start else 0) { if (n > 1) n * LOOPS else 1 }
        val index = pager.currentPage.mod(n)

        LaunchedEffect(pager) { snapshotFlow { pager.settledPage }.collect { shown = types[it.mod(n)] } }
        LaunchedEffect(pager) {
            pager.interactionSource.interactions.collect {
                if (it is DragInteraction.Start) { muted = lastSuggested; hint = null }
            }
        }
        LaunchedEffect(pager) {
            events.collect { e ->
                when (e) {
                    StackEvent.Wake -> {
                        muted = null; lastSuggested = null; hint = null
                        if (n > 1) pager.scrollToPage(pager.nearest(0, n))
                    }
                    is StackEvent.Suggest -> {
                        if (e.type != muted) muted = null
                        val target = types.indexOf(e.type)
                        if (!currentSmart || muted != null || target < 0) return@collect
                        lastSuggested = e.type
                        if (pager.currentPage.mod(n) != target) {
                            hint = e.label
                            pager.animateScrollToPage(pager.nearest(target, n), animationSpec = tween(420))
                        }
                    }
                }
            }
        }

        // Pontos: aparecem ao tocar, arrastar ou na troca automática e somem 1,5 s depois (R17.3).
        val active = touching || pager.isScrollInProgress
        var dots by remember { mutableStateOf(false) }
        LaunchedEffect(active) { if (active) dots = true else { delay(DOTS_HOLD_MS); dots = false } }

        Box(
            modifier
                .clip(RoundedCornerShape(Dimens.cardRadius))
                .interceptLongPress(true) { if (currentParked) onLongPress(types[pager.currentPage.mod(n)]) else warn = true }
                .trackTouch { touching = it }
                .onFocusChanged { focused = it.isFocused }
                .onKeyEvent { ev ->
                    // Teclas do volante (R17.4): só quando o foco está na própria pilha.
                    if (!focused || n < 2 || ev.type != KeyEventType.KeyDown) return@onKeyEvent false
                    val step = when (ev.key) { Key.DirectionRight -> 1; Key.DirectionLeft -> -1; else -> return@onKeyEvent false }
                    scope.launch { pager.animateScrollToPage(pager.currentPage + step) }
                    true
                }
                .focusable()
                .semantics { stateDescription = "${types[index].label}, ${index + 1} de $n" },
        ) {
            HorizontalPager(
                state = pager,
                // Só o widget visível e os vizinhos ficam compostos (R17.9).
                beyondViewportPageCount = if (n > 1) 1 else 0,
                pageSpacing = Dimens.gap,
                // Troca depois de 20% da largura (ou com um gesto rápido), como pede a R17.2.
                flingBehavior = PagerDefaults.flingBehavior(pager, snapPositionalThreshold = SWAP_THRESHOLD),
                userScrollEnabled = n > 1,
                key = { it },
                modifier = Modifier.fillMaxSize(),
            ) { page ->
                // Só a página assentada participa do movimento compartilhado (card → player, spec 010).
                // Vizinhos com o mesmo widget teriam a mesma chave e seriam desenhados fora da pilha.
                val live = n == 1 || page == pager.settledPage
                CompositionLocalProvider(LocalSharedScope provides if (live) shared else null) {
                    widget(types[page.mod(n)], Modifier.fillMaxSize())
                }
            }

            AnimatedVisibility(
                visible = n > 1 && dots,
                enter = fadeIn(tween(160)),
                exit = fadeOut(tween(260)),
                modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 3.dp),
            ) { StackDots(n, index) }

            AnimatedVisibility(
                visible = hint != null,
                enter = fadeIn(tween(200)) + scaleIn(tween(200), initialScale = 0.9f),
                exit = fadeOut(tween(300)),
                modifier = Modifier.align(Alignment.TopStart).padding(14.dp),
            ) {
                Chip(LauncherIcons.Sparkle, hint.orEmpty(), c.selectedBg, c.onSelected)
            }

            AnimatedVisibility(
                visible = warn,
                enter = fadeIn(tween(160)),
                exit = fadeOut(tween(260)),
                modifier = Modifier.align(Alignment.Center),
            ) {
                Chip(LauncherIcons.Car, "Disponível só com o carro parado", c.surfaceElevated, c.onSurface)
            }

            if (focused) {
                Box(Modifier.matchParentSizeBorder(c.primary))
            }
        }
    }
}

/** Página mais próxima de [page] que mostra o widget [target] (o pager é circular). */
private fun PagerState.nearest(target: Int, n: Int): Int {
    val base = currentPage - currentPage.mod(n)
    return listOf(base + target - n, base + target, base + target + n).minBy { abs(it - currentPage) }
}

/** Observa se há um dedo na pilha sem consumir o toque (o pager e os botões continuam recebendo). */
private fun Modifier.trackTouch(onChange: (Boolean) -> Unit): Modifier = pointerInput(Unit) {
    awaitEachGesture {
        awaitFirstDown(requireUnconsumed = false, pass = PointerEventPass.Initial)
        onChange(true)
        do {
            val event = awaitPointerEvent(PointerEventPass.Initial)
        } while (event.changes.any { it.pressed })
        onChange(false)
    }
}

private fun Modifier.matchParentSizeBorder(color: Color): Modifier =
    fillMaxSize().border(4.dp, color, RoundedCornerShape(Dimens.cardRadius))

/** Pontos de 6 dp numa pílula translúcida; o ativo vira um traço de 18 dp. */
@Composable
private fun StackDots(count: Int, index: Int) {
    Row(
        Modifier
            .clip(RoundedCornerShape(9.dp))
            .background(Color.Black.copy(alpha = 0.32f))
            .padding(horizontal = 8.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        repeat(count) { i ->
            val on = i == index
            val w by animateDpAsState(if (on) 18.dp else 6.dp, tween(200), label = "dot")
            Box(
                Modifier
                    .width(w)
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(if (on) Color.White else Color.White.copy(alpha = 0.55f)),
            )
        }
    }
}

@Composable
private fun Chip(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String, bg: Color, fg: Color) {
    Row(
        Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(bg)
            .padding(start = 12.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Icon(icon, null, Modifier.size(22.dp), tint = fg)
        Text(text, style = MaterialTheme.typography.titleSmall, color = fg, maxLines = 1, overflow = TextOverflow.Ellipsis)
    }
}
