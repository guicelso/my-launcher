package app.mylauncher.ui.notifications

import android.app.PendingIntent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import app.mylauncher.data.media.MessageNotification
import app.mylauncher.data.media.NotificationsSummary
import app.mylauncher.ui.components.AppIcon
import app.mylauncher.ui.components.PillShape
import app.mylauncher.ui.components.ScreenPanel
import app.mylauncher.ui.components.TouchIconButton
import app.mylauncher.ui.components.pressable
import app.mylauncher.ui.icons.LauncherIcons
import app.mylauncher.ui.theme.LocalLauncherColors

/**
 * Central de notificações (Android Auto, "Notificações"): cards do mais novo para o mais antigo,
 * cada um com ícone do app, texto principal e secundário e até três ações. Abre pelo selo da barra.
 * "Central do sistema" mantém o acesso à cortina do Android (Wi-Fi, brilho etc.).
 */
@Composable
fun NotificationCenter(
    summary: NotificationsSummary,
    apps: AppsRepository,
    now: Long,
    onBack: () -> Unit,
    onOpen: (String, PendingIntent?) -> Unit,
    onDismiss: (String) -> Unit,
    onDismissAll: () -> Unit,
    onSystemShade: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val c = LocalLauncherColors.current
    ScreenPanel(modifier) {
        Column(Modifier.fillMaxSize().padding(start = 12.dp, top = 12.dp, end = 24.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                TouchIconButton(LauncherIcons.Back, "Voltar", onBack, iconSize = 32.dp)
                Text("Notificações", style = MaterialTheme.typography.headlineMedium, color = c.onSurface, modifier = Modifier.weight(1f))
                if (summary.recent.isNotEmpty()) ActionPill("Limpar tudo", null, onClick = onDismissAll)
                ActionPill("Central do sistema", LauncherIcons.External, onClick = onSystemShade)
            }
            if (summary.recent.isEmpty()) {
                Column(
                    Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
                ) {
                    Icon(LauncherIcons.Bell, null, Modifier.size(56.dp), tint = c.onSurfaceVariant)
                    Text("Nenhuma notificação", style = MaterialTheme.typography.titleLarge, color = c.onSurfaceVariant)
                }
                return@Column
            }
            LazyColumn(
                Modifier.fillMaxSize().padding(start = 64.dp),
                contentPadding = PaddingValues(top = 12.dp, bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(summary.recent, key = { it.key.ifBlank { it.packageName + it.postedAt } }) { n ->
                    NotificationCard(n, apps, now, onOpen, onDismiss)
                }
            }
        }
    }
}

@Composable
private fun NotificationCard(
    n: MessageNotification,
    apps: AppsRepository,
    now: Long,
    onOpen: (String, PendingIntent?) -> Unit,
    onDismiss: (String) -> Unit,
) {
    val c = LocalLauncherColors.current
    val title = n.sender.ifBlank { apps.label(n.packageName) }
    Column(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(c.surfaceRow)
            .padding(horizontal = 20.dp, vertical = 18.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(18.dp)) {
            AppIcon(apps, n.packageName, 64.dp)
            Column(Modifier.weight(1f)) {
                Text(
                    "$title · ${ago(now, n.postedAt)}", style = MaterialTheme.typography.titleLarge, color = c.onSurface,
                    maxLines = 1, overflow = TextOverflow.Ellipsis,
                )
                if (n.text.isNotBlank()) {
                    Text(
                        n.text, style = MaterialTheme.typography.titleMedium, color = c.onSurfaceVariant,
                        maxLines = 1, overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            ActionPill(
                if (n.isMessage) "Abrir conversa" else "Abrir", LauncherIcons.External,
                Modifier.weight(1.6f),
            ) { onOpen(n.packageName, n.contentIntent) }
            if (n.key.isNotBlank()) {
                ActionPill("Dispensar", LauncherIcons.Close, Modifier.weight(1f)) { onDismiss(n.key) }
            }
        }
    }
}

@Composable
private fun ActionPill(label: String, icon: ImageVector?, modifier: Modifier = Modifier, onClick: () -> Unit) {
    val c = LocalLauncherColors.current
    Row(
        modifier
            .height(72.dp)
            .clip(PillShape)
            .background(c.surface2)
            .pressable(onClick = onClick)
            .padding(horizontal = 26.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
    ) {
        if (icon != null) Icon(icon, null, Modifier.size(26.dp), tint = c.onSurface)
        Box { Text(label, style = MaterialTheme.typography.labelLarge, color = c.onSurface, maxLines = 1) }
    }
}

/** "agora", "8 min", "2 h", "3 d". */
internal fun ago(now: Long, then: Long): String {
    val min = ((now - then) / 60_000).coerceAtLeast(0)
    return when {
        min < 1 -> "agora"
        min < 60 -> "$min min"
        min < 60 * 24 -> "${min / 60} h"
        else -> "${min / (60 * 24)} d"
    }
}
