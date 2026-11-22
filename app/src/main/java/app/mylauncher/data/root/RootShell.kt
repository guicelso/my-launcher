package app.mylauncher.data.root

import android.content.Context
import android.net.LocalSocket
import android.net.LocalSocketAddress
import app.mylauncher.core.FileLog
import java.io.File

/**
 * Execução de comandos como **root** pela porta dos fundos do fabricante (spec 008,
 * "Root: como conseguimos"; `docs/00-discovery.md` §4.2).
 *
 * Mecanismo (confirmado no carro em 2026-11-22):
 * 1. O serviço de init `cmd_services` (roda como root, `oneshot`, desligado) é ligado pela
 *    propriedade [PROP]. Como é `oneshot`, cada uso precisa de uma transição `disable`→`enable`.
 * 2. O daemon escuta no socket Unix **abstrato** [SOCKET]. Qualquer processo conecta
 *    (socket abstrato + SELinux permissivo).
 * 3. Protocolo (igual ao `com.sprd.engineermode`): escrever `[origem][tid]<comando\n>`,
 *    ler a resposta. O daemon roda o comando com `popen`.
 *
 * Particularidades do daemon (medidas no carro, spec 008 → Resultados):
 *  - roda `sh -c` com a linha inteira, prefixo incluído → o primeiro comando é engolido ([GUARD]);
 *  - lê em blocos de 256 bytes e roda cada bloco separado → comandos longos vão por script ([writeScript]);
 *  - a saída não volta pelo socket → [exec] lê de um arquivo;
 *  - o `stop` do serviço mata o cgroup inteiro e o filho herda o socket dele → ver [execDetached].
 *
 * Tudo aqui é tolerante a falha: se não houver root, os métodos devolvem `null`/`false` e o
 * recurso que depende disso cai para a alternativa (ex.: mapa próprio).
 */
object RootShell {
    private const val PROP = "persist.sys.cmdservice.enable"
    private const val PROP_TEST = "sys.lumia.roottest"
    private const val SOCKET = "cmd_skt"
    private const val GUARD = "/dev/null; "
    private const val MAX_WIRE = 255
    private const val HOLD_MS = 400L

    @Volatile private var cachedAvailable: Boolean? = null
    @Volatile private var appContext: Context? = null

    /** Chamado uma vez no [app.mylauncher.LauncherApp] para permitir ler a saída dos comandos. */
    fun init(context: Context) { appContext = context.applicationContext }

    /**
     * `true` se o daemon roda de fato nossos comandos como root. Resultado fica em cache.
     *
     * Confirma por ida-e-volta de propriedade (robusto: props são globais, sem depender de ler
     * arquivo/saída entre uids): o root seta `sys.lumia.roottest=<token>` e o app lê de volta.
     */
    @Synchronized // um comando por vez: o disable→enable de um mataria o comando do outro
    fun isAvailable(forceRecheck: Boolean = false): Boolean {
        if (!forceRecheck) cachedAvailable?.let { return it }
        val ok = runCatching {
            if (!enableDaemon()) return@runCatching false
            val token = "r${System.nanoTime()}"
            runCatching { send("setprop $PROP_TEST $token") }
            repeat(20) {
                if (getProp(PROP_TEST).trim() == token) return@runCatching true
                Thread.sleep(100)
            }
            false
        }.getOrDefault(false)
        cachedAvailable = ok
        FileLog.i("RootShell.isAvailable = $ok")
        return ok
    }

