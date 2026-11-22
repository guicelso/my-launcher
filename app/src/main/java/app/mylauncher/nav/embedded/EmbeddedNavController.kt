package app.mylauncher.nav.embedded

import android.content.Context
import android.graphics.SurfaceTexture
import android.hardware.display.DisplayManager
import android.hardware.display.VirtualDisplay
import android.view.MotionEvent
import android.view.Surface
import app.mylauncher.core.FileLog
import app.mylauncher.data.root.RootShell
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStream
import android.net.LocalServerSocket
import android.net.LocalSocket
import java.nio.charset.StandardCharsets
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Orquestra a navegação embutida no lado do app (spec 008).
 *
 * Divisão de trabalho:
 *  - **Aqui (app comum):** cria e redimensiona o [VirtualDisplay] (API pública), hospeda o
 *    [LocalServerSocket] e repassa os toques do card.
 *  - **[NavHelper] (root, via [RootShell]):** abre o app de navegação no display e injeta os toques.
 *
 * Tolerante a falha: se não houver root ou o auxiliar não conectar, [start] devolve `false` e o
 * painel cai para o mapa próprio (R8.1).
 */
class EmbeddedNavController(private val context: Context) {

    private val io = Executors.newSingleThreadExecutor { r -> Thread(r, "embedded-nav") }
    /** Densidade do display do app embutido (ajuste "Tamanho da interface do mapa"). */
    @Volatile private var density = context.resources.displayMetrics.densityDpi

    private var virtualDisplay: VirtualDisplay? = null
    private var serverSocket: LocalServerSocket? = null
    private var helper: LocalSocket? = null
    private var helperOut: OutputStream? = null
    private var dummySurface: Surface? = null
    private var dummyTexture: SurfaceTexture? = null
    private var sizeW = 0
    private var sizeH = 0

    private val starting = AtomicBoolean(false)
    @Volatile var currentPkg: String? = null
        private set
    @Volatile var displayId: Int = -1
        private set

    /** Callback quando o auxiliar fica indisponível (caiu, sem root): o painel deve mostrar o mapa próprio. */
    var onUnavailable: (() -> Unit)? = null

    val isRunning: Boolean get() = virtualDisplay != null && helper != null

    /**
     * Sobe o display + auxiliar e abre [pkg]. Deve ser chamado fora da main thread.
     * @return true se tudo subiu.
     */
    fun start(surface: Surface, widthPx: Int, heightPx: Int, pkg: String, startUri: String? = null): Boolean {
        FileLog.i("EmbeddedNav.start chamado pkg=$pkg ${widthPx}x$heightPx surfaceValid=${surface.isValid}")
        if (widthPx <= 0 || heightPx <= 0) return false
        if (!starting.compareAndSet(false, true)) return isRunning
        try {
            // Não pré-checamos root: tentamos subir o auxiliar; se ele conectar de volta, o root
            // funcionou (prova fim-a-fim). Senão, caímos para o mapa próprio (R8.1).
            currentPkg = pkg
            createDisplay(surface, widthPx, heightPx)
            val name = SOCKET_PREFIX + System.nanoTime().toString(36)
            if (!openSocketAndHelper(name)) {
                stop()
                return false
            }
            send(if (startUri != null) "OPEN $pkg $startUri" else "LAUNCH $pkg")
            send("WATCH $pkg on")
            FileLog.i("EmbeddedNav: iniciado pkg=$pkg display=$displayId")
            return true
        } catch (t: Throwable) {
            FileLog.w("EmbeddedNav.start falhou: ${t.javaClass.simpleName}: ${t.message}")
            stop()
            return false
        } finally {
            starting.set(false)
        }
    }

    private fun createDisplay(surface: Surface, w: Int, h: Int) {
        val dm = context.getSystemService(Context.DISPLAY_SERVICE) as DisplayManager
        val flags = DisplayManager.VIRTUAL_DISPLAY_FLAG_PUBLIC or
            DisplayManager.VIRTUAL_DISPLAY_FLAG_OWN_CONTENT_ONLY or
            DisplayManager.VIRTUAL_DISPLAY_FLAG_PRESENTATION
        val vd = dm.createVirtualDisplay("lumia-nav", w, h, density, surface, flags)
        virtualDisplay = vd
        sizeW = w
        sizeH = h
        displayId = vd.display.displayId
    }

