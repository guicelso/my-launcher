package app.mylauncher.data.media

import android.graphics.Bitmap

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
) {
    /** Posição interpolada localmente (R4.8), sem consultar a sessão. */
    fun positionAt(elapsedRealtime: Long): Long {
        if (!playing || positionUpdatedAt == 0L) return positionMs.coerceAtLeast(0)
        val p = positionMs + ((elapsedRealtime - positionUpdatedAt) * speed).toLong()
        return if (durationMs > 0) p.coerceIn(0, durationMs) else p.coerceAtLeast(0)
    }
}

data class QueueItem(val id: Long, val title: String, val subtitle: String)

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

    fun pick(candidates: List<Candidate>): String? =
        candidates.filter { it.playing }.maxByOrNull { it.lastPlayingAt }?.key
            ?: candidates.maxByOrNull { it.lastPlayingAt }?.key
}
