@file:OptIn(androidx.compose.animation.ExperimentalSharedTransitionApi::class)

package app.mylauncher.ui.dashboard

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import app.mylauncher.data.prefs.DashboardLayout
import app.mylauncher.data.prefs.WidgetStacks
import app.mylauncher.data.prefs.WidgetType
import app.mylauncher.ui.icons.LauncherIcons
import app.mylauncher.ui.settings.ToggleRow
import app.mylauncher.ui.theme.Dimens
import app.mylauncher.ui.theme.LocalLauncherColors
import app.mylauncher.ui.workspace.LocalSharedScope

/** Pedido para abrir o editor de uma pilha (spec 017, R17.6). */
data class StackEditRequest(val slot: Int, val adding: Boolean = false)

/** Nome de cada pilha conforme a grade do painel ("Pilha de cima", "Pilha de baixo"…). */
fun stackNames(layout: DashboardLayout): List<String> = when (layout) {
    DashboardLayout.ONE -> listOf("Pilha do painel", "Pilha 2 (oculta)", "Pilha 3 (oculta)")
    DashboardLayout.TWO -> listOf("Pilha de cima", "Pilha de baixo", "Pilha 3 (oculta)")
    DashboardLayout.THREE -> listOf("Pilha de cima", "Pilha de baixo à esquerda", "Pilha de baixo à direita")
}

/** Ícone de cada widget no catálogo. */
fun widgetIcon(type: WidgetType): ImageVector = when (type) {
    WidgetType.MEDIA -> LauncherIcons.Music
    WidgetType.SUGGESTIONS -> LauncherIcons.Pin
    WidgetType.WEATHER -> LauncherIcons.CloudSun
    WidgetType.VEHICLE -> LauncherIcons.Car
    WidgetType.CLOCK -> LauncherIcons.Clock
    WidgetType.NOTIFICATIONS -> LauncherIcons.Bell
    WidgetType.CLOCK_WEATHER -> LauncherIcons.Clock
    WidgetType.WEATHER_DETAILED -> LauncherIcons.Thermometer
    WidgetType.WEATHER_HOURLY -> LauncherIcons.Cloud
    WidgetType.WEATHER_DAILY -> LauncherIcons.Sun
}

private val TileShape = RoundedCornerShape(Dimens.cardRadius)
private val TILE_W = 232.dp
private val TILE_H = 254.dp

/**
 * Editar pilha (R17.6): widgets lado a lado com prévia, remover, setas para mover, card "+",
 * a chave de sugestões inteligentes e "Desfazer pilha". Overlay dentro da janela (sem diálogo,
 * para não mostrar as barras do sistema). Artboards "Editar pilha" e "Adicionar widget à pilha".
 */
