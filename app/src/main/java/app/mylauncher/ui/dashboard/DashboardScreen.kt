package app.mylauncher.ui.dashboard

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.PopupProperties
import app.mylauncher.data.prefs.DashboardLayout
import app.mylauncher.data.prefs.WidgetType
import app.mylauncher.ui.components.interceptLongPress
import app.mylauncher.ui.icons.LauncherIcons
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
 * Coluna direita do painel com widgets trocáveis (spec 013): grade de 1, 2 ou 3 cards. Toque
 * longo num card (só com o carro parado, [editable]) abre "Trocar widget".
 * Artboard "Início · noite/dia".
 */
@Composable
fun DashboardPanel(
    layout: DashboardLayout,
    widgets: List<WidgetType>,
    editable: Boolean,
    onSwap: (slot: Int, type: WidgetType) -> Unit,
    widget: @Composable (WidgetType, Modifier) -> Unit,
    modifier: Modifier = Modifier,
) {
    val gap = Dimens.gap
    @Composable
    fun slot(index: Int, m: Modifier) = WidgetSlot(widgets[index], editable, { onSwap(index, it) }, widget, m)
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

@Composable
private fun WidgetSlot(
    type: WidgetType,
    editable: Boolean,
    onSwap: (WidgetType) -> Unit,
    widget: @Composable (WidgetType, Modifier) -> Unit,
    modifier: Modifier,
) {
    val c = LocalLauncherColors.current
    var menuOpen by remember { mutableStateOf(false) }
    Box(modifier.interceptLongPress(editable) { menuOpen = true }) {
        AnimatedContent(
            targetState = type,
            transitionSpec = {
                (fadeIn(tween(220, delayMillis = 60)) + scaleIn(tween(260, delayMillis = 60), initialScale = 0.94f))
                    .togetherWith(fadeOut(tween(140)) + scaleOut(tween(140), targetScale = 0.97f))
            },
            label = "widget",
        ) { t -> widget(t, Modifier.fillMaxSize()) }
        BackHandler(enabled = menuOpen) { menuOpen = false }
        DropdownMenu(
            expanded = menuOpen,
            onDismissRequest = { menuOpen = false },
            properties = PopupProperties(focusable = false),
            shape = RoundedCornerShape(24.dp),
            containerColor = c.surface,
            shadowElevation = 12.dp,
        ) {
            Text("Trocar widget", style = MaterialTheme.typography.labelLarge, color = c.onSurfaceVariant, modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp))
            WidgetType.entries.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option.label, style = MaterialTheme.typography.titleMedium, color = c.onSurface) },
                    trailingIcon = { if (option == type) Icon(LauncherIcons.Check, "Atual", Modifier.size(26.dp), tint = c.primary) },
                    onClick = { onSwap(option); menuOpen = false },
                    modifier = Modifier.height(72.dp),
                )
            }
        }
    }
}

@Composable
fun SuggestionsCard(items: List<Suggestion>, apps: AppsRepository, modifier: Modifier = Modifier) {
    val c = LocalLauncherColors.current
    LauncherCard(modifier.padding(0.dp)) {
        Column(Modifier.padding(8.dp)) {
            items.take(3).forEachIndexed { i, s ->
                if (i > 0) HorizontalDivider(Modifier.padding(horizontal = 14.dp), color = c.outline)
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(104.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .pressable(onClick = s.onClick)
                        .padding(horizontal = 14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    when {
                        s.appPackage != null -> AppIcon(apps, s.appPackage, 56.dp)
                        s.icon != null -> Box(
                            Modifier.size(56.dp).clip(CircleShape).background(c.surface2),
                            contentAlignment = Alignment.Center,
                        ) { Icon(s.icon, null, Modifier.size(30.dp), tint = c.primary) }
                    }
                    Column(Modifier.weight(1f)) {
                        Text(
                            s.title, style = MaterialTheme.typography.titleLarge, color = c.onSurface,
                            maxLines = 1, overflow = TextOverflow.Ellipsis,
                        )
                        Text(
                            s.subtitle, style = MaterialTheme.typography.bodyMedium, color = c.onSurfaceVariant,
                            maxLines = 1, overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
            }
        }
    }
}
