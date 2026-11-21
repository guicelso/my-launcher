package app.mylauncher.data.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import app.mylauncher.core.FileLog
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/** Posição pelo GPS a 1 Hz (spec 005, R5.3), sem depender do Google Play Services. */
class LocationRepository(private val context: Context) {
    private val lm = context.getSystemService(LocationManager::class.java)
    private val _location = MutableStateFlow<Location?>(null)
    val location: StateFlow<Location?> = _location.asStateFlow()

    private val _granted = MutableStateFlow(hasPermission())
    val granted: StateFlow<Boolean> = _granted.asStateFlow()

    private var started = false

    private val listener = object : LocationListener {
        override fun onLocationChanged(l: Location) {
            val cur = _location.value
            // GPS tem prioridade; a rede só serve até o primeiro fix de GPS.
            if (l.provider == LocationManager.GPS_PROVIDER || cur == null || cur.provider != LocationManager.GPS_PROVIDER) {
                _location.value = l
            }
        }

        @Deprecated("Obrigatório no Android 10")
        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) = Unit
        override fun onProviderEnabled(provider: String) = Unit
        override fun onProviderDisabled(provider: String) = Unit
    }

    fun hasPermission(): Boolean =
        context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

    @SuppressLint("MissingPermission")
    fun start() {
        _granted.value = hasPermission()
        if (started || !_granted.value) return
        started = true
        runCatching {
            val last = listOf(LocationManager.GPS_PROVIDER, LocationManager.NETWORK_PROVIDER)
                .mapNotNull { p -> runCatching { lm.getLastKnownLocation(p) }.getOrNull() }
                .maxByOrNull { it.time }
            if (last != null) _location.value = last
            if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1_000L, 0f, listener, Looper.getMainLooper())
            }
            if (lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10_000L, 0f, listener, Looper.getMainLooper())
            }
            FileLog.i("Localização iniciada")
        }.onFailure { FileLog.e("Falha ao iniciar localização", it) }
    }

    companion object {
        /** km/h a partir do GPS, ou null sem velocidade. */
        fun speedKmh(l: Location?): Int? = l?.takeIf { it.hasSpeed() }?.let { (it.speed * 3.6f).toInt() }
    }
}
