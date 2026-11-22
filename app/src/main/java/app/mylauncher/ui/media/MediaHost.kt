package app.mylauncher.ui.media

import android.content.Intent
import android.speech.RecognizerIntent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.mylauncher.core.FileLog
import app.mylauncher.data.apps.AppsRepository
import app.mylauncher.data.media.MediaUiState
import app.mylauncher.data.media.browse.BrowseItem
import app.mylauncher.data.media.browse.HeadUnitSource
import app.mylauncher.data.media.browse.MediaBrowserConnector
import app.mylauncher.data.media.browse.MediaLibrary
import app.mylauncher.ui.icons.LauncherIcons

/** Situação da conexão de navegação do app aberto. */
private sealed interface Conn {
    data object Loading : Conn
    data class Ready(val c: MediaBrowserConnector) : Conn
    data object None : Conn
}

/** O que o host precisa do resto do launcher. */
data class MediaHostCallbacks(
    val onExpand: () -> Unit,
    val onCollapse: () -> Unit,
    val onLaunchApp: (String) -> Unit,
    val onPickApp: (String) -> Unit,
    val onPrefer: (String) -> Unit,
    val onGrantAccess: () -> Unit,
)

/**
 * Mídia no padrão do Android Auto (spec 018). `full = false`: player dividido com o mapa
 * (MxSplit). `full = true`: as páginas em tela cheia (MxPlayer, MxBrowse, MxAlbum, MxQueue,
 * MxSearch, MxSources), com Voltar pela [MediaNav].
 */
