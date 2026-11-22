package app.mylauncher.core.debug

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import app.mylauncher.R
import app.mylauncher.core.FileLog
import app.mylauncher.data.media.MediaKeys

/**
 * Player simulado para testar a mídia no emulador (sem Spotify/YT Music): publica uma
 * MediaSession real e, junto com o [DebugMediaBrowserService], um app de mídia completo no padrão
 * do Android Auto (spec 018): navegação, busca, fila com capas, ações personalizadas, modo podcast
 * (±15/30 s e velocidade no lugar de anterior/próxima), carregando e erro de login.
 * Ligado pelo painel de depuração (toque longo no relógio).
 */
class DebugMediaPlayer(private val context: Context) {

    private var session: MediaSessionCompat? = null
    private var queue: List<DebugCatalog.Entry> = DebugCatalog.roadTrip
    private var index = 0
    private var playing = false
    private var buffering = false
    private var positionMs = 0L
    private var updatedAt = 0L
    private var shuffle = PlaybackStateCompat.SHUFFLE_MODE_NONE
    private var repeat = PlaybackStateCompat.REPEAT_MODE_NONE
    private var liked = false
    private var speed = 1f
    private var loginRequired = false
    private val main = Handler(Looper.getMainLooper())

    val isRunning: Boolean get() = session != null
    val token: MediaSessionCompat.Token? get() = session?.sessionToken
    val isLoginRequired: Boolean get() = loginRequired

    private val loginReceiver = object : BroadcastReceiver() {
        override fun onReceive(c: Context, intent: Intent) {
            FileLog.i("Player simulado: login concluído")
            setLoginRequired(false)
        }
    }

    fun start(autoplay: Boolean = true) {
        if (session != null) return
        session = MediaSessionCompat(context, "LumiaDebugPlayer").apply {
            setCallback(callback)
            isActive = true
        }
        setBrowserEnabled(true)
        runCatching { context.registerReceiver(loginReceiver, IntentFilter(ACTION_LOGIN)) }
        index = 0
        playing = autoplay
        positionMs = 34_000
        publish()
        FileLog.i("Player simulado iniciado")
    }

    /** Tocar/pausar pelo painel de depuração (testa as sugestões das pilhas, spec 017). */
    fun togglePlay() {
        if (session == null) return
        if (playing) callback.onPause() else callback.onPlay()
    }

    /** Simula `STATE_ERROR` com "Entrar" (ERROR_CODE_AUTHENTICATION_EXPIRED, R18.17). */
    fun setLoginRequired(on: Boolean) {
        loginRequired = on
        if (on) playing = false
        publish()
    }

    fun stop() {
        session?.run {
            isActive = false
            release()
        }
        session = null
        runCatching { context.unregisterReceiver(loginReceiver) }
        setBrowserEnabled(false)
        FileLog.i("Player simulado parado")
    }

    /** O serviço de navegação simulado só existe (para o sistema) com o player ligado. */
    private fun setBrowserEnabled(on: Boolean) {
        runCatching {
            context.packageManager.setComponentEnabledSetting(
                ComponentName(context, DebugMediaBrowserService::class.java),
                if (on) PackageManager.COMPONENT_ENABLED_STATE_ENABLED else PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP,
            )
        }
    }

    private val podcast: Boolean get() = queue.getOrNull(index)?.podcast == true

