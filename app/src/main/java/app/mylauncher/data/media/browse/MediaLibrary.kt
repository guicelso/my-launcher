package app.mylauncher.data.media.browse

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.util.LruCache
import androidx.core.graphics.ColorUtils
import androidx.palette.graphics.Palette
import app.mylauncher.core.FileLog
import app.mylauncher.data.media.MediaApps
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

/** Fonte de áudio da própria multimídia (TopWay), sem navegação: abre o app dela. */
data class HeadUnitSource(val label: String, val pkg: String, val kind: Kind) {
    enum class Kind { RADIO, BLUETOOTH, USB, AUX }
}

/**
 * Biblioteca de mídia (spec 018): descobre os apps de mídia, mantém a conexão de navegação do app
 * aberto, lembra quem recusa o Lumia (modo só player, R18.2) e busca as sugestões do card (R18.19).
 */
class MediaLibrary(private val context: Context) {
    private val pm: PackageManager = context.packageManager
    private val prefs = context.getSharedPreferences("media_browse", Context.MODE_PRIVATE)

    private var current: MediaBrowserConnector? = null
    private val _denied = MutableStateFlow(loadDenied())
    /** Pacotes (com versão) que recusaram a navegação: aparecem como "Só controles". */
    val denied: StateFlow<Set<String>> = _denied.asStateFlow()

    /** Componente do serviço de navegação do app (MediaBrowserService ou Media3 MediaLibraryService). */
    fun browseComponent(pkg: String): ComponentName? = ACTIONS.firstNotNullOfOrNull { action ->
        pm.queryIntentServices(Intent(action).setPackage(pkg), 0).firstOrNull()?.serviceInfo
            ?.let { ComponentName(it.packageName, it.name) }
    }

    /** O app se declara de mídia para carros (`<uses name="media"/>`)? */
    fun isCarMediaApp(pkg: String): Boolean = runCatching {
        pm.getApplicationInfo(pkg, PackageManager.GET_META_DATA).metaData?.containsKey(CAR_APP_META) == true
    }.getOrDefault(false)

    /**
     * Apps de mídia instalados (R18.1): os que têm serviço de navegação, primeiro os de carro.
     * O próprio Lumia só aparece quando o app simulado do painel de depuração está ligado.
     */
    fun mediaApps(extra: Collection<String> = emptyList()): List<String> {
        val found = ACTIONS.flatMap { action ->
            pm.queryIntentServices(Intent(action), 0).mapNotNull { it.serviceInfo?.packageName }
        }
        val self = context.packageName
        val all = (found + extra).distinct().filter { pkg ->
            when {
                pkg.startsWith("com.tw.") -> false
                // O serviço simulado do Lumia só é encontrado quando o painel de depuração o liga.
                pkg == self -> true
                // Como no Android Auto: só apps de mídia para carros (`<uses name="media"/>`), além
                // dos que estão tocando, do app de música padrão e dos conhecidos.
                pkg in extra || pkg in MediaApps.KNOWN -> pm.getLaunchIntentForPackage(pkg) != null || isCarMediaApp(pkg)
                else -> isCarMediaApp(pkg)
            }
        }
        return all.sortedWith(compareBy<String>({ !isCarMediaApp(it) && it !in MediaApps.KNOWN }, { label(it).lowercase() }))
    }

    /** Fontes da multimídia instaladas (Rádio, Bluetooth, USB, AUX). */
    fun headUnitSources(): List<HeadUnitSource> = HEAD_UNIT.filter { pm.getLaunchIntentForPackage(it.pkg) != null }

    private val labels = java.util.concurrent.ConcurrentHashMap<String, String>()

    /**
     * Nome do app de mídia. Como no Android Auto, o `android:label` do serviço de navegação, quando
     * declarado, tem precedência sobre o nome do app.
     */
    fun label(pkg: String): String = labels.getOrPut(pkg) {
        val service = runCatching {
            browseComponent(pkg)?.let { pm.getServiceInfo(it, 0) }?.takeIf { it.labelRes != 0 || it.nonLocalizedLabel != null }?.loadLabel(pm)?.toString()
        }.getOrNull()
        service ?: runCatching { pm.getApplicationLabel(pm.getApplicationInfo(pkg, 0)).toString() }.getOrDefault(pkg)
    }

    private fun versionKey(pkg: String): String = runCatching {
        @Suppress("DEPRECATION")
        "$pkg@${pm.getPackageInfo(pkg, 0).versionCode}"
    }.getOrDefault(pkg)

