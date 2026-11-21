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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class StatusState(
    val now: Long = System.currentTimeMillis(),
    val wifi: Boolean = false,
    val cellular: Boolean = false,
    val bluetooth: Boolean = false,
)

/** Relógio (troca de minuto) e conectividade para a barra lateral (spec 002, R2.1/R2.4). */
class StatusRepository(private val context: Context) {
    private val _state = MutableStateFlow(StatusState())
    val state: StateFlow<StatusState> = _state.asStateFlow()

    private val cm = context.getSystemService(ConnectivityManager::class.java)
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
