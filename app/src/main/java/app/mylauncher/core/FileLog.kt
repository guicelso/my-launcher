package app.mylauncher.core

import android.content.Context
import android.util.Log
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Log simples em arquivo (filesDir/logs/app.log), limitado a ~1 MB, para exportação
 * pela tela de Ajustes → Sistema (spec 007). Também espelha no logcat.
 */
object FileLog {
    private const val TAG = "MyLauncher"
    private const val MAX_BYTES = 1_000_000L
    private val fmt = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US)
    @Volatile private var file: File? = null

    fun init(context: Context) {
        val dir = File(context.filesDir, "logs").apply { mkdirs() }
        file = File(dir, "app.log")
    }

    val logFile: File? get() = file

    fun i(msg: String) = write("I", msg, null)
    fun w(msg: String, t: Throwable? = null) = write("W", msg, t)
    fun e(msg: String, t: Throwable? = null) = write("E", msg, t)

    @Synchronized
    private fun write(level: String, msg: String, t: Throwable?) {
        when (level) {
            "E" -> Log.e(TAG, msg, t)
            "W" -> Log.w(TAG, msg, t)
            else -> Log.i(TAG, msg)
        }
        val f = file ?: return
        runCatching {
            if (f.length() > MAX_BYTES) {
                val old = File(f.parentFile, "app.log.1")
                old.delete()
                f.renameTo(old)
            }
            f.appendText(buildString {
                append(fmt.format(Date())).append(' ').append(level).append(' ').append(msg).append('\n')
                if (t != null) append(Log.getStackTraceString(t)).append('\n')
            })
        }
    }
}
