package app.mylauncher.data.vehicle

import kotlinx.coroutines.flow.StateFlow

/** Fonte de áudio reportada pela MCU (`SourceValue` do CommandService). */
enum class AudioSource(val code: Int) {
    OTHER(0), RADIO(1), MUSIC(3), BLUETOOTH(8), VIDEO(9);

    companion object {
        fun of(code: Int) = entries.firstOrNull { it.code == code } ?: OTHER
    }
}

enum class Signal(val label: String) {
    HEADLIGHTS("Faróis"),
    REVERSE("Marcha à ré"),
    ACC("Ignição (ACC)"),
    SPEED("Velocidade"),
    BATTERY("Bateria"),
    HANDBRAKE("Freio de mão"),
    AUDIO_SOURCE("Fonte de áudio"),
    THEME_PROP("Tema do sistema"),
}

/** De onde veio o último valor de um sinal (para a tela de diagnóstico, R6.7). */
data class SignalInfo(val value: String, val channel: String, val at: Long)

/**
 * Estado do veículo. `null` = sinal indisponível (nenhum canal entregou valor ainda).
 */
data class VehicleState(
    val headlightsOn: Boolean? = null,
    val reverse: Boolean? = null,
    val accOn: Boolean? = null,
    val speedKmh: Int? = null,
    val batteryV: Float? = null,
    val handbrake: Boolean? = null,
    val audioSource: AudioSource? = null,
    /** `persist.tw.theme.mode` == night (canal C4), usado como reserva do tema automático. */
    val systemNight: Boolean? = null,
    val info: Map<Signal, SignalInfo> = emptyMap(),
)

interface VehicleSource {
    val state: StateFlow<VehicleState>
    fun start()
    fun stop()
}
