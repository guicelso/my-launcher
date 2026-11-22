package app.mylauncher.ui.dashboard

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.PopupProperties
import app.mylauncher.data.prefs.DashboardLayout
import app.mylauncher.data.prefs.WidgetStacks
import app.mylauncher.data.prefs.WidgetType
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.flow.Flow
import app.mylauncher.ui.icons.LauncherIcons
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import app.mylauncher.data.apps.AppsRepository
import app.mylauncher.ui.components.AppIcon
import app.mylauncher.ui.components.LauncherCard
import app.mylauncher.ui.components.pressable
import app.mylauncher.ui.theme.Dimens
import app.mylauncher.ui.theme.LocalLauncherColors

/** Item do card de sugestões (R2.9): destino salvo, mensagem recente ou atalho. */
data class Suggestion(
    val title: String,
    val subtitle: String,
    val icon: ImageVector? = null,
    val appPackage: String? = null,
    val onClick: () -> Unit,
)

/**
 * Coluna direita do painel (specs 013 e 017): grade de 1, 2 ou 3 espaços, cada um uma pilha de
 * widgets. Deslizar troca o widget; toque longo (só com o carro parado) abre o menu da pilha.
 * Artboards "Painel com 2 pilhas" e "Toque longo numa pilha".
 */
@Composable
fun DashboardPanel(
    layout: DashboardLayout,
    stacks: List<List<WidgetType>>,
    parked: Boolean,
    smart: Boolean,
    events: Flow<StackEvent>,
    onEditStacks: ((List<List<WidgetType>>) -> List<List<WidgetType>>) -> Unit,
    onOpenEditor: (StackEditRequest) -> Unit,
    widget: @Composable (WidgetType, Modifier) -> Unit,
    modifier: Modifier = Modifier,
) {
    val gap = Dimens.gap
    val names = stackNames(layout)
    @Composable
    fun slot(index: Int, m: Modifier) = StackSlot(
        index, names[index], stacks[index], parked, smart, events, onEditStacks, onOpenEditor, widget, m,
    )
    when (layout) {
        DashboardLayout.ONE -> Box(modifier) { slot(0, Modifier.fillMaxSize()) }
        DashboardLayout.TWO -> Column(modifier, verticalArrangement = Arrangement.spacedBy(gap)) {
            slot(0, Modifier.fillMaxWidth().weight(1.05f))
            slot(1, Modifier.fillMaxWidth().weight(1f))
        }
        DashboardLayout.THREE -> Column(modifier, verticalArrangement = Arrangement.spacedBy(gap)) {
            slot(0, Modifier.fillMaxWidth().weight(1.05f))
            Row(Modifier.fillMaxWidth().weight(1f), horizontalArrangement = Arrangement.spacedBy(gap)) {
                slot(1, Modifier.weight(1f).fillMaxHeight())
                slot(2, Modifier.weight(1f).fillMaxHeight())
            }
        }
    }
}

