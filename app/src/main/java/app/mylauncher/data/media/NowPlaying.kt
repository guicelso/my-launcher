package app.mylauncher.data.media

import android.app.PendingIntent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle

enum class RepeatMode { OFF, ONE, ALL }

/** Item tocando agora, de qualquer origem (MediaSession ou ponte TopWay). */
data class NowPlaying(
    val sourcePackage: String,
    val sourceLabel: String,
    val title: String,
    val artist: String,
    val album: String = "",
    val art: Bitmap? = null,
    val durationMs: Long = 0,
    val positionMs: Long = 0,
    /** `SystemClock.elapsedRealtime()` em que [positionMs] foi medido. */
    val positionUpdatedAt: Long = 0,
    val speed: Float = 1f,
    val playing: Boolean = false,
    val canSkip: Boolean = true,
    val canSeek: Boolean = false,
    val shuffle: Boolean? = null,
    val repeat: RepeatMode? = null,
    val queue: List<QueueItem> = emptyList(),
    // ---- Spec 018: o que o Android Auto lê da sessão ----
    /** `METADATA_KEY_MEDIA_ID`: liga o item tocando aos itens da navegação. */
    val mediaId: String? = null,
    /** Anterior/próxima suportados separadamente (`ACTION_SKIP_TO_PREVIOUS/NEXT`). */
    val canPrevious: Boolean = canSkip,
    val canNext: Boolean = canSkip,
    /** Extras `ALWAYS_RESERVE_SPACE_FOR.ACTION_SKIP_TO_*`: deixa a posição vazia sem a ação. */
    val reservePrevious: Boolean = false,
    val reserveNext: Boolean = false,
    /** `STATE_BUFFERING`/`STATE_CONNECTING`: o tocar vira indicador de carregamento. */
    val buffering: Boolean = false,
    /** `PlaybackState.getCustomActions()`, na ordem do app. */
    val customActions: List<MediaAction> = emptyList(),
    val explicit: Boolean = false,
    /** `android.media.extra.DOWNLOAD_STATUS` = 2. */
    val downloaded: Boolean = false,
    /** Subtítulo vira link para este item da navegação (`KEY_SUBTITLE_LINK_MEDIA_ID`). */
    val subtitleLinkId: String? = null,
    val queueTitle: String = "",
    val activeQueueId: Long = -1,
    /** `STATE_ERROR` com mensagem e ação de resolução (ex.: Entrar). */
    val error: MediaError? = null,
    /** Rádio ao vivo (sem duração): mostra "AO VIVO" em vez da barra. */
    val live: Boolean = false,
    /** `getSessionActivity()`: abre a tela do app. */
    val sessionActivity: PendingIntent? = null,
) {
    /** A barra de progresso só aparece com duração (spec 018, R18.15). */
    val hasProgress: Boolean get() = durationMs > 0

    /** Posição interpolada localmente (R4.8), sem consultar a sessão. */
    fun positionAt(elapsedRealtime: Long): Long {
        if (!playing || positionUpdatedAt == 0L) return positionMs.coerceAtLeast(0)
        val p = positionMs + ((elapsedRealtime - positionUpdatedAt) * speed).toLong()
        return if (durationMs > 0) p.coerceIn(0, durationMs) else p.coerceAtLeast(0)
    }
}

data class QueueItem(
    val id: Long,
    val title: String,
    val subtitle: String,
    val art: Bitmap? = null,
    val artUri: Uri? = null,
    val durationMs: Long = 0,
)

/**
 * Ação personalizada do app (`PlaybackState.CustomAction`) ou embutida (aleatório/repetir), na
 * barra de controles ou em "Mais" (spec 018, R18.13/R18.14).
 */
data class MediaAction(
    val id: String,
    val label: String,
    /** Ícone do app (recurso no pacote dele), tingido de branco pela interface. */
    val iconRes: Int = 0,
    val pkg: String = "",
    val builtin: BuiltinAction? = null,
    /** Estado ligado (aleatório ativo, curtido…), quando a interface consegue saber. */
    val active: Boolean = false,
    val extras: Bundle? = null,
)

enum class BuiltinAction { SHUFFLE, REPEAT, REPEAT_ONE }

/** Erro da sessão (`STATE_ERROR`), com a ação de resolução opcional (R18.17). */
data class MediaError(
    val code: Int,
    val message: String,
    val resolutionLabel: String? = null,
    val resolutionIntent: PendingIntent? = null,
) {
    val isAuth: Boolean get() = code == ERROR_CODE_AUTHENTICATION_EXPIRED

    companion object {
        const val ERROR_CODE_AUTHENTICATION_EXPIRED = 3
    }
}

/** Controles comuns às origens de mídia. */
interface MediaControls {
    fun play()
    fun pause()
    fun next()
    fun previous()
    fun seekTo(ms: Long) {}
    fun toggleShuffle() {}
    fun cycleRepeat() {}
    fun playQueueItem(id: Long) {}
    /** Ação personalizada da sessão (`sendCustomAction`). */
    fun customAction(action: MediaAction) {}
    fun playFromMediaId(id: String, extras: Bundle? = null) {}
    fun playFromSearch(query: String) {}
}

fun formatTime(ms: Long): String {
    val total = (ms / 1000).coerceAtLeast(0)
    val h = total / 3600
    val m = (total % 3600) / 60
    val s = total % 60
    return if (h > 0) "%d:%02d:%02d".format(h, m, s) else "%d:%02d".format(m, s)
}

/** Escolhe a sessão a exibir (R4.4): a última que entrou em PLAYING; senão a mais recente. */
object SessionPicker {
    data class Candidate(val key: String, val playing: Boolean, val lastPlayingAt: Long)

    fun pick(candidates: List<Candidate>, preferred: String? = null, preferredAt: Long = 0L): String? {
        val byPlaying = candidates.filter { it.playing }.maxByOrNull { it.lastPlayingAt }
            ?: candidates.maxByOrNull { it.lastPlayingAt }
        val pref = candidates.firstOrNull { it.key == preferred } ?: return byPlaying?.key
        // A escolha do usuário vale até outra sessão começar a tocar depois dela.
        val overridden = byPlaying != null && byPlaying.key != pref.key && byPlaying.playing &&
            byPlaying.lastPlayingAt > preferredAt && !pref.playing
        return if (overridden) byPlaying?.key else pref.key
    }
}
