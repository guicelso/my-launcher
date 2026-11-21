package app.mylauncher.data.vehicle.topway

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.IBinder
import android.os.Parcel
import app.mylauncher.core.FileLog
import app.mylauncher.data.vehicle.AudioSource
import app.mylauncher.data.vehicle.Signal
import app.mylauncher.data.vehicle.SignalInfo
import app.mylauncher.data.vehicle.VehicleSource
import app.mylauncher.data.vehicle.VehicleState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

/** Envia comandos sem argumentos ao CommandService (controle de mídia da TopWay). */
fun interface TopWayCommands {
    fun send(code: Int): Boolean
}

/**
 * Sinais do veículo pelos serviços da TopWay (spec 006). Somente leitura (R6.8).
 * C1 CarService → farol, tensão, velocidade, freio de mão, ré pelo CAN
 * C2 CommandService → ré, ACC, fonte de áudio
 * C3 broadcasts → ACC
 * C4 persist.tw.theme.mode → tema do sistema (reserva do tema automático)
 * C5 (TWUtil) fica como teste manual na tela de diagnóstico: abrir a MCU tem efeitos colaterais.
 */
class TopWayVehicleSource(
    private val context: Context,
    private val scope: CoroutineScope,
) : VehicleSource, TopWayCommands {

    private val _state = MutableStateFlow(VehicleState())
    override val state: StateFlow<VehicleState> = _state.asStateFlow()

    private val carCallback = CallbackBinder(CarInfo.CALLBACK_DESCRIPTOR, "CarService", ::onCarCallback)
    private val commandCallback = CallbackBinder(Command.CALLBACK_DESCRIPTOR, "CommandService", ::onCommandCallback)

    val carLink = BinderLink(
        context, "CarService", CarInfo.ACTION, CarInfo.PACKAGE, scope,
        onConnected = { b -> scope.launch(Dispatchers.IO) { onCarConnected(b) } },
        onDisconnected = {},
    )
    val commandLink = BinderLink(
        context, "CommandService", Command.ACTION, Command.PACKAGE, scope,
        onConnected = { b -> scope.launch(Dispatchers.IO) { onCommandConnected(b) } },
        onDisconnected = {},
    )

    private var pollJob: Job? = null

    private val accReceiver = object : BroadcastReceiver() {
        override fun onReceive(c: Context, intent: Intent) {
            when (intent.action) {
                TopWayBroadcasts.ACC_ON -> set(Signal.ACC, C3, true) { copy(accOn = true) }
                TopWayBroadcasts.ACC_OFF -> set(Signal.ACC, C3, false) { copy(accOn = false) }
            }
        }
    }

    override fun start() {
        carLink.start()
        commandLink.start()
        context.registerReceiver(accReceiver, IntentFilter().apply {
            addAction(TopWayBroadcasts.ACC_ON)
            addAction(TopWayBroadcasts.ACC_OFF)
        })
        pollJob = scope.launch(Dispatchers.IO) {
            while (isActive) {
                readThemeProperty()
                delay(5_000)
            }
        }
    }

    override fun stop() {
        pollJob?.cancel()
        runCatching { context.unregisterReceiver(accReceiver) }
        carLink.binder?.let { b -> runCatching { b.call(CarInfo.DESCRIPTOR, CarInfo.UNREGISTER, { writeStrongBinder(carCallback) }) {} } }
        commandLink.binder?.let { b -> runCatching { b.call(Command.DESCRIPTOR, Command.UNREGISTER, { writeStrongBinder(commandCallback) }) {} } }
        carLink.stop()
        commandLink.stop()
    }

    // ---- C1: CarService --------------------------------------------------------------

    private fun onCarConnected(b: IBinder) {
        runCatching {
            val headlight = b.call(CarInfo.DESCRIPTOR, CarInfo.GET_HEADLIGHT) { it.readInt() }
            if (headlight >= 0) set(Signal.HEADLIGHTS, C1, headlight == 1) { copy(headlightsOn = headlight == 1) }
        }.onFailure { FileLog.w("CarService.getHeadlight falhou", it) }
        runCatching {
            val v = b.call(CarInfo.DESCRIPTOR, CarInfo.GET_VOLTAGE) { it.readFloat() }
            if (v > 0f) set(Signal.BATTERY, C1, v) { copy(batteryV = v) }
        }.onFailure { FileLog.w("CarService.getElectricVoltage falhou", it) }
        runCatching {
            b.call(CarInfo.DESCRIPTOR, CarInfo.REGISTER, { writeStrongBinder(carCallback) }) {}
            b.call(CarInfo.DESCRIPTOR, CarInfo.REQUEST_DATA) {}
        }.onFailure { FileLog.e("CarService: registro do callback falhou", it) }
    }

    private fun onCarCallback(code: Int, p: Parcel) {
        when (code) {
            CarInfo.CB_HEADLIGHT -> {
                val on = p.readInt() != 0
                set(Signal.HEADLIGHTS, C1, on) { copy(headlightsOn = on) }
            }
            CarInfo.CB_VOLTAGE -> {
                val v = p.readFloat()
                if (v > 0f) set(Signal.BATTERY, C1, v) { copy(batteryV = v) }
            }
            CarInfo.CB_SPEED -> {
                val kmh = p.readInt()
                if (kmh in 0..400) set(Signal.SPEED, C1, kmh) { copy(speedKmh = kmh) }
            }
            CarInfo.CB_HANDBRAKE -> {
                val on = p.readInt() != 0
                set(Signal.HANDBRAKE, C1, on) { copy(handbrake = on) }
            }
            CarInfo.CB_CAN_REVERSE -> {
                // A ré pela MCU (C2) tem prioridade; a do CAN só vale se o C2 nunca respondeu.
                if (_state.value.info[Signal.REVERSE]?.channel != C2) {
                    val on = p.readInt() != 0
                    set(Signal.REVERSE, C1, on) { copy(reverse = on) }
                }
            }
        }
    }

    // ---- C2: CommandService ------------------------------------------------------------

    private fun onCommandConnected(b: IBinder) {
        runCatching {
            b.call(Command.DESCRIPTOR, Command.REGISTER, { writeStrongBinder(commandCallback) }) {}
            b.call(Command.DESCRIPTOR, Command.GET_REVERSE) {}
            b.call(Command.DESCRIPTOR, Command.EXTENDED, {
                writeInt(1)
                Bundle().apply {
                    putString("project", "system")
                    putString(Command.KEY_ACTION, "source_request")
                }.writeToParcel(this, 0)
            }) {}
        }.onFailure { FileLog.e("CommandService: registro do callback falhou", it) }
    }

    private fun onCommandCallback(code: Int, p: Parcel) {
        when (code) {
            Command.CB_REVERSE -> {
                val on = p.readInt() != 0
                set(Signal.REVERSE, C2, on) { copy(reverse = on) }
            }
            Command.CB_SLEEP -> {
                val on = p.readInt() == 1
                set(Signal.ACC, C2, on) { copy(accOn = on) }
            }
            Command.CB_EXTENDED -> {
                if (p.readInt() == 0) return
                val bundle = Bundle.CREATOR.createFromParcel(p)
                if (bundle.getString(Command.KEY_ACTION) == Command.ACTION_SOURCE) {
                    val src = AudioSource.of(bundle.getInt(Command.KEY_SOURCE, 0))
                    set(Signal.AUDIO_SOURCE, C2, src) { copy(audioSource = src) }
                }
            }
        }
    }

    override fun send(code: Int): Boolean {
        val b = commandLink.binder ?: return false
        scope.launch(Dispatchers.IO) {
            runCatching { b.call(Command.DESCRIPTOR, code) {} }
                .onFailure { FileLog.w("CommandService: comando $code falhou", it) }
        }
        return true
    }

    // ---- C4: propriedade do sistema ----------------------------------------------------

    @SuppressLint("PrivateApi")
    private fun readThemeProperty() {
        val value = runCatching {
            Class.forName("android.os.SystemProperties")
                .getMethod("get", String::class.java, String::class.java)
                .invoke(null, THEME_PROP, "") as String
        }.getOrDefault("")
        val night = when (value.lowercase()) {
            "night" -> true
            "day" -> false
            else -> return
        }
        if (_state.value.systemNight != night) {
            set(Signal.THEME_PROP, C4, value) { copy(systemNight = night) }
        }
    }

    // ------------------------------------------------------------------------------------

    private fun set(signal: Signal, channel: String, value: Any?, change: VehicleState.() -> VehicleState) {
        _state.update { s ->
            s.change().copy(info = s.info + (signal to SignalInfo(value.toString(), channel, System.currentTimeMillis())))
        }
    }

    companion object {
        const val C1 = "C1 CarService"
        const val C2 = "C2 CommandService"
        const val C3 = "C3 broadcast"
        const val C4 = "C4 propriedade"
        const val THEME_PROP = "persist.tw.theme.mode"
    }
}
