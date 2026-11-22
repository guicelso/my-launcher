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
    mediaApps: MediaApps,
) {
    private val sessionManager = context.getSystemService(MediaSessionManager::class.java)
    private val listenerComponent = ComponentName(context, MediaListenerService::class.java)
    private val main = Handler(Looper.getMainLooper())

    private val controllers = mutableMapOf<String, MediaController>()
    private val callbacks = mutableMapOf<String, MediaController.Callback>()
    private val lastPlayingAt = mutableMapOf<String, Long>()
    private val playingKeys = mutableSetOf<String>()
    private var preferred: String? = null
    private var preferredAt = 0L

    private val _activePackages = MutableStateFlow<Set<String>>(emptySet())
    /** Pacotes com MediaSession ativa agora. */
    val activePackages: StateFlow<Set<String>> = _activePackages.asStateFlow()

    private val sessionState = MutableStateFlow<Pair<NowPlaying, MediaControls>?>(null)
    private val externalState = MutableStateFlow<Pair<NowPlaying, MediaControls>?>(null)
    private val _state = MutableStateFlow(MediaUiState())
    val state: StateFlow<MediaUiState> = _state.asStateFlow()

    // Última sessão vista (spec 016): mostrada quando não há sessão ativa, com ▶ para retomar.
    private val store = LastSessionStore(context)
    private val _lastSession = MutableStateFlow(store.load())
    /** Última sessão de mídia guardada (sobrevive ao app de música ser fechado). */
    val lastSession: StateFlow<LastSession?> = _lastSession.asStateFlow()
    private var lastArt: Bitmap? = store.loadArt()
    private var lastArtKey: String? = _lastSession.value?.let { it.packageName + "|" + it.title }

    private val resumer = MediaResumer(
        context = context,
        scope = scope,
        mediaApps = mediaApps,
        activePackages = _activePackages,
        controlsFor = { pkg -> controllers[pkg]?.let { SessionControls(context, it) } },
        launchApp = { pkg -> apps.launch(pkg) },
    )

    private val sessionsListener = MediaSessionManager.OnActiveSessionsChangedListener { list ->
        updateControllers(list.orEmpty())
    }

    fun start() {
        scope.launch {
            MediaListenerService.connected.collect { connected -> if (connected) attach() }
        }
        scope.launch {
            combine(sessionState, externalState, MediaListenerService.connected, _lastSession) { s, ext, connected, last ->
                // Concedido ≠ conectado: no boot o listener demora a conectar (ver ensureListenerBound);
                // o card só pede a permissão quando ela realmente não foi dada.
                Quad(s, ext, connected || MediaListenerService.isGranted(context), last)
            }.collect { (s, extNow, granted, last) ->
                val chosen = when {
                    s != null && s.first.playing -> s
                    extNow != null && extNow.first.playing -> extNow
                    else -> s ?: extNow
                }
                // Sem sessão ativa: a última sessão guardada, com ▶ retomando em segundo plano (R16.4).
                val shown = chosen ?: last?.takeIf { granted }?.let { it.toNowPlaying(lastArt) to ResumeControls(it.packageName) }
                _state.value = MediaUiState(shown?.first, shown?.second, granted)
            }
        }
        scope.launch { sessionState.collect { remember(it?.first) } }
        attach()
    }

    /**
     * O usuário escolheu este app (toque na barra, spec 010 R10.7): ele passa a ser exibido,
     * a menos que outra sessão comece a tocar depois.
     */
    fun prefer(pkg: String) {
        preferred = pkg
        preferredAt = SystemClock.elapsedRealtime()
        recompute()
    }

    /** Retoma a última sessão guardada (▶ do card ou ao ligar a chave, R16.5/R16.6). */
    fun resumeLast() {
        _lastSession.value?.let { resumer.resume(it.packageName) }
    }

    /** Retoma um app de música específico em segundo plano (R16.5). */
    fun resume(pkg: String) = resumer.resume(pkg)

    /** Atualiza a sessão guardada a partir da sessão ativa exibida. */
    private fun remember(np: NowPlaying?) {
        if (np == null || np.title.isBlank()) return
        val prev = _lastSession.value
        val now = System.currentTimeMillis()
        val sameTrack = prev != null && prev.packageName == np.sourcePackage && prev.title == np.title
        val pausedAt = when {
            np.playing -> 0L
            prev?.playing == true && sameTrack -> now
            sameTrack -> prev?.pausedAt ?: 0L
            else -> 0L
        }
        val next = LastSession(
            packageName = np.sourcePackage,
            label = np.sourceLabel,
            title = np.title,
            artist = np.artist,
            album = np.album,
            durationMs = np.durationMs,
            positionMs = np.positionAt(SystemClock.elapsedRealtime()),
            playing = np.playing,
            pausedAt = pausedAt,
            savedAt = now,
        )
        val artKey = next.packageName + "|" + next.title
        val art = np.art
        // A capa costuma chegar depois do título: troca de faixa sem capa limpa a anterior, e a
        // chegada da capa da faixa atual também conta como mudança.
        val artChanged = when {
            art != null && artKey != lastArtKey -> { lastArt = art; lastArtKey = artKey; true }
            art == null && !sameTrack -> { lastArt = null; lastArtKey = null; true }
            else -> false
        }
        // Grava só quando algo relevante muda (faixa, capa, tocar/pausar), não a cada recálculo.
        val changed = prev == null || !sameTrack || prev.playing != next.playing || prev.artist != next.artist || artChanged
        if (!changed) return
        _lastSession.value = next
        val artToSave = lastArt
        scope.launch(kotlinx.coroutines.Dispatchers.IO) {
            store.save(next)
            if (artChanged) store.saveArt(artToSave)
        }
    }

    /** Controles da sessão guardada: só ▶, que retoma o app em segundo plano. */
    private inner class ResumeControls(private val pkg: String) : MediaControls {
        override fun play() = resumer.resume(pkg)
        override fun pause() {}
        override fun next() {}
        override fun previous() {}
    }

    private data class Quad<A, B, C, D>(val a: A, val b: B, val c: C, val d: D)

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
        _activePackages.value = controllers.keys.toSet()
        recompute()
    }

    private fun recompute() {
        val now = SystemClock.elapsedRealtime()
        // Registra quando cada sessão COMEÇOU a tocar (não a cada recálculo).
        controllers.forEach { (key, c) ->
            val playing = c.playbackState?.state == PlaybackState.STATE_PLAYING
            if (playing && playingKeys.add(key)) lastPlayingAt[key] = now
            if (!playing) playingKeys.remove(key)
        }
        playingKeys.retainAll(controllers.keys)
        val candidates = controllers.map { (key, c) ->
            SessionPicker.Candidate(key, c.playbackState?.state == PlaybackState.STATE_PLAYING, lastPlayingAt[key] ?: 0L)
        }
        val chosenKey = SessionPicker.pick(candidates, preferred, preferredAt)
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
        val shuffle = compat?.shuffleMode?.takeIf { it != PlaybackStateCompat.SHUFFLE_MODE_INVALID }
            ?.let { it != PlaybackStateCompat.SHUFFLE_MODE_NONE }
        val repeat = compat?.repeatMode?.let {
            when (it) {
                PlaybackStateCompat.REPEAT_MODE_ONE -> RepeatMode.ONE
                PlaybackStateCompat.REPEAT_MODE_ALL, PlaybackStateCompat.REPEAT_MODE_GROUP -> RepeatMode.ALL
                PlaybackStateCompat.REPEAT_MODE_NONE -> RepeatMode.OFF
                else -> null
            }
        }
        val sessionExtras = runCatching { extras }.getOrNull()
        fun reserved(key: String) = sessionExtras?.getBoolean(key) == true || ps?.extras?.getBoolean(key) == true
        val custom = ps?.customActions.orEmpty().map {
            MediaAction(id = it.action, label = it.name?.toString().orEmpty(), iconRes = it.icon, pkg = packageName, extras = it.extras)
        }
        val error = if (ps?.state == PlaybackState.STATE_ERROR) {
            val e = ps.extras ?: compat?.playbackState?.extras
            MediaError(
                code = compat?.playbackState?.errorCode ?: 0,
                message = ps.errorMessage?.toString().orEmpty(),
                resolutionLabel = e?.getString(MediaKeys.ERROR_RESOLUTION_LABEL),
                resolutionIntent = runCatching { e?.getParcelable<android.app.PendingIntent>(MediaKeys.ERROR_RESOLUTION_INTENT) }.getOrNull(),
            )
        } else {
            null
        }
        return NowPlaying(
            sourcePackage = packageName,
            sourceLabel = apps.label(packageName),
            title = md?.getString(MediaMetadata.METADATA_KEY_TITLE)
                ?: md?.getString(MediaMetadata.METADATA_KEY_DISPLAY_TITLE).orEmpty(),
            artist = md?.getString(MediaMetadata.METADATA_KEY_ARTIST)
                ?: md?.getString(MediaMetadata.METADATA_KEY_DISPLAY_SUBTITLE).orEmpty(),
            album = md?.getString(MediaMetadata.METADATA_KEY_ALBUM).orEmpty(),
            art = art,
            durationMs = md?.getLong(MediaMetadata.METADATA_KEY_DURATION)?.coerceAtLeast(0) ?: 0L,
            positionMs = ps?.position ?: 0L,
            positionUpdatedAt = ps?.lastPositionUpdateTime ?: 0L,
            speed = ps?.playbackSpeed?.takeIf { it > 0f } ?: 1f,
            playing = ps?.state == PlaybackState.STATE_PLAYING,
            canSkip = actions and (PlaybackState.ACTION_SKIP_TO_NEXT or PlaybackState.ACTION_SKIP_TO_PREVIOUS) != 0L,
            canSeek = actions and PlaybackState.ACTION_SEEK_TO != 0L,
            shuffle = shuffle,
            repeat = repeat,
            queue = queue.orEmpty().take(MAX_QUEUE).map {
                QueueItem(
                    it.queueId, it.description.title?.toString().orEmpty(), it.description.subtitle?.toString().orEmpty(),
                    art = it.description.iconBitmap, artUri = it.description.iconUri,
                )
            },
            mediaId = md?.getString(MediaMetadata.METADATA_KEY_MEDIA_ID),
            canPrevious = actions and PlaybackState.ACTION_SKIP_TO_PREVIOUS != 0L,
            canNext = actions and PlaybackState.ACTION_SKIP_TO_NEXT != 0L,
            reservePrevious = reserved(MediaKeys.RESERVE_PREVIOUS),
            reserveNext = reserved(MediaKeys.RESERVE_NEXT),
            buffering = ps?.state == PlaybackState.STATE_BUFFERING || ps?.state == PlaybackState.STATE_CONNECTING,
            customActions = MediaActionsMerger.merge(custom, shuffle, repeat, packageName),
            explicit = md?.getLong(MediaKeys.IS_EXPLICIT) == 1L,
            downloaded = md?.getLong(MediaKeys.DOWNLOAD_STATUS) == 2L,
            subtitleLinkId = md?.getString(MediaKeys.SUBTITLE_LINK),
            queueTitle = queueTitle?.toString().orEmpty(),
            activeQueueId = ps?.activeQueueItemId ?: -1,
            error = error,
            sessionActivity = sessionActivity,
        )
    }

    private companion object {
        const val MAX_QUEUE = 100
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
    override fun playFromMediaId(id: String, extras: android.os.Bundle?) = t.playFromMediaId(id, extras)
    override fun playFromSearch(query: String) = t.playFromSearch(query, android.os.Bundle())

    override fun customAction(action: MediaAction) {
        when (action.builtin) {
            BuiltinAction.SHUFFLE -> toggleShuffle()
            BuiltinAction.REPEAT, BuiltinAction.REPEAT_ONE -> cycleRepeat()
            null -> t.sendCustomAction(action.id, action.extras)
        }
    }

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
