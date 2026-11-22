package app.mylauncher.core.debug

import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat.MediaItem
import androidx.media.MediaBrowserServiceCompat
import app.mylauncher.LauncherApp
import app.mylauncher.data.media.MediaKeys

/**
 * App de mídia simulado (spec 018): a árvore do [DebugCatalog] servida pelo protocolo do Android
 * Auto (`MediaBrowserServiceCompat`), para testar no emulador a navegação, a busca e as ações.
 * O componente fica desligado no manifesto e o [DebugMediaPlayer] o liga junto com o player.
 */
class DebugMediaBrowserService : MediaBrowserServiceCompat() {

    override fun onCreate() {
        super.onCreate()
        val player = (application as LauncherApp).container.debugPlayer
        if (!player.isRunning) player.start(autoplay = false)
        player.token?.let { sessionToken = it }
    }

    override fun onGetRoot(clientPackageName: String, clientUid: Int, rootHints: Bundle?): BrowserRoot {
        val extras = Bundle().apply {
            putBoolean(MediaKeys.SEARCH_SUPPORTED, true)
            putInt(MediaKeys.STYLE_BROWSABLE, MediaKeys.STYLE_GRID)
            putInt(MediaKeys.STYLE_PLAYABLE, MediaKeys.STYLE_LIST)
            putParcelableArrayList(MediaKeys.BROWSE_ACTION_ROOT_LIST, DebugCatalog.rootActions())
        }
        return BrowserRoot("root", extras)
    }

    override fun onLoadChildren(parentId: String, result: Result<MutableList<MediaItem>>) {
        result.sendResult(DebugCatalog.children(parentId)?.toMutableList())
    }

    override fun onSearch(query: String, extras: Bundle?, result: Result<MutableList<MediaItem>>) {
        result.sendResult(DebugCatalog.search(query).toMutableList())
    }

    override fun onCustomAction(action: String, extras: Bundle?, result: Result<Bundle>) {
        val message = when (action) {
            DebugCatalog.ACTION_DOWNLOAD -> "Download iniciado"
            DebugCatalog.ACTION_SAVE -> "Salvo na biblioteca"
            else -> null
        }
        result.sendResult(Bundle().apply { message?.let { putString(MediaKeys.BROWSE_ACTION_RESULT_MESSAGE, it) } })
    }
}