/** Uma pilha do painel e o menu do toque longo (R17.5). */
@Composable
private fun StackSlot(
    slot: Int,
    name: String,
    types: List<WidgetType>,
    parked: Boolean,
    smart: Boolean,
    events: Flow<StackEvent>,
    onEditStacks: ((List<List<WidgetType>>) -> List<List<WidgetType>>) -> Unit,
    onOpenEditor: (StackEditRequest) -> Unit,
    widget: @Composable (WidgetType, Modifier) -> Unit,
    modifier: Modifier,
) {
    val c = LocalLauncherColors.current
    var menuFor by remember { mutableStateOf<WidgetType?>(null) }
    var replacing by remember { mutableStateOf(false) }
    val close = { menuFor = null; replacing = false }
    // Editar só com o carro parado: o menu fecha se o carro andar.
    LaunchedEffect(parked) { if (!parked) close() }
    Box(modifier) {
        WidgetStack(
            types = types,
            parked = parked,
            smart = smart,
            events = events,
            onLongPress = { menuFor = it },
            widget = widget,
            modifier = Modifier.fillMaxSize(),
        )
        BackHandler(enabled = menuFor != null) { if (replacing) replacing = false else close() }
        val current = menuFor
        DropdownMenu(
            expanded = current != null,
            onDismissRequest = close,
            properties = PopupProperties(focusable = false),
            shape = RoundedCornerShape(24.dp),
            containerColor = c.surfaceElevated,
            shadowElevation = 12.dp,
            modifier = Modifier.widthIn(min = 400.dp),
        ) {
            if (current == null) return@DropdownMenu
            if (replacing) {
                Text("Trocar ${current.label} por", style = MaterialTheme.typography.labelLarge, color = c.onSurfaceVariant, modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp))
                WidgetType.entries.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option.label, style = MaterialTheme.typography.titleMedium, color = c.onSurface) },
                        leadingIcon = { Icon(widgetIcon(option), null, Modifier.size(26.dp), tint = c.onSurfaceVariant) },
                        trailingIcon = { if (option == current) Icon(LauncherIcons.Check, "Atual", Modifier.size(26.dp), tint = c.primary) },
                        onClick = {
                            val index = types.indexOf(current)
                            if (index >= 0) onEditStacks { WidgetStacks.replace(it, slot, index, option) }
                            close()
                        },
                        modifier = Modifier.height(72.dp),
                    )
                }
                return@DropdownMenu
            }
            Column(Modifier.padding(horizontal = 24.dp, vertical = 10.dp)) {
                Text(name, style = MaterialTheme.typography.titleLarge, color = c.onSurface)
                Text(
                    "${types.size} ${if (types.size == 1) "widget" else "widgets"} · mostrando ${current.label}",
                    style = MaterialTheme.typography.bodyMedium, color = c.onSurfaceVariant,
                )
            }
            HorizontalDivider(color = c.outline)
            MenuItem(LauncherIcons.Layers, "Editar pilha") { close(); onOpenEditor(StackEditRequest(slot)) }
            if (types.size < WidgetStacks.MAX) {
                MenuItem(LauncherIcons.Plus, "Adicionar widget à pilha") { close(); onOpenEditor(StackEditRequest(slot, adding = true)) }
            }
            MenuItem(LauncherIcons.Swap, "Trocar este widget") { replacing = true }
            if (types.size > 1) {
                HorizontalDivider(color = c.outline)
                MenuItem(LauncherIcons.RemoveCircle, "Remover ${current.label} da pilha", c.danger) {
                    onEditStacks { WidgetStacks.remove(it, slot, current) }
                    close()
                }
            }
        }
    }
}

@Composable
private fun MenuItem(icon: ImageVector, label: String, color: Color? = null, onClick: () -> Unit) {
    val c = LocalLauncherColors.current
    val tint = color ?: c.onSurface
    DropdownMenuItem(
        text = { Text(label, style = MaterialTheme.typography.titleMedium, color = tint) },
        leadingIcon = { Icon(icon, null, Modifier.size(28.dp), tint = tint) },
        onClick = onClick,
        modifier = Modifier.height(76.dp),
    )
}

@Composable
fun SuggestionsCard(items: List<Suggestion>, apps: AppsRepository, modifier: Modifier = Modifier) {
    val c = LocalLauncherColors.current
    LauncherCard(modifier) {
        BoxWithConstraints(Modifier.fillMaxSize()) {
            // Em cards estreitos (grade de 3), esconde o ícone e deixa o título em 2 linhas.
            val compact = maxWidth < 300.dp
            val rows = if (compact) 2 else 3
            val rowHeight = if (compact) 120.dp else 104.dp
            Column(Modifier.padding(8.dp)) {
                items.take(rows).forEachIndexed { i, s ->
                    if (i > 0) HorizontalDivider(Modifier.padding(horizontal = 14.dp), color = c.outline)
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(rowHeight)
                            .clip(RoundedCornerShape(20.dp))
                            .pressable(onClick = s.onClick)
                            .padding(horizontal = 14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        if (!compact) {
                            when {
                                s.appPackage != null -> AppIcon(apps, s.appPackage, 56.dp)
                                s.icon != null -> Box(
                                    Modifier.size(56.dp).clip(CircleShape).background(c.surface2),
                                    contentAlignment = Alignment.Center,
                                ) { Icon(s.icon, null, Modifier.size(30.dp), tint = c.primary) }
                            }
                        }
                        Column(Modifier.weight(1f)) {
                            Text(
                                s.title, style = if (compact) MaterialTheme.typography.titleMedium else MaterialTheme.typography.titleLarge,
                                color = c.onSurface, maxLines = if (compact) 2 else 1, overflow = TextOverflow.Ellipsis,
                            )
                            Text(
                                s.subtitle, style = MaterialTheme.typography.bodyMedium, color = c.onSurfaceVariant,
                                maxLines = if (compact) 2 else 1, overflow = TextOverflow.Ellipsis,
                            )
                        }
                    }
                }
            }
        }
    }
}