    private fun openSocketAndHelper(name: String): Boolean {
        val server = LocalServerSocket(name)
        serverSocket = server
        // O auxiliar conecta de volta neste socket (abstrato). Sobe como root via cmd_services.
        val apk = context.applicationInfo.sourceDir
        // Log do auxiliar no cache do app (fica com dono root, mas o app consegue apagar).
        val log = java.io.File(context.cacheDir, "navhelper.log").path
        val cmd = "env CLASSPATH=$apk app_process /system/bin ${NavHelper::class.java.name} " +
            "$name $displayId </dev/null >$log 2>&1"
        FileLog.i("EmbeddedNav: iniciando auxiliar: $cmd")
        if (!RootShell.execDetached(cmd)) {
            FileLog.w("EmbeddedNav: não consegui iniciar o auxiliar")
            return false
        }
        FileLog.i("EmbeddedNav: auxiliar disparado, aguardando conexão no socket $name")
        // Espera o auxiliar conectar, com timeout. accept() só desbloqueia fechando o server socket.
        val latch = java.util.concurrent.CountDownLatch(1)
        Thread({
            runCatching { server.accept() }.getOrNull()?.let { sock ->
                helper = sock
                helperOut = sock.outputStream
                listen(sock)
            }
            latch.countDown()
        }, "embedded-nav-accept").start()
        if (!latch.await(HELPER_TIMEOUT_MS, java.util.concurrent.TimeUnit.MILLISECONDS)) {
            FileLog.w("EmbeddedNav: auxiliar não conectou em ${HELPER_TIMEOUT_MS}ms")
            runCatching { server.close() } // desbloqueia o accept
            return false
        }
        return helper != null
    }

    /** Loop de leitura das respostas/logs do auxiliar, em thread própria. */
    private fun listen(sock: LocalSocket) {
        Thread({
            runCatching {
                val reader = BufferedReader(InputStreamReader(sock.inputStream, StandardCharsets.UTF_8))
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    FileLog.i("NavHelper> $line")
                }
            }
            FileLog.w("EmbeddedNav: auxiliar desconectou")
            if (virtualDisplay != null) onUnavailable?.invoke()
        }, "embedded-nav-listen").start()
    }

    /** Abre um deep link de navegação (ex.: google.navigation:q=…) dentro do card. */
    fun open(pkg: String, uri: String) = send("OPEN $pkg $uri")

    /** Redimensiona o display (card ↔ tela cheia) sem reiniciar o app (R8.3). */
    fun resize(widthPx: Int, heightPx: Int) {
        if (widthPx <= 0 || heightPx <= 0) return
        val vd = virtualDisplay ?: return
        if (widthPx == sizeW && heightPx == sizeH) return
        sizeW = widthPx
        sizeH = heightPx
        vd.resize(widthPx, heightPx, density)
    }

    /** Muda a densidade do display; aplicada na hora se ele já existe (o app não reinicia). */
    fun setDensity(dpi: Int) {
        if (dpi <= 0 || dpi == density) return
        density = dpi
        val vd = virtualDisplay ?: return
        if (sizeW > 0 && sizeH > 0) vd.resize(sizeW, sizeH, dpi)
    }

    fun setSurface(surface: Surface?) {
        val vd = virtualDisplay ?: return
        // Reatribuir a mesma surface reconfigura o display (um quadro em branco): só troca se mudou.
        if (surface != null && vd.surface === surface) return
        if (surface != null) {
            vd.surface = surface
        } else {
            // Sem surface, o display é tratado como desligado e o app dorme. Troca por uma surface
            // mínima para manter o app vivo enquanto o card está escondido (spec 008, ciclo de vida).
            vd.surface = ensureDummy()
        }
    }

    /** Repassa um toque do card para o display do app de navegação (R8.2). */
    fun forwardTouch(ev: MotionEvent) {
        val n = ev.pointerCount
        val sb = StringBuilder("TOUCH ")
            .append(ev.downTime).append(' ')
            .append(ev.eventTime).append(' ')
            .append(ev.action).append(' ')
            .append(n)
        for (p in 0 until n) {
            sb.append(' ').append(ev.getPointerId(p))
                .append(' ').append(ev.getX(p))
                .append(' ').append(ev.getY(p))
        }
        send(sb.toString())
    }

    fun sendBack() = send("KEY 4")

    private fun send(line: String) {
        val o = helperOut ?: return
        io.execute {
            runCatching {
                o.write((line + "\n").toByteArray(StandardCharsets.UTF_8))
                o.flush()
            }.onFailure { FileLog.w("EmbeddedNav.send falhou: ${it.message}") }
        }
    }

    private fun ensureDummy(): Surface {
        dummySurface?.let { return it }
        val tex = SurfaceTexture(false).apply { setDefaultBufferSize(1, 1) }
        val s = Surface(tex)
        dummyTexture = tex
        dummySurface = s
        return s
    }

    fun stop() {
        runCatching { send("STOP") }
        runCatching { helper?.close() }
        runCatching { serverSocket?.close() }
        runCatching { virtualDisplay?.release() }
        runCatching { dummySurface?.release() }
        runCatching { dummyTexture?.release() }
        helper = null
        helperOut = null
        serverSocket = null
        virtualDisplay = null
        dummySurface = null
        dummyTexture = null
        displayId = -1
        currentPkg = null
        FileLog.i("EmbeddedNav: parado")
    }

    private companion object {
        const val SOCKET_PREFIX = "lumia_nav_"
        const val HELPER_TIMEOUT_MS = 6000L
    }
}