    /**
     * Roda [cmd] como root e devolve a saída (stdout+stderr), ou `null` se falhar.
     *
     * A saída do daemon não volta de forma confiável pelo socket (vai para um arquivo interno dele),
     * então o script redireciona para um arquivo na pasta **interna** do app e passa o dono para o
     * uid do app. A pasta externa não serve: ela é FUSE e o arquivo criado pelo root não aparece
     * para o app. Síncrono: use para comandos rápidos. Para processos longos use [execDetached].
     */
    @Synchronized
    fun exec(cmd: String, timeoutMs: Int = 4000): String? {
        val ctx = appContext ?: return null
        if (!enableDaemon()) return null
        val out = File(ctx.filesDir, "root_out_${System.nanoTime()}.txt")
        val tmp = File(out.path + ".tmp")
        var script: File? = null
        return runCatching {
            // Escreve num .tmp e só renomeia no fim: o app não lê o arquivo pela metade.
            script = writeScript(ctx, "{\n$cmd\n} > ${tmp.path} 2>&1\nchown ${ctx.applicationInfo.uid} ${tmp.path}\nmv ${tmp.path} ${out.path}\n")
            send("sh ${script!!.path}")
            var waited = 0
            while (!out.exists() && waited < timeoutMs) { Thread.sleep(50); waited += 50 }
            val res = if (out.exists()) out.readText().trim() else null
            FileLog.i("RootShell.exec '${cmd.take(40)}' -> ${if (res != null) "ok (${res.take(60)})" else "sem saída"}")
            res
        }.onFailure { FileLog.w("RootShell.exec falhou: ${it.javaClass.simpleName}: ${it.message}") }
            .getOrNull()
            .also { runCatching { out.delete(); tmp.delete(); script?.delete() } }
    }

    /**
     * Inicia um processo longo (ex.: o auxiliar de navegação) em segundo plano, como root, e volta
     * sem esperar. [cmd] deve trazer seus próprios redirecionamentos de stdin/stdout/stderr.
     *
     * Dois cuidados, descobertos no carro (spec 008, "Resultados"):
     *  - **cgroup:** o init acompanha o serviço pelo cgroup `cpuacct:/uid_0/pid_<daemon>` e, no
     *    `stop cmd_services` (o `disable` de [enableDaemon]), mata **todo** o cgroup. `setsid` não
     *    escapa disso. Por isso o shell se move antes para o cgroup raiz (`echo 0 > /acct/cgroup.procs`,
     *    onde 0 = o próprio processo); o filho herda o cgroup novo.
     *  - **fds herdados:** o filho herdaria o socket de escuta do daemon (fd 3) e as conexões dos
     *    clientes. Com o socket abstrato preso no filho, o próximo `cmd_services` não consegue subir
     *    ("Address already in use") e o root fica indisponível até o filho morrer. Fechamos 3–9.
     */
    @Synchronized
    fun execDetached(cmd: String): Boolean {
        val ctx = appContext ?: return false
        if (!enableDaemon()) return false
        return runCatching {
            val script = File(ctx.filesDir, "root_detached.sh")
            // O script se apaga e troca o shell pelo processo final (exec).
            writeScript(ctx, "rm -f ${script.path}\nexec $cmd\n", script)
            send(
                "echo 0 > /acct/cgroup.procs; setsid sh ${script.path} </dev/null >/dev/null 2>&1 " +
                    "3>&- 4>&- 5>&- 6>&- 7>&- 8>&- 9>&- &",
            )
            true
        }.onFailure { FileLog.w("RootShell.execDetached falhou: ${it.message}") }.getOrDefault(false)
    }

    /**
     * Grava o script que o root vai rodar. O daemon lê o comando em blocos de **256 bytes** e roda
     * cada bloco como um comando separado, então comandos longos (ex.: caminho do APK) são cortados
     * no meio. Com o script, o que vai pelo socket é só `sh <arquivo>`, e o script não tem limite de
     * tamanho nem restrição de aspas. O app grava na própria pasta interna; o root lê sem problema.
     */
    private fun writeScript(ctx: Context, body: String, file: File = File(ctx.filesDir, "root_job_${System.nanoTime()}.sh")): File {
        file.writeText(body)
        file.setReadable(true, false)
        return file
    }

