package app.mylauncher.data.media

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaControllerCompat
import app.mylauncher.core.FileLog
import kotlin.coroutines.resume
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withTimeoutOrNull

/**
 * Retoma a música de um app sem abrir a tela dele (spec 016, R16.5), como a retomada de mídia
 * do Android Auto e do Android 11+:
 * 1. sessão ativa → play();
 * 2. MediaBrowserService do app, pedindo a raiz de retomada (EXTRA_RECENT) → play();
 * 3. tecla MEDIA_PLAY direcionada ao app;
 * 4. sem sessão em [SESSION_TIMEOUT_MS], abre o app.
 */
class MediaResumer(
    private val context: Context,
    private val scope: CoroutineScope,
    private val mediaApps: MediaApps,
    private val activePackages: StateFlow<Set<String>>,
    private val controlsFor: (String) -> MediaControls?,
    private val launchApp: (String) -> Unit,
) {
    private var browser: MediaBrowserCompat? = null
    private var job: Job? = null

    fun resume(pkg: String) {
        job?.cancel()
        job = scope.launch {
            controlsFor(pkg)?.let {
                FileLog.i("Retomando $pkg pela sessão ativa")
                it.play()
                return@launch
            }
            if (browserPlay(pkg) && awaitSession(pkg)) return@launch
            mediaApps.sendPlay(pkg)
            if (awaitSession(pkg)) {
                // Algumas sessões nascem pausadas depois do MEDIA_PLAY: confirma o play.
                controlsFor(pkg)?.play()
                return@launch
            }
            FileLog.i("$pkg não retomou em segundo plano; abrindo o app")
            launchApp(pkg)
        }
    }

    private suspend fun awaitSession(pkg: String): Boolean =
        withTimeoutOrNull(SESSION_TIMEOUT_MS) { activePackages.first { pkg in it } } != null

    /** Conecta ao MediaBrowserService do app e manda tocar. @return true se a conexão foi aceita. */
    private suspend fun browserPlay(pkg: String): Boolean {
        val component = browserService(pkg) ?: run {
            FileLog.i("$pkg não declara MediaBrowserService")
            return false
        }
        browser?.disconnect()
        val connected = withTimeoutOrNull(CONNECT_TIMEOUT_MS) {
            suspendCancellableCoroutine { cont ->
                lateinit var b: MediaBrowserCompat
                b = MediaBrowserCompat(
                    context, component,
                    object : MediaBrowserCompat.ConnectionCallback() {
                        override fun onConnected() {
                            runCatching {
                                MediaControllerCompat(context, b.sessionToken).transportControls.play()
                                FileLog.i("Retomando $pkg pelo MediaBrowserService")
                            }.onFailure { FileLog.w("Falha ao mandar tocar em $pkg", it) }
                            if (cont.isActive) cont.resume(true)
                        }

                        override fun onConnectionFailed() {
                            FileLog.i("$pkg recusou a conexão do MediaBrowser")
                            if (cont.isActive) cont.resume(false)
                        }

                        override fun onConnectionSuspended() {
                            if (cont.isActive) cont.resume(false)
                        }
                    },
                    Bundle().apply { putBoolean(EXTRA_RECENT, true) },
                )
                browser = b
                runCatching { b.connect() }.onFailure { if (cont.isActive) cont.resume(false) }
                cont.invokeOnCancellation { runCatching { b.disconnect() } }
            }
        } ?: false
        // A conexão só precisa durar até o app assumir a reprodução.
        browser?.let { b -> scope.launch { delay(KEEP_CONNECTED_MS); if (browser === b) { b.disconnect(); browser = null } } }
        return connected
    }

    private fun browserService(pkg: String): ComponentName? =
        context.packageManager
            .queryIntentServices(Intent(MediaApps.MEDIA_BROWSER_SERVICE).setPackage(pkg), 0)
            .firstOrNull()
            ?.serviceInfo
            ?.let { ComponentName(it.packageName, it.name) }

    companion object {
        /** `MediaBrowserService.BrowserRoot.EXTRA_RECENT`: pede a raiz de "retomada" ao app. */
        const val EXTRA_RECENT = "android.service.media.extra.RECENT"
        private const val CONNECT_TIMEOUT_MS = 4_000L
        private const val SESSION_TIMEOUT_MS = 5_000L
        private const val KEEP_CONNECTED_MS = 30_000L
    }
}
