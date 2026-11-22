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

/** Formato dos ícones (spec 011, R11.5). */
enum class IconShape(val label: String) { ROUND("Redondo"), SQUIRCLE("Quadrado arredondado"), ORIGINAL("Original do app") }

/** Animação de abertura da gaveta de apps. CONTAINER é o padrão canônico (card crescendo). */
enum class DrawerAnimation(val label: String) {
    CONTAINER("Crescer do botão (padrão)"),
    CIRCULAR("Revelação circular"),
    SLIDE_SIDE("Deslizar da barra"),
    SLIDE_UP("Subir de baixo"),
    FADE_SCALE("Esmaecer e ampliar"),
    NONE("Sem animação"),
}

enum class AppOrder(val label: String) { ALPHABETICAL("Alfabética"), CUSTOM("Personalizada") }

/** Grade da coluna direita do painel (spec 013). */
enum class DashboardLayout(val slots: Int, val label: String) { ONE(1, "1 card"), TWO(2, "2 cards"), THREE(3, "3 cards") }

enum class WidgetType(val label: String) {
    MEDIA("Mídia"),
    SUGGESTIONS("Destinos e sugestões"),
    WEATHER("Clima agora"),
    VEHICLE("Veículo"),
    CLOCK("Relógio e data"),
    NOTIFICATIONS("Notificações"),
    CLOCK_WEATHER("Data, hora e clima"),
    WEATHER_DETAILED("Clima detalhado"),
    WEATHER_HOURLY("Clima por hora"),
    WEATHER_DAILY("Previsão da semana"),
}

/** Posição da barra (spec 014). */
enum class RailPosition(val label: String) { LEFT("Vertical (lateral)"), BOTTOM("Horizontal (embaixo)") }