    fun isDenied(pkg: String): Boolean = versionKey(pkg) in _denied.value

    /**
     * Abre (ou reaproveita) a navegação do app. Mantém uma conexão por vez, como o Android Auto
     * com o app em primeiro plano. Recusa ou raiz vazia ficam registradas por pacote e versão.
     */
    suspend fun open(pkg: String): MediaBrowserConnector? {
        current?.let { if (it.pkg == pkg && it.status.value is BrowseStatus.Ready) return it }
        current?.disconnect()
        current = null
        val component = browseComponent(pkg)
        if (component == null) {
            markDenied(pkg, true)
            return null
        }
        val conn = MediaBrowserConnector(context, pkg, component)
        current = conn
        val status = conn.connect()
        val usable = status is BrowseStatus.Ready && conn.load(status.root.id).orEmpty().isNotEmpty()
        markDenied(pkg, !usable)
        if (!usable) {
            conn.disconnect()
            if (current === conn) current = null
            return null
        }
        return conn
    }

    /** Conexão de navegação atual, se for deste app. */
    fun connected(pkg: String): MediaBrowserConnector? = current?.takeIf { it.pkg == pkg && it.status.value is BrowseStatus.Ready }

    fun close() {
        current?.disconnect()
        current = null
    }

    /**
     * Sugestões do app para o card quando nada toca (R18.19): root com `EXTRA_SUGGESTED`, até 10
     * itens. Os tocáveis vêm primeiro; se o root só tiver pastas, desce um nível na primeira.
     */
    suspend fun suggestions(pkg: String): List<BrowseItem> {
        val component = browseComponent(pkg) ?: return emptyList()
        val conn = MediaBrowserConnector(context, pkg, component, suggested = true)
        return try {
            val st = conn.connect() as? BrowseStatus.Ready ?: return emptyList()
            var items = conn.load(st.root.id).orEmpty()
            if (items.none { it.playable }) {
                items.firstOrNull { it.browsable }?.let { first -> items = conn.load(first.id).orEmpty() }
            }
            items.filter { it.playable || it.browsable }.take(MAX_SUGGESTIONS)
        } finally {
            conn.disconnect()
        }
    }

    /** Toca uma sugestão: precisa de uma conexão ao app (a sugestão pode estar desconectada). */
    suspend fun playSuggestion(pkg: String, item: BrowseItem) {
        val conn = connected(pkg) ?: open(pkg) ?: return
        if (item.playable) conn.play(item.id) else conn.play(item.id, shuffle = false)
    }

    /** Buscas recentes do app (mostradas em movimento, quando só a voz está disponível). */
    fun recentSearches(pkg: String): List<String> =
        prefs.getString("recent_$pkg", null)?.split('\n')?.filter { it.isNotBlank() }.orEmpty()

    fun addSearch(pkg: String, query: String) {
        val q = query.trim()
        if (q.isEmpty()) return
        val list = (listOf(q) + recentSearches(pkg).filterNot { it.equals(q, ignoreCase = true) }).take(MAX_RECENT_SEARCHES)
        prefs.edit().putString("recent_$pkg", list.joinToString("\n")).apply()
    }

    private fun markDenied(pkg: String, denied: Boolean) {
        val key = versionKey(pkg)
        val set = _denied.value.filterNot { it.substringBefore('@') == pkg }.toMutableSet()
        if (denied) set += key
        if (set != _denied.value) {
            _denied.value = set
            prefs.edit().putStringSet(KEY_DENIED, set).apply()
            FileLog.i("Browse $pkg: ${if (denied) "só player" else "navegação disponível"}")
        }
    }

    private fun loadDenied(): Set<String> = prefs.getStringSet(KEY_DENIED, emptySet()).orEmpty().toSet()

    companion object {
        val ACTIONS = listOf(MediaApps.MEDIA_BROWSER_SERVICE, "androidx.media3.session.MediaLibraryService")
        const val CAR_APP_META = "com.google.android.gms.car.application"
        const val MAX_SUGGESTIONS = 10
        const val MAX_RECENT_SEARCHES = 5
        private const val KEY_DENIED = "denied"
        val HEAD_UNIT = listOf(
            HeadUnitSource("Rádio FM/AM", "com.tw.radio", HeadUnitSource.Kind.RADIO),
            HeadUnitSource("Bluetooth", "com.tw.bt", HeadUnitSource.Kind.BLUETOOTH),
            HeadUnitSource("USB", "com.tw.music", HeadUnitSource.Kind.USB),
            HeadUnitSource("AUX", "com.tw.aux", HeadUnitSource.Kind.AUX),
        )
    }
}

