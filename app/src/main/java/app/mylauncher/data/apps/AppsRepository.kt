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
import androidx.core.graphics.drawable.toBitmap
import app.mylauncher.core.FileLog
import app.mylauncher.data.prefs.SettingsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
    private val iconCache = LruCache<String, ImageBitmap>(128)
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

    /** Ícone do app em [sizePx], com cache. Chamar fora da thread principal quando possível. */
    fun icon(pkg: String, sizePx: Int = 112): ImageBitmap? {
        val entry = find(pkg)
        val key = "${entry?.key ?: "$pkg/"}@$sizePx"
        iconCache.get(key)?.let { return it }
        val drawable = runCatching {
            if (entry != null) {
                launcherApps.getActivityList(pkg, entry.user)
                    .firstOrNull { it.componentName == entry.component }
                    ?.getBadgedIcon(0)
            } else {
                context.packageManager.getApplicationIcon(pkg)
            }
        }.getOrNull() ?: return null
        val bmp = drawable.toBitmap(sizePx, sizePx).asImageBitmap()
        iconCache.put(key, bmp)
        return bmp
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
        /** Busca sem acento e sem diferenciar maiúsculas (R3.4). */
        fun normalize(s: String): String =
            Normalizer.normalize(s, Normalizer.Form.NFD).replace(Regex("\\p{Mn}+"), "").lowercase(Locale.ROOT)

        fun filter(apps: List<AppEntry>, query: String, hidden: Set<String>): List<AppEntry> {
            val q = normalize(query.trim())
            return apps.filter { it.packageName !in hidden && (q.isEmpty() || normalize(it.label).contains(q)) }
        }
    }
}