    private val callback = object : MediaSessionCompat.Callback() {
        override fun onPlay() { if (loginRequired) return; playing = true; snapshot(); publish() }
        override fun onPause() { snapshot(); playing = false; publish() }
        override fun onSkipToNext() = go(index + 1)
        override fun onSkipToPrevious() = go(if (current() > 3_000) index else index - 1)
        override fun onSkipToQueueItem(id: Long) = go(id.toInt())
        override fun onSeekTo(pos: Long) { positionMs = pos.coerceIn(0, entry().durationMs); updatedAt = SystemClock.elapsedRealtime(); publish() }
        override fun onSetShuffleMode(mode: Int) { shuffle = mode; publish() }
        override fun onSetRepeatMode(mode: Int) { repeat = mode; publish() }
        override fun onPlayFromMediaId(mediaId: String, extras: Bundle?) {
            val q = DebugCatalog.queueFor(mediaId)
            queue = q
            index = q.indexOfFirst { it.id == mediaId }.coerceAtLeast(0)
            if (extras?.getBoolean(MediaKeys.SHUFFLE_EXTRA) == true) shuffle = PlaybackStateCompat.SHUFFLE_MODE_ALL
            speed = 1f
            go(index)
        }
        override fun onPlayFromSearch(query: String, extras: Bundle?) {
            val hit = DebugCatalog.allTracks.firstOrNull { query.isBlank() || query.lowercase() in it.title.lowercase() || query.lowercase() in it.subtitle.lowercase() }
            onPlayFromMediaId(hit?.id ?: DebugCatalog.roadTrip.first().id, null)
        }
        override fun onCustomAction(action: String, extras: Bundle?) {
            when (action) {
                A_LIKE -> liked = !liked
                A_SHUFFLE -> shuffle = if (shuffle == PlaybackStateCompat.SHUFFLE_MODE_NONE) PlaybackStateCompat.SHUFFLE_MODE_ALL else PlaybackStateCompat.SHUFFLE_MODE_NONE
                A_REPEAT -> repeat = when (repeat) {
                    PlaybackStateCompat.REPEAT_MODE_NONE -> PlaybackStateCompat.REPEAT_MODE_ALL
                    PlaybackStateCompat.REPEAT_MODE_ALL -> PlaybackStateCompat.REPEAT_MODE_ONE
                    else -> PlaybackStateCompat.REPEAT_MODE_NONE
                }
                A_BACK15 -> { snapshot(); positionMs = (positionMs - 15_000).coerceAtLeast(0) }
                A_FWD30 -> { snapshot(); positionMs = (positionMs + 30_000).coerceAtMost(entry().durationMs) }
                A_SPEED -> { snapshot(); speed = when (speed) { 1f -> 1.5f; 1.5f -> 2f; else -> 1f } }
            }
            publish()
        }
    }

    private fun entry() = queue[index.coerceIn(0, queue.lastIndex)]

    private fun current(): Long =
        if (playing && !buffering) positionMs + ((SystemClock.elapsedRealtime() - updatedAt) * speed).toLong() else positionMs

    private fun snapshot() {
        positionMs = current().coerceAtMost(entry().durationMs)
        updatedAt = SystemClock.elapsedRealtime()
    }

    /** Troca de faixa com 700 ms de carregamento (o tocar vira indicador, R18.15). */
    private fun go(i: Int) {
        index = Math.floorMod(i, queue.size)
        positionMs = entry().pct?.let { (it * entry().durationMs).toLong() } ?: 0
        if (loginRequired) { publish(); return }
        playing = true
        buffering = true
        publish()
        main.postDelayed({ buffering = false; publish() }, 700)
    }

    private fun action(id: String, label: String, icon: Int) =
        PlaybackStateCompat.CustomAction.Builder(id, label, icon).build()

