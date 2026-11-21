package app.mylauncher.data.apps

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.LauncherApps
import android.os.Process
import android.os.UserHandle
import android.util.LruCache
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import app.mylauncher.core.FileLog
import app.mylauncher.data.prefs.SettingsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.Collator
import java.text.Normalizer
import java.util.Locale

data class AppEntry(
    val packageName: String,
    val component: ComponentName,
    val label: String,
    val user: UserHandle,
) {
    val key: String get() = component.flattenToShortString()
}

/** Apps instalados com activity de LAUNCHER (spec 003). */
class AppsRepository(
    private val context: Context,
    private val settings: SettingsRepository,
    private val scope: CoroutineScope,
) {
    private val launcherApps = context.getSystemService(LauncherApps::class.java)
    val iconPacks = IconPacks(context)

    /** Muda quando o formato ou o pacote de ícones muda: as telas recarregam os ícones. */
    private val _iconStyle = MutableStateFlow("")
    val iconStyle: StateFlow<String> = _iconStyle.asStateFlow()
    private val iconCache = LruCache<String, ImageBitmap>(192)
    private val _apps = MutableStateFlow<List<AppEntry>>(emptyList())
    val apps: StateFlow<List<AppEntry>> = _apps.asStateFlow()

    private val callback = object : LauncherApps.Callback() {
        override fun onPackageRemoved(packageName: String, user: UserHandle) = changed(packageName)
        override fun onPackageAdded(packageName: String, user: UserHandle) = changed(packageName)
        override fun onPackageChanged(packageName: String, user: UserHandle) = changed(packageName)
        override fun onPackagesAvailable(p: Array<out String>, user: UserHandle, replacing: Boolean) = refresh()
        override fun onPackagesUnavailable(p: Array<out String>, user: UserHandle, replacing: Boolean) = refresh()
    }

    fun start() {
        launcherApps.registerCallback(callback)
        refresh()
        scope.launch {
            settings.settings.map { "${it.iconShape}|${it.iconPack}" }.distinctUntilChanged().collect { style ->
                if (_iconStyle.value.isNotEmpty()) iconCache.evictAll()
                _iconStyle.value = style
            }
        }
    }

    private fun changed(pkg: String) {
        iconCache.snapshot().keys.filter { it.startsWith("$pkg/") }.forEach { iconCache.remove(it) }
        refresh()
    }

    fun refresh() {
        scope.launch {
            val list = withContext(Dispatchers.Default) { load() }
            _apps.value = list
            FileLog.i("Apps carregados: ${list.size}")
            // Aquece o cache dos ícones da gaveta (96 dp) para ela abrir sem atraso.
            withContext(Dispatchers.Default) {
                val px = (96 * context.resources.displayMetrics.density).toInt()
                list.take(PREWARM).forEach { icon(it.packageName, px) }
            }
        }
    }

    private fun load(): List<AppEntry> {
        val self = context.packageName
        val collator = Collator.getInstance(Locale("pt", "BR"))
        return launcherApps.getActivityList(null, Process.myUserHandle())
            .filter { it.applicationInfo.packageName != self }
            .map { AppEntry(it.applicationInfo.packageName, it.componentName, it.label.toString(), it.user) }
            .sortedWith { a, b -> collator.compare(a.label, b.label) }
    }

    fun find(pkg: String): AppEntry? = _apps.value.firstOrNull { it.packageName == pkg }

    fun isInstalled(pkg: String): Boolean = find(pkg) != null ||
        runCatching { context.packageManager.getPackageInfo(pkg, 0); true }.getOrDefault(false)

    fun label(pkg: String): String = find(pkg)?.label ?: runCatching {
        val pm = context.packageManager
        pm.getApplicationLabel(pm.getApplicationInfo(pkg, 0)).toString()
    }.getOrDefault(pkg)

    /** Ícone redondo do app em [sizePx], com cache. Chamar fora da thread principal quando possível. */
    fun icon(pkg: String, sizePx: Int = 112): ImageBitmap? {
        val entry = find(pkg)
        val s = settings.settings.value
        val key = "${entry?.key ?: "$pkg/"}@$sizePx|${s.iconShape}|${s.iconPack}"
        iconCache.get(key)?.let { return it }
        // Pacote de ícones primeiro (já vem no formato do pacote); senão, o ícone do app no formato escolhido.
        val packIcon = s.iconPack?.let { pack ->
            entry?.component?.let { iconPacks.drawable(pack, it) }
        }
        val bmp = if (packIcon != null) {
            IconRenderer.original(packIcon, sizePx)
        } else {
            val drawable = runCatching {
                if (entry != null) {
                    launcherApps.getActivityList(pkg, entry.user)
                        .firstOrNull { it.componentName == entry.component }
                        ?.getIcon(0)
                } else {
                    context.packageManager.getApplicationIcon(pkg)
                }
            }.getOrNull() ?: return null
            IconRenderer.render(drawable, sizePx, s.iconShape)
        }.asImageBitmap()
        iconCache.put(key, bmp)
        return bmp
    }

    /** Desinstalar pela confirmação do sistema (spec 011, R11.1). Apps de sistema não podem. */
    fun canUninstall(pkg: String): Boolean = runCatching {
        val flags = context.packageManager.getApplicationInfo(pkg, 0).flags
        flags and android.content.pm.ApplicationInfo.FLAG_SYSTEM == 0
    }.getOrDefault(false)

    fun uninstall(pkg: String) {
        runCatching {
            context.startActivity(
                Intent(Intent.ACTION_DELETE, android.net.Uri.fromParts("package", pkg, null)).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK),
            )
        }.onFailure { FileLog.e("Falha ao desinstalar $pkg", it) }
    }

    /** Abre o app e registra nos recentes (R3.5). */
    fun launch(pkg: String): Boolean {
        val entry = find(pkg)
        val ok = runCatching {
            if (entry != null) {
                launcherApps.startMainActivity(entry.component, entry.user, null, null)
            } else {
                val intent = context.packageManager.getLaunchIntentForPackage(pkg)
                    ?: return@runCatching false
                context.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
            }
            true
        }.getOrElse {
            FileLog.e("Falha ao abrir $pkg", it)
            false
        }
        if (ok) settings.recordLaunch(pkg)
        return ok
    }

    /** Abre um Intent externo (deep link) e registra o app de destino nos recentes. */
    fun launchIntent(intent: Intent, recordPkg: String? = intent.`package`): Boolean = runCatching {
        context.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        recordPkg?.let(settings::recordLaunch)
        true
    }.getOrElse {
        FileLog.e("Falha ao abrir intent $intent", it)
        false
    }

    companion object {
        private const val PREWARM = 72

        /** Busca sem acento e sem diferenciar maiúsculas (R3.4). */
        fun normalize(s: String): String =
            Normalizer.normalize(s, Normalizer.Form.NFD).replace(Regex("\\p{Mn}+"), "").lowercase(Locale.ROOT)

        fun filter(apps: List<AppEntry>, query: String, hidden: Set<String>): List<AppEntry> {
            val q = normalize(query.trim())
            return apps.filter { it.packageName !in hidden && (q.isEmpty() || normalize(it.label).contains(q)) }
        }
    }
}
