package app.mylauncher.data.prefs

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class ThemePref { AUTO, DAY, NIGHT }
enum class TextScale(val factor: Float) { NORMAL(1f), LARGE(1.15f) }

/** Destino salvo. [query] é um endereço livre ou "lat,lon". */
data class Destination(val label: String, val query: String) {
    val latLon: Pair<Double, Double>?
        get() {
            val parts = query.split(',').map { it.trim() }
            if (parts.size != 2) return null
            val lat = parts[0].toDoubleOrNull() ?: return null
            val lon = parts[1].toDoubleOrNull() ?: return null
            return if (lat in -90.0..90.0 && lon in -180.0..180.0) lat to lon else null
        }
}

data class LauncherSettings(
    val theme: ThemePref = ThemePref.AUTO,
    val textScale: TextScale = TextScale.NORMAL,
    val navApp: String = DEFAULT_NAV_APP,
    val musicApp: String = DEFAULT_MUSIC_APP,
    val home: Destination? = null,
    val work: Destination? = null,
    val pinned: List<String> = emptyList(),
    val recents: List<String> = emptyList(),
    val hidden: Set<String> = DEFAULT_HIDDEN,
    val duckOnReverse: Boolean = true,
    val showBattery: Boolean = true,
    val simulateVehicle: Boolean = false,
    val showBell: Boolean = true,
    val showVoice: Boolean = true,
    val splitMedia: Boolean = true,
) {
    companion object {
        const val DEFAULT_NAV_APP = "com.waze"
        const val DEFAULT_MUSIC_APP = "com.spotify.music"
        const val PHONE_APP = "com.tw.bt"
        val DEFAULT_HIDDEN = setOf("com.sprd.engineermode", "de.szalkowski.activitylauncher")
        const val MAX_RAIL_APPS = 3
    }
}

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsRepository(private val context: Context, private val scope: CoroutineScope) {

    private object K {
        val theme = stringPreferencesKey("theme")
        val textScale = stringPreferencesKey("text_scale")
        val navApp = stringPreferencesKey("nav_app")
        val musicApp = stringPreferencesKey("music_app")
        val home = stringPreferencesKey("home")
        val work = stringPreferencesKey("work")
        val pinned = stringPreferencesKey("pinned")
        val recents = stringPreferencesKey("recents")
        val hidden = stringPreferencesKey("hidden")
        val duck = booleanPreferencesKey("duck_on_reverse")
        val battery = booleanPreferencesKey("show_battery")
        val simulate = booleanPreferencesKey("simulate_vehicle")
        val bell = booleanPreferencesKey("show_bell")
        val voice = booleanPreferencesKey("show_voice")
        val split = booleanPreferencesKey("split_media")
    }

    val settings: StateFlow<LauncherSettings> = context.dataStore.data
        .map { it.toSettings() }
        .stateIn(scope, SharingStarted.Eagerly, LauncherSettings())

    fun setTheme(v: ThemePref) = put { it[K.theme] = v.name }
    fun setTextScale(v: TextScale) = put { it[K.textScale] = v.name }
    fun setNavApp(pkg: String) = put { it[K.navApp] = pkg }
    fun setMusicApp(pkg: String) = put { it[K.musicApp] = pkg }
    fun setHome(d: Destination?) = put { if (d == null) it.remove(K.home) else it[K.home] = encode(d) }
    fun setWork(d: Destination?) = put { if (d == null) it.remove(K.work) else it[K.work] = encode(d) }
    fun setPinned(list: List<String>) = put { it[K.pinned] = list.take(LauncherSettings.MAX_RAIL_APPS).joinToString(SEP) }
    fun setDuckOnReverse(v: Boolean) = put { it[K.duck] = v }
    fun setShowBattery(v: Boolean) = put { it[K.battery] = v }
    fun setSimulateVehicle(v: Boolean) = put { it[K.simulate] = v }
    fun setShowBell(v: Boolean) = put { it[K.bell] = v }
    fun setShowVoice(v: Boolean) = put { it[K.voice] = v }
    fun setSplitMedia(v: Boolean) = put { it[K.split] = v }

    /** Remove um app dos recentes (spec 009, R9.1). */
    fun removeRecent(pkg: String) = put { prefs ->
        val current = prefs[K.recents]?.split(SEP)?.filter { it.isNotBlank() } ?: emptyList()
        prefs[K.recents] = (current - pkg).joinToString(SEP)
    }

    fun pin(pkg: String) = put { prefs ->
        val current = prefs[K.pinned]?.split(SEP)?.filter { it.isNotBlank() } ?: emptyList()
        if (pkg !in current && current.size < LauncherSettings.MAX_RAIL_APPS) prefs[K.pinned] = (current + pkg).joinToString(SEP)
    }

    fun unpin(pkg: String) = put { prefs ->
        val current = prefs[K.pinned]?.split(SEP)?.filter { it.isNotBlank() } ?: emptyList()
        prefs[K.pinned] = (current - pkg).joinToString(SEP)
    }

    /** Registra que um app foi aberto pelo launcher (spec 003, R3.5). */
    fun recordLaunch(pkg: String) = put { prefs ->
        val current = prefs[K.recents]?.split(SEP)?.filter { it.isNotBlank() } ?: emptyList()
        prefs[K.recents] = Recents.push(current, pkg).joinToString(SEP)
    }

    private fun put(block: (androidx.datastore.preferences.core.MutablePreferences) -> Unit) {
        scope.launch { context.dataStore.edit { block(it) } }
    }

    private fun Preferences.toSettings(): LauncherSettings {
        val d = LauncherSettings()
        return LauncherSettings(
            theme = this[K.theme]?.let { runCatching { ThemePref.valueOf(it) }.getOrNull() } ?: d.theme,
            textScale = this[K.textScale]?.let { runCatching { TextScale.valueOf(it) }.getOrNull() } ?: d.textScale,
            navApp = this[K.navApp] ?: d.navApp,
            musicApp = this[K.musicApp] ?: d.musicApp,
            home = this[K.home]?.let(::decode),
            work = this[K.work]?.let(::decode),
            pinned = this[K.pinned]?.split(SEP)?.filter { it.isNotBlank() } ?: d.pinned,
            recents = this[K.recents]?.split(SEP)?.filter { it.isNotBlank() } ?: d.recents,
            hidden = this[K.hidden]?.split(SEP)?.filter { it.isNotBlank() }?.toSet() ?: d.hidden,
            duckOnReverse = this[K.duck] ?: d.duckOnReverse,
            showBattery = this[K.battery] ?: d.showBattery,
            simulateVehicle = this[K.simulate] ?: d.simulateVehicle,
            showBell = this[K.bell] ?: d.showBell,
            showVoice = this[K.voice] ?: d.showVoice,
            splitMedia = this[K.split] ?: d.splitMedia,
        )
    }

    private fun encode(d: Destination) = d.label + FIELD + d.query
    private fun decode(s: String): Destination? = s.split(FIELD, limit = 2).takeIf { it.size == 2 }
        ?.let { Destination(it[0], it[1]) }

    private companion object {
        const val SEP = "|"
        const val FIELD = "\u001F"
    }
}

/** Regras puras dos recentes (testáveis). */
object Recents {
    const val MAX = 8

    fun push(current: List<String>, pkg: String): List<String> =
        (listOf(pkg) + current.filter { it != pkg }).take(MAX)

    /**
     * Apps da barra lateral: fixados primeiro, depois recentes, depois padrões, sem repetir,
     * apenas instalados, no máximo [LauncherSettings.MAX_RAIL_APPS].
     */
    fun railApps(
        pinned: List<String>,
        recents: List<String>,
        defaults: List<String>,
        isInstalled: (String) -> Boolean,
        exclude: Set<String> = emptySet(),
    ): List<String> = (pinned + recents + defaults)
        .distinct()
        .filter { it !in exclude && isInstalled(it) }
        .take(LauncherSettings.MAX_RAIL_APPS)
}
