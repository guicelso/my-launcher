package app.mylauncher.ui.drawer

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import app.mylauncher.data.apps.AppEntry
import app.mylauncher.data.apps.AppsRepository
import app.mylauncher.data.prefs.LauncherSettings
import app.mylauncher.ui.components.AppContextMenu
import app.mylauncher.ui.components.AppIcon
import app.mylauncher.ui.components.AppMenuActions
import app.mylauncher.ui.components.PillShape
import app.mylauncher.ui.components.ScreenPanel
import app.mylauncher.ui.components.pressable
import app.mylauncher.ui.icons.LauncherIcons
import app.mylauncher.ui.theme.LocalLauncherColors

private const val COLUMNS = 6
private const val ROWS = 3
private const val PER_PAGE = COLUMNS * ROWS
const val STOCK_LAUNCHER = "com.dofun.variety"

private sealed interface DrawerItem {
    val label: String

    data class App(val entry: AppEntry) : DrawerItem {
        override val label get() = entry.label
    }

    data class Internal(override val label: String, val icon: ImageVector, val action: () -> Unit) : DrawerItem
}

/** Gaveta de apps (spec 003). Artboard "Gaveta de apps". */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AppDrawerScreen(
    apps: AppsRepository,
    allApps: List<AppEntry>,
    settings: LauncherSettings,
    onLaunch: (String) -> Unit,
    onOpenSettings: () -> Unit,
    onOpenStockLauncher: () -> Unit,
    onSetPinned: (List<String>) -> Unit,
    menu: AppMenuActions,
    modifier: Modifier = Modifier,
) {
    val c = LocalLauncherColors.current
    var query by rememberSaveable { mutableStateOf("") }
    var editing by rememberSaveable { mutableStateOf(false) }

    val items: List<DrawerItem> = remember(allApps, query, settings.hidden) {
        val filtered = AppsRepository.filter(allApps, query, settings.hidden).map { DrawerItem.App(it) }
        val internal = buildList {
            add(DrawerItem.Internal("Ajustes do launcher", LauncherIcons.Settings, onOpenSettings))
            if (allApps.none { it.packageName == STOCK_LAUNCHER }) {
                add(DrawerItem.Internal("Launcher original", LauncherIcons.Dashboard, onOpenStockLauncher))
            }
        }.filter { query.isBlank() || AppsRepository.normalize(it.label).contains(AppsRepository.normalize(query)) }
        internal + filtered
    }
    val pages = items.chunked(PER_PAGE).ifEmpty { listOf(emptyList()) }
    val pager = rememberPagerState { pages.size }

    ScreenPanel(modifier) {
        Column(Modifier.fillMaxSize().padding(start = 24.dp, top = 20.dp, end = 32.dp, bottom = 16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(
                    if (editing) "Toque para fixar na barra (máx. ${LauncherSettings.MAX_RAIL_APPS})" else "Apps",
                    style = MaterialTheme.typography.headlineMedium, color = c.onSurface, modifier = Modifier.weight(1f),
                )
                SearchField(query, { query = it }, Modifier.width(380.dp))
                Box(
                    Modifier
                        .height(64.dp)
                        .clip(PillShape)
                        .background(if (editing) c.primary else c.surface2)
                        .clickable(role = Role.Button) { editing = !editing }
                        .padding(horizontal = 24.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        if (editing) "Concluir" else "Editar barra",
                        style = MaterialTheme.typography.labelLarge,
                        color = if (editing) c.onPrimary else c.onSurface,
                    )
                }
            }
            Spacer(Modifier.height(16.dp))
            HorizontalPager(pager, Modifier.weight(1f).fillMaxWidth()) { page ->
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    pages[page].chunked(COLUMNS).forEach { row ->
                        Row(Modifier.fillMaxWidth().height(176.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            row.forEach { item ->
                                Tile(
                                    item = item,
                                    apps = apps,
                                    menu = menu,
                                    pinned = item is DrawerItem.App && item.entry.packageName in settings.pinned,
                                    editing = editing,
                                    modifier = Modifier.weight(1f),
                                    onClick = {
                                        when (item) {
                                            is DrawerItem.Internal -> item.action()
                                            is DrawerItem.App -> if (editing) {
                                                onSetPinned(togglePin(settings.pinned, item.entry.packageName))
                                            } else {
                                                onLaunch(item.entry.packageName)
                                            }
                                        }
                                    },
                                )
                            }
                            repeat(COLUMNS - row.size) { Spacer(Modifier.weight(1f)) }
                        }
                    }
                }
            }
            if (pages.size > 1) {
                Row(Modifier.fillMaxWidth().padding(top = 8.dp), horizontalArrangement = Arrangement.Center) {
                    repeat(pages.size) { i ->
                        Box(
                            Modifier
                                .padding(horizontal = 4.dp)
                                .size(width = if (i == pager.currentPage) 24.dp else 8.dp, height = 8.dp)
                                .clip(CircleShape)
                                .background(if (i == pager.currentPage) c.onSurface else c.onSurfaceVariant),
                        )
                    }
                }
            }
        }
    }
}