@Composable
fun MediaHost(
    full: Boolean,
    state: MediaUiState,
    nav: MediaNav,
    apps: AppsRepository,
    library: MediaLibrary,
    musicApp: String,
    driving: Boolean,
    activePackages: Set<String>,
    cb: MediaHostCallbacks,
    modifier: Modifier = Modifier,
) {
    val np = state.nowPlaying
    val controls = state.controls

    // Uma sessão começou a tocar em outro app: a mídia passa a mostrar esse app.
    LaunchedEffect(np?.sourcePackage, np?.playing) {
        if (np != null && np.playing && nav.app != null && nav.app != np.sourcePackage) nav.app = null
    }
    val appPkg = nav.app ?: np?.sourcePackage ?: musicApp
    val label = remember(appPkg) { library.label(appPkg) }
    val accent = rememberAccent(appPkg)
    val conn by produceState<Conn>(Conn.Loading, appPkg) {
        value = Conn.Loading
        if (appPkg.startsWith("com.tw.")) { value = Conn.None; return@produceState }
        value = library.open(appPkg)?.let { Conn.Ready(it) } ?: Conn.None
    }
    val ready = (conn as? Conn.Ready)?.c
    val root = ready?.root
    val npForApp = np?.takeIf { it.sourcePackage == appPkg }

    // Busca por voz (R18.11): reconhecedor do sistema → "toque X" no app.
    var voiceTarget by remember { mutableStateOf<String?>(null) }
    val voice = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { r ->
        val q = r.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.firstOrNull()
        val pkg = voiceTarget
        if (!q.isNullOrBlank() && pkg != null) {
            library.addSearch(pkg, q)
            cb.onPrefer(pkg)
            library.connected(pkg)?.playFromSearch(q) ?: controls?.playFromSearch(q)
            nav.reset(MediaPage.Player)
        }
    }
    val startVoice: () -> Unit = {
        voiceTarget = appPkg
        runCatching {
            voice.launch(
                Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                    .putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                    .putExtra(RecognizerIntent.EXTRA_LANGUAGE, "pt-BR")
                    .putExtra(RecognizerIntent.EXTRA_PROMPT, "Diga o que quer ouvir"),
            )
        }.onFailure { FileLog.w("Reconhecimento de voz indisponível: ${it.message}") }
    }

    val playerActions = PlayerActions(
        onBrowse = { nav.go(MediaPage.Browse()) },
        onSearch = { nav.go(MediaPage.Search) },
        onQueue = { nav.go(MediaPage.Queue) },
        onSources = { nav.go(MediaPage.Sources) },
        onOpenApp = {
            val pi = npForApp?.sessionActivity
            if (pi != null) runCatching { pi.send() }.onFailure { cb.onLaunchApp(appPkg) } else cb.onLaunchApp(appPkg)
        },
        onSubtitleLink = { id -> if (ready != null) nav.go(MediaPage.Browse(listOf(BrowseItem(id, npForApp?.artist.orEmpty(), browsable = true)))) },
    )

    if (!full) {
        // ---- Player dividido (MxSplit) ----
        if (np == null) {
            Box(modifier) {
                MediaMessage(
                    state = MessageState(
                        LauncherIcons.Music, if (state.listenerGranted) "Nada tocando" else "Acesso às notificações necessário",
                        if (state.listenerGranted) "Escolha um app de mídia para começar." else "Permita o acesso para ver e controlar o que está tocando.",
                        if (state.listenerGranted) null else "Permitir acesso", "Trocar de app", false,
                    ),
                    accent = accent, driving = driving, onPrimary = cb.onGrantAccess,
                    onSecondary = { nav.reset(MediaPage.Sources); cb.onExpand() },
                    modifier = Modifier.fillMaxSize(), compact = true,
                )
            }
            return
        }
        val splitAccent = rememberAccent(np.sourcePackage)
        SplitPlayer(
            np, controls, apps,
            PlayerContext(np.sourcePackage, remember(np.sourcePackage) { library.label(np.sourcePackage) }, splitAccent, ready != null && np.sourcePackage == appPkg, root?.searchSupported == true, driving),
            PlayerActions(
                onQueue = { nav.reset(MediaPage.Player); nav.go(MediaPage.Queue); cb.onExpand() },
                onSources = { nav.reset(MediaPage.Player); nav.go(MediaPage.Sources); cb.onExpand() },
                onExpand = { nav.moreOpen = false; nav.reset(MediaPage.Player); cb.onExpand() },
                onMore = { nav.moreOpen = true; nav.reset(MediaPage.Player); cb.onExpand() },
                onSubtitleLink = { id -> nav.reset(MediaPage.Player); nav.go(MediaPage.Browse(listOf(BrowseItem(id, np.artist, browsable = true)))); cb.onExpand() },
            ),
            modifier,
        )
        return
    }

    // ---- Tela cheia ----
    val browseActions = BrowseActions(
        onOpen = { item -> val p = nav.page; nav.go(MediaPage.Browse(((p as? MediaPage.Browse)?.stack ?: emptyList()) + item)) },
        onPlay = { item, shuffle ->
            cb.onPrefer(appPkg)
            ready?.play(item.id, shuffle)
            nav.go(MediaPage.Player)
        },
        onSources = { nav.go(MediaPage.Sources) },
        onSearch = { nav.go(MediaPage.Search) },
        onOpenPlayer = { nav.go(MediaPage.Player) },
        onBack = { if (!nav.back()) nav.reset(MediaPage.Player) },
        onTab = { nav.setTab(appPkg, it) },
    )
    AnimatedContent(
        targetState = nav.page,
        contentKey = { p -> if (p is MediaPage.Browse) "browse${p.stack.size}" else p.toString() },
        transitionSpec = { fadeIn(tween(180)).togetherWith(fadeOut(tween(120))) },
        modifier = modifier,
        label = "mediaPage",
    ) { page ->
        val m = Modifier.fillMaxSize()
        when (page) {
            MediaPage.Player -> {
                if (npForApp != null) {
                    FullPlayer(
                        npForApp, controls, apps,
                        PlayerContext(appPkg, label, accent, ready != null, root?.searchSupported == true, driving),
                        playerActions, nav.moreOpen, { nav.moreOpen = it }, m,
                    )
                } else {
                    NothingPlaying(apps, appPkg, label, accent, conn, driving, m, onBrowse = { nav.go(MediaPage.Browse()) }, onSources = { nav.go(MediaPage.Sources) }, onCollapse = cb.onCollapse)
                }
            }
            MediaPage.Queue -> {
                if (npForApp != null && npForApp.queue.isNotEmpty()) {
                    QueueScreen(npForApp, controls, accent, onBack = { if (!nav.back()) nav.reset(MediaPage.Player) }, modifier = m)
                } else {
                    LaunchedEffect(Unit) { nav.reset(MediaPage.Player) }
                }
            }
            MediaPage.Sources -> SourcesScreen(
                apps, library, np, activePackages, listOf(musicApp), accent,
                onClose = { if (!nav.back()) nav.reset(MediaPage.Player) },
                onOpenPlayer = { nav.app = null; nav.reset(MediaPage.Player) },
                onPickApp = { pkg -> cb.onPickApp(pkg); nav.app = pkg; nav.reset(MediaPage.Browse()) },
                onPickHeadUnit = { s: HeadUnitSource -> cb.onLaunchApp(s.pkg) },
                modifier = m,
            )
            MediaPage.Search -> {
                val c = ready
                if (c == null) {
                    LaunchedEffect(Unit) { nav.reset(MediaPage.Player) }
                } else {
                    SearchScreen(
                        search = { q -> c.search(q) },
                        accent = accent,
                        driving = driving,
                        recents = remember(appPkg) { library.recentSearches(appPkg) },
                        onBack = { if (!nav.back()) nav.reset(MediaPage.Player) },
                        onPlay = { item -> cb.onPrefer(appPkg); c.play(item.id); nav.go(MediaPage.Player) },
                        onOpen = { item -> nav.go(MediaPage.Browse(listOf(item))) },
                        onVoice = startVoice,
                        onPlayQuery = { q -> cb.onPrefer(appPkg); c.playFromSearch(q); library.addSearch(appPkg, q); nav.go(MediaPage.Player) },
                        onSubmit = { q -> library.addSearch(appPkg, q) },
                        modifier = m,
                    )
                }
            }
            is MediaPage.Browse -> when (val st = conn) {
                Conn.Loading -> LoadingScreen(apps, appPkg, label, accent, m, onSources = { nav.go(MediaPage.Sources) })
                Conn.None -> {
                    // App sem navegação (ou recusou o Lumia): modo só player (R18.2).
                    LaunchedEffect(appPkg) {
                        if (appPkg !in activePackages) cb.onPickApp(appPkg)
                        nav.reset(MediaPage.Player)
                    }
                }
                is Conn.Ready -> {
                    val r = st.c.root
                    if (r == null) {
                        LoadingScreen(apps, appPkg, label, accent, m, onSources = { nav.go(MediaPage.Sources) })
                    } else {
                        val b = BrowseContext(st.c, r, appPkg, label, accent, driving)
                        if (page.stack.isEmpty()) {
                            BrowseTabsScreen(b, apps, npForApp, controls, nav.tabs[appPkg], browseActions, m)
                        } else {
                            BrowseNodeScreen(b, page.stack.last(), page.stack.size + 1, npForApp, controls, browseActions, m)
                        }
                    }
                }
            }
        }
    }
}

