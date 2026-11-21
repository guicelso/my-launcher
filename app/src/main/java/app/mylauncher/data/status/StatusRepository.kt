package app.mylauncher.data.status

import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.telephony.PhoneStateListener
import android.telephony.SignalStrength
import android.telephony.TelephonyManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class StatusState(
    val now: Long = System.currentTimeMillis(),
    val wifi: Boolean = false,
    val cellular: Boolean = false,
    val bluetooth: Boolean = false,
    /** Chip presente e registrado na operadora. */
    val hasSim: Boolean = false,
    /** Intensidade do sinal móvel, 0–4 (null = desconhecida). */
    val signalLevel: Int? = null,
    /** Tipo de rede móvel ("4G", "3G", "H", "E", "5G"), ou null. */
    val networkLabel: String? = null,
)

/** Rótulo curto do tipo de rede, como na barra de status do Android. */
object NetworkLabels {
    fun of(type: Int): String? = when (type) {
        TelephonyManager.NETWORK_TYPE_NR -> "5G"
        TelephonyManager.NETWORK_TYPE_LTE, TelephonyManager.NETWORK_TYPE_IWLAN -> "4G"
        TelephonyManager.NETWORK_TYPE_HSPAP -> "H+"
        TelephonyManager.NETWORK_TYPE_HSPA, TelephonyManager.NETWORK_TYPE_HSDPA, TelephonyManager.NETWORK_TYPE_HSUPA -> "H"
        TelephonyManager.NETWORK_TYPE_UMTS, TelephonyManager.NETWORK_TYPE_EVDO_0, TelephonyManager.NETWORK_TYPE_EVDO_A,
        TelephonyManager.NETWORK_TYPE_EVDO_B, TelephonyManager.NETWORK_TYPE_EHRPD, TelephonyManager.NETWORK_TYPE_TD_SCDMA -> "3G"
        TelephonyManager.NETWORK_TYPE_EDGE -> "E"
        TelephonyManager.NETWORK_TYPE_GPRS, TelephonyManager.NETWORK_TYPE_CDMA, TelephonyManager.NETWORK_TYPE_1xRTT,
        TelephonyManager.NETWORK_TYPE_GSM -> "G"
        else -> null
    }
}

/** Relógio (troca de minuto) e conectividade para a barra lateral (spec 002, R2.1/R2.4). */
class StatusRepository(private val context: Context) {
    private val _state = MutableStateFlow(StatusState())
    val state: StateFlow<StatusState> = _state.asStateFlow()

    private val cm = context.getSystemService(ConnectivityManager::class.java)
    private val tm = context.getSystemService(TelephonyManager::class.java)

    /** Sinal direto do modem: aparece mesmo quando o Wi-Fi é a conexão ativa. */
    @Suppress("DEPRECATION")
    private val phoneListener = object : PhoneStateListener() {
        override fun onSignalStrengthsChanged(signal: SignalStrength) {
            _state.update { it.copy(signalLevel = signal.level, hasSim = simReady()) }
        }

        override fun onDataConnectionStateChanged(state: Int, networkType: Int) {
            _state.update { it.copy(networkLabel = NetworkLabels.of(networkType) ?: it.networkLabel, hasSim = simReady()) }
        }
    }

    private fun simReady() = runCatching { tm.simState == TelephonyManager.SIM_STATE_READY }.getOrDefault(false)
    private val networks = mutableMapOf<Network, NetworkCapabilities>()

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(c: Context, intent: Intent) {
            when (intent.action) {
                BluetoothAdapter.ACTION_STATE_CHANGED -> updateBluetooth()
                else -> _state.update { it.copy(now = System.currentTimeMillis()) }
            }
        }
    }

    private val netCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onCapabilitiesChanged(network: Network, caps: NetworkCapabilities) {
            synchronized(networks) { networks[network] = caps }
            publishNetworks()
        }

        override fun onLost(network: Network) {
            synchronized(networks) { networks.remove(network) }
            publishNetworks()
        }
    }

    fun start() {
        context.registerReceiver(receiver, IntentFilter().apply {
            addAction(Intent.ACTION_TIME_TICK)
            addAction(Intent.ACTION_TIME_CHANGED)
            addAction(Intent.ACTION_TIMEZONE_CHANGED)
            addAction(BluetoothAdapter.ACTION_STATE_CHANGED)
        })
        runCatching { cm.registerNetworkCallback(NetworkRequest.Builder().build(), netCallback) }
        @Suppress("DEPRECATION")
        runCatching {
            tm.listen(phoneListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS or PhoneStateListener.LISTEN_DATA_CONNECTION_STATE)
        }
        _state.update { it.copy(hasSim = simReady()) }
        updateBluetooth()
    }

    private fun publishNetworks() {
        val caps = synchronized(networks) { networks.values.toList() }
        _state.update { s ->
            s.copy(
                wifi = caps.any { it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) },
                cellular = caps.any { it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) },
            )
        }
    }

    @Suppress("MissingPermission")
    private fun updateBluetooth() {
        val on = runCatching { BluetoothAdapter.getDefaultAdapter()?.isEnabled == true }.getOrDefault(false)
        _state.update { it.copy(bluetooth = on) }
    }
}
