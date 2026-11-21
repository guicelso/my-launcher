package app.mylauncher.data.vehicle.topway

import android.content.Context
import app.mylauncher.core.FileLog
import java.io.File
import java.lang.reflect.Method

/**
 * Testes manuais (botões na tela de diagnóstico), nunca automáticos:
 * - Spike S6.1/C5: um app comum consegue abrir `android.tw.john.TWUtil`?
 * - Spike S8.1: `TWUtil.write(0x9F1A, 1, 0, cmd)` executa um comando como root?
 * Ver docs/00-discovery.md §4.2 e specs 006/008.
 */
object TwUtilProbe {
    private const val CLASS = "android.tw.john.TWUtil"
    const val ROOT_SHELL = 0x9F1A
    const val HEADLIGHT = 0x0204

    /** S6.1: tenta `new TWUtil().open(short[]{0x0204})` e fecha em seguida. */
    fun probeOpen(): String = runCatching {
        val cls = Class.forName(CLASS)
        val util = cls.getConstructor().newInstance()
        val open = cls.getMethod("open", ShortArray::class.java)
        val result = open.invoke(util, shortArrayOf(HEADLIGHT.toShort())) as Int
        runCatching { cls.getMethod("close").invoke(util) }
        "TWUtil.open() = $result (${if (result == 0) "sucesso" else "falhou"})"
    }.getOrElse { "TWUtil indisponível: ${it.javaClass.simpleName}: ${it.cause?.message ?: it.message}" }
        .also { FileLog.i("Spike S6.1: $it") }

    /**
     * S8.1: pede ao serviço da MCU para rodar `id` e gravar o resultado num arquivo que o app consegue ler.
     * Só lê informação (nenhuma alteração no sistema).
     */
    fun probeRootShell(context: Context): String {
        val out = File(context.getExternalFilesDir(null), "s81_id.txt")
        out.delete()
        return runCatching {
            val cls = Class.forName(CLASS)
            val util = cls.getConstructor().newInstance()
            val write = findWrite(cls) ?: return "TWUtil.write(int,int,int,Object) não encontrado"
            write.invoke(util, ROOT_SHELL, 1, 0, "id > ${out.absolutePath}")
            Thread.sleep(1_500)
            if (out.exists()) "Resultado: ${out.readText().trim()}" else "Comando enviado, mas nenhum arquivo foi criado"
        }.getOrElse { "Falhou: ${it.javaClass.simpleName}: ${it.cause?.message ?: it.message}" }
            .also { FileLog.i("Spike S8.1: $it") }
    }

    private fun findWrite(cls: Class<*>): Method? = cls.methods.firstOrNull { m ->
        m.name == "write" && m.parameterTypes.size == 4 &&
            m.parameterTypes[0] == Int::class.javaPrimitiveType &&
            m.parameterTypes[1] == Int::class.javaPrimitiveType &&
            m.parameterTypes[2] == Int::class.javaPrimitiveType
    }
}
