package app.mylauncher.ui.media

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import app.mylauncher.data.apps.AppsRepository
import app.mylauncher.data.media.MediaControls
import app.mylauncher.data.media.NowPlaying
import app.mylauncher.data.media.browse.BrowseAction
import app.mylauncher.data.media.browse.BrowseItem
import app.mylauncher.data.media.browse.BrowseLayout
import app.mylauncher.data.media.browse.BrowseRoot
import app.mylauncher.data.media.browse.BrowseSection
import app.mylauncher.data.media.browse.ItemStyle
import app.mylauncher.data.media.browse.MediaArt
import app.mylauncher.data.media.browse.MediaBrowserConnector
import app.mylauncher.ui.components.pressable
import app.mylauncher.ui.icons.LauncherIcons
import kotlinx.coroutines.launch

/** Contexto comum às telas de navegação. */
data class BrowseContext(
    val conn: MediaBrowserConnector,
    val root: BrowseRoot,
    val pkg: String,
    val label: String,
    val accent: Color,
    val driving: Boolean,
)

/** Ações das telas de navegação. */
data class BrowseActions(
    val onOpen: (BrowseItem) -> Unit,
    val onPlay: (BrowseItem, Boolean) -> Unit,
    val onSources: () -> Unit,
    val onSearch: () -> Unit,
    val onOpenPlayer: () -> Unit,
    val onBack: () -> Unit,
    val onTab: (String) -> Unit,
)

/**
 * Navegação em abas (wireframes MxBrowse, MxBrowseExplore, MxBrowsePodcasts, MxBrowseDriving):
 * os filhos navegáveis do root viram até 4 abas (R18.5); o conteúdo segue os content style hints
 * (R18.6) com grupos e indicadores (R18.7); mini player embaixo (R18.10).
 */
@Composable
fun BrowseTabsScreen(
    b: BrowseContext,
    apps: AppsRepository,
    np: NowPlaying?,
    controls: MediaControls?,
    selectedTab: String?,
    actions: BrowseActions,
    modifier: Modifier = Modifier,
) {
    val m = mediaPalette()
    val rootChildren by b.conn.children(b.root.id).collectAsState(initial = null)
    val tabs = rootChildren.orEmpty().filter { it.browsable }.take(BrowseLayout.MAX_TABS)
    val tab = tabs.firstOrNull { it.id == selectedTab } ?: tabs.firstOrNull()
    val items by produceState<List<BrowseItem>?>(null, tab?.id, rootChildren) {
        value = null
        if (tab == null) {
            value = rootChildren
        } else {
            b.conn.children(tab.id).collect { value = it }
        }
    }
    MediaCardFrame(modifier, start = 12.dp, end = 20.dp, top = 12.dp, bottom = 12.dp) {
        Row(Modifier.height(88.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            AppPill(apps, b.pkg, b.label, actions.onSources, modifier = Modifier.padding(end = 12.dp), endPad = 14.dp)
            if (rootChildren == null) {
                SkeletonTabs()
            } else {
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    tabs.forEach { t -> TabChip(t, t.id == tab?.id) { actions.onTab(t.id) } }
                }
            }
            Spacer(Modifier.weight(1f))
            if (b.root.searchSupported) RoundIcon(LauncherIcons.Search, "Buscar", actions.onSearch, iconSize = 32.dp)
        }
        if (b.driving) DrivingBanner()
        val list = items
        if (list == null) {
            MediaLoading(b.accent, Modifier.weight(1f).fillMaxWidth())
        } else {
            val parent = tab
            SectionsList(
                sections = BrowseLayout.sections(list, parent, b.root),
                b = b, np = np, depthLocked = false,
                actions = actions,
                modifier = Modifier.weight(1f).fillMaxWidth(),
                gridHeaders = true,
                allowAz = true,
            )
        }
        if (np != null) MiniPlayer(np, controls, b.accent, actions.onOpenPlayer, Modifier.padding(top = 8.dp))
    }
}

/**
 * Nível 2 em diante (wireframe MxAlbum): cabeçalho fixo com voltar; se o nó é uma lista de faixas,
 * capa à esquerda, ações do nó (Aleatório + ações de navegação, R18.8) e faixas numeradas; senão as
 * mesmas seções da navegação.
 */
