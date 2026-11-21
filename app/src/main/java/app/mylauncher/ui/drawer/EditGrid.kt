package app.mylauncher.ui.drawer

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round
import androidx.compose.ui.unit.toOffset
import androidx.compose.ui.zIndex
import app.mylauncher.data.apps.AppEntry
import app.mylauncher.data.apps.AppsRepository
import app.mylauncher.data.prefs.AppOrdering
import app.mylauncher.ui.components.AppIcon
import app.mylauncher.ui.components.pressable
import app.mylauncher.ui.icons.LauncherIcons
import app.mylauncher.ui.theme.LocalLauncherColors

/**
 * Modo de edição da gaveta (spec 011, R11.4): toque fixa/desafixa na barra; toque longo + arrastar
 * reordena, com os vizinhos se reacomodando com mola. A nova ordem é salva ao soltar.
 */
@Composable
fun EditGrid(
    apps: AppsRepository,
    entries: List<AppEntry>,
    pinned: List<String>,
    onTogglePin: (String) -> Unit,
    onCommitOrder: (List<String>) -> Unit,
    modifier: Modifier = Modifier,
) {
    val c = LocalLauncherColors.current
    val gridState = rememberLazyGridState()
    var order by remember { mutableStateOf(entries.map { it.packageName }) }
    LaunchedEffect(entries) {
        // Apps instalados/removidos enquanto edita: mantém a ordem atual e ajusta o conjunto.
        val current = entries.map { it.packageName }.toSet()
        order = order.filter { it in current } + entries.map { it.packageName }.filter { it !in order }
    }
    val byPkg = remember(entries) { entries.associateBy { it.packageName } }
    var dragging by remember { mutableStateOf<String?>(null) }
    var dragDelta by remember { mutableStateOf(Offset.Zero) }
    val commit by rememberUpdatedState(onCommitOrder)

    fun itemAt(pos: Offset) = gridState.layoutInfo.visibleItemsInfo.firstOrNull {
        IntRect(it.offset, it.size).contains(pos.round())
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(6),
        state = gridState,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.pointerInput(Unit) {
            detectDragGesturesAfterLongPress(
                onDragStart = { pos ->
                    dragging = itemAt(pos)?.key as? String
                    dragDelta = Offset.Zero
                },
                onDrag = { change, amount ->
                    change.consume()
                    val key = dragging ?: return@detectDragGesturesAfterLongPress
                    dragDelta += amount
                    val info = gridState.layoutInfo.visibleItemsInfo.firstOrNull { it.key == key } ?: return@detectDragGesturesAfterLongPress
                    val center = info.offset.toOffset() + Offset(info.size.width / 2f, info.size.height / 2f) + dragDelta
                    val target = itemAt(center)?.takeIf { it.key != key } ?: return@detectDragGesturesAfterLongPress
                    val from = order.indexOf(key)
                    val to = order.indexOf(target.key as String)
                    order = AppOrdering.move(order, from, to)
                    // O item passa a ocupar o lugar do alvo: compensa para continuar sob o dedo.
                    dragDelta += (info.offset - target.offset).toOffset()
                },
                onDragEnd = {
                    commit(order)
                    dragging = null
                    dragDelta = Offset.Zero
                },
                onDragCancel = {
                    dragging = null
                    dragDelta = Offset.Zero
                },
            )
        },
    ) {
        items(order, key = { it }) { pkg ->
            val entry = byPkg[pkg] ?: return@items
            val isDragging = pkg == dragging
            val isPinned = pkg in pinned
            Column(
                Modifier
                    .animateItem(placementSpec = spring(dampingRatio = Spring.DampingRatioNoBouncy, stiffness = Spring.StiffnessMediumLow))
                    .zIndex(if (isDragging) 1f else 0f)
                    .graphicsLayer {
                        if (isDragging) {
                            translationX = dragDelta.x
                            translationY = dragDelta.y
                            scaleX = 1.1f
                            scaleY = 1.1f
                            shadowElevation = 16f
                        }
                    }
                    .height(176.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(if (isDragging) c.surface2 else androidx.compose.ui.graphics.Color.Transparent)
                    .pressable(onClickLabel = if (isPinned) "Desafixar" else "Fixar") { onTogglePin(pkg) },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Box {
                    AppIcon(apps, pkg, 96.dp)
                    Box(
                        Modifier
                            .align(Alignment.TopEnd)
                            .offset { IntOffset(6.dp.roundToPx(), (-6).dp.roundToPx()) }
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(if (isPinned) c.primary else c.surface2),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            if (isPinned) LauncherIcons.Check else LauncherIcons.Pin,
                            if (isPinned) "Fixado" else "Fixar",
                            Modifier.size(22.dp),
                            tint = if (isPinned) c.onPrimary else c.onSurface,
                        )
                    }
                }
                Text(
                    entry.label,
                    style = MaterialTheme.typography.bodyMedium,
                    color = c.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().padding(top = 12.dp, start = 4.dp, end = 4.dp),
                )
            }
        }
    }
}
