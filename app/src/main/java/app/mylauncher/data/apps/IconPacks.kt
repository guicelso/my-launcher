package app.mylauncher.data.apps

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.drawable.Drawable
import app.mylauncher.core.FileLog
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory

data class IconPack(val packageName: String, val label: String)

/**
 * Pacotes de ícones de terceiros nos formatos ADW/Nova (spec 011, R11.6): o pacote declara uma
 * activity com uma das ações abaixo e um `appfilter.xml` que mapeia componentes para drawables.
 */
class IconPacks(private val context: Context) {
    private val pm = context.packageManager

    @Volatile private var loadedPack: String? = null
    @Volatile private var filter: Map<String, String> = emptyMap()
    @Volatile private var resources: Resources? = null

    fun installed(): List<IconPack> = ACTIONS
        .flatMap { action -> runCatching { pm.queryIntentActivities(Intent(action), 0) }.getOrDefault(emptyList()) }
        .map { it.activityInfo.packageName }
        .distinct()
        .map { pkg -> IconPack(pkg, runCatching { pm.getApplicationLabel(pm.getApplicationInfo(pkg, 0)).toString() }.getOrDefault(pkg)) }
        .sortedBy { it.label.lowercase() }

    /** Ícone do pacote para [component], ou null se o pacote não tiver esse app. */
    @Synchronized
    fun drawable(pack: String, component: ComponentName): Drawable? {
        if (loadedPack != pack) load(pack)
        val res = resources ?: return null
        val name = filter["${component.packageName}/${component.className}"]
            ?: filter.entries.firstOrNull { it.key.startsWith("${component.packageName}/") }?.value
            ?: return null
        val id = res.getIdentifier(name, "drawable", pack)
        return if (id != 0) runCatching { res.getDrawable(id, null) }.getOrNull() else null
    }

    private fun load(pack: String) {
        loadedPack = pack
        filter = emptyMap()
        resources = null
        runCatching {
            val res = pm.getResourcesForApplication(pack)
            resources = res
            val xmlId = res.getIdentifier("appfilter", "xml", pack)
            val parser: XmlPullParser = if (xmlId != 0) {
                res.getXml(xmlId)
            } else {
                XmlPullParserFactory.newInstance().newPullParser().apply {
                    setInput(res.assets.open("appfilter.xml"), "UTF-8")
                }
            }
            filter = parse(parser)
            FileLog.i("Pacote de ícones $pack: ${filter.size} ícones")
        }.onFailure { FileLog.w("Falha ao carregar pacote de ícones $pack", it) }
    }

    companion object {
        val ACTIONS = listOf(
            "org.adw.launcher.THEMES",
            "com.novalauncher.THEME",
            "com.teslacoilsw.launcher.THEME",
            "com.gau.go.launcherex.theme",
        )

        /** `ComponentInfo{pkg/cls}` → `pkg/cls` (classe relativa expandida). */
        fun parseComponent(raw: String): String? {
            val inner = raw.removePrefix("ComponentInfo{").removeSuffix("}").trim()
            val slash = inner.indexOf('/')
            if (slash <= 0 || slash == inner.lastIndex) return null
            val pkg = inner.substring(0, slash)
            val cls = inner.substring(slash + 1).let { if (it.startsWith(".")) pkg + it else it }
            return "$pkg/$cls"
        }

        fun parse(parser: XmlPullParser): Map<String, String> {
            val map = HashMap<String, String>()
            var event = parser.eventType
            while (event != XmlPullParser.END_DOCUMENT) {
                if (event == XmlPullParser.START_TAG && parser.name == "item") {
                    val component = parser.getAttributeValue(null, "component")?.let(::parseComponent)
                    val drawable = parser.getAttributeValue(null, "drawable")
                    if (component != null && !drawable.isNullOrBlank()) map[component] = drawable
                }
                event = parser.next()
            }
            return map
        }
    }
}
