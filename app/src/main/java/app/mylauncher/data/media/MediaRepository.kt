package app.mylauncher.data.media

import android.content.ComponentName
import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadata
import android.media.session.MediaController
import android.media.session.MediaSessionManager
import android.media.session.PlaybackState
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import app.mylauncher.core.FileLog
import app.mylauncher.data.apps.AppsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

/** Estado exibido pelo card e pela tela de mídia. */
data class MediaUiState(
    val nowPlaying: NowPlaying? = null,
    val controls: MediaControls? = null,
    val listenerGranted: Boolean = false,
)

/**
 * Fonte alternativa de mídia (apps nativos da TopWay sem MediaSession, R4.6).
 * Implementada pela ponte da spec 006.
 */
interface ExternalMediaSource {
    val state: StateFlow<Pair<NowPlaying, MediaControls>?>
}

/** Une as MediaSessions de terceiros (R4.1–R4.4) e a fonte TopWay (R4.6). */
class MediaRepository(
    private val context: Context,
    private val apps: AppsRepository,
    private val scope: CoroutineScope,
) {
    private val sessionManager = context.getSystemService(MediaSessionManager::class.java)
    private val listenerComponent = ComponentName(context, MediaListenerService::class.java)
    private val main = Handler(Looper.getMainLooper())

    private val controllers = mutableMapOf<String, MediaController>()
    private val callbacks = mutableMapOf<String, MediaController.Callback>()
    private val lastPlayingAt = mutableMapOf<String, Long>()

    private val sessionState = MutableStateFlow<Pair<NowPlaying, MediaControls>?>(null)
    private val externalState = MutableStateFlow<Pair<NowPlaying, MediaControls>?>(null)
    private val _state = MutableStateFlow(MediaUiState())
    val state: StateFlow<MediaUiState> = _state.asStateFlow()

    private val sessionsListener = MediaSessionManager.OnActiveSessionsChangedListener { list ->
        updateControllers(list.orEmpty())
    }

    fun start() {
        scope.launch {
            MediaListenerService.connected.collect { connected -> if (connected) attach() }
        }
        scope.launch {
            combine(sessionState, externalState, MediaListenerService.connected) { s, ext, granted -> Triple(s, ext, granted) }
                .collect { (s, extNow, granted) ->
                    val chosen = when {
                        s != null && s.first.playing -> s
                        extNow != null && extNow.first.playing -> extNow
                        else -> s ?: extNow
                    }
                    _state.value = MediaUiState(chosen?.first, chosen?.second, granted)
                }
        }
        attach()
    }

    /** Liga a fonte TopWay (spec 006). */
    fun setExternalSource(source: ExternalMediaSource) {
        scope.launch { source.state.collect { externalState.value = it } }
    }

    private fun attach() {
        runCatching {
            sessionManager.removeOnActiveSessionsChangedListener(sessionsListener)
            sessionManager.addOnActiveSessionsChangedListener(sessionsListener, listenerComponent, main)
            updateControllers(sessionManager.getActiveSessions(listenerComponent))
        }.onFailure {
            // SecurityException enquanto o acesso às notificações não for concedido.
            FileLog.w("MediaSessions indisponíveis: ${it.message}")
        }
    }

    private fun updateControllers(list: List<MediaController>) {
        val keys = list.map { it.packageName }.toSet()
        (controllers.keys - keys).forEach { key ->
            callbacks.remove(key)?.let { cb -> controllers[key]?.unregisterCallback(cb) }
            controllers.remove(key)
        }
        list.forEach { controller ->
            val key = controller.packageName
            if (controllers[key]?.sessionToken == controller.sessionToken) return@forEach
            callbacks.remove(key)?.let { cb -> controllers[key]?.unregisterCallback(cb) }
            val cb = object : MediaController.Callback() {
                override fun onPlaybackStateChanged(state: PlaybackState?) = recompute()
                override fun onMetadataChanged(metadata: MediaMetadata?) = recompute()
                override fun onQueueChanged(queue: MutableList<android.media.session.MediaSession.QueueItem>?) = recompute()
                override fun onSessionDestroyed() = recompute()
            }
            controller.registerCallback(cb, main)
            controllers[key] = controller
            callbacks[key] = cb
        }
        recompute()
    }

    private fun recompute() {
        val now = SystemClock.elapsedRealtime()
        controllers.forEach { (key, c) ->
            if (c.playbackState?.state == PlaybackState.STATE_PLAYING) lastPlayingAt[key] = now
        }
        val chosenKey = SessionPicker.pick(
            controllers.map { (key, c) ->
                SessionPicker.Candidate(key, c.playbackState?.state == PlaybackState.STATE_PLAYING, lastPlayingAt[key] ?: 0L)
            },
        )
        val controller = chosenKey?.let { controllers[it] }
        sessionState.value = controller?.let { it.toNowPlaying() to SessionControls(context, it) }
    }

    private fun MediaController.toNowPlaying(): NowPlaying {
        val md = metadata
        val ps = playbackState
        val compat = runCatching {
            MediaControllerCompat(context, MediaSessionCompat.Token.fromToken(sessionToken))
        }.getOrNull()
        val art: Bitmap? = md?.getBitmap(MediaMetadata.METADATA_KEY_ALBUM_ART)
            ?: md?.getBitmap(MediaMetadata.METADATA_KEY_ART)
            ?: md?.getBitmap(MediaMetadata.METADATA_KEY_DISPLAY_ICON)
        val actions = ps?.actions ?: 0L
        return NowPlaying(
            sourcePackage = packageName,
            sourceLabel = apps.label(packageName),
            title = md?.getString(MediaMetadata.METADATA_KEY_TITLE)
                ?: md?.getString(MediaMetadata.METADATA_KEY_DISPLAY_TITLE).orEmpty(),
            artist = md?.getString(MediaMetadata.METADATA_KEY_ARTIST)
                ?: md?.getString(MediaMetadata.METADATA_KEY_DISPLAY_SUBTITLE).orEmpty(),
            album = md?.getString(MediaMetadata.METADATA_KEY_ALBUM).orEmpty(),
            art = art,
            durationMs = md?.getLong(MediaMetadata.METADATA_KEY_DURATION) ?: 0L,
            positionMs = ps?.position ?: 0L,
            positionUpdatedAt = ps?.lastPositionUpdateTime ?: 0L,
            speed = ps?.playbackSpeed?.takeIf { it > 0f } ?: 1f,
            playing = ps?.state == PlaybackState.STATE_PLAYING,
            canSkip = actions and (PlaybackState.ACTION_SKIP_TO_NEXT or PlaybackState.ACTION_SKIP_TO_PREVIOUS) != 0L,
            canSeek = actions and PlaybackState.ACTION_SEEK_TO != 0L,
            shuffle = compat?.shuffleMode?.let { it != PlaybackStateCompat.SHUFFLE_MODE_NONE && it != PlaybackStateCompat.SHUFFLE_MODE_INVALID },
            repeat = compat?.repeatMode?.let {
                when (it) {
                    PlaybackStateCompat.REPEAT_MODE_ONE -> RepeatMode.ONE
                    PlaybackStateCompat.REPEAT_MODE_ALL, PlaybackStateCompat.REPEAT_MODE_GROUP -> RepeatMode.ALL
                    PlaybackStateCompat.REPEAT_MODE_NONE -> RepeatMode.OFF
                    else -> null
                }
            },
            queue = queue.orEmpty().take(50).map {
                QueueItem(it.queueId, it.description.title?.toString().orEmpty(), it.description.subtitle?.toString().orEmpty())
            },
        )
    }
}