    private fun publish() {
        val s = session ?: return
        val t = entry()
        updatedAt = SystemClock.elapsedRealtime()
        s.setQueueTitle(if (podcast) "Conversa de Estrada" else if (queue === DebugCatalog.album) "Horizonte" else "Viagem longa")
        s.setQueue(queue.mapIndexed { i, q ->
            MediaSessionCompat.QueueItem(
                MediaDescriptionCompat.Builder().setMediaId(q.id).setTitle(q.title).setSubtitle(q.subtitle)
                    .setIconBitmap(DebugCatalog.bitmap(q.art, 96)).build(),
                i.toLong(),
            )
        })
        s.setMetadata(
            MediaMetadataCompat.Builder()
                .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, t.id)
                .putString(MediaMetadataCompat.METADATA_KEY_TITLE, t.title)
                .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, if (podcast) t.description else t.subtitle)
                .putString(MediaMetadataCompat.METADATA_KEY_ALBUM, if (podcast) "30 jul · ${t.durationMs / 60_000} min" else "Horizonte · 2026")
                .putLong(MediaMetadataCompat.METADATA_KEY_DURATION, t.durationMs)
                .putBitmap(MediaMetadataCompat.METADATA_KEY_ALBUM_ART, DebugCatalog.bitmap(t.art, 512))
                .putLong(MediaKeys.IS_EXPLICIT, if (t.explicit) 1 else 0)
                .putLong(MediaKeys.DOWNLOAD_STATUS, if (t.downloaded) 2 else 0)
                .putString(MediaKeys.SUBTITLE_LINK, if (podcast) "pod:conversa" else "al:horizonte")
                .build(),
        )
        s.setShuffleMode(shuffle)
        s.setRepeatMode(repeat)
        val base = PlaybackStateCompat.ACTION_PLAY or PlaybackStateCompat.ACTION_PAUSE or
            PlaybackStateCompat.ACTION_PLAY_PAUSE or PlaybackStateCompat.ACTION_SEEK_TO or
            PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM or PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID or
            PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH
        val icon = R.mipmap.ic_launcher
        val builder = PlaybackStateCompat.Builder()
            .setActiveQueueItemId(index.toLong())
            .setExtras(Bundle().apply { putString(MediaKeys.PLAYBACK_MEDIA_ID, t.id) })
        if (podcast) {
            // Podcast: sem anterior/próxima; ±15/30 s ocupam as posições (R18.13).
            builder.setActions(base)
            builder.addCustomAction(action(A_BACK15, "Voltar 15 segundos", icon))
            builder.addCustomAction(action(A_FWD30, "Avançar 30 segundos", icon))
            builder.addCustomAction(action(A_SPEED, "Velocidade ${if (speed == 1f) "1" else if (speed == 1.5f) "1,5" else "2"}×", icon))
            builder.addCustomAction(action("dbg.ouvido", "Marcar como ouvido", icon))
            builder.addCustomAction(action("dbg.baixar", "Baixar episódio", icon))
            builder.addCustomAction(action("dbg.timer", "Desligar em 30 min", icon))
            builder.addCustomAction(action("dbg.fav", "Favoritar", icon))
        } else {
            builder.setActions(base or PlaybackStateCompat.ACTION_SKIP_TO_NEXT or PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS or
                PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE or PlaybackStateCompat.ACTION_SET_REPEAT_MODE)
            builder.addCustomAction(action(A_LIKE, if (liked) "Curtido" else "Curtir", icon))
            builder.addCustomAction(action(A_SHUFFLE, "Aleatório", icon))
            builder.addCustomAction(action(A_REPEAT, "Repetir", icon))
            builder.addCustomAction(action("dbg.radio", "Rádio da faixa", icon))
            builder.addCustomAction(action("dbg.playlist", "Adicionar à playlist", icon))
        }
        if (loginRequired) {
            val pi = PendingIntent.getBroadcast(context, 0, Intent(ACTION_LOGIN).setPackage(context.packageName), PendingIntent.FLAG_UPDATE_CURRENT)
            builder.setErrorMessage(PlaybackStateCompat.ERROR_CODE_AUTHENTICATION_EXPIRED, "Para ouvir suas músicas, entre na sua conta. A tela de login abre aqui na multimídia.")
            builder.setExtras(Bundle().apply {
                putString(MediaKeys.ERROR_RESOLUTION_LABEL, "Entrar")
                putParcelable(MediaKeys.ERROR_RESOLUTION_INTENT, pi)
            })
            builder.setState(PlaybackStateCompat.STATE_ERROR, positionMs, 0f, updatedAt)
        } else {
            val state = when {
                buffering -> PlaybackStateCompat.STATE_BUFFERING
                playing -> PlaybackStateCompat.STATE_PLAYING
                else -> PlaybackStateCompat.STATE_PAUSED
            }
            builder.setState(state, positionMs, if (playing && !buffering) speed else 0f, updatedAt)
        }
        s.setPlaybackState(builder.build())
    }

    companion object {
        const val ACTION_LOGIN = "app.mylauncher.DEBUG_LOGIN"
        private const val A_LIKE = "dbg.like"
        private const val A_SHUFFLE = "dbg.shuffle"
        private const val A_REPEAT = "dbg.repeat"
        private const val A_BACK15 = "dbg.back15"
        private const val A_FWD30 = "dbg.fwd30"
        private const val A_SPEED = "dbg.speed"
    }
}