fun togglePin(current: List<String>, pkg: String): List<String> = when {
    pkg in current -> current - pkg
    current.size >= LauncherSettings.MAX_RAIL_APPS -> current
    else -> current + pkg
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Tile(
    item: DrawerItem,
    apps: AppsRepository,
    menu: AppMenuActions,
    pinned: Boolean,
    editing: Boolean,
    modifier: Modifier,
    onClick: () -> Unit,
) {
    val c = LocalLauncherColors.current
    var menuOpen by remember { mutableStateOf(false) }
    Column(
        modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(24.dp))
            .pressable(
                onClick = onClick,
                onLongClick = if (item is DrawerItem.App && !editing) ({ menuOpen = true }) else null,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Box {
            when (item) {
                is DrawerItem.App -> AppIcon(apps, item.entry.packageName, 96.dp)
                is DrawerItem.Internal -> Box(
                    Modifier.size(96.dp).clip(CircleShape).background(c.surface2),
                    contentAlignment = Alignment.Center,
                ) { Icon(item.icon, null, Modifier.size(44.dp), tint = c.primary) }
            }
            if (editing && item is DrawerItem.App) {
                Box(
                    Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = 6.dp, y = (-6).dp)
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(if (pinned) c.primary else c.surface2),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        if (pinned) LauncherIcons.Check else LauncherIcons.Pin,
                        if (pinned) "Fixado" else "Fixar",
                        Modifier.size(22.dp),
                        tint = if (pinned) c.onPrimary else c.onSurface,
                    )
                }
            }
        }
        Text(
            item.label,
            style = MaterialTheme.typography.bodyMedium,
            color = c.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 12.dp, start = 4.dp, end = 4.dp),
        )
        if (item is DrawerItem.App) {
            AppContextMenu(item.entry.packageName, menuOpen, { menuOpen = false }, menu, showRemoveRecent = false)
        }
    }
}

@Composable
private fun SearchField(value: String, onChange: (String) -> Unit, modifier: Modifier = Modifier) {
    val c = LocalLauncherColors.current
    Row(
        modifier.height(64.dp).clip(PillShape).background(c.surface2).padding(horizontal = 22.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Icon(LauncherIcons.Search, null, Modifier.size(26.dp), tint = c.onSurfaceVariant)
        Box(Modifier.weight(1f)) {
            if (value.isEmpty()) {
                Text("Buscar app", style = MaterialTheme.typography.bodyLarge, color = c.onSurfaceVariant)
            }
            BasicTextField(
                value = value,
                onValueChange = onChange,
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyLarge.copy(color = c.onSurface),
                cursorBrush = SolidColor(c.primary),
                modifier = Modifier.fillMaxWidth(),
            )
        }
        if (value.isNotEmpty()) {
            Icon(
                LauncherIcons.Close, "Limpar busca",
                Modifier.size(26.dp).clip(CircleShape).clickable { onChange("") },
                tint = c.onSurfaceVariant,
            )
        }
    }
}