    /** Liga o `cmd_services` com uma transição disable→enable (necessária por ser `oneshot`). */
    private fun enableDaemon(): Boolean {
        if (!setProp("disable") || !setProp("enable")) {
            FileLog.w("RootShell: não consegui setar $PROP (app sem permissão de setprop?)")
            return false
        }
        // Espera o init subir o daemon (confirma por init.svc), até ~2 s.
        repeat(20) {
            if (getProp("init.svc.cmd_services").trim() == "running") return true
            Thread.sleep(100)
        }
        val state = getProp("init.svc.cmd_services").trim()
        FileLog.w("RootShell: cmd_services não subiu (init.svc=$state)")
        // Mesmo sem confirmação, tenta conectar: o daemon pode ser oneshot e já ter saído.
        return true
    }

    private fun getProp(name: String): String = runCatching {
        val p = ProcessBuilder("getprop", name).redirectErrorStream(true).start()
        val out = p.inputStream.bufferedReader().readText()
        p.waitFor()
        out
    }.getOrDefault("")

    /** Tenta setar a propriedade via `setprop` (funciona com SELinux permissivo) e, como reserva, por reflexão. */
    private fun setProp(value: String): Boolean {
        // 1) binário setprop (contexto untrusted_app, permitido porque o SELinux é permissivo)
        runCatching {
            val p = ProcessBuilder("setprop", PROP, value).redirectErrorStream(true).start()
            p.waitFor()
            if (p.exitValue() == 0) return true
        }
        // 2) android.os.SystemProperties.set por reflexão (pode ser bloqueado pela hidden-API no Android 10)
        return runCatching {
            val cls = Class.forName("android.os.SystemProperties")
            val set = cls.getMethod("set", String::class.java, String::class.java)
            set.invoke(null, PROP, value)
            true
        }.getOrDefault(false)
    }

    /**
     * Entrega [cmd] ao daemon e fecha a conexão, **sem ler resposta** (a saída não volta pelo socket).
     *
     * Medido no carro: fechar logo após escrever faz o daemon rodar um comando vazio (ele copia o
     * comando do buffer do cliente, que é limpo no `close`); por isso seguramos a conexão
     * [HOLD_MS]. Fechar depois disso não interrompe o comando. Ler a resposta, ao contrário, prendia
     * a thread por dezenas de segundos: o daemon só fecha quando o comando termina, e fechar um
     * `LocalSocket` de outra thread não desbloqueia o `read()`.
     */
    private fun send(cmd: String) {
        val socket = connectWithRetry()
        try {
            val tid = Thread.currentThread().id
            // O daemon roda `popen()` com a linha INTEIRA, prefixo incluído: `sh -c [lumia][tid]<cmd␤> 2>&1`.
            // O shell lê `[lumia][tid]<palavra` como "rodar [lumia][tid] com stdin vindo do arquivo
            // `palavra`", ou seja, o primeiro comando seria engolido. [GUARD] é esse primeiro comando
            // descartável. A linha final (`> 2>&1`) é inofensiva.
            val wire = "[lumia][$tid]<$GUARD$cmd\n>"
            // Limite do buffer de leitura do daemon (ver [writeScript]): acima disso o comando é partido.
            check(wire.toByteArray(Charsets.UTF_8).size <= MAX_WIRE) { "comando root longo demais (${wire.length} bytes)" }
            socket.outputStream.write(wire.toByteArray(Charsets.UTF_8))
            socket.outputStream.flush()
            Thread.sleep(HOLD_MS)
        } finally {
            runCatching { socket.close() }
        }
    }

    /**
     * O socket abstrato pode dar EAGAIN ("Try again") enquanto o daemon ainda não aceita.
     * Cada tentativa usa um socket novo (um LocalSocket só conecta uma vez).
     */
    private fun connectWithRetry(): LocalSocket {
        val addr = LocalSocketAddress(SOCKET, LocalSocketAddress.Namespace.ABSTRACT)
        var last: Exception? = null
        repeat(20) {
            val socket = LocalSocket()
            try {
                socket.connect(addr)
                return socket
            } catch (e: Exception) {
                last = e
                runCatching { socket.close() }
                Thread.sleep(150)
            }
        }
        throw last ?: java.io.IOException("connect falhou")
    }
}
