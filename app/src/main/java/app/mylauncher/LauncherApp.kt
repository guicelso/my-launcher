package app.mylauncher

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.UserManager
import android.util.Log
import app.mylauncher.core.CrashGuard
import app.mylauncher.core.FileLog
import app.mylauncher.nav.MapStyles
import org.maplibre.android.MapLibre

class LauncherApp : Application() {
    private var started: AppContainer? = null

    /**
     * Dependências do app. Só existem com o usuário desbloqueado: o listener de notificações é
     * `directBootAware` (spec 016) e pode criar o processo antes disso, quando os arquivos do app
     * (DataStore, logs, cache do mapa) ainda não estão acessíveis. As activities nunca rodam antes
     * do desbloqueio, então acessar o container nelas é sempre seguro.
     */
    val container: AppContainer get() = started ?: initialize()

    override fun onCreate() {
        super.onCreate()
        if (getSystemService(UserManager::class.java).isUserUnlocked) {
            initialize()
        } else {
            Log.i("MyLauncher", "Processo criado antes do desbloqueio (listener de notificações); inicialização adiada")
            registerReceiver(
                object : BroadcastReceiver() {
                    override fun onReceive(context: Context, intent: Intent) {
                        unregisterReceiver(this)
                        initialize()
                    }
                },
                IntentFilter(Intent.ACTION_USER_UNLOCKED),
            )
        }
    }

    @Synchronized
    private fun initialize(): AppContainer {
        started?.let { return it }
        FileLog.init(this)
        CrashGuard.install(this)
        FileLog.i("LauncherApp iniciado (versão ${BuildConfig.VERSION_NAME})")
        MapLibre.getInstance(this)
        MapStyles.configureCache(this)
        return AppContainer(this).also {
            started = it
            it.start()
        }
    }
}
