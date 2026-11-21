package app.mylauncher.data.weather

import android.content.Context
import android.location.Geocoder
import android.location.Location
import app.mylauncher.core.FileLog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.util.Locale

enum class WeatherKind { CLEAR, PARTLY, CLOUDY, FOG, DRIZZLE, RAIN, SNOW, STORM }

data class Weather(
    val temperature: Int,
    val code: Int,
    val isDay: Boolean,
    val min: Int?,
    val max: Int?,
    val city: String?,
    val updatedAt: Long,
) {
    val kind: WeatherKind get() = WeatherCodes.kind(code)
    val description: String get() = WeatherCodes.description(code)
}

/** Códigos WMO usados pelo Open-Meteo. */
object WeatherCodes {
    fun kind(code: Int): WeatherKind = when (code) {
        0 -> WeatherKind.CLEAR
        1, 2 -> WeatherKind.PARTLY
        3 -> WeatherKind.CLOUDY
        45, 48 -> WeatherKind.FOG
        in 51..57 -> WeatherKind.DRIZZLE
        in 61..67, in 80..82 -> WeatherKind.RAIN
        in 71..77, 85, 86 -> WeatherKind.SNOW
        in 95..99 -> WeatherKind.STORM
        else -> WeatherKind.CLOUDY
    }

    fun description(code: Int): String = when (code) {
        0 -> "Céu limpo"
        1 -> "Poucas nuvens"
        2 -> "Parcialmente nublado"
        3 -> "Nublado"
        45, 48 -> "Neblina"
        in 51..57 -> "Garoa"
        in 61..65 -> "Chuva"
        66, 67 -> "Chuva congelante"
        in 71..77, 85, 86 -> "Neve"
        in 80..82 -> "Pancadas de chuva"
        in 95..99 -> "Tempestade"
        else -> "—"
    }

    fun parse(json: String, city: String?, now: Long): Weather {
        val o = JSONObject(json)
        val cur = o.getJSONObject("current")
        val daily = o.optJSONObject("daily")
        return Weather(
            temperature = Math.round(cur.getDouble("temperature_2m")).toInt(),
            code = cur.getInt("weather_code"),
            isDay = cur.optInt("is_day", 1) == 1,
            min = daily?.optJSONArray("temperature_2m_min")?.optDouble(0)?.takeIf { !it.isNaN() }?.let { Math.round(it).toInt() },
            max = daily?.optJSONArray("temperature_2m_max")?.optDouble(0)?.takeIf { !it.isNaN() }?.let { Math.round(it).toInt() },
            city = city,
            updatedAt = now,
        )
    }
}

/**
 * Clima atual pela posição do GPS (spec 013, R13.4): Open-Meteo, gratuito e sem chave.
 * Atualiza a cada 30 min (ou ao andar > 5 km) e guarda o último valor para uso offline.
 */
class WeatherRepository(
    private val context: Context,
    private val scope: CoroutineScope,
    private val location: StateFlow<Location?>,
) {
    private val prefs = context.getSharedPreferences("weather", Context.MODE_PRIVATE)
    private val _weather = MutableStateFlow(loadCached())
    val weather: StateFlow<Weather?> = _weather.asStateFlow()
    private var lastFetchAt = 0L
    private var lastFetchLocation: Location? = null

    fun start() {
        scope.launch {
            while (isActive) {
                location.value?.let { maybeRefresh(it) }
                delay(60_000)
            }
        }
    }

    private suspend fun maybeRefresh(l: Location) {
        val now = System.currentTimeMillis()
        val moved = lastFetchLocation?.distanceTo(l)?.let { it > MOVE_M } ?: true
        if (now - lastFetchAt < REFRESH_MS && !moved) return
        lastFetchAt = now
        lastFetchLocation = l
        val result = withContext(Dispatchers.IO) {
            runCatching {
                val url = URL(
                    "https://api.open-meteo.com/v1/forecast?latitude=%.4f&longitude=%.4f".format(Locale.US, l.latitude, l.longitude) +
                        "&current=temperature_2m,weather_code,is_day&daily=temperature_2m_max,temperature_2m_min&timezone=auto&forecast_days=1",
                )
                val conn = (url.openConnection() as HttpURLConnection).apply { connectTimeout = 8_000; readTimeout = 8_000 }
                val body = conn.inputStream.bufferedReader().use { it.readText() }
                conn.disconnect()
                val city = runCatching {
                    @Suppress("DEPRECATION")
                    Geocoder(context, Locale("pt", "BR")).getFromLocation(l.latitude, l.longitude, 1)
                        ?.firstOrNull()?.let { it.subAdminArea ?: it.locality ?: it.subLocality }
                }.getOrNull()
                body to city
            }
        }
        result.onSuccess { (body, city) ->
            val w = WeatherCodes.parse(body, city ?: _weather.value?.city, System.currentTimeMillis())
            _weather.value = w
            prefs.edit().putString(KEY, body).putString(KEY_CITY, w.city).putLong(KEY_AT, w.updatedAt).apply()
        }.onFailure { FileLog.w("Clima indisponível: ${it.message}") }
    }

    private fun loadCached(): Weather? = runCatching {
        val body = prefs.getString(KEY, null) ?: return null
        WeatherCodes.parse(body, prefs.getString(KEY_CITY, null), prefs.getLong(KEY_AT, 0))
    }.getOrNull()

    private companion object {
        const val KEY = "last"
        const val KEY_CITY = "city"
        const val KEY_AT = "at"
        const val REFRESH_MS = 30 * 60_000L
        const val MOVE_M = 5_000f
    }
}