@Composable
fun StackEditor(
    stacks: List<List<WidgetType>>,
    request: StackEditRequest,
    names: List<String>,
    /** Quantas pilhas a grade mostra; as outras ficam ocultas e seus widgets contam como livres. */
    visible: Int,
    smart: Boolean,
    onEdit: ((List<List<WidgetType>>) -> List<List<WidgetType>>) -> Unit,
    onSmart: (Boolean) -> Unit,
    onClose: () -> Unit,
    preview: @Composable (WidgetType, Modifier) -> Unit,
    modifier: Modifier = Modifier,
) {
    val c = LocalLauncherColors.current
    val slot = request.slot
    val list = stacks.getOrNull(slot) ?: return
    var adding by remember(request) { mutableStateOf(request.adding) }
    BackHandler { if (adding) adding = false else onClose() }

    Box(modifier.fillMaxSize()) {
        // Fundo escurecido: tocar fora fecha.
        Box(
            Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.6f))
                .clickable(interactionSource = remember { MutableInteractionSource() }, indication = null, onClick = onClose),
        )
        Column(
            Modifier
                .fillMaxSize()
                .padding(start = 8.dp, top = Dimens.gap, end = Dimens.gap, bottom = Dimens.gap)
                .clip(TileShape)
                .background(c.surfaceElevated)
                .clickable(interactionSource = remember { MutableInteractionSource() }, indication = null) {}
                .padding(start = 28.dp, top = 16.dp, end = 24.dp, bottom = 20.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp),
        ) {
            Header(
                title = if (adding) "Adicionar à ${names[slot].lowercase()}" else "Editar pilha",
                subtitle = if (adding) {
                    "Cada widget fica numa pilha só. Os que estão em outra pilha mudam de lugar."
                } else {
                    "${names[slot]} · ${list.size} de ${WidgetStacks.MAX} widgets · o principal aparece ao ligar o carro"
                },
                onBack = if (adding) ({ adding = false }) else null,
                onDone = onClose,
            )
            if (adding) {
                Catalog(stacks, slot, names, visible) { type ->
                    onEdit { WidgetStacks.add(it, slot, type) }
                    adding = false
                }
            } else {
                Tiles(list, onEdit = { change -> onEdit { change(it) } }, slot = slot, preview = preview, onAdd = { adding = true })
                Spacer(Modifier.weight(1f))
                HorizontalDivider(color = c.outline)
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                    Box(Modifier.weight(1f)) {
                        ToggleRow(
                            "Sugestões inteligentes", smart,
                            subtitle = "Mostra sozinha o widget certo: mídia quando a música começa, veículo com bateria fraca, " +
                                "notificações com mensagem nova. Nunca gira por tempo. Vale para todas as pilhas.",
                            onChange = onSmart,
                        )
                    }
                    TextAction("Desfazer pilha", c.danger, enabled = list.size > 1) { onEdit { WidgetStacks.unstack(it, slot) } }
                }
            }
        }
    }
}

@Composable
private fun Header(title: String, subtitle: String, onBack: (() -> Unit)?, onDone: () -> Unit) {
    val c = LocalLauncherColors.current
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        if (onBack != null) RoundIcon(LauncherIcons.Back, "Voltar", c.onSurface, Color.Transparent, Dimens.touch, onClick = onBack)
        Column(Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.headlineSmall, color = c.onSurface)
            Text(subtitle, style = MaterialTheme.typography.bodyMedium, color = c.onSurfaceVariant, maxLines = 2, overflow = TextOverflow.Ellipsis)
        }
        Box(
            Modifier
                .height(64.dp)
                .clip(RoundedCornerShape(32.dp))
                .background(c.primaryContainer)
                .clickable(role = Role.Button, onClick = onDone)
                .padding(horizontal = 30.dp),
            contentAlignment = Alignment.Center,
        ) { Text("Concluído", style = MaterialTheme.typography.titleMedium, color = c.onPrimaryContainer) }
    }
}

