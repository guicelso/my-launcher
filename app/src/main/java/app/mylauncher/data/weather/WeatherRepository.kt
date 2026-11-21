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

data class HourForecast(val time: Long, val temperature: Int, val code: Int, val isDay: Boolean) {
    val kind: WeatherKind get() = WeatherCodes.kind(code)
}

data class DayForecast(val date: Long, val code: Int, val max: Int, val min: Int, val rainChance: Int?) {
    val kind: WeatherKind get() = WeatherCodes.kind(code)
}

data class Weather(
    val temperature: Int,
    val code: Int,
    val isDay: Boolean,
    val min: Int?,
    val max: Int?,
    val city: String?,
    val updatedAt: Long,
    val feelsLike: Int? = null,
    val humidity: Int? = null,
    val windKmh: Int? = null,
    val rainChance: Int? = null,
    val sunrise: Long? = null,
    val sunset: Long? = null,
    val hourly: List<HourForecast> = emptyList(),
    val daily: List<DayForecast> = emptyList(),
    val simulated: Boolean = false,
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
        val hourly = o.optJSONObject("hourly")
        val offsetMs = o.optLong("utc_offset_seconds", 0) * 1000
        fun iso(s: String?): Long? = s?.let { runCatching { parseLocal(it) - offsetMs }.getOrNull() }
        fun round(d: Double) = Math.round(d).toInt()
        val days = daily?.let { d ->
            val times = d.optJSONArray("time")
            (0 until (times?.length() ?: 0)).mapNotNull { i ->
                val max = d.optJSONArray("temperature_2m_max")?.optDouble(i) ?: return@mapNotNull null
                val min = d.optJSONArray("temperature_2m_min")?.optDouble(i) ?: return@mapNotNull null
                if (max.isNaN() || min.isNaN()) return@mapNotNull null
                DayForecast(
                    date = iso(times!!.getString(i) + "T12:00") ?: return@mapNotNull null,
                    code = d.optJSONArray("weather_code")?.optInt(i) ?: 3,
                    max = round(max), min = round(min),
                    rainChance = d.optJSONArray("precipitation_probability_max")?.optInt(i, -1)?.takeIf { it >= 0 },
                )
            }
        }.orEmpty()
        val hours = hourly?.let { h ->
            val times = h.optJSONArray("time")
            (0 until (times?.length() ?: 0)).mapNotNull { i ->
                val t = iso(times!!.getString(i)) ?: return@mapNotNull null
                if (t < now - 3_600_000L) return@mapNotNull null
                HourForecast(t, round(h.getJSONArray("temperature_2m").getDouble(i)), h.getJSONArray("weather_code").getInt(i), h.optJSONArray("is_day")?.optInt(i, 1) != 0)
            }.take(24)
        }.orEmpty()
        return Weather(
            temperature = round(cur.getDouble("temperature_2m")),
            code = cur.getInt("weather_code"),
            isDay = cur.optInt("is_day", 1) == 1,
            min = days.firstOrNull()?.min,
            max = days.firstOrNull()?.max,
            city = city,
            updatedAt = now,
            feelsLike = cur.optDouble("apparent_temperature").takeIf { !it.isNaN() }?.let(::round),
            humidity = cur.optInt("relative_humidity_2m", -1).takeIf { it >= 0 },
            windKmh = cur.optDouble("wind_speed_10m").takeIf { !it.isNaN() }?.let(::round),
            rainChance = days.firstOrNull()?.rainChance,
            sunrise = daily?.optJSONArray("sunrise")?.optString(0)?.takeIf { it.isNotBlank() }?.let(::iso),
            sunset = daily?.optJSONArray("sunset")?.optString(0)?.takeIf { it.isNotBlank() }?.let(::iso),
            hourly = hours,
            daily = days,
        )
    }

    /** "2026-11-21T14:00" no fuso UTC (o deslocamento local é aplicado por quem chama). */
    private fun parseLocal(s: String): Long {
        val f = java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.US).apply { timeZone = java.util.TimeZone.getTimeZone("UTC") }
        return f.parse(s)!!.time
    }

    /** Clima de demonstração para o emulador (painel de depuração). */
    fun simulated(kind: WeatherKind, isDay: Boolean, now: Long): Weather {
        val code = when (kind) {
            WeatherKind.CLEAR -> 0
            WeatherKind.PARTLY -> 2
            WeatherKind.CLOUDY -> 3
            WeatherKind.FOG -> 45
            WeatherKind.DRIZZLE -> 53
            WeatherKind.RAIN -> 63
            WeatherKind.SNOW -> 73
            WeatherKind.STORM -> 95
        }
        val base = when (kind) { WeatherKind.SNOW -> -2; WeatherKind.CLEAR -> 27; WeatherKind.STORM, WeatherKind.RAIN -> 19; else -> 22 }
        val codes = listOf(0, 2, 3, 63, 95, 2, 0)
        return Weather(
            temperature = base, code = code, isDay = isDay, min = base - 6, max = base + 4, city = "Cidade simulada",
            updatedAt = now, feelsLike = base + 1, humidity = 64, windKmh = 14, rainChance = if (kind == WeatherKind.RAIN || kind == WeatherKind.STORM) 80 else 20,
            sunrise = now - 6 * 3_600_000L, sunset = now + 5 * 3_600_000L,
            hourly = (0 until 12).map { i -> HourForecast(now + i * 3_600_000L, base + listOf(0, 1, 2, 2, 1, 0, -1, -2, -3, -3, -4, -4)[i], if (i < 4) code else codes[i % codes.size], isDay) },
            daily = (0 until 7).map { i -> DayForecast(now + i * 86_400_000L, if (i == 0) code else codes[i], base + 4 - i % 3, base - 6 + i % 2, (i * 15) % 90) },
            simulated = true,
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
    private val real = MutableStateFlow(loadCached())
    private val simulation = MutableStateFlow<Weather?>(null)
    private val _weather = MutableStateFlow(real.value)
    val weather: StateFlow<Weather?> = _weather.asStateFlow()

    val isSimulating: Boolean get() = simulation.value != null

    /** Condição simulada atual (para o painel de depuração reabrir no mesmo estado). */
    var simulatedKind: WeatherKind? = null
        private set
    var simulatedDay: Boolean = true
        private set

    /** Liga/desliga o clima simulado (painel de depuração, para o emulador). */
    fun simulate(kind: WeatherKind?, isDay: Boolean = true) {
        simulatedKind = kind
        simulatedDay = isDay
        simulation.value = kind?.let { WeatherCodes.simulated(it, isDay, System.currentTimeMillis()) }
        _weather.value = simulation.value ?: real.value
    }
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
                        "&current=temperature_2m,apparent_temperature,relative_humidity_2m,wind_speed_10m,weather_code,is_day" +
                        "&hourly=temperature_2m,weather_code,is_day" +
                        "&daily=weather_code,temperature_2m_max,temperature_2m_min,sunrise,sunset,precipitation_probability_max" +
                        "&timezone=auto&forecast_days=7",
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
            real.value = w
            if (simulation.value == null) _weather.value = w
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