/** Controles de uma MediaSession de terceiros. */
private class SessionControls(context: Context, private val controller: MediaController) : MediaControls {
    private val compat = runCatching {
        MediaControllerCompat(context, MediaSessionCompat.Token.fromToken(controller.sessionToken))
    }.getOrNull()
    private val t get() = controller.transportControls

    override fun play() = t.play()
    override fun pause() = t.pause()
    override fun next() = t.skipToNext()
    override fun previous() = t.skipToPrevious()
    override fun seekTo(ms: Long) = t.seekTo(ms)
    override fun playQueueItem(id: Long) = t.skipToQueueItem(id)

    override fun toggleShuffle() {
        val c = compat ?: return
        val on = c.shuffleMode == PlaybackStateCompat.SHUFFLE_MODE_ALL
        c.transportControls.setShuffleMode(if (on) PlaybackStateCompat.SHUFFLE_MODE_NONE else PlaybackStateCompat.SHUFFLE_MODE_ALL)
    }

    override fun cycleRepeat() {
        val c = compat ?: return
        val next = when (c.repeatMode) {
            PlaybackStateCompat.REPEAT_MODE_NONE -> PlaybackStateCompat.REPEAT_MODE_ALL
            PlaybackStateCompat.REPEAT_MODE_ALL -> PlaybackStateCompat.REPEAT_MODE_ONE
            else -> PlaybackStateCompat.REPEAT_MODE_NONE
        }
        c.transportControls.setRepeatMode(next)
    }
}