@Composable
private fun Tiles(
    list: List<WidgetType>,
    slot: Int,
    onEdit: ((List<List<WidgetType>>) -> List<List<WidgetType>>) -> Unit,
    preview: @Composable (WidgetType, Modifier) -> Unit,
    onAdd: () -> Unit,
) {
    val c = LocalLauncherColors.current
    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        itemsIndexed(list, key = { _, t -> t.name }) { i, type ->
            Column(Modifier.width(TILE_W).animateItem(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Box(
                    Modifier
                        .size(TILE_W, TILE_H)
                        .clip(TileShape)
                        .then(if (i == 0) Modifier.border(3.dp, c.primary, TileShape) else Modifier),
                ) {
                    // Prévia sem movimento compartilhado: o card do painel continua sendo o único com a chave.
                    CompositionLocalProvider(LocalSharedScope provides null) { preview(type, Modifier.fillMaxSize()) }
                    // A prévia é só para ver: bloqueia os toques nos botões do widget.
                    Box(Modifier.fillMaxSize().pointerInput(Unit) { awaitEachGesture { awaitFirstDown().consume() } })
                    if (list.size > 1) {
                        RoundIcon(
                            LauncherIcons.Minus, "Remover ${type.label} da pilha", c.onPrimary, c.danger, 56.dp,
                            Modifier.align(Alignment.TopStart).padding(4.dp),
                        ) { onEdit { WidgetStacks.remove(it, slot, type) } }
                    }
                    if (i == 0) {
                        Text(
                            "Principal",
                            style = MaterialTheme.typography.labelLarge,
                            color = c.onSelected,
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(12.dp)
                                .clip(RoundedCornerShape(15.dp))
                                .background(c.selectedBg)
                                .padding(horizontal = 12.dp, vertical = 5.dp),
                        )
                    }
                }
                Text(
                    type.label, style = MaterialTheme.typography.titleMedium, color = c.onSurface,
                    textAlign = TextAlign.Center, maxLines = 1, overflow = TextOverflow.Ellipsis, modifier = Modifier.fillMaxWidth(),
                )
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    RoundIcon(
                        LauncherIcons.Back, "Mover ${type.label} para a esquerda", c.onSurface, c.surface2, 64.dp,
                        Modifier.alpha(if (i > 0) 1f else 0.35f), enabled = i > 0,
                    ) { onEdit { WidgetStacks.move(it, slot, i, i - 1) } }
                    RoundIcon(
                        LauncherIcons.Chevron, "Mover ${type.label} para a direita", c.onSurface, c.surface2, 64.dp,
                        Modifier.alpha(if (i < list.lastIndex) 1f else 0.35f), enabled = i < list.lastIndex,
                    ) { onEdit { WidgetStacks.move(it, slot, i, i + 1) } }
                }
            }
        }
        if (list.size < WidgetStacks.MAX) {
            item(key = "add") {
                Column(
                    Modifier
                        .size(TILE_W, TILE_H)
                        .clip(TileShape)
                        .border(BorderStroke(3.dp, c.outline), TileShape)
                        .clickable(role = Role.Button, onClick = onAdd),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
                ) {
                    Icon(LauncherIcons.Plus, null, Modifier.size(44.dp), tint = c.primary)
                    Text("Adicionar widget", style = MaterialTheme.typography.titleMedium, color = c.primary)
                }
            }
        }
    }
}

@Composable
private fun Catalog(stacks: List<List<WidgetType>>, slot: Int, names: List<String>, visible: Int, onPick: (WidgetType) -> Unit) {
    val c = LocalLauncherColors.current
    val full = stacks[slot].size >= WidgetStacks.MAX
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(WidgetType.entries, key = { it.name }) { type ->
            val where = WidgetStacks.slotOf(stacks, type)?.takeIf { it == slot || it < visible }
            val here = where == slot
            val enabled = !here && !full
            val status = when {
                here -> "Já está nesta pilha"
                full -> "Pilha cheia (${WidgetStacks.MAX})"
                where != null -> "Na ${names[where].lowercase()} · tocar move para cá"
                else -> "Disponível"
            }
            Column(
                Modifier
                    .height(168.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(c.surfaceRow)
                    .alpha(if (enabled) 1f else 0.45f)
                    .clickable(enabled = enabled, role = Role.Button) { onPick(type) }
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Icon(widgetIcon(type), null, Modifier.size(36.dp), tint = c.primary)
                Column {
                    Text(type.label, style = MaterialTheme.typography.titleMedium, color = c.onSurface, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    Text(
                        status, style = MaterialTheme.typography.bodyMedium, maxLines = 2, overflow = TextOverflow.Ellipsis,
                        color = if (where != null && !here) c.primary else c.onSurfaceVariant,
                    )
                }
            }
        }
    }
}

@Composable
private fun RoundIcon(
    icon: ImageVector,
    description: String,
    tint: Color,
    bg: Color,
    size: androidx.compose.ui.unit.Dp,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    Box(
        modifier
            .size(size)
            .clip(CircleShape)
            .background(bg)
            .clickable(enabled = enabled, role = Role.Button, onClickLabel = description, onClick = onClick),
        contentAlignment = Alignment.Center,
    ) { Icon(icon, description, Modifier.size(size * 0.45f), tint = tint) }
}

@Composable
private fun TextAction(label: String, color: Color, enabled: Boolean, onClick: () -> Unit) {
    Box(
        Modifier
            .height(64.dp)
            .clip(RoundedCornerShape(32.dp))
            .alpha(if (enabled) 1f else 0.4f)
            .clickable(enabled = enabled, role = Role.Button, onClick = onClick)
            .padding(horizontal = 22.dp),
        contentAlignment = Alignment.Center,
    ) { Text(label, style = MaterialTheme.typography.titleMedium, color = color) }
}
