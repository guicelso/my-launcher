package app.mylauncher.data.media

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import app.mylauncher.core.FileLog
import java.io.File
import org.json.JSONObject

/**
 * Última sessão de mídia vista (spec 016, R16.4): sobrevive ao app de música (ou ao launcher)
 * ser fechado, para o card continuar mostrando a faixa e permitir retomá-la.
 */
data class LastSession(
    val packageName: String,
    val label: String,
    val title: String,
    val artist: String,
    val album: String = "",
    val durationMs: Long = 0,
    val positionMs: Long = 0,
    /** Estava tocando na última vez que a sessão foi vista. */
    val playing: Boolean = false,
    /** Hora (System.currentTimeMillis) em que a música foi pausada; 0 se não houve pausa. */
    val pausedAt: Long = 0,
    val savedAt: Long = 0,
) {
    /**
     * "Estava tocando" ao desligar a chave (R16.6): tocando, ou pausada no máximo
     * [PAUSE_GRACE_MS] antes do aviso de ACC desligado. A TopWay manda PAUSE à mídia ~130 ms
     * DEPOIS do broadcast ACC_OFF (medido no carro); a folga só absorve atraso de entrega, e uma
     * pausa do usuário segundos antes de tirar a chave continua valendo.
     */
    fun wasPlayingAt(accOffAt: Long): Boolean =
        playing || (pausedAt > 0 && accOffAt - pausedAt in 0..PAUSE_GRACE_MS)

    fun toNowPlaying(art: Bitmap?) = NowPlaying(
        sourcePackage = packageName,
        sourceLabel = label,
        title = title,
        artist = artist,
        album = album,
        art = art,
        durationMs = durationMs,
        positionMs = positionMs,
        playing = false,
        canSkip = false,
        canSeek = false,
    )

    companion object {
        const val PAUSE_GRACE_MS = 2_000L
    }
}

/** Grava a última sessão em `files/` (JSON + capa em PNG reduzida). */
class LastSessionStore(context: Context) {
    private val json = File(context.filesDir, "last_media.json")
    private val artFile = File(context.filesDir, "last_media_art.png")

    fun load(): LastSession? = runCatching {
        if (!json.isFile) return null
        val o = JSONObject(json.readText())
        LastSession(
            packageName = o.getString("pkg"),
            label = o.optString("label"),
            title = o.optString("title"),
            artist = o.optString("artist"),
            album = o.optString("album"),
            durationMs = o.optLong("duration"),
            positionMs = o.optLong("position"),
            playing = o.optBoolean("playing"),
            pausedAt = o.optLong("pausedAt"),
            savedAt = o.optLong("savedAt"),
        )
    }.getOrElse { FileLog.w("Sessão de mídia guardada ilegível: ${it.message}"); null }

    fun loadArt(): Bitmap? = runCatching { if (artFile.isFile) BitmapFactory.decodeFile(artFile.path) else null }.getOrNull()

    fun save(s: LastSession) {
        runCatching {
            val o = JSONObject()
                .put("pkg", s.packageName).put("label", s.label).put("title", s.title).put("artist", s.artist)
                .put("album", s.album).put("duration", s.durationMs).put("position", s.positionMs)
                .put("playing", s.playing).put("pausedAt", s.pausedAt).put("savedAt", s.savedAt)
            val tmp = File(json.parentFile, "${json.name}.tmp")
            tmp.writeText(o.toString())
            tmp.renameTo(json)
        }.onFailure { FileLog.w("Falha ao guardar a sessão de mídia: ${it.message}") }
    }

    fun saveArt(art: Bitmap?) {
        runCatching {
            if (art == null) {
                artFile.delete()
                return
            }
            val max = 512
            val scaled = if (art.width > max || art.height > max) {
                val k = max.toFloat() / maxOf(art.width, art.height)
                Bitmap.createScaledBitmap(art, (art.width * k).toInt().coerceAtLeast(1), (art.height * k).toInt().coerceAtLeast(1), true)
            } else {
                art
            }
            val tmp = File(artFile.parentFile, "${artFile.name}.tmp")
            tmp.outputStream().use { scaled.compress(Bitmap.CompressFormat.PNG, 100, it) }
            tmp.renameTo(artFile)
        }.onFailure { FileLog.w("Falha ao guardar a capa: ${it.message}") }
    }
}
