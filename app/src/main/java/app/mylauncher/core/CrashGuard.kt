package app.mylauncher.core

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Process
import kotlin.system.exitProcess

/**
 * Proteção contra loop de crash (spec 001, R1.7): se o launcher cair 2 vezes em 60 s,
 * abre o launcher original da multimídia em vez de deixar a tela inicial quebrada.
 */
object CrashGuard {
    const val WINDOW_MS = 60_000L
    const val MAX_CRASHES = 2
    val FALLBACK_HOME = ComponentName("com.dofun.variety", "com.dofun.overseasvariety.Launcher")

    private const val PREFS = "crash_guard"
    private const val KEY = "crash_times"

    fun install(context: Context) {
        val app = context.applicationContext
        val previous = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { thread, error ->
            FileLog.e("Crash não tratado em ${thread.name}", error)
            val times = record(app, System.currentTimeMillis())
            if (shouldFallBack(times, System.currentTimeMillis())) {
                FileLog.w("CrashGuard: ${times.size} crashes em ${WINDOW_MS / 1000}s, abrindo launcher original")
                clear(app)
                openFallback(app)
                Process.killProcess(Process.myPid())
                exitProcess(10)
            }
            previous?.uncaughtException(thread, error)
        }
    }

    /** Regra pura, testável: há [MAX_CRASHES] crashes dentro da janela? */
    fun shouldFallBack(times: List<Long>, now: Long): Boolean =
        times.count { now - it <= WINDOW_MS } >= MAX_CRASHES

    fun openFallback(context: Context): Boolean = runCatching {
        context.startActivity(
            Intent(Intent.ACTION_MAIN)
                .addCategory(Intent.CATEGORY_HOME)
                .setComponent(FALLBACK_HOME)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        )
        true
    }.getOrElse {
        FileLog.e("Não foi possível abrir o launcher original", it)
        false
    }

    private fun record(context: Context, now: Long): List<Long> {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val times = (prefs.getString(KEY, "") ?: "")
            .split(',').mapNotNull { it.toLongOrNull() }
            .filter { now - it <= WINDOW_MS } + now
        // commit() síncrono: o processo vai morrer logo em seguida.
        prefs.edit().putString(KEY, times.joinToString(",")).commit()
        return times
    }

    private fun clear(context: Context) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit().remove(KEY).commit()
    }
}
