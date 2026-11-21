package app.mylauncher.data.destinations

import android.content.Context
import app.mylauncher.core.FileLog
import app.mylauncher.data.prefs.LauncherSettings
import app.mylauncher.data.vehicle.VehicleState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.util.Calendar
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

data class Visit(val lat: Double, val lon: Double, val time: Long)

enum class PlaceKind { HOME, WORK }

data class LearnedPlace(val kind: PlaceKind, val lat: Double, val lon: Double, val visits: Int) {
    val query: String get() = "%.6f,%.6f".format(java.util.Locale.US, lat, lon)
    val key: String get() = "${kind}@%.3f,%.3f".format(java.util.Locale.US, lat, lon)
}

/** Regras puras do aprendizado (testáveis, spec 012 R12.4). */
object PlaceRules {
    const val RADIUS_M = 150.0
    const val MIN_VISITS = 3

    fun distanceM(aLat: Double, aLon: Double, bLat: Double, bLon: Double): Double {
        val r = 6_371_000.0
        val dLat = Math.toRadians(bLat - aLat)
        val dLon = Math.toRadians(bLon - aLon)
        val h = sin(dLat / 2) * sin(dLat / 2) + cos(Math.toRadians(aLat)) * cos(Math.toRadians(bLat)) * sin(dLon / 2) * sin(dLon / 2)
        return 2 * r * atan2(sqrt(h), sqrt(1 - h))
    }

    fun isNight(c: Calendar) = c.get(Calendar.HOUR_OF_DAY).let { it >= 20 || it < 6 }

    fun isWorkHours(c: Calendar): Boolean {
        val day = c.get(Calendar.DAY_OF_WEEK)
        val hour = c.get(Calendar.HOUR_OF_DAY)
        return day in Calendar.MONDAY..Calendar.FRIDAY && hour in 8..17
    }

    /** Agrupa visitas por proximidade e devolve o lugar mais frequente que cumpre [filter]. */
    fun best(visits: List<Visit>, filter: (Visit) -> Boolean, exclude: Pair<Double, Double>? = null): Triple<Double, Double, Int>? {
        val chosen = visits.filter(filter)
        val clusters = mutableListOf<MutableList<Visit>>()
        for (v in chosen) {
            val c = clusters.firstOrNull { cl -> distanceM(cl[0].lat, cl[0].lon, v.lat, v.lon) <= RADIUS_M }
            if (c != null) c.add(v) else clusters.add(mutableListOf(v))
        }
        return clusters
            .filter { it.size >= MIN_VISITS }
            .map { cl -> Triple(cl.map { it.lat }.average(), cl.map { it.lon }.average(), cl.size) }
            .filter { (lat, lon, _) -> exclude == null || distanceM(lat, lon, exclude.first, exclude.second) > RADIUS_M }
            .maxByOrNull { it.third }
    }
}

/**
 * Aprende onde o carro costuma ficar desligado (spec 012, R12.4/R12.6). Opcional, desligado por
 * padrão, e tudo fica só na multimídia (SharedPreferences privadas do app).
 */
class PlacesLearner(
    context: Context,
    private val scope: CoroutineScope,
    private val vehicle: StateFlow<VehicleState>,
    private val settings: StateFlow<LauncherSettings>,
    private val lastLocation: () -> android.location.Location?,
) {
    private val prefs = context.getSharedPreferences("places", Context.MODE_PRIVATE)
    private val _suggestion = MutableStateFlow<LearnedPlace?>(null)
    val suggestion: StateFlow<LearnedPlace?> = _suggestion.asStateFlow()

    fun start() {
        scope.launch {
            vehicle.map { it.accOn }.distinctUntilChanged().collect { acc ->
                if (acc == false && settings.value.learnPlaces) record()
            }
        }
        scope.launch { settings.collect { recompute(it) } }
    }

    private fun record() {
        val l = lastLocation() ?: return
        val list = load() + Visit(l.latitude, l.longitude, System.currentTimeMillis())
        save(list.takeLast(MAX_VISITS))
        FileLog.i("Local de parada registrado (${list.size} no total)")
        recompute(settings.value)
    }

    private fun recompute(s: LauncherSettings) {
        if (!s.learnPlaces) { _suggestion.value = null; return }
        val visits = load()
        val dismissed = prefs.getStringSet(KEY_DISMISSED, emptySet()).orEmpty()
        val cal = Calendar.getInstance()
        fun at(v: Visit) = cal.apply { timeInMillis = v.time }
        val home = if (s.home == null) {
            PlaceRules.best(visits, { PlaceRules.isNight(at(it)) })?.let { LearnedPlace(PlaceKind.HOME, it.first, it.second, it.third) }
        } else null
        val homeLatLon = s.home?.latLon ?: home?.let { it.lat to it.lon }
        val work = if (s.work == null) {
            PlaceRules.best(visits, { PlaceRules.isWorkHours(at(it)) }, exclude = homeLatLon)
                ?.let { LearnedPlace(PlaceKind.WORK, it.first, it.second, it.third) }
        } else null
        _suggestion.value = listOfNotNull(home, work).firstOrNull { it.key !in dismissed }
    }

    fun dismiss(place: LearnedPlace) {
        prefs.edit().putStringSet(KEY_DISMISSED, prefs.getStringSet(KEY_DISMISSED, emptySet()).orEmpty() + place.key).apply()
        recompute(settings.value)
    }

    fun clearHistory() {
        prefs.edit().clear().apply()
        _suggestion.value = null
        FileLog.i("Histórico de locais apagado")
    }

    private fun load(): List<Visit> = runCatching {
        val arr = JSONArray(prefs.getString(KEY_VISITS, "[]"))
        (0 until arr.length()).map { i -> arr.getJSONObject(i).let { Visit(it.getDouble("a"), it.getDouble("o"), it.getLong("t")) } }
    }.getOrDefault(emptyList())

    private fun save(list: List<Visit>) {
        val arr = JSONArray()
        list.forEach { arr.put(JSONObject().put("a", it.lat).put("o", it.lon).put("t", it.time)) }
        prefs.edit().putString(KEY_VISITS, arr.toString()).apply()
    }

    private companion object {
        const val KEY_VISITS = "visits"
        const val KEY_DISMISSED = "dismissed"
        const val MAX_VISITS = 300
    }
}