@Composable
fun BrowseNodeScreen(
    b: BrowseContext,
    node: BrowseItem,
    depth: Int,
    np: NowPlaying?,
    controls: MediaControls?,
    actions: BrowseActions,
    modifier: Modifier = Modifier,
) {
    val children by b.conn.children(node.id).collectAsState(initial = null)
    val scope = rememberCoroutineScope()
    var toast by remember { mutableStateOf<String?>(null) }
    val runAction: (BrowseAction, BrowseItem) -> Unit = { action, item ->
        scope.launch {
            val r = b.conn.runAction(action, item.id)
            toast = r?.message ?: if (r == null) "Não foi possível concluir" else null
            if (r?.showPlaying == true) actions.onOpenPlayer()
        }
    }
    Box(modifier) {
        MediaCardFrame(Modifier.fillMaxSize(), start = 12.dp, end = 20.dp, top = 12.dp, bottom = 12.dp) {
            Row(Modifier.height(88.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                RoundIcon(LauncherIcons.ArrowBack, "Voltar", actions.onBack, iconSize = 34.dp)
                Text(node.title, fontSize = 30.sp, color = mediaPalette().text, maxLines = 1, overflow = TextOverflow.Ellipsis, modifier = Modifier.weight(1f))
                if (b.root.searchSupported) RoundIcon(LauncherIcons.Search, "Buscar", actions.onSearch, iconSize = 32.dp)
            }
            if (b.driving) DrivingBanner()
            val list = children
            when {
                list == null -> MediaLoading(b.accent, Modifier.weight(1f).fillMaxWidth())
                list.isNotEmpty() && list.all { it.playable && !it.browsable } ->
                    TrackListBody(b, node, list, np, actions, runAction, Modifier.weight(1f).fillMaxWidth())
                else -> SectionsList(
                    sections = BrowseLayout.sections(list, node, b.root),
                    b = b, np = np,
                    depthLocked = b.driving && depth >= BrowseLayout.MAX_DEPTH,
                    actions = actions,
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    gridHeaders = true,
                    allowAz = false,
                    onAction = runAction,
                )
            }
            if (np != null) MiniPlayer(np, controls, b.accent, actions.onOpenPlayer, Modifier.padding(top = 8.dp))
        }
        MediaToast(toast) { toast = null }
    }
}

/** Card das telas de mídia sem capa (fundo #262625, raio 24). */
@Composable
fun MediaCardFrame(
    modifier: Modifier,
    start: androidx.compose.ui.unit.Dp,
    end: androidx.compose.ui.unit.Dp,
    top: androidx.compose.ui.unit.Dp,
    bottom: androidx.compose.ui.unit.Dp,
    content: @Composable androidx.compose.foundation.layout.ColumnScope.() -> Unit,
) {
    Column(
        modifier.clip(RoundedCornerShape(24.dp)).background(mediaPalette().card).padding(start = start, end = end, top = top, bottom = bottom),
        content = content,
    )
}

@Composable
private fun DrivingBanner() {
    val m = mediaPalette()
    Row(
        Modifier.padding(start = 88.dp, bottom = 8.dp).height(52.dp).clip(RoundedCornerShape(26.dp)).background(m.warnBg).padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Icon(LauncherIcons.CarMoving, null, Modifier.size(24.dp), tint = m.warnFg)
        Text("Em movimento: até ${BrowseLayout.DRIVING_MAX_ITEMS} itens por lista. Busca só por voz.", fontSize = 20.sp, color = m.warnFg)
    }
}

@Composable
private fun TabChip(t: BrowseItem, selected: Boolean, onClick: () -> Unit) {
    val m = mediaPalette()
    Row(
        Modifier
            .height(64.dp)
            .clip(CircleShape)
            .background(if (selected) m.text.copy(alpha = if (m === NightMedia) 0.14f else 0.1f) else Color.Transparent)
            .alpha(if (selected) 1f else 0.55f)
            .pressable(onClickLabel = t.title, onClick = onClick)
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        TabIcon(t, m.text)
        Text(t.title, fontSize = 22.sp, color = m.text, maxLines = 1)
    }
}

/** Ícone da aba: o do app (monocromático, tingido) ou um nosso pelo nome. */
@Composable
private fun TabIcon(t: BrowseItem, tint: Color) {
    val context = LocalContext.current
    val bmp by produceState<ImageBitmap?>(t.iconBitmap?.asImageBitmap(), t.iconUri) {
        if (value == null) value = MediaArt.load(context, t.iconUri, 56)?.asImageBitmap()
    }
    val img = bmp
    if (img != null) {
        Image(img, null, Modifier.size(28.dp), colorFilter = ColorFilter.tint(tint))
    } else {
        Icon(tabIconFor(t.title), null, Modifier.size(28.dp), tint = tint)
    }
}

private fun tabIconFor(title: String): ImageVector {
    val t = title.lowercase()
    return when {
        "iníc" in t || "inic" in t || "home" in t -> LauncherIcons.Home
        "bibli" in t || "library" in t || "sua" in t -> LauncherIcons.Library
        "pod" in t || "episód" in t -> LauncherIcons.Mic
        "rádio" in t || "radio" in t -> LauncherIcons.Radio
        "busc" in t || "search" in t -> LauncherIcons.Search
        "livro" in t || "book" in t -> LauncherIcons.Library
        else -> LauncherIcons.Compass
    }
}

private fun isArtist(i: BrowseItem) =
    i.subtitle.equals("Artista", true) || i.subtitle.equals("Artist", true) ||
        (i.groupTitle?.lowercase()?.let { "artista" in it || "artist" in it } == true && !i.playable)

/** Lista rolável das seções (grade, lista e categorias), com trilho de rolagem e A–Z. */
@Composable
private fun SectionsList(
    sections: List<BrowseSection>,
    b: BrowseContext,
    np: NowPlaying?,
    depthLocked: Boolean,
    actions: BrowseActions,
    modifier: Modifier,
    gridHeaders: Boolean,
    allowAz: Boolean,
    onAction: ((BrowseAction, BrowseItem) -> Unit)? = null,
) {
    val (shown, cut) = if (b.driving) BrowseLayout.limit(sections, BrowseLayout.DRIVING_MAX_ITEMS) else sections to false
    val state = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val total = shown.sumOf { it.items.size }
    val az = allowAz && shown.any { it.style == ItemStyle.LIST } && total >= AZ_MIN
    var azOpen by remember { mutableStateOf(false) }
    // Índices das linhas da LazyColumn por item (para o A–Z).
    val rows = remember(shown) { buildRows(shown, gridHeaders) }
    Row(modifier, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        val rail by remember(state) {
            derivedStateOf {
                val info = state.layoutInfo
                val count = info.totalItemsCount.coerceAtLeast(1)
                val visible = info.visibleItemsInfo.size.coerceAtLeast(1)
                Triple(state.firstVisibleItemIndex.toFloat() / count, visible.toFloat() / count, state.canScrollForward)
            }
        }
        ScrollRail(
            canUp = state.canScrollBackward,
            canDown = rail.third,
            thumbStart = rail.first,
            thumbSize = rail.second,
            onUp = { scope.launch { state.animateScrollBy(-state.layoutInfo.viewportSize.height * 0.8f) } },
            onDown = { scope.launch { state.animateScrollBy(state.layoutInfo.viewportSize.height * 0.8f) } },
            az = if (az) ({ azOpen = true }) else null,
        )
        Box(Modifier.weight(1f).fillMaxHeight()) {
            LazyColumn(state = state, modifier = Modifier.fillMaxSize().padding(end = 8.dp)) {
                rows.forEachIndexed { index, row ->
                    item(key = "r$index") {
                        when (row) {
                            is Row_.Header -> SectionHeader(row.title, row.grid)
                            is Row_.Grid -> GridRow(row.items, b, actions, depthLocked, Modifier.padding(bottom = if (row.last) 16.dp else 20.dp))
                            is Row_.Cats -> CategoryRow(row.items, b, actions, depthLocked, Modifier.padding(bottom = 12.dp))
                            is Row_.Item -> ListRow(row.item, b, np, actions, depthLocked, onAction)
                        }
                    }
                }
                if (cut) item(key = "lock") { LockRow("Mais itens com o carro parado") }
                if (depthLocked) item(key = "depth") { LockRow("Mais níveis com o carro parado") }
            }
            if (azOpen) {
                AzPicker(
                    letters = rows.mapNotNull { (it as? Row_.Item)?.item?.title?.firstOrNull()?.uppercaseChar() }.distinct().sorted(),
                    onPick = { letter ->
                        azOpen = false
                        val idx = rows.indexOfFirst { (it as? Row_.Item)?.item?.title?.firstOrNull()?.uppercaseChar() == letter }
                        if (idx >= 0) scope.launch { state.animateScrollToItem(idx) }
                    },
                    onClose = { azOpen = false },
                )
            }
        }
    }
}

private const val AZ_MIN = 15

private sealed interface Row_ {
    data class Header(val title: String, val grid: Boolean) : Row_
    data class Grid(val items: List<BrowseItem>, val last: Boolean) : Row_
    data class Cats(val items: List<BrowseItem>) : Row_
    data class Item(val item: BrowseItem) : Row_
}

private fun buildRows(sections: List<BrowseSection>, gridHeaders: Boolean): List<Row_> = buildList {
    sections.forEach { s ->
        s.title?.let { add(Row_.Header(it, s.style != ItemStyle.LIST || !gridHeaders)) }
        when (s.style) {
            ItemStyle.GRID -> s.items.chunked(5).let { chunks -> chunks.forEachIndexed { i, c -> add(Row_.Grid(c, i == chunks.lastIndex)) } }
            ItemStyle.CATEGORY_GRID -> s.items.chunked(4).forEach { add(Row_.Cats(it)) }
            ItemStyle.CATEGORY_LIST, ItemStyle.LIST -> s.items.forEach { add(Row_.Item(it)) }
        }
    }
}

@Composable
private fun SectionHeader(title: String, grid: Boolean) {
    val m = mediaPalette()
    if (grid) {
        Box(Modifier.fillMaxWidth().height(56.dp), contentAlignment = Alignment.CenterStart) {
            Text(title, fontSize = 24.sp, fontWeight = FontWeight.Medium, color = m.text)
        }
    } else {
        Box(Modifier.fillMaxWidth().height(56.dp).padding(start = 8.dp, bottom = 8.dp), contentAlignment = Alignment.BottomStart) {
            Text(title, fontSize = 22.sp, fontWeight = FontWeight.Medium, color = m.muted)
        }
    }
}

private fun open(item: BrowseItem, actions: BrowseActions, locked: Boolean) {
    when {
        item.browsable && !locked -> actions.onOpen(item)
        item.playable -> actions.onPlay(item, false)
    }
}

@Composable
private fun GridRow(items: List<BrowseItem>, b: BrowseContext, actions: BrowseActions, locked: Boolean, modifier: Modifier) {
    val m = mediaPalette()
    Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(20.dp)) {
        items.forEach { it ->
            Column(
                Modifier.weight(1f).clip(RoundedCornerShape(16.dp)).pressable(onClickLabel = it.title) { open(it, actions, locked) },
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                ArtBox(it.iconBitmap, it.iconUri, it.title, null, 16.dp, Modifier.fillMaxWidth().aspectRatio(1f), circle = isArtist(it))
                Text(it.title, fontSize = 22.sp, color = m.text, maxLines = 1, overflow = TextOverflow.Ellipsis)
                if (it.subtitle.isNotBlank()) {
                    Text(it.subtitle, fontSize = 18.sp, color = m.subtle, maxLines = 1, overflow = TextOverflow.Ellipsis, modifier = Modifier)
                }
            }
        }
        repeat(5 - items.size) { Spacer(Modifier.weight(1f)) }
    }
}

