package app.mylauncher.data.vehicle

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/** Veículo simulado para o emulador e para testar a UI sem o carro (docs/01-architecture.md, D4). */
class FakeVehicleSource : VehicleSource {
    private val _state = MutableStateFlow(
        VehicleState(
            headlightsOn = true, reverse = false, accOn = true, speedKmh = 0,
            batteryV = 12.3f, handbrake = true, audioSource = AudioSource.OTHER,
        ).withInfo(),
    )
    override val state: StateFlow<VehicleState> = _state.asStateFlow()

    override fun start() = Unit
    override fun stop() = Unit

    fun setHeadlights(on: Boolean) = _state.update { it.copy(headlightsOn = on).withInfo() }
    fun setReverse(on: Boolean) = _state.update { it.copy(reverse = on).withInfo() }
    fun setSpeed(kmh: Int) = _state.update { it.copy(speedKmh = kmh).withInfo() }

    private fun VehicleState.withInfo(): VehicleState {
        val now = System.currentTimeMillis()
        fun i(v: Any?) = SignalInfo(v.toString(), CHANNEL, now)
        return copy(
            info = mapOf(
                Signal.HEADLIGHTS to i(headlightsOn),
                Signal.REVERSE to i(reverse),
                Signal.ACC to i(accOn),
                Signal.SPEED to i(speedKmh),
                Signal.BATTERY to i(batteryV),
                Signal.HANDBRAKE to i(handbrake),
                Signal.AUDIO_SOURCE to i(audioSource),
            ),
        )
    }

    companion object {
        const val CHANNEL = "simulado"
    }
}

/** Fonte vazia: usada até a integração real existir ou quando nada responde. */
class UnavailableVehicleSource : VehicleSource {
    override val state: StateFlow<VehicleState> = MutableStateFlow(VehicleState())
    override fun start() = Unit
    override fun stop() = Unit
}
