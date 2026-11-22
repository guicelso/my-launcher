package app.mylauncher.core.debug

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.os.SystemClock
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import app.mylauncher.core.FileLog

/**
 * Player simulado para testar a mídia no emulador (sem Spotify/YT Music): publica uma
 * MediaSession real com faixas, capas geradas, fila, busca, aleatório e repetição.
 * Ligado pelo painel de depuração (toque longo no relógio).
 */
class DebugMediaPlayer(private val context: Context) {

    private data class Track(val title: String, val artist: String, val album: String, val durationMs: Long, val colors: IntArray)

    private val tracks = listOf(
        Track("Faixa de teste 1", "Artista Simulado", "Álbum Azul", 184_000, intArrayOf(0xFF1A73E8.toInt(), 0xFF0B1A33.toInt())),
        Track("Faixa de teste 2", "Outro Artista", "Álbum Laranja", 211_000, intArrayOf(0xFFF29900.toInt(), 0xFF5B2C00.toInt())),
        Track("Faixa de teste 3", "Banda Fictícia", "Álbum Verde", 157_000, intArrayOf(0xFF34A853.toInt(), 0xFF0D3B1A.toInt())),
        Track("Faixa com um título bem comprido para testar o corte", "Artista Simulado", "Álbum Roxo", 245_000, intArrayOf(0xFFA142F4.toInt(), 0xFF2A0B47.toInt())),
    )
    private val arts = HashMap<Int, Bitmap>()

    private var session: MediaSessionCompat? = null
    private var index = 0
    private var playing = false
    private var positionMs = 0L
    private var updatedAt = 0L
    private var shuffle = PlaybackStateCompat.SHUFFLE_MODE_NONE
    private var repeat = PlaybackStateCompat.REPEAT_MODE_NONE

    val isRunning: Boolean get() = session != null

    fun start() {
        if (session != null) return
        session = MediaSessionCompat(context, "LumiaDebugPlayer").apply {
            setCallback(callback)
            setQueue(tracks.mapIndexed { i, t ->
                MediaSessionCompat.QueueItem(
                    MediaDescriptionCompat.Builder().setMediaId("t$i").setTitle(t.title).setSubtitle(t.artist).build(),
                    i.toLong(),
                )
            })
            isActive = true
        }
        playing = true
        positionMs = 0
        publish()
        FileLog.i("Player simulado iniciado")
    }

    /** Tocar/pausar pelo painel de depuração (testa as sugestões das pilhas, spec 017). */
    fun togglePlay() {
        if (session == null) return
        if (playing) callback.onPause() else callback.onPlay()
    }

    fun stop() {
        session?.run {
            isActive = false
            release()
        }
        session = null
        FileLog.i("Player simulado parado")
    }

    private val callback = object : MediaSessionCompat.Callback() {
        override fun onPlay() { playing = true; snapshot(); publish() }
        override fun onPause() { snapshot(); playing = false; publish() }
        override fun onSkipToNext() = go(index + 1)
        override fun onSkipToPrevious() = go(if (current() > 3_000) index else index - 1)
        override fun onSkipToQueueItem(id: Long) = go(id.toInt())
        override fun onSeekTo(pos: Long) { positionMs = pos.coerceIn(0, tracks[index].durationMs); updatedAt = SystemClock.elapsedRealtime(); publish() }
        override fun onSetShuffleMode(mode: Int) { shuffle = mode; publish() }
        override fun onSetRepeatMode(mode: Int) { repeat = mode; publish() }
    }

    private fun current(): Long =
        if (playing) positionMs + (SystemClock.elapsedRealtime() - updatedAt) else positionMs

    private fun snapshot() {
        positionMs = current().coerceAtMost(tracks[index].durationMs)
        updatedAt = SystemClock.elapsedRealtime()
    }

    private fun go(i: Int) {
        index = Math.floorMod(i, tracks.size)
        positionMs = 0
        publish()
    }

    private fun publish() {
        val s = session ?: return
        val t = tracks[index]
        updatedAt = SystemClock.elapsedRealtime()
        s.setMetadata(
            MediaMetadataCompat.Builder()
                .putString(MediaMetadataCompat.METADATA_KEY_TITLE, t.title)
                .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, t.artist)
                .putString(MediaMetadataCompat.METADATA_KEY_ALBUM, t.album)
                .putLong(MediaMetadataCompat.METADATA_KEY_DURATION, t.durationMs)
                .putBitmap(MediaMetadataCompat.METADATA_KEY_ALBUM_ART, art(index))
                .build(),
        )
        s.setShuffleMode(shuffle)
        s.setRepeatMode(repeat)
        s.setPlaybackState(
            PlaybackStateCompat.Builder()
                .setActions(
                    PlaybackStateCompat.ACTION_PLAY or PlaybackStateCompat.ACTION_PAUSE or
                        PlaybackStateCompat.ACTION_PLAY_PAUSE or PlaybackStateCompat.ACTION_SKIP_TO_NEXT or
                        PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS or PlaybackStateCompat.ACTION_SEEK_TO or
                        PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM or PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE or
                        PlaybackStateCompat.ACTION_SET_REPEAT_MODE,
                )
                .setActiveQueueItemId(index.toLong())
                .setState(
                    if (playing) PlaybackStateCompat.STATE_PLAYING else PlaybackStateCompat.STATE_PAUSED,
                    positionMs, if (playing) 1f else 0f, updatedAt,
                )
                .build(),
        )
    }

    /** Capa gerada: degradê com o número da faixa. */
    private fun art(i: Int): Bitmap = arts.getOrPut(i) {
        val size = 512
        val bmp = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val c = Canvas(bmp)
        val colors = tracks[i].colors
        c.drawPaint(Paint().apply { shader = LinearGradient(0f, 0f, size.toFloat(), size.toFloat(), colors[0], colors[1], Shader.TileMode.CLAMP) })
        c.drawText(
            "${i + 1}", size / 2f, size * 0.66f,
            Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.WHITE; textSize = size * 0.45f; textAlign = Paint.Align.CENTER; alpha = 220 },
        )
        bmp
    }
}
