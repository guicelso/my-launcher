package app.mylauncher.data.media

import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.view.KeyEvent
import app.mylauncher.core.FileLog
import java.util.concurrent.ConcurrentHashMap

/**
 * Identifica apps de mídia e inicia a reprodução sem abrir o app (spec 010, R10.6/R10.7).
 */
class MediaApps(private val context: Context) {
    private val cache = ConcurrentHashMap<String, Boolean>()

    /** Apps da TopWay trocam a fonte pela própria interface: abrem normalmente. */
    private fun isTopWay(pkg: String) = pkg.startsWith("com.tw.")

    fun isMediaApp(pkg: String): Boolean = cache.getOrPut(pkg) {
        if (isTopWay(pkg)) return@getOrPut false
        val pm = context.packageManager
        val browser = pm.queryIntentServices(Intent(MEDIA_BROWSER_SERVICE).setPackage(pkg), 0).isNotEmpty()
        val button = pm.queryBroadcastReceivers(Intent(Intent.ACTION_MEDIA_BUTTON).setPackage(pkg), 0).isNotEmpty()
        browser || button || pkg in KNOWN
    }

    /** Envia MEDIA_PLAY direto ao receptor de botões de mídia do app. */
    fun sendPlay(pkg: String): Boolean = runCatching {
        val now = SystemClock.uptimeMillis()
        listOf(KeyEvent.ACTION_DOWN, KeyEvent.ACTION_UP).forEach { action ->
            val event = KeyEvent(now, now, action, KeyEvent.KEYCODE_MEDIA_PLAY, 0)
            context.sendBroadcast(
                Intent(Intent.ACTION_MEDIA_BUTTON).setPackage(pkg).putExtra(Intent.EXTRA_KEY_EVENT, event),
            )
        }
        FileLog.i("MEDIA_PLAY enviado para $pkg")
        true
    }.getOrElse { FileLog.w("Falha ao enviar MEDIA_PLAY para $pkg", it); false }

    companion object {
        const val MEDIA_BROWSER_SERVICE = "android.media.browse.MediaBrowserService"
        val KNOWN = setOf(
            "com.spotify.music",
            "com.google.android.apps.youtube.music",
            "com.soundcloud.android",
            "deezer.android.app",
            "com.amazon.mp3",
        )
    }
}
