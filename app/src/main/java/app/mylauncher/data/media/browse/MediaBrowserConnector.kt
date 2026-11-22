package app.mylauncher.data.media.browse

import android.content.ComponentName
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaControllerCompat
import androidx.media.MediaBrowserServiceCompat
import app.mylauncher.core.FileLog
import app.mylauncher.data.media.MediaKeys
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.coroutines.resume

/**
 * Conexão com o `MediaBrowserService` de um app (spec 018, R18.2), como o host do Android Auto:
 * envia os mesmos root hints, assina os nós, busca, executa ações de navegação e toca por id.
 *
 * Tudo roda na main thread (exigência do MediaBrowserCompat); os métodos `suspend` trocam de
 * contexto sozinhos.
 */
class MediaBrowserConnector(
    private val context: Context,
    val pkg: String,
    private val component: ComponentName,
    private val suggested: Boolean = false,
) {
    private var browser: MediaBrowserCompat? = null
    private var controller: MediaControllerCompat? = null
    private val _status = MutableStateFlow<BrowseStatus>(BrowseStatus.Connecting)
    val status: StateFlow<BrowseStatus> = _status.asStateFlow()

    val root: BrowseRoot? get() = (status.value as? BrowseStatus.Ready)?.root

    private fun hints() = Bundle().apply {
        putInt(MediaKeys.ROOT_CHILDREN_LIMIT, BrowseLayout.MAX_TABS)
        putInt(MediaKeys.ROOT_CHILDREN_FLAGS, MediaKeys.FLAG_BROWSABLE)
        putInt(MediaKeys.ART_SIZE_HINT, ART_SIZE_PX)
        putInt(MediaKeys.BROWSE_ACTION_LIMIT, BROWSE_ACTION_LIMIT)
        if (suggested) putBoolean(MediaBrowserServiceCompat.BrowserRoot.EXTRA_SUGGESTED, true)
    }

    /** Conecta (até [timeoutMs]). Sem resposta, root vazio ou recusa → [BrowseStatus.Unavailable]. */
    suspend fun connect(timeoutMs: Long = CONNECT_TIMEOUT_MS): BrowseStatus = withContext(Dispatchers.Main) {
        (status.value as? BrowseStatus.Ready)?.let { return@withContext it }
        val ok = withTimeoutOrNull(timeoutMs) {
            suspendCancellableCoroutine { cont ->
                var done = false
                val cb = object : MediaBrowserCompat.ConnectionCallback() {
                    override fun onConnected() { if (!done) { done = true; cont.resume(true) } }
                    override fun onConnectionFailed() { if (!done) { done = true; cont.resume(false) } }
                    override fun onConnectionSuspended() {
                        _status.value = BrowseStatus.Unavailable("conexão suspensa")
                    }
                }
                val b = runCatching { MediaBrowserCompat(context, component, cb, hints()) }.getOrNull()
                if (b == null) {
                    done = true
                    cont.resume(false)
                    return@suspendCancellableCoroutine
                }
                browser = b
                runCatching { b.connect() }.onFailure { if (!done) { done = true; cont.resume(false) } }
            }
        } ?: false
        val b = browser
        val status = if (!ok || b == null || !b.isConnected) {
            BrowseStatus.Unavailable(if (ok) "desconectado" else "recusou ou não respondeu")
        } else {
            val rootId = b.root
            if (rootId.isNullOrBlank()) {
                BrowseStatus.Unavailable("raiz vazia")
            } else {
                controller = runCatching { MediaControllerCompat(context, b.sessionToken) }.getOrNull()
                BrowseStatus.Ready(parseRoot(rootId, b.extras))
            }
        }
        if (status is BrowseStatus.Unavailable) {
            FileLog.i("Browse $pkg indisponível: ${status.reason}")
            disconnect()
        } else {
            FileLog.i("Browse $pkg conectado (busca=${(status as BrowseStatus.Ready).root.searchSupported})")
        }
        _status.value = status
        status
    }

    private fun parseRoot(id: String, extras: Bundle?): BrowseRoot {
        @Suppress("DEPRECATION")
        val actions = runCatching { extras?.getParcelableArrayList<Bundle>(MediaKeys.BROWSE_ACTION_ROOT_LIST) }.getOrNull().orEmpty().mapNotNull { b ->
            val aid = b.getString(MediaKeys.BROWSE_ACTION_ID) ?: return@mapNotNull null
            BrowseAction(aid, b.getString(MediaKeys.BROWSE_ACTION_LABEL).orEmpty(), b.getString(MediaKeys.BROWSE_ACTION_ICON)?.let(Uri::parse), b.getBundle("androidx.media.utils.extras.KEY_CUSTOM_BROWSER_ACTION_EXTRAS"))
        }
        return BrowseRoot(
            id = id,
            searchSupported = extras?.getBoolean(MediaKeys.SEARCH_SUPPORTED) == true,
            browsableStyle = extras?.getInt(MediaKeys.STYLE_BROWSABLE, 0)?.let { ItemStyle.of(it) },
            playableStyle = extras?.getInt(MediaKeys.STYLE_PLAYABLE, 0)?.let { ItemStyle.of(it) },
            actions = actions,
        )
    }

    /** Filhos de um nó, atualizados ao vivo enquanto coletados (`subscribe`). null = erro. */
    fun children(parentId: String): Flow<List<BrowseItem>?> = callbackFlow {
        val b = browser
        if (b == null || !b.isConnected) {
            trySend(null)
            awaitClose { }
            return@callbackFlow
        }
        val cb = object : MediaBrowserCompat.SubscriptionCallback() {
            override fun onChildrenLoaded(id: String, children: MutableList<MediaBrowserCompat.MediaItem>) {
                trySend(children.map(BrowseItem::from))
            }
            override fun onChildrenLoaded(id: String, children: MutableList<MediaBrowserCompat.MediaItem>, options: Bundle) {
                trySend(children.map(BrowseItem::from))
            }
            override fun onError(id: String) { trySend(null) }
            override fun onError(id: String, options: Bundle) { trySend(null) }
        }
        runCatching { b.subscribe(parentId, cb) }.onFailure { trySend(null) }
        awaitClose { runCatching { if (b.isConnected) b.unsubscribe(parentId, cb) } }
    }.flowOn(Dispatchers.Main)

    /** Uma leitura dos filhos (sem manter a assinatura). */
    suspend fun load(parentId: String, timeoutMs: Long = LOAD_TIMEOUT_MS): List<BrowseItem>? =
        withTimeoutOrNull(timeoutMs) { children(parentId).first() }

    /** Busca navegável (`onSearch`). Lista vazia = sem resultados; null = erro. */
    suspend fun search(query: String): List<BrowseItem>? = withContext(Dispatchers.Main) {
        val b = browser ?: return@withContext null
        withTimeoutOrNull(LOAD_TIMEOUT_MS) {
            suspendCancellableCoroutine { cont ->
                runCatching {
                    b.search(query, Bundle(), object : MediaBrowserCompat.SearchCallback() {
                        override fun onSearchResult(q: String, extras: Bundle?, items: MutableList<MediaBrowserCompat.MediaItem>) {
                            if (cont.isActive) cont.resume(items.map(BrowseItem::from))
                        }
                        override fun onError(q: String, extras: Bundle?) { if (cont.isActive) cont.resume(null) }
                    })
                }.onFailure { if (cont.isActive) cont.resume(null) }
            }
        }
    }

    /** Executa uma ação de navegação personalizada sobre um item. */
    suspend fun runAction(action: BrowseAction, itemId: String): BrowseActionResult? = withContext(Dispatchers.Main) {
        val b = browser ?: return@withContext null
        val extras = Bundle().apply {
            putString(MediaKeys.BROWSE_ACTION_ITEM_ID, itemId)
            action.extras?.let { putAll(it) }
        }
        withTimeoutOrNull(LOAD_TIMEOUT_MS) {
            suspendCancellableCoroutine { cont ->
                runCatching {
                    b.sendCustomAction(action.id, extras, object : MediaBrowserCompat.CustomActionCallback() {
                        override fun onResult(a: String, e: Bundle?, result: Bundle?) {
                            if (cont.isActive) cont.resume(
                                BrowseActionResult(
                                    result?.getString(MediaKeys.BROWSE_ACTION_RESULT_MESSAGE),
                                    result?.containsKey(MediaKeys.BROWSE_ACTION_RESULT_REFRESH) == true,
                                    result?.containsKey(MediaKeys.BROWSE_ACTION_RESULT_SHOW_PLAYING) == true,
                                ),
                            )
                        }
                        override fun onError(a: String, e: Bundle?, data: Bundle?) { if (cont.isActive) cont.resume(null) }
                    })
                }.onFailure { if (cont.isActive) cont.resume(null) }
            }
        }
    }

    /** Toca um item (`playFromMediaId`); `shuffle` usa o extra padrão de aleatório. */
    fun play(id: String, shuffle: Boolean = false) {
        val extras = Bundle().apply { if (shuffle) putBoolean(MediaKeys.SHUFFLE_EXTRA, true) }
        runCatching { controller?.transportControls?.playFromMediaId(id, extras) }
            .onFailure { FileLog.w("playFromMediaId falhou ($pkg): ${it.message}") }
    }

    /** Busca por voz: "toque X" (`playFromSearch`). */
    fun playFromSearch(query: String) {
        runCatching { controller?.transportControls?.playFromSearch(query, Bundle()) }
    }

    fun disconnect() {
        runCatching { browser?.disconnect() }
        browser = null
        controller = null
        if (status.value is BrowseStatus.Ready) _status.value = BrowseStatus.Unavailable("desconectado")
    }

    companion object {
        const val ART_SIZE_PX = 256
        const val BROWSE_ACTION_LIMIT = 4
        const val CONNECT_TIMEOUT_MS = 5_000L
        const val LOAD_TIMEOUT_MS = 8_000L
    }
}
