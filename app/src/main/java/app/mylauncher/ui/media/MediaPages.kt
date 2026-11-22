package app.mylauncher.ui.media

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.mylauncher.data.apps.AppsRepository
import app.mylauncher.data.media.MediaControls
import app.mylauncher.data.media.NowPlaying
import app.mylauncher.data.media.browse.BrowseItem
import app.mylauncher.data.media.browse.HeadUnitSource
import app.mylauncher.data.media.browse.MediaLibrary
import app.mylauncher.ui.components.AppIcon
import app.mylauncher.ui.components.pressable
import app.mylauncher.ui.icons.LauncherIcons
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

private fun Modifier.bottomLine(color: Color): Modifier = this.drawBehind {
    drawLine(color, Offset(0f, size.height - 0.5f), Offset(size.width, size.height - 0.5f), 1f)
}

// =============================================================================================
// Fila (MxQueue)

/**
 * Fila sobre a capa desfocada (R18.16): título da fila, item atual destacado na cor do app, os
 * já tocados esmaecidos acima (aviso "N já tocadas acima") e miniaturas. Tocar troca a faixa.
 */
@Composable
fun QueueScreen(np: NowPlaying, controls: MediaControls?, accent: Color, onBack: () -> Unit, modifier: Modifier = Modifier) {
    val active = np.queue.indexOfFirst { it.id == np.activeQueueId }.coerceAtLeast(0)
    val state = rememberLazyListState(initialFirstVisibleItemIndex = active)
    val position = rememberPosition(np)
    Box(modifier.clip(RoundedCornerShape(24.dp))) {
        BlurredArtBackdrop(np.art, np.artKey(), scrim = 0.78f, modifier = Modifier.fillMaxSize(), fallback = NightMedia.card)
        Column(Modifier.fillMaxSize().padding(start = 12.dp, top = 12.dp, end = 24.dp)) {
            Row(Modifier.height(88.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                RoundIcon(LauncherIcons.ArrowBack, "Voltar ao tocando agora", onBack, iconSize = 34.dp, tint = OnArtMedia.text)
                Column(Modifier.weight(1f)) {
                    Text("Fila", fontSize = 30.sp, color = OnArtMedia.text)
                    val sub = listOfNotNull(np.queueTitle.takeIf { it.isNotBlank() }, "${np.queue.size} ${if (np.queue.size == 1) "item" else "itens"}").joinToString(" · ")
                    Text(sub, fontSize = 20.sp, color = OnArtMedia.meta, maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
                if (active > 0 && state.firstVisibleItemIndex > 0) {
                    Row(
                        Modifier.height(44.dp).clip(CircleShape).background(Color.White.copy(alpha = 0.1f)).padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Icon(LauncherIcons.ChevronUp, null, Modifier.size(20.dp), tint = OnArtMedia.muted)
                        Text("$active já ${if (active == 1) "tocada" else "tocadas"} acima", fontSize = 19.sp, color = OnArtMedia.muted)
                    }
                }
            }
            LazyColumn(state = state, modifier = Modifier.weight(1f).fillMaxWidth().padding(start = 88.dp)) {
                itemsIndexed(np.queue, key = { _, q -> q.id }) { i, q ->
                    val cur = i == active
                    Row(
                        Modifier
                            .padding(bottom = 4.dp)
                            .fillMaxWidth()
                            .height(96.dp)
                            .alpha(if (i < active) 0.55f else 1f)
                            .clip(RoundedCornerShape(20.dp))
                            .background(if (cur) Color.White.copy(alpha = 0.12f) else Color.Transparent)
                            .pressable(onClickLabel = q.title) { controls?.playQueueItem(q.id) }
                            .padding(start = 12.dp, end = 20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(22.dp),
                    ) {
                        ArtBox(q.art, q.artUri, q.title, 68.dp, 10.dp)
                        Column(Modifier.weight(1f)) {
                            Text(q.title, fontSize = 25.sp, color = if (cur) accent else OnArtMedia.text, maxLines = 1, overflow = TextOverflow.Ellipsis)
                            if (q.subtitle.isNotBlank()) Text(q.subtitle, fontSize = 20.sp, color = OnArtMedia.meta, maxLines = 1, overflow = TextOverflow.Ellipsis)
                        }
                        if (cur) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                NowPlayingBars(accent, 28.dp)
                                if (np.hasProgress) Text("${formatTimeMs(position)} / ${formatTimeMs(np.durationMs)}", fontSize = 20.sp, color = accent)
                            }
                        } else if (q.durationMs > 0) {
                            Text(formatTimeMs(q.durationMs), fontSize = 20.sp, color = OnArtMedia.meta)
                        }
                    }
                }
            }
        }
    }
}

// =============================================================================================
// Busca (MxSearch / MxSearchDriving)

/**
 * Busca no app (R18.11): parado, campo com o teclado do Lumia dentro do card e resultados ao vivo
 * agrupados; em movimento, o teclado some e fica a voz, com as buscas recentes.
 */
@Composable
fun SearchScreen(
    search: suspend (String) -> List<BrowseItem>?,
    accent: Color,
    driving: Boolean,
    recents: List<String>,
    onBack: () -> Unit,
    onPlay: (BrowseItem) -> Unit,
    onOpen: (BrowseItem) -> Unit,
    onVoice: () -> Unit,
    onPlayQuery: (String) -> Unit,
    onSubmit: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val m = mediaPalette()
    var query by rememberSaveable { mutableStateOf("") }
    var results by remember { mutableStateOf<List<BrowseItem>?>(null) }
    LaunchedEffect(query) {
        if (query.isBlank()) { results = null; return@LaunchedEffect }
        delay(450)
        results = search(query)
    }
    MediaCardFrame(modifier, start = 12.dp, end = 20.dp, top = 12.dp, bottom = 16.dp) {
        Row(Modifier.height(88.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            RoundIcon(LauncherIcons.ArrowBack, "Voltar", onBack, iconSize = 34.dp)
            if (driving) {
                Row(
                    Modifier.weight(1f).height(68.dp).dashedBorder(m.dashed, 34.dp).padding(horizontal = 24.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                ) {
                    Icon(LauncherIcons.Lock, null, Modifier.size(26.dp), tint = m.disabled)
                    Text("Teclado indisponível com o carro em movimento", fontSize = 22.sp, color = m.disabled, maxLines = 1)
                }
            } else {
                Row(
                    Modifier.weight(1f).height(68.dp).clip(CircleShape).background(m.field).padding(start = 24.dp, end = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                ) {
                    Icon(LauncherIcons.Search, null, Modifier.size(28.dp), tint = m.muted)
                    Box(Modifier.weight(1f)) {
                        if (query.isEmpty()) Text("Buscar", fontSize = 26.sp, color = m.subtle) else Text(query + "|", fontSize = 26.sp, color = m.text, maxLines = 1)
                    }
                    if (query.isNotEmpty()) RoundIcon(LauncherIcons.Close, "Limpar", { query = "" }, size = 56.dp, iconSize = 26.dp, tint = m.muted)
                }
            }
            Box(
                Modifier.size(76.dp).clip(CircleShape).background(m.text.copy(alpha = 0.1f)).pressable(onClickLabel = "Buscar por voz", onClick = onVoice),
                contentAlignment = Alignment.Center,
            ) { MicGlyph(32.dp, accent, m.text) }
        }

        if (driving) {
            Row(Modifier.weight(1f).fillMaxWidth().padding(start = 120.dp, end = 40.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(64.dp)) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(20.dp)) {
                    Box(
                        Modifier.size(208.dp).clip(CircleShape).background(accent.copy(alpha = 0.18f)),
                        contentAlignment = Alignment.Center,
                    ) {
                        Box(Modifier.size(176.dp).clip(CircleShape).background(accent).pressable(onClickLabel = "Toque para falar", onClick = onVoice), contentAlignment = Alignment.Center) {
                            MicGlyph(72.dp, Color(0xFF3A2300), Color(0xFF3A2300))
                        }
                    }
                    Text("Diga o que quer ouvir", fontSize = 26.sp, color = m.text)
                    Text("Ex.: \"tocar Duo Litoral\"", fontSize = 20.sp, color = m.subtle)
                }
                Column(Modifier.weight(1f)) {
                    Text("Buscas recentes", fontSize = 22.sp, fontWeight = FontWeight.Medium, color = m.muted, modifier = Modifier.padding(bottom = 8.dp))
                    if (recents.isEmpty()) Text("Nenhuma busca ainda", fontSize = 22.sp, color = m.subtle)
                    recents.forEach { r ->
                        Row(
                            Modifier.fillMaxWidth().height(88.dp).bottomLine(m.line).pressable(onClickLabel = r) { onPlayQuery(r) },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(18.dp),
                        ) {
                            Icon(LauncherIcons.History, null, Modifier.size(28.dp), tint = m.subtle)
                            Text(r, fontSize = 25.sp, color = m.text, maxLines = 1, overflow = TextOverflow.Ellipsis)
                        }
                    }
                }
            }
            return@MediaCardFrame
        }

        val list = results.orEmpty()
        val songs = list.filter { it.playable && !it.browsable }.take(4)
        val others = list.filter { it.browsable }.take(4)
        Row(Modifier.weight(1f).fillMaxWidth().padding(start = 88.dp, top = 4.dp, bottom = 12.dp), horizontalArrangement = Arrangement.spacedBy(24.dp)) {
            Column(Modifier.weight(1.3f).verticalScroll(rememberScrollState())) {
                if (results != null && list.isEmpty()) Text("Nenhum resultado para \"$query\"", fontSize = 22.sp, color = m.subtle)
                if (songs.isNotEmpty()) {
                    Text(songs.first().groupTitle ?: "Músicas", fontSize = 20.sp, fontWeight = FontWeight.Medium, color = m.muted, modifier = Modifier.padding(bottom = 4.dp))
                    songs.forEach { s ->
                        Row(
                            Modifier.fillMaxWidth().height(80.dp).bottomLine(m.line).pressable(onClickLabel = s.title) { onPlay(s) },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(18.dp),
                        ) {
                            ArtBox(s.iconBitmap, s.iconUri, s.title, 60.dp, 10.dp)
                            Column(Modifier.weight(1f)) {
                                Text(s.title, fontSize = 24.sp, color = m.text, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                Text(s.subtitle, fontSize = 19.sp, color = m.subtle, maxLines = 1, overflow = TextOverflow.Ellipsis)
                            }
                        }
                    }
                }
            }
            Column(Modifier.weight(1f)) {
                if (others.isNotEmpty()) {
                    Text(others.first().groupTitle ?: "Artistas e álbuns", fontSize = 20.sp, fontWeight = FontWeight.Medium, color = m.muted, modifier = Modifier.padding(bottom = 8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                        others.take(3).forEach { o ->
                            val artist = o.subtitle.equals("Artista", true)
                            Column(
                                Modifier.width(112.dp).clip(RoundedCornerShape(16.dp)).pressable(onClickLabel = o.title) { onOpen(o) },
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                            ) {
                                ArtBox(o.iconBitmap, o.iconUri, o.title, 104.dp, 14.dp, circle = artist)
                                Text(o.title, fontSize = 19.sp, color = m.text, textAlign = TextAlign.Center, maxLines = 2, overflow = TextOverflow.Ellipsis)
                            }
                        }
                    }
                }
            }
        }
        Keyboard(
            accent = accent,
            onKey = { query += it },
            onBackspace = { query = query.dropLast(1) },
            onSubmit = { if (query.isNotBlank()) onSubmit(query) },
        )
    }
}

/** Microfone com a cápsula na cor de destaque (como no wireframe). */
@Composable
private fun MicGlyph(size: Dp, capsule: Color, stroke: Color) {
    androidx.compose.foundation.Canvas(Modifier.size(size)) {
        val u = this.size.width / 24f
        drawRoundRect(capsule, topLeft = Offset(8.5f * u, 2.5f * u), size = androidx.compose.ui.geometry.Size(7f * u, 12f * u), cornerRadius = androidx.compose.ui.geometry.CornerRadius(3.5f * u))
        val p = androidx.compose.ui.graphics.Path().apply {
            moveTo(5f * u, 11f * u)
            cubicTo(5f * u, 15f * u, 8f * u, 18f * u, 12f * u, 18f * u)
            cubicTo(16f * u, 18f * u, 19f * u, 15f * u, 19f * u, 11f * u)
            moveTo(12f * u, 18f * u)
            lineTo(12f * u, 21.5f * u)
        }
        drawPath(p, stroke, style = androidx.compose.ui.graphics.drawscope.Stroke(2f * u, cap = androidx.compose.ui.graphics.StrokeCap.Round))
    }
}

/**
 * Teclado do Lumia dentro do card (wireframe MxSearch). O teclado do sistema abriria uma janela
 * com foco e as barras do sistema apareceriam (CLAUDE.md, armadilhas).
 */
@Composable
private fun Keyboard(accent: Color, onKey: (String) -> Unit, onBackspace: () -> Unit, onSubmit: () -> Unit) {
    val m = mediaPalette()
    var shift by remember { mutableStateOf(false) }
    var symbols by remember { mutableStateOf(false) }
    val rows = if (symbols) listOf("1234567890", "-/:;()\$&@\"", ".,?!'#%*+=") else listOf("qwertyuiop", "asdfghjklç", "zxcvbnm")
    Column(
        Modifier.fillMaxWidth().clip(RoundedCornerShape(20.dp)).background(m.keyboard).padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)) {
            rows[0].forEach { ch -> Key(if (shift) ch.uppercase() else ch.toString(), 84.dp) { onKey(if (shift) ch.uppercase() else ch.toString()); shift = false } }
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)) {
            rows[1].forEach { ch -> Key(if (shift) ch.uppercase() else ch.toString(), 84.dp) { onKey(if (shift) ch.uppercase() else ch.toString()); shift = false } }
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)) {
            Key("⇧", 120.dp, background = if (shift) m.text.copy(alpha = 0.3f) else m.keyAlt, label = "Maiúsculas", icon = LauncherIcons.ShiftKey) { shift = !shift }
            rows[2].forEach { ch -> Key(if (shift) ch.uppercase() else ch.toString(), 84.dp) { onKey(if (shift) ch.uppercase() else ch.toString()); shift = false } }
            Key("⌫", 120.dp, background = m.keyAlt, label = "Apagar", icon = LauncherIcons.Backspace, onClick = onBackspace)
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)) {
            Key(if (symbols) "ABC" else "?123", 140.dp, background = m.keyAlt, label = if (symbols) "Letras" else "Números e símbolos") { symbols = !symbols }
            Key("espaço", 520.dp, label = "Espaço") { onKey(" ") }
            Key("Buscar", 180.dp, background = accent, color = Color(0xFF3A2300), onClick = onSubmit)
        }
    }
}

@Composable
private fun Key(
    text: String,
    width: Dp,
    background: Color = mediaPalette().key,
    color: Color = mediaPalette().text,
    label: String = text,
    icon: ImageVector? = null,
    onClick: () -> Unit,
) {
    Box(
        Modifier.size(width, 56.dp).clip(RoundedCornerShape(12.dp)).background(background).pressable(onClickLabel = label, onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        if (icon != null) Icon(icon, label, Modifier.size(28.dp), tint = color) else Text(text, fontSize = 24.sp, color = color)
    }
}

// =============================================================================================
// Seletor de apps (MxSources)

/** Estado de um app de mídia no seletor (R18.1). */
private data class AppState(val text: String, val color: Color)

/**
 * Trocar app de mídia: tocando agora em destaque, apps de mídia em grade (com o estado de cada um)
 * e as fontes da multimídia (rádio, Bluetooth, USB, AUX).
 */
@Composable
fun SourcesScreen(
    apps: AppsRepository,
    library: MediaLibrary,
    np: NowPlaying?,
    activePackages: Set<String>,
    extraApps: List<String>,
    accent: Color,
    onClose: () -> Unit,
    onOpenPlayer: () -> Unit,
    onPickApp: (String) -> Unit,
    onPickHeadUnit: (HeadUnitSource) -> Unit,
    modifier: Modifier = Modifier,
) {
    val m = mediaPalette()
    val list by produceState<List<String>>(emptyList(), extraApps, activePackages) {
        value = withContext(Dispatchers.IO) { library.mediaApps(extraApps + activePackages) }
    }
    val headUnit by produceState<List<HeadUnitSource>>(emptyList()) { value = withContext(Dispatchers.IO) { library.headUnitSources() } }
    val tint = rememberArtTint(np)
    MediaCardFrame(modifier, start = 12.dp, end = 28.dp, top = 12.dp, bottom = 20.dp) {
        Row(Modifier.height(88.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            RoundIcon(LauncherIcons.Close, "Fechar", onClose, iconSize = 34.dp)
            Text("Mídia", fontSize = 30.sp, color = m.text)
        }
        Column(Modifier.weight(1f).fillMaxWidth().verticalScroll(rememberScrollState()).padding(start = 88.dp)) {
            if (np != null && np.title.isNotBlank()) {
                Row(
                    Modifier.fillMaxWidth().height(128.dp).clip(RoundedCornerShape(24.dp)).background(if (np.art != null) tint else m.tile)
                        .pressable(onClickLabel = "Abrir tocando agora", onClick = onOpenPlayer).padding(start = 16.dp, end = 24.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(22.dp),
                ) {
                    NowPlayingArt(np, 96.dp, 16.dp, shadow = false)
                    Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            NowPlayingBars(accent, 20.dp)
                            Text(
                                "${if (np.playing) "TOCANDO AGORA" else "PAUSADO"} · ${library.label(np.sourcePackage).uppercase()}",
                                fontSize = 18.sp, fontWeight = FontWeight.Medium, color = accent, letterSpacing = 0.4.sp, maxLines = 1,
                            )
                        }
                        Text(np.title, fontSize = 28.sp, color = m.text, maxLines = 1, overflow = TextOverflow.Ellipsis)
                        Text(np.artist, fontSize = 21.sp, color = m.muted, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    }
                    Icon(LauncherIcons.Chevron, null, Modifier.size(32.dp), tint = m.text)
                }
            }
            Text("Apps de mídia", fontSize = 22.sp, fontWeight = FontWeight.Medium, color = m.muted, modifier = Modifier.padding(top = 20.dp, bottom = 8.dp))
            list.chunked(6).forEach { row ->
                Row(Modifier.fillMaxWidth().padding(bottom = 12.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    row.forEach { pkg ->
                        val st = appState(pkg, np, activePackages, library, accent, m)
                        val playing = np?.sourcePackage == pkg && np.playing
                        Column(
                            Modifier.weight(1f).height(168.dp).clip(RoundedCornerShape(20.dp)).background(if (playing) m.tileActive else m.tile)
                                .pressable(onClickLabel = library.label(pkg)) { onPickApp(pkg) },
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
                        ) {
                            AppIcon(apps, pkg, 72.dp)
                            Text(library.label(pkg), fontSize = 21.sp, color = m.text, maxLines = 1, overflow = TextOverflow.Ellipsis, modifier = Modifier.padding(horizontal = 8.dp))
                            Text(st.text, fontSize = 17.sp, color = st.color, maxLines = 1)
                        }
                    }
                    repeat(6 - row.size) { Spacer(Modifier.weight(1f)) }
                }
            }
            if (headUnit.isNotEmpty()) {
                Text("Fontes da multimídia", fontSize = 22.sp, fontWeight = FontWeight.Medium, color = m.muted, modifier = Modifier.padding(top = 8.dp, bottom = 8.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    headUnit.forEach { s ->
                        Column(
                            Modifier.weight(1f).height(112.dp).clip(RoundedCornerShape(20.dp)).background(m.tile).pressable(onClickLabel = s.label) { onPickHeadUnit(s) },
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                        ) {
                            Icon(headUnitIcon(s.kind), null, Modifier.size(40.dp), tint = m.text)
                            Text(s.label, fontSize = 20.sp, color = m.text, maxLines = 1)
                        }
                    }
                    repeat(6 - headUnit.size) { Spacer(Modifier.weight(1f)) }
                }
            }
        }
    }
}

private fun appState(pkg: String, np: NowPlaying?, active: Set<String>, library: MediaLibrary, accent: Color, m: MediaPalette): AppState = when {
    np?.sourcePackage == pkg && np.error?.isAuth == true -> AppState("Entrar", Color(0xFFF2B8B5))
    np?.sourcePackage == pkg && np.playing -> AppState("Tocando", accent)
    pkg in active -> AppState("Pausado", m.subtle)
    library.isDenied(pkg) -> AppState("Só controles", m.subtle)
    else -> AppState(" ", m.subtle)
}

private fun headUnitIcon(k: HeadUnitSource.Kind): ImageVector = when (k) {
    HeadUnitSource.Kind.RADIO -> LauncherIcons.Radio
    HeadUnitSource.Kind.BLUETOOTH -> LauncherIcons.Bluetooth
    HeadUnitSource.Kind.USB -> LauncherIcons.Usb
    HeadUnitSource.Kind.AUX -> LauncherIcons.Aux
}