/**
 * Capas e ícones dos itens de navegação (URIs `content://`, `android.resource://`, `file://` ou
 * http). O Android Auto recomenda URIs, não bitmaps (limite de 1 MB do binder).
 */
object MediaArt {
    private val cache = LruCache<String, Bitmap>(80)

    fun cached(uri: Uri?): Bitmap? = uri?.let { cache.get(it.toString()) }

    suspend fun load(context: Context, uri: Uri?, sizePx: Int): Bitmap? {
        uri ?: return null
        val key = uri.toString()
        cache.get(key)?.let { return it }
        return withContext(Dispatchers.IO) {
            runCatching {
                val bytes = when (uri.scheme) {
                    "http", "https" -> (URL(key).openConnection() as HttpURLConnection).run {
                        connectTimeout = 4_000
                        readTimeout = 6_000
                        inputStream.use { it.readBytes() }
                    }
                    else -> context.contentResolver.openInputStream(uri)?.use { it.readBytes() }
                } ?: return@runCatching null
                val bounds = BitmapFactory.Options().apply { inJustDecodeBounds = true }
                BitmapFactory.decodeByteArray(bytes, 0, bytes.size, bounds)
                var sample = 1
                while (bounds.outWidth / (sample * 2) >= sizePx && bounds.outHeight / (sample * 2) >= sizePx) sample *= 2
                BitmapFactory.decodeByteArray(bytes, 0, bytes.size, BitmapFactory.Options().apply { inSampleSize = sample })
            }.onFailure { FileLog.w("Capa $key: ${it.javaClass.simpleName}") }.getOrNull()?.also { cache.put(key, it) }
        }
    }
}

/**
 * Cor de destaque do app (progresso, "tocando agora", ponto de novo), como o Android Auto lê do
 * tema de carro do app (`com.google.android.gms.car.application.theme` → colorAccent). Sem tema,
 * a cor vibrante do ícone. Sempre clareada para ter contraste sobre o fundo escuro.
 */
object AppAccent {
    private val cache = HashMap<String, Int>()

    fun of(context: Context, pkg: String): Int? = synchronized(cache) {
        cache[pkg]?.let { return it.takeIf { c -> c != 0 } }
        val raw = fromTheme(context, pkg) ?: fromIcon(context, pkg)
        val c = raw?.let(::readable) ?: 0
        cache[pkg] = c
        c.takeIf { it != 0 }
    }

    private fun fromTheme(context: Context, pkg: String): Int? = runCatching {
        val pm = context.packageManager
        val ai = pm.getApplicationInfo(pkg, PackageManager.GET_META_DATA)
        val theme = ai.metaData?.getInt("com.google.android.gms.car.application.theme")?.takeIf { it != 0 } ?: return null
        val pc = context.createPackageContext(pkg, 0)
        val res = pc.resources.newTheme().apply { applyStyle(theme, true) }
        val a = res.obtainStyledAttributes(intArrayOf(android.R.attr.colorAccent, android.R.attr.colorPrimary))
        val color = a.getColor(0, 0).takeIf { it != 0 } ?: a.getColor(1, 0).takeIf { it != 0 }
        a.recycle()
        color
    }.getOrNull()

    private fun fromIcon(context: Context, pkg: String): Int? = runCatching {
        val d = context.packageManager.getApplicationIcon(pkg)
        val bmp = Bitmap.createBitmap(64, 64, Bitmap.Config.ARGB_8888)
        val canvas = android.graphics.Canvas(bmp)
        d.setBounds(0, 0, 64, 64)
        d.draw(canvas)
        val p = Palette.from(bmp).generate()
        p.getVibrantColor(p.getLightVibrantColor(p.getDominantColor(0))).takeIf { it != 0 }
    }.getOrNull()

    /** Clareia cores escuras para contraste ≥ 4,5:1 sobre o card (#262625). */
    internal fun readable(color: Int): Int {
        val hsl = FloatArray(3)
        ColorUtils.colorToHSL(color, hsl)
        if (hsl[1] < 0.15f) return Color.parseColor("#C4C7C5")
        hsl[2] = hsl[2].coerceAtLeast(0.68f)
        hsl[1] = hsl[1].coerceAtMost(0.9f)
        return ColorUtils.HSLToColor(hsl)
    }
}
