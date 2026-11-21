package app.mylauncher.data.vehicle

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import app.mylauncher.core.FileLog
import app.mylauncher.data.prefs.LauncherSettings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

/**
 * Baixa o volume da mídia na marcha à ré (spec 007, R7.6) sem escrever na MCU:
 * pede foco de áudio transitório com "duck" e os players reduzem o volume sozinhos.
 */
class ReverseDucker(
    context: Context,
    private val scope: CoroutineScope,
    private val vehicle: StateFlow<VehicleState>,
    private val settings: StateFlow<LauncherSettings>,
) {
    private val audio = context.getSystemService(AudioManager::class.java)
    private val request = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK)
        .setAudioAttributes(
            AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build(),
        )
        .setOnAudioFocusChangeListener { }
        .build()
    private var ducking = false

    fun start() {
        scope.launch {
            combine(vehicle, settings) { v, s -> v.reverse == true && s.duckOnReverse }
                .distinctUntilChanged()
                .collect { shouldDuck -> if (shouldDuck) duck() else release() }
        }
    }

    private fun duck() {
        if (ducking) return
        ducking = audio.requestAudioFocus(request) == AudioManager.AUDIOFOCUS_REQUEST_GRANTED
        FileLog.i("Ré: duck de áudio ${if (ducking) "ativo" else "negado"}")
    }

    private fun release() {
        if (!ducking) return
        audio.abandonAudioFocusRequest(request)
        ducking = false
        FileLog.i("Ré: duck de áudio liberado")
    }
}