@Composable
private fun CategoryRow(items: List<BrowseItem>, b: BrowseContext, actions: BrowseActions, locked: Boolean, modifier: Modifier) {
    val m = mediaPalette()
    Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        items.forEach { it ->
            Row(
                Modifier.weight(1f).height(104.dp).clip(RoundedCornerShape(20.dp)).background(m.tile)
                    .pressable(onClickLabel = it.title) { open(it, actions, locked) }.padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                CategoryIcon(it, m.text)
                Text(it.title, fontSize = 24.sp, color = m.text, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
        }
        repeat(4 - items.size) { Spacer(Modifier.weight(1f)) }
    }
}

/** Ícone de categoria: vetor tingível do app (CATEGORY_*), senão um nosso pelo nome. */
@Composable
private fun CategoryIcon(i: BrowseItem, tint: Color) {
    val context = LocalContext.current
    val bmp by produceState<ImageBitmap?>(i.iconBitmap?.asImageBitmap(), i.iconUri) {
        if (value == null) value = MediaArt.load(context, i.iconUri, 88)?.asImageBitmap()
    }
    val img = bmp
    if (img != null) {
        Image(img, null, Modifier.size(44.dp), colorFilter = ColorFilter.tint(tint))
    } else {
        Icon(categoryIconFor(i.title), null, Modifier.size(44.dp), tint = tint)
    }
}

private fun categoryIconFor(title: String): ImageVector {
    val t = title.lowercase()
    return when {
        "rádio" in t || "radio" in t -> LauncherIcons.Radio
        "dirig" in t || "carro" in t || "estrada" in t -> LauncherIcons.CarMoving
        "relax" in t || "calm" in t -> LauncherIcons.Moon
        "parada" in t || "top" in t || "chart" in t -> LauncherIcons.Signal
        "infant" in t || "criança" in t -> LauncherIcons.Person
        "livro" in t || "book" in t -> LauncherIcons.Library
        "notíc" in t || "news" in t -> LauncherIcons.Message
        "podcast" in t -> LauncherIcons.Mic
        else -> LauncherIcons.Music
    }
}

@Composable
private fun ListRow(
    item: BrowseItem,
    b: BrowseContext,
    np: NowPlaying?,
    actions: BrowseActions,
    locked: Boolean,
    onAction: ((BrowseAction, BrowseItem) -> Unit)?,
) {
    val m = mediaPalette()
    var menu by remember { mutableStateOf(false) }
    val itemActions = b.root.actions.filter { it.id in item.actionIds }
    val playingNow = np?.mediaId != null && np.mediaId == item.id
    Box {
        Row(
            Modifier
                .fillMaxWidth()
                .height(100.dp)
                .alpha(if (item.done) 0.6f else 1f)
                .pressable(
                    onClickLabel = item.title,
                    onLongClick = if (itemActions.isNotEmpty() && onAction != null) ({ menu = true }) else null,
                ) { open(item, actions, locked) }
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(22.dp),
        ) {
            if (item.singleStyle?.isCategory == true) {
                Box(Modifier.size(72.dp), contentAlignment = Alignment.Center) { CategoryIcon(item, m.text) }
            } else {
                ArtBox(item.iconBitmap, item.iconUri, item.title, 72.dp, 10.dp, circle = isArtist(item))
            }
            Column(
                Modifier.weight(1f).fillMaxHeight().bottomLine(m.line),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(item.title, fontSize = 26.sp, color = if (playingNow) b.accent else m.text, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Row(Modifier.padding(top = 4.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    if (item.explicit) ExplicitBadge(22.dp, m.subtle, m.card)
                    if (item.download == 2) Icon(LauncherIcons.Download, "Baixado", Modifier.size(22.dp), tint = m.subtle)
                    if (item.done) Icon(LauncherIcons.Check, "Concluído", Modifier.size(22.dp), tint = m.subtle)
                    if (item.subtitle.isNotBlank()) Text(item.subtitle, fontSize = 21.sp, color = m.subtle, maxLines = if (b.driving) 1 else 1, overflow = TextOverflow.Ellipsis)
                }
                val pct = item.completionPct
                if (item.inProgress && pct != null) {
                    ThinProgress(pct.toFloat(), b.accent, Color(0xFF474747), Modifier.padding(top = 10.dp, end = 24.dp), rounded = true)
                }
            }
            if (item.isNew) Box(Modifier.padding(end = 16.dp).size(16.dp).clip(CircleShape).background(b.accent))
            if (playingNow) Box(Modifier.padding(end = 16.dp)) { NowPlayingBars(b.accent, 32.dp) }
        }
        if (onAction != null) ActionsMenu(menu, item.title, itemActions, onDismiss = { menu = false }) { onAction(it, item) }
    }
}

private fun Modifier.bottomLine(color: Color): Modifier = this.drawBehind {
    drawLine(color, androidx.compose.ui.geometry.Offset(0f, size.height - 0.5f), androidx.compose.ui.geometry.Offset(size.width, size.height - 0.5f), 1f)
}

@Composable
private fun LockRow(text: String) {
    val m = mediaPalette()
    Row(
        Modifier.padding(vertical = 8.dp).fillMaxWidth().height(88.dp).dashedBorder(m.dashed, 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
    ) {
        Icon(LauncherIcons.Lock, null, Modifier.size(26.dp), tint = m.subtle)
        Text(text, fontSize = 22.sp, color = m.subtle)
    }
}

@Composable
private fun AzPicker(letters: List<Char>, onPick: (Char) -> Unit, onClose: () -> Unit) {
    val m = mediaPalette()
    BackHandler(onBack = onClose)
    Box(Modifier.fillMaxSize().background(m.card.copy(alpha = 0.96f)).pressable(onClickLabel = "Fechar", onClick = onClose)) {
        Column(Modifier.align(Alignment.Center), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            letters.chunked(9).forEach { row ->
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    row.forEach { ch ->
                        Box(
                            Modifier.size(76.dp).clip(RoundedCornerShape(16.dp)).background(m.tile).pressable(onClickLabel = ch.toString()) { onPick(ch) },
                            contentAlignment = Alignment.Center,
                        ) { Text(ch.toString(), fontSize = 28.sp, color = m.text) }
                    }
                }
            }
        }
    }
}

/** Menu das ações de navegação de um item ou nó (overflow, R18.8). */
@Composable
fun ActionsMenu(expanded: Boolean, title: String, list: List<BrowseAction>, onDismiss: () -> Unit, onPick: (BrowseAction) -> Unit) {
    val m = mediaPalette()
    BackHandler(enabled = expanded, onBack = onDismiss)
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismiss,
        offset = DpOffset(400.dp, (-40).dp),
        properties = PopupProperties(focusable = false, dismissOnClickOutside = true),
        shape = RoundedCornerShape(24.dp),
        containerColor = Color(0xFF292A2A).takeIf { m === NightMedia } ?: m.card,
        shadowElevation = 12.dp,
        modifier = Modifier.widthIn(min = 320.dp),
    ) {
        Text(title, fontSize = 18.sp, fontWeight = FontWeight.Medium, color = m.subtle, maxLines = 1, overflow = TextOverflow.Ellipsis, modifier = Modifier.padding(start = 20.dp, top = 12.dp, end = 20.dp, bottom = 4.dp))
        list.forEach { a ->
            DropdownMenuItem(
                text = { Text(a.label, fontSize = 22.sp, color = m.text) },
                leadingIcon = { Icon(browseActionIcon(a), null, Modifier.size(28.dp), tint = m.text) },
                onClick = { onDismiss(); onPick(a) },
                modifier = Modifier.height(76.dp),
            )
        }
    }
}

fun browseActionIcon(a: BrowseAction): ImageVector {
    val t = (a.id + " " + a.label).lowercase()
    return when {
        "baix" in t || "download" in t -> LauncherIcons.Download
        "salv" in t || "save" in t || "curt" in t || "like" in t || "favor" in t -> LauncherIcons.Heart
        "aleat" in t || "shuffle" in t -> LauncherIcons.Shuffle
        "fila" in t || "queue" in t -> LauncherIcons.Queue
        "playlist" in t || "adicion" in t -> LauncherIcons.PlaylistAdd
        "rádio" in t || "radio" in t -> LauncherIcons.Radio
        else -> LauncherIcons.MoreVert
    }
}

// ---- Lista de faixas do nível 2 (MxAlbum) ----

@Composable
private fun TrackListBody(
    b: BrowseContext,
    node: BrowseItem,
    tracks: List<BrowseItem>,
    np: NowPlaying?,
    actions: BrowseActions,
    runAction: (BrowseAction, BrowseItem) -> Unit,
    modifier: Modifier,
) {
    val m = mediaPalette()
    val nodeActions = b.root.actions.filter { it.id in node.actionIds }
    var nodeMenu by remember { mutableStateOf(false) }
    val shown = if (b.driving) tracks.take(BrowseLayout.DRIVING_MAX_ITEMS) else tracks
    Row(modifier.padding(start = 24.dp), horizontalArrangement = Arrangement.spacedBy(36.dp)) {
        Column(Modifier.width(300.dp).padding(top = 4.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            ArtBox(node.iconBitmap, node.iconUri, node.title, 240.dp, 20.dp, shadow = true, circle = isArtist(node))
            Column {
                Text(node.title, fontSize = 28.sp, color = m.text, maxLines = 2, overflow = TextOverflow.Ellipsis)
                if (node.subtitle.isNotBlank()) {
                    Text(node.subtitle, fontSize = 22.sp, color = m.muted, maxLines = 1, overflow = TextOverflow.Ellipsis, modifier = Modifier.padding(top = 2.dp))
                }
                Text(
                    node.description.ifBlank { "${tracks.size} ${if (tracks.size == 1) "item" else "itens"}" },
                    fontSize = 20.sp, color = m.subtle, modifier = Modifier.padding(top = 4.dp),
                )
            }
        }
        Column(Modifier.weight(1f).fillMaxHeight()) {
            Row(Modifier.padding(bottom = 12.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                MediaPill(
                    "Aleatório", LauncherIcons.Shuffle,
                    onClick = { if (node.playable) actions.onPlay(node, true) else actions.onPlay(tracks.first(), true) },
                    background = m.play, color = m.onPlay, weight = FontWeight.Medium, startPad = 22.dp, endPad = 28.dp,
                )
                nodeActions.take(2).forEach { a ->
                    MediaPill(a.label, browseActionIcon(a), onClick = { runAction(a, node) }, iconSize = 26.dp)
                }
                if (nodeActions.size > 2 || nodeActions.isNotEmpty()) {
                    Box {
                        RoundIcon(LauncherIcons.MoreVert, "Mais ações do item", { nodeMenu = true }, size = 64.dp, iconSize = 28.dp, background = m.chipStrong)
                        ActionsMenu(nodeMenu, node.title, nodeActions, onDismiss = { nodeMenu = false }) { runAction(it, node) }
                    }
                }
            }
            LazyColumn(Modifier.weight(1f)) {
                shown.forEachIndexed { i, t ->
                    item(key = t.id + i) { TrackRow(i + 1, t, b, np, actions, runAction) }
                }
                if (shown.size < tracks.size) item(key = "lock") { LockRow("Mais itens com o carro parado") }
            }
        }
    }
}

@Composable
private fun TrackRow(n: Int, t: BrowseItem, b: BrowseContext, np: NowPlaying?, actions: BrowseActions, runAction: (BrowseAction, BrowseItem) -> Unit) {
    val m = mediaPalette()
    val playing = np?.mediaId != null && np.mediaId == t.id
    val itemActions = b.root.actions.filter { it.id in t.actionIds }
    var menu by remember { mutableStateOf(false) }
    Row(Modifier.fillMaxWidth().height(88.dp).bottomLine(m.line), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(
            Modifier.weight(1f).fillMaxHeight().pressable(onClickLabel = t.title) { actions.onPlay(t, false) }.padding(start = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            Box(Modifier.width(36.dp), contentAlignment = Alignment.Center) {
                if (playing) NowPlayingBars(b.accent, 28.dp) else Text("$n", fontSize = 22.sp, color = m.subtle)
            }
            Column(Modifier.weight(1f)) {
                Text(t.title, fontSize = 25.sp, color = if (playing) b.accent else m.text, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    if (t.explicit) ExplicitBadge(22.dp, m.subtle, m.card)
                    if (t.download == 2) Icon(LauncherIcons.Download, "Baixado", Modifier.size(22.dp), tint = m.subtle)
                    Text(t.subtitle, fontSize = 20.sp, color = m.subtle, maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
            }
        }
        if (itemActions.isNotEmpty()) {
            Box {
                RoundIcon(LauncherIcons.MoreVert, "Ações de ${t.title}", { menu = true }, iconSize = 28.dp, tint = m.muted)
                ActionsMenu(menu, t.title, itemActions, onDismiss = { menu = false }) { runAction(it, t) }
            }
        }
    }
}

