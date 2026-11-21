package app.mylauncher.data.vehicle.topway

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.SystemClock
import app.mylauncher.data.media.ExternalMediaSource
import app.mylauncher.data.media.MediaControls
import app.mylauncher.data.media.NowPlaying
import app.mylauncher.data.vehicle.AudioSource
import app.mylauncher.data.vehicle.VehicleState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

private data class LocalMusicInfo(
    val path: String = "",
    val artist: String = "",
    val album: String = "",
    val art: Bitmap? = null,
    val positionMs: Long = 0,
    val durationMs: Long = 0,
    val updatedAt: Long = 0,
)

/**
 * Mídia dos apps nativos da TopWay, que não publicam MediaSession (spec 004, R4.6).
 * A fonte ativa vem do CommandService (`SourceValue`); os metadados da música local, dos
 * broadcasts `com.tw.*`. O rádio não expõe frequência/estação (docs/03-topway-protocol.md).
 */
class TopWayMediaBridge(
    private val context: Context,
    scope: CoroutineScope,
    vehicle: StateFlow<VehicleState>,
    private val commands: TopWayCommands,
) : ExternalMediaSource {

    private val music = MutableStateFlow(LocalMusicInfo())

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(c: Context, intent: Intent) {
            val cur = music.value
            music.value = when (intent.action) {
                TopWayBroadcasts.MUSIC_INFO -> cur.copy(
                    path = intent.getStringExtra("musicPath") ?: cur.path,
                    artist = intent.getStringExtra("musicaArtist") ?: cur.artist,
                    album = intent.getStringExtra("musicAlbum") ?: cur.album,
                )
                TopWayBroadcasts.LAUNCHER_MUSIC_INFO -> cur.copy(
                    artist = intent.getStringExtra("msg_Artist") ?: cur.artist,
                    art = intent.getByteArrayExtra("msg_Albumart")?.let { BitmapFactory.decodeByteArray(it, 0, it.size) } ?: cur.art,
                )
                TopWayBroadcasts.MUSIC_PROGRESS -> cur.copy(
                    positionMs = intent.getIntExtra("msg_music_progress", 0).toLong(),
                    durationMs = intent.getIntExtra("msg_music_duration", 0).toLong(),
                    updatedAt = SystemClock.elapsedRealtime(),
                )
                else -> cur
            }
        }
    }

    fun start() {
        context.registerReceiver(receiver, IntentFilter().apply {
            addAction(TopWayBroadcasts.MUSIC_INFO)
            addAction(TopWayBroadcasts.LAUNCHER_MUSIC_INFO)
            addAction(TopWayBroadcasts.MUSIC_PROGRESS)
        })
    }

    override val state: StateFlow<Pair<NowPlaying, MediaControls>?> =
        combine(vehicle, music) { v, m -> build(v.audioSource, m) }
            .stateIn(scope, SharingStarted.Eagerly, null)

    private fun build(source: AudioSource?, m: LocalMusicInfo): Pair<NowPlaying, MediaControls>? = when (source) {
        AudioSource.RADIO -> NowPlaying(
            sourcePackage = TopWayPackages.RADIO, sourceLabel = "Rádio",
            title = "Rádio", artist = "Toque em ‹ › para trocar de estação", playing = true,
        ) to controls(Command.RADIO_NEXT, Command.RADIO_PRE, Command.MEDIA_PLAY, Command.MEDIA_PAUSE)
        AudioSource.MUSIC -> NowPlaying(
            sourcePackage = TopWayPackages.MUSIC, sourceLabel = "Música",
            title = m.path.substringAfterLast('/').substringBeforeLast('.').ifBlank { "Música local" },
            artist = m.artist, album = m.album, art = m.art,
            durationMs = m.durationMs, positionMs = m.positionMs, positionUpdatedAt = m.updatedAt,
            playing = true,
        ) to controls(Command.MUSIC_NEXT, Command.MUSIC_PRE, Command.MUSIC_PLAY, Command.MUSIC_PAUSE)
        AudioSource.BLUETOOTH -> NowPlaying(
            sourcePackage = TopWayPackages.BT, sourceLabel = "Bluetooth",
            title = "Áudio Bluetooth", artist = "Celular conectado", playing = true,
        ) to controls(Command.BT_NEXT, Command.BT_PRE, Command.BT_PLAY, Command.MEDIA_PAUSE)
        else -> null
    }

    private fun controls(next: Int, pre: Int, play: Int, pause: Int) = object : MediaControls {
        override fun play() { commands.send(play) }
        override fun pause() { commands.send(pause) }
        override fun next() { commands.send(next) }
        override fun previous() { commands.send(pre) }
    }
}