/** Carregando a navegação (wireframe MxState). */
@Composable
private fun LoadingScreen(apps: AppsRepository, pkg: String, label: String, accent: androidx.compose.ui.graphics.Color, modifier: Modifier, onSources: () -> Unit) {
    MediaCardFrame(modifier, start = 12.dp, end = 20.dp, top = 12.dp, bottom = 16.dp) {
        Row(Modifier.height(88.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            AppPill(apps, pkg, label, onSources, endPad = 14.dp)
            SkeletonTabs()
        }
        MediaLoading(accent, Modifier.weight(1f).fillMaxWidth())
    }
}

/** Nada tocando no app aberto: convida a navegar (ou trocar de app). */
@Composable
private fun NothingPlaying(
    apps: AppsRepository,
    pkg: String,
    label: String,
    accent: androidx.compose.ui.graphics.Color,
    conn: Conn,
    driving: Boolean,
    modifier: Modifier,
    onBrowse: () -> Unit,
    onSources: () -> Unit,
    onCollapse: () -> Unit,
) {
    MediaCardFrame(modifier, start = 12.dp, end = 20.dp, top = 12.dp, bottom = 16.dp) {
        Row(Modifier.height(88.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(14.dp)) {
            RoundIcon(LauncherIcons.ChevronDown, "Voltar", onCollapse, iconSize = 34.dp)
            AppPill(apps, pkg, label, onSources)
            Spacer(Modifier.weight(1f))
        }
        MediaMessage(
            state = MessageState(
                LauncherIcons.Music, "Nada tocando em $label",
                if (conn is Conn.Ready) "Escolha algo para ouvir no app." else "Toque no app para começar a ouvir.",
                if (conn is Conn.Ready) "Navegar" else null, "Trocar de app", false,
            ),
            accent = accent, driving = driving, onPrimary = onBrowse, onSecondary = onSources,
            modifier = Modifier.weight(1f).fillMaxWidth(),
        )
    }
}
