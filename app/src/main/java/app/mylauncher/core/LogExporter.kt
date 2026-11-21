package app.mylauncher.core

import android.content.Context
import android.os.Process
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Exporta o log do app e o logcat do processo (spec 007, R7.7) para
 * /sdcard/Android/data/app.mylauncher/files/logs/, legível via `adb pull` sem permissão extra.
 */
object LogExporter {
    fun export(context: Context): Result<File> = runCatching {
        val dir = File(context.getExternalFilesDir(null), "logs").apply { mkdirs() }
        val stamp = SimpleDateFormat("yyyyMMdd-HHmmss", Locale.US).format(Date())
        FileLog.logFile?.takeIf { it.exists() }?.copyTo(File(dir, "app-$stamp.log"), overwrite = true)
        val logcat = File(dir, "logcat-$stamp.txt")
        val proc = ProcessBuilder("logcat", "-d", "-v", "time", "--pid=${Process.myPid()}")
            .redirectErrorStream(true)
            .redirectOutput(logcat)
            .start()
        proc.waitFor()
        FileLog.i("Logs exportados para ${dir.absolutePath}")
        dir
    }
}
