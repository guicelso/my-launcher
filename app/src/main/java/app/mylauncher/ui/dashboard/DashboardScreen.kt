package app.mylauncher.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import app.mylauncher.data.apps.AppsRepository
import app.mylauncher.nav.NavigationPane
import app.mylauncher.nav.PaneMode
import app.mylauncher.ui.components.AppIcon
import app.mylauncher.ui.components.LauncherCard
import app.mylauncher.ui.icons.LauncherIcons
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
 * Painel (spec 002, R2.6–R2.9): mapa à esquerda, mídia (344 dp) e sugestões à direita (408 dp).
 * Artboard "Início · noite/dia" em design/wireframes.
 */
@Composable
fun DashboardScreen(
    navPane: NavigationPane,
    suggestions: List<Suggestion>,
    apps: AppsRepository,
    onOpenMap: () -> Unit,
    mediaCard: @Composable (Modifier) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier
            .fillMaxSize()
            .padding(start = 8.dp, top = Dimens.gap, end = Dimens.gap, bottom = Dimens.gap),
        horizontalArrangement = Arrangement.spacedBy(Dimens.gap),
    ) {
        Box(
            Modifier
                .weight(1f)
                .fillMaxHeight()
                .clip(RoundedCornerShape(Dimens.cardRadius)),
        ) {
            navPane.Content(PaneMode.CARD, Modifier.fillMaxSize(), onExpand = onOpenMap)
        }
        Column(
            Modifier.width(408.dp).fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(Dimens.gap),
        ) {
            mediaCard(Modifier.fillMaxWidth().height(344.dp))
            SuggestionsCard(suggestions, apps, Modifier.fillMaxWidth().weight(1f))
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
                        .clickable(role = Role.Button, onClick = s.onClick)
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
