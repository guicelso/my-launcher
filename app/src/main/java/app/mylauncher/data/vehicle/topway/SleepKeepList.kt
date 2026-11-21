package app.mylauncher.data.vehicle.topway

import android.os.Environment
import app.mylauncher.core.FileLog
import java.io.File

/**
 * Lista de apps que a TopWay NÃO fecha ao entrar em espera (spec 016, R16.1/R16.2).
 *
 * Ao tirar a chave, `TWService.killPackages` faz `forceStopPackage` em todos os apps instalados,
 * exceto uma lista fixa (DoFun, `com.android.launcher`, serviços `com.tw.*`…) e os pacotes deste
 * arquivo ("mDiskillPackageList"), relido a cada desligamento. Ficar fora dele fazia o launcher
 * ser fechado e a tela de recentes aparecer ao religar.
 */
object SleepKeepList {
    private const val DIR = ".Tcfg"
    private const val FILE = "sleepKillPackage.cfg"

    private fun file(): File = File(File(Environment.getExternalStorageDirectory(), DIR), FILE)

    /** Só existe em multimídias TopWay (a pasta guarda a configuração do fabricante). */
    fun available(): Boolean = file().parentFile?.isDirectory == true

    /** Pacotes da lista hoje (vazia se o arquivo não existir). */
    fun read(): List<String> = runCatching {
        val f = file()
        if (f.isFile) parse(f.readText()) else emptyList()
    }.getOrDefault(emptyList())

    /**
     * Garante [packages] na lista, preservando as linhas que já estão lá.
     * @return true se o arquivo foi alterado.
     */
    fun ensure(packages: Collection<String>): Boolean {
        if (!available()) return false
        val current = read()
        val merged = merge(current, packages)
        if (merged == current) return false
        return runCatching {
            val f = file()
            val tmp = File(f.parentFile, "$FILE.tmp")
            tmp.writeText(merged.joinToString("\n", postfix = "\n"))
            if (!tmp.renameTo(f)) {
                f.writeText(tmp.readText())
                tmp.delete()
            }
            FileLog.i("Lista de apps mantidos na espera (TopWay): $merged")
            true
        }.getOrElse {
            FileLog.w("Não foi possível atualizar $FILE: ${it.message}")
            false
        }
    }

    /** Uma linha por pacote; ignora linhas vazias e espaços (o TWService compara a linha inteira). */
    internal fun parse(text: String): List<String> =
        text.lineSequence().map { it.trim() }.filter { it.isNotEmpty() }.distinct().toList()

    internal fun merge(current: List<String>, wanted: Collection<String>): List<String> =
        (current + wanted.map { it.trim() }.filter { it.isNotEmpty() }).distinct()
}