/** Lado do motorista (Android Auto): à direita, a barra vertical vai para a direita e a horizontal se inverte. */
enum class DriverSide(val label: String) { LEFT("Esquerda"), RIGHT("Direita") }

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
    val iconShape: IconShape = IconShape.ROUND,
    val iconPack: String? = null,
    val drawerAnimation: DrawerAnimation = DrawerAnimation.CONTAINER,
    val appOrder: AppOrder = AppOrder.ALPHABETICAL,
    val customOrder: List<String> = emptyList(),
    val favorites: List<Destination> = emptyList(),
    val learnPlaces: Boolean = false,
    val dashboardLayout: DashboardLayout = DashboardLayout.TWO,
    /** Spec 017: cada espaço do painel é uma pilha de 1 a 7 widgets (o primeiro é o principal). */
    val stacks: List<List<WidgetType>> = WidgetStacks.DEFAULT,
    /** Spec 017, R17.7: a pilha vai sozinha até o widget certo quando algo acontece. */
    val smartStacks: Boolean = true,
    val railPosition: RailPosition = RailPosition.LEFT,
    val driverSide: DriverSide = DriverSide.LEFT,
    val showWeatherInBar: Boolean = false,
    val showExitTile: Boolean = true,
    /** Spec 016: manter o app de música aberto com a chave desligada (lista da TopWay). */
    val keepMusicAlive: Boolean = true,
    /** Spec 016: retomar a música ao ligar a chave, se ela estava tocando ao desligar. */
    val resumeOnWake: Boolean = true,
) {
    /** Destinos para o card e o mapa: Casa, Trabalho e favoritos. */
    val destinations: List<Destination> get() = listOfNotNull(home, work) + favorites

    companion object {
        const val DEFAULT_NAV_APP = "com.waze"
        const val DEFAULT_MUSIC_APP = "com.spotify.music"
        const val PHONE_APP = "com.tw.bt"
        val DEFAULT_HIDDEN = setOf("com.sprd.engineermode", "de.szalkowski.activitylauncher")
        const val MAX_RAIL_APPS = 3
        val DEFAULT_WIDGETS = listOf(WidgetType.MEDIA, WidgetType.SUGGESTIONS, WidgetType.WEATHER)
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
        val iconShape = stringPreferencesKey("icon_shape")
        val iconPack = stringPreferencesKey("icon_pack")
        val drawerAnim = stringPreferencesKey("drawer_animation")
        val appOrder = stringPreferencesKey("app_order")
        val customOrder = stringPreferencesKey("custom_order")
        val favorites = stringPreferencesKey("favorites")
        val learn = booleanPreferencesKey("learn_places")
        val dashLayout = stringPreferencesKey("dashboard_layout")
        val widgets = stringPreferencesKey("widgets")
        val stacks = stringPreferencesKey("widget_stacks")
        val smartStacks = booleanPreferencesKey("smart_stacks")
        val railPos = stringPreferencesKey("rail_position")
        val driverSide = stringPreferencesKey("driver_side")
        val barWeather = booleanPreferencesKey("bar_weather")
        val exitTile = booleanPreferencesKey("exit_tile")
        val keepMusic = booleanPreferencesKey("keep_music_alive")
        val resumeWake = booleanPreferencesKey("resume_on_wake")
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
    fun setIconShape(v: IconShape) = put { it[K.iconShape] = v.name }
    fun setIconPack(pkg: String?) = put { if (pkg == null) it.remove(K.iconPack) else it[K.iconPack] = pkg }
    fun setDrawerAnimation(v: DrawerAnimation) = put { it[K.drawerAnim] = v.name }
    fun setAppOrder(v: AppOrder) = put { it[K.appOrder] = v.name }
    fun setCustomOrder(list: List<String>) = put { it[K.customOrder] = list.joinToString(SEP) }
    fun setLearnPlaces(v: Boolean) = put { it[K.learn] = v }
    fun setDashboardLayout(v: DashboardLayout) = put { it[K.dashLayout] = v.name }
    fun setRailPosition(v: RailPosition) = put { it[K.railPos] = v.name }
    fun setDriverSide(v: DriverSide) = put { it[K.driverSide] = v.name }
    fun setShowWeatherInBar(v: Boolean) = put { it[K.barWeather] = v }
    fun setShowExitTile(v: Boolean) = put { it[K.exitTile] = v }
    fun setKeepMusicAlive(v: Boolean) = put { it[K.keepMusic] = v }
    fun setResumeOnWake(v: Boolean) = put { it[K.resumeWake] = v }

    fun setSmartStacks(v: Boolean) = put { it[K.smartStacks] = v }

    /** Aplica [change] às pilhas do painel (spec 017); o resultado é normalizado e persistido. */
    fun editStacks(change: (List<List<WidgetType>>) -> List<List<WidgetType>>) = put { prefs ->
        val current = decodeStacks(prefs[K.stacks], prefs[K.widgets])
        prefs[K.stacks] = WidgetStacks.encode(WidgetStacks.normalize(change(current)))
    }

    /** Oculta da gaveta (spec 011, R11.1). */
    fun hide(pkg: String) = put { prefs ->
        val current = prefs[K.hidden]?.split(SEP)?.filter { it.isNotBlank() }?.toSet() ?: LauncherSettings.DEFAULT_HIDDEN
        prefs[K.hidden] = (current + pkg).joinToString(SEP)
    }

    /** Mostra de novo na gaveta (R11.2). */
    fun unhide(pkg: String) = put { prefs ->
        val current = prefs[K.hidden]?.split(SEP)?.filter { it.isNotBlank() }?.toSet() ?: LauncherSettings.DEFAULT_HIDDEN
        prefs[K.hidden] = (current - pkg).joinToString(SEP)
    }

    /** Favorito com o mesmo nome é substituído (spec 012). */
    fun addFavorite(d: Destination) = put { prefs ->
        val current = decodeList(prefs[K.favorites]).filter { it.label != d.label }
        prefs[K.favorites] = (current + d).joinToString(LIST_SEP) { encode(it) }
    }

    fun removeFavorite(label: String) = put { prefs ->
        val current = decodeList(prefs[K.favorites]).filter { it.label != label }
        prefs[K.favorites] = current.joinToString(LIST_SEP) { encode(it) }
    }

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
            iconShape = enumOr(this[K.iconShape], d.iconShape),
            iconPack = this[K.iconPack],
            drawerAnimation = enumOr(this[K.drawerAnim], d.drawerAnimation),
            appOrder = enumOr(this[K.appOrder], d.appOrder),
            customOrder = this[K.customOrder]?.split(SEP)?.filter { it.isNotBlank() } ?: d.customOrder,
            favorites = decodeList(this[K.favorites]),
            learnPlaces = this[K.learn] ?: d.learnPlaces,
            dashboardLayout = enumOr(this[K.dashLayout], d.dashboardLayout),
            stacks = decodeStacks(this[K.stacks], this[K.widgets]),
            smartStacks = this[K.smartStacks] ?: d.smartStacks,
            railPosition = enumOr(this[K.railPos], d.railPosition),
            driverSide = enumOr(this[K.driverSide], d.driverSide),
            showWeatherInBar = this[K.barWeather] ?: d.showWeatherInBar,
            showExitTile = this[K.exitTile] ?: d.showExitTile,
            keepMusicAlive = this[K.keepMusic] ?: d.keepMusicAlive,
            resumeOnWake = this[K.resumeWake] ?: d.resumeOnWake,
        )
    }

    private inline fun <reified E : Enum<E>> enumOr(value: String?, default: E): E =
        value?.let { v -> runCatching { enumValueOf<E>(v) }.getOrNull() } ?: default

    private fun decodeList(s: String?): List<Destination> =
        s?.split(LIST_SEP)?.filter { it.isNotBlank() }?.mapNotNull(::decode) ?: emptyList()

    private fun decodeWidgets(s: String?): List<WidgetType> {
        val parsed = s?.split(SEP)?.mapNotNull { runCatching { WidgetType.valueOf(it) }.getOrNull() } ?: emptyList()
        return Widgets.normalize(parsed)
    }

    /** Pilhas salvas; sem elas, migra a configuração da spec 013 (um widget por espaço, R17.1). */
    private fun decodeStacks(stacks: String?, legacy: String?): List<List<WidgetType>> =
        if (stacks != null) WidgetStacks.decode(stacks)
        else WidgetStacks.normalize(decodeWidgets(legacy).map { listOf(it) })

    private fun encode(d: Destination) = d.label + FIELD + d.query
    private fun decode(s: String): Destination? = s.split(FIELD, limit = 2).takeIf { it.size == 2 }
        ?.let { Destination(it[0], it[1]) }

    private companion object {
        const val SEP = "|"
        const val FIELD = "\u001F"
        const val LIST_SEP = "\u001E"
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

/** Configuração antiga da spec 013 (um widget por espaço), lida só para migrar (R17.1). */
object Widgets {
    /** Garante 3 espaços sem repetição, completando com os padrões. */
    fun normalize(list: List<WidgetType>): List<WidgetType> {
        val distinct = list.distinct().take(3)
        return (distinct + LauncherSettings.DEFAULT_WIDGETS + WidgetType.entries).distinct().take(3)
    }
}

/**
 * Regras das pilhas de widgets (spec 017), puras e testáveis. Sempre [SLOTS] pilhas, cada uma com
 * 1 a [MAX] widgets, e um widget nunca aparece em duas pilhas (mantém a R13.2).
 */
object WidgetStacks {
    const val SLOTS = 3
    const val MAX = 7
    private const val STACK_SEP = "|"
    private const val ITEM_SEP = ","
    val DEFAULT: List<List<WidgetType>> = LauncherSettings.DEFAULT_WIDGETS.map { listOf(it) }

    fun normalize(stacks: List<List<WidgetType>>): List<List<WidgetType>> {
        val seen = mutableSetOf<WidgetType>()
        val out = (0 until SLOTS).map { i ->
            val kept = stacks.getOrNull(i).orEmpty().distinct().filter { it !in seen }.take(MAX)
            seen += kept
            kept
        }.toMutableList()
        // Pilha que ficou vazia recebe um widget livre, começando pelos padrões.
        for (i in out.indices) if (out[i].isEmpty()) {
            val free = (LauncherSettings.DEFAULT_WIDGETS + WidgetType.entries).first { it !in seen }
            seen += free
            out[i] = listOf(free)
        }
        return out
    }

    /** Em que pilha [type] está, ou null. */
    fun slotOf(stacks: List<List<WidgetType>>, type: WidgetType): Int? =
        stacks.indexOfFirst { type in it }.takeIf { it >= 0 }

    /** Põe [type] no fim da pilha [slot], tirando de onde estava (R17.6). Pilha cheia: nada muda. */
    fun add(stacks: List<List<WidgetType>>, slot: Int, type: WidgetType): List<List<WidgetType>> {
        val target = stacks.getOrNull(slot) ?: return stacks
        if (type in target || target.size >= MAX) return stacks
        return normalize(stacks.mapIndexed { i, s -> if (i == slot) s + type else s - type })
    }

    /** Tira [type] da pilha [slot]; a pilha nunca fica vazia. */
    fun remove(stacks: List<List<WidgetType>>, slot: Int, type: WidgetType): List<List<WidgetType>> {
        val target = stacks.getOrNull(slot) ?: return stacks
        if (target.size <= 1 || type !in target) return stacks
        return stacks.mapIndexed { i, s -> if (i == slot) s - type else s }
    }

    /** Move o widget da posição [from] para [to] dentro da pilha [slot]. */
    fun move(stacks: List<List<WidgetType>>, slot: Int, from: Int, to: Int): List<List<WidgetType>> {
        val target = stacks.getOrNull(slot)?.toMutableList() ?: return stacks
        if (from !in target.indices || to !in target.indices || from == to) return stacks
        target.add(to, target.removeAt(from))
        return stacks.mapIndexed { i, s -> if (i == slot) target else s }
    }

    /**
     * Troca o widget da posição [index] da pilha [slot] por [type]. Se [type] já estava em outro
     * lugar, os dois trocam de lugar (mesma regra da R13.2).
     */
    fun replace(stacks: List<List<WidgetType>>, slot: Int, index: Int, type: WidgetType): List<List<WidgetType>> {
        val old = stacks.getOrNull(slot)?.getOrNull(index) ?: return stacks
        if (old == type) return stacks
        return stacks.map { s -> s.map { t -> if (t == type) old else t } }
            .mapIndexed { i, s -> if (i == slot) s.toMutableList().also { it[index] = type } else s }
    }

    /** Desfaz a pilha: fica só o widget principal (R17.6). */
    fun unstack(stacks: List<List<WidgetType>>, slot: Int): List<List<WidgetType>> =
        stacks.mapIndexed { i, s -> if (i == slot) s.take(1) else s }

    fun encode(stacks: List<List<WidgetType>>): String =
        stacks.joinToString(STACK_SEP) { s -> s.joinToString(ITEM_SEP) { it.name } }

    fun decode(s: String): List<List<WidgetType>> = normalize(
        s.split(STACK_SEP).map { part ->
            part.split(ITEM_SEP).mapNotNull { name -> runCatching { WidgetType.valueOf(name) }.getOrNull() }
        },
    )
}

/** Ordem dos apps na gaveta (spec 011, R11.3/R11.4). */
object AppOrdering {
    /** Ordem personalizada primeiro; apps novos entram no fim, na ordem de [alphabetical]. */
    fun <T> apply(alphabetical: List<T>, customOrder: List<String>, key: (T) -> String): List<T> {
        if (customOrder.isEmpty()) return alphabetical
        val index = customOrder.withIndex().associate { it.value to it.index }
        val (known, unknown) = alphabetical.partition { key(it) in index }
        return known.sortedBy { index.getValue(key(it)) } + unknown
    }

    fun move(list: List<String>, from: Int, to: Int): List<String> {
        if (from == to || from !in list.indices || to !in list.indices) return list
        return list.toMutableList().apply { add(to, removeAt(from)) }
    }
}
