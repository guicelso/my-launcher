package app.mylauncher

import android.app.Application
import app.mylauncher.core.CrashGuard
import app.mylauncher.core.FileLog

class LauncherApp : Application() {
    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        FileLog.init(this)
        CrashGuard.install(this)
        FileLog.i("LauncherApp iniciado (versão ${BuildConfig.VERSION_NAME})")
        container = AppContainer(this).also { it.start() }
    }
}
