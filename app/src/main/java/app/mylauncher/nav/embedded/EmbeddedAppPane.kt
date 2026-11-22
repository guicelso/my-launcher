package app.mylauncher.nav.embedded

import android.graphics.Bitmap
import android.graphics.SurfaceTexture
import android.os.SystemClock
import android.view.Surface
import android.view.TextureView
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import kotlinx.coroutines.launch
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntSize
import kotlinx.coroutines.delay
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import app.mylauncher.core.FileLog
import app.mylauncher.nav.LocalMapTargetSize
import app.mylauncher.nav.PaneMode
import app.mylauncher.ui.components.TouchIconButton
import app.mylauncher.ui.icons.LauncherIcons
import app.mylauncher.ui.theme.LocalLauncherColors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.util.concurrent.atomic.AtomicInteger

/**
 * Painel que embute o app de navegação dentro do card (spec 008).
 *
 * O display virtual tem o tamanho **visível** do card (ou da tela cheia), para o app de navegação
 * se organizar nele, como no Android Auto. Redimensionar a cada quadro da animação é pesado demais
 * (cada resize é uma mudança de configuração no app), então o card **revela** o mapa:
 *  - ao crescer, o display vai já no início para o tamanho final ([LocalMapTargetSize]) e o card,
 *    crescendo, mostra cada vez mais dele;
 *  - ao encolher, o display fica grande até o card assentar ([SETTLE_MS]) e só então encolhe.
 * A superfície fica centrada no card, então o centro do mapa acompanha o centro do card.
 * Redimensionar não reinicia o app (R8.3).
 *
 * Ao crescer, o app leva ~0,5 s para se redesenhar no tamanho novo; até lá o display mostra o app no
 * tamanho antigo com uma faixa preta (medido no carro com `screenrecord`). Por isso o card cobre a
 * superfície com o último quadro (a "capa") até chegar um quadro sem preto (R8.10).
 *
 * Se o auxiliar cair, ele é reiniciado; só se isso falhar, [fallback] (o mapa próprio) é mostrado no
 * lugar (R8.1). Ao voltar ao primeiro plano ou ligar a chave ([wake]), o auxiliar é conferido (R8.9).
 */
class EmbeddedAppPane(
    private val controller: EmbeddedNavController,
    private val wake: Flow<Unit>,
) {

    @Composable
    fun Content(
        pkg: String,
        startUri: String?,
        mode: PaneMode,
        modifier: Modifier,
        active: Boolean,
        onExpand: () -> Unit,
        fallback: @Composable () -> Unit,
    ) {
        val c = LocalLauncherColors.current
        val context = LocalContext.current
        val density = LocalDensity.current

        // Tamanho do card (muda a cada quadro nas animações), tamanho final dele e o da superfície.
        var paneSize by remember { mutableStateOf(IntSize.Zero) }
        var surfaceSize by remember { mutableStateOf(IntSize.Zero) }
        val targetDp = LocalMapTargetSize.current
        val target = targetDp?.let { with(density) { IntSize(it.width.roundToPx(), it.height.roundToPx()) } }
        // Capa com o último quadro, enquanto o app se redesenha no tamanho novo (R8.10).
        var cover by remember { mutableStateOf<Cover?>(null) }
        val coverAlpha = remember { Animatable(1f) }
        // Quadros recebidos desde o último redimensionamento do display.
        val framesSinceResize = remember { AtomicInteger(0) }

        var failed by remember(pkg) { mutableStateOf(false) }
        var started by remember(pkg) { mutableStateOf(false) }
        val starting = remember(pkg) { java.util.concurrent.atomic.AtomicBoolean(false) }
        val currentActive by rememberUpdatedState(active)
        val scope = rememberCoroutineScope()
        // TextureView, não SurfaceView: ao mudar de tamanho ela mantém o último quadro na tela até
        // o próximo chegar (a SurfaceView recriava os buffers e o mapa piscava em preto a cada
        // card ⇄ tela cheia) e respeita o recorte arredondado do card. Mesma escolha do mapa próprio.
        val textureView = remember(pkg) {
            TextureView(context).apply {
                setOnTouchListener { _, ev ->
                    if (controller.isRunning) controller.forwardTouch(ev)
                    true
                }
            }
        }

        // Começo da transição: se o destino é maior, o display já cresce até ele (o card revela).
        LaunchedEffect(target) {
            val t = target ?: return@LaunchedEffect
            if (t.width <= 0 || t.height <= 0 || surfaceSize == IntSize.Zero) return@LaunchedEffect
            val grown = IntSize(maxOf(surfaceSize.width, t.width), maxOf(surfaceSize.height, t.height))
            if (grown == surfaceSize) return@LaunchedEffect
            if (started && currentActive && cover == null) {
                cover = Cover.capture(textureView)
                coverAlpha.snapTo(1f)
            }
            surfaceSize = grown
        }
        // Tira a capa quando o app já se redesenhou no tamanho novo (ou no tempo máximo).
        LaunchedEffect(cover) {
            val c = cover ?: return@LaunchedEffect
            val t0 = SystemClock.uptimeMillis()
            delay(COVER_MIN_MS)
            val probe = Bitmap.createBitmap(PROBE_W, PROBE_H, Bitmap.Config.ARGB_8888)
            var fresh = false
            while (SystemClock.uptimeMillis() - t0 < COVER_MAX_MS) {
                if (framesSinceResize.get() >= 2 && isFreshFrame(textureView, probe)) { fresh = true; break }
                delay(COVER_POLL_MS)
            }
            probe.recycle()
            FileLog.i("EmbeddedNav: capa retirada em ${SystemClock.uptimeMillis() - t0}ms" + if (fresh) "" else " (tempo máximo)")
            coverAlpha.animateTo(0f, tween(COVER_FADE_MS))
            cover = null
            c.bitmap.recycle()
        }
        // Fim da transição: o card parou de mudar → o display assume o tamanho exato dele.
        LaunchedEffect(paneSize) {
            if (paneSize == IntSize.Zero) return@LaunchedEffect
            if (surfaceSize != IntSize.Zero) delay(SETTLE_MS)
            surfaceSize = paneSize
        }

        // Auxiliar caiu (ou não respondeu): reinicia, até RESTART_MAX vezes por minuto (R8.1).
        val restarts = remember(pkg) { ArrayDeque<Long>() }
        val onHelperLost: () -> Unit = {
            scope.launch {
                if (failed || !started) return@launch
                val now = SystemClock.elapsedRealtime()
                while (restarts.isNotEmpty() && now - restarts.first() > RESTART_WINDOW_MS) restarts.removeFirst()
                if (restarts.size >= RESTART_MAX) {
                    FileLog.w("EmbeddedNav: auxiliar caiu ${restarts.size}x em 1 min; usando o mapa próprio")
                    failed = true
                    return@launch
                }
                restarts.addLast(now)
                if (!withContext(Dispatchers.IO) { controller.restartHelper() }) failed = true
            }
        }
        val checkHelper: () -> Unit = {
            scope.launch {
                if (started && !failed && !withContext(Dispatchers.IO) { controller.ping() }) onHelperLost()
            }
        }
        // Volta ao primeiro plano e chave ligada: o auxiliar pode ter morrido na espera (R8.9).
        val lifecycle = LocalLifecycleOwner.current.lifecycle
        DisposableEffect(lifecycle, pkg) {
            val obs = LifecycleEventObserver { _, e -> if (e == Lifecycle.Event.ON_START) checkHelper() }
            lifecycle.addObserver(obs)
            onDispose { lifecycle.removeObserver(obs) }
        }
        LaunchedEffect(pkg) { wake.collect { checkHelper() } }

        if (failed) {
            fallback()
            return
        }
        // A Surface ligada à SurfaceTexture atual (estável enquanto a textura existir).
        val surfaceRef = remember(pkg) { arrayOfNulls<Surface>(1) }

        DisposableEffect(textureView, pkg) {
            val listener = object : TextureView.SurfaceTextureListener {
                override fun onSurfaceTextureAvailable(st: SurfaceTexture, width: Int, height: Int) {
                    val surface = Surface(st)
                    surfaceRef[0] = surface
                    when {
                        controller.isRunning -> {
                            controller.resize(width, height)
                            if (currentActive) controller.setSurface(surface)
                        }
                        !started && !failed && starting.compareAndSet(false, true) -> {
                            scope.launch {
                                val ok = withContext(Dispatchers.IO) { controller.start(surface, width, height, pkg, startUri) }
                                if (ok) {
                                    // O card pode ter mudado de tamanho enquanto o auxiliar subia.
                                    val w = textureView.width
                                    val h = textureView.height
                                    if (w > 0 && h > 0 && (w != width || h != height)) controller.resize(w, h)
                                    controller.onUnavailable = { onHelperLost() }
                                    started = true
                                } else {
                                    failed = true
                                }
                                starting.set(false)
                            }
                        }
                    }
                }

                // A TextureView já ajusta o tamanho do buffer; aqui só o display acompanha.
                override fun onSurfaceTextureSizeChanged(st: SurfaceTexture, width: Int, height: Int) {
                    framesSinceResize.set(0)
                    if (controller.isRunning) controller.resize(width, height)
                }

                override fun onSurfaceTextureDestroyed(st: SurfaceTexture): Boolean {
                    if (controller.isRunning) controller.setSurface(null)
                    surfaceRef[0]?.release()
                    surfaceRef[0] = null
                    return true
                }

                override fun onSurfaceTextureUpdated(st: SurfaceTexture) {
                    framesSinceResize.incrementAndGet()
                }
            }
            textureView.surfaceTextureListener = listener
            onDispose {
                textureView.surfaceTextureListener = null
                controller.stop()
                surfaceRef[0]?.release()
                surfaceRef[0] = null
                started = false
            }
        }

        // Pausa/retoma conforme o card está ativo (tela por cima, mídia em tela cheia…).
        LaunchedEffect(active, started) {
            if (!started) return@LaunchedEffect
            controller.setSurface(if (currentActive) surfaceRef[0] else null)
        }

        Box(modifier.onSizeChanged { paneSize = it }) {
            if (surfaceSize != IntSize.Zero) {
                AndroidView(
                    factory = { textureView },
                    // Centrada: durante a animação o card cresce/encolhe em volta dela.
                    modifier = Modifier.align(Alignment.Center).requiredSize(
                        with(density) { DpSize(surfaceSize.width.toDp(), surfaceSize.height.toDp()) },
                    ),
                )
            }

            cover?.let { c ->
                // Último quadro no tamanho original, centrado como a superfície; em volta, a cor média
                // dele (em vez do preto) enquanto o card cresce.
                Box(Modifier.matchParentSize().graphicsLayer { alpha = coverAlpha.value }.background(c.average)) {
                    Image(
                        c.image, contentDescription = null,
                        modifier = Modifier.align(Alignment.Center).requiredSize(
                            with(density) { DpSize(c.bitmap.width.toDp(), c.bitmap.height.toDp()) },
                        ),
                        contentScale = ContentScale.FillBounds,
                    )
                }
            }

            if (!started) {
                Column(
                    Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    CircularProgressIndicator(color = c.primary)
                    Text("Abrindo o mapa…", style = MaterialTheme.typography.titleMedium, color = c.onSurfaceVariant)
                }
            }

            // Controles mínimos por cima do app embutido.
            if (mode == PaneMode.CARD) {
                // No meio da borda direita: os cantos são do app embutido (busca, camadas, rotas).
                TouchIconButton(
                    LauncherIcons.Expand, "Mapa em tela cheia", onExpand,
                    Modifier.align(Alignment.CenterEnd).padding(16.dp),
                    background = c.mapPanel, tint = c.onSurface,
                )
            } else {
                TouchIconButton(
                    LauncherIcons.Back, "Voltar ao painel", onExpand,
                    Modifier.align(Alignment.CenterStart).padding(16.dp),
                    background = c.mapPanel, tint = c.onSurface,
                )
            }
        }
    }

    /** Último quadro da superfície e a cor média dele. */
    private class Cover(val bitmap: Bitmap, val image: ImageBitmap, val average: Color) {
        companion object {
            fun capture(view: TextureView): Cover? {
                if (!view.isAvailable || view.width <= 0 || view.height <= 0) return null
                val bmp = runCatching { view.bitmap }.getOrNull() ?: return null
                return Cover(bmp, bmp.asImageBitmap(), runCatching { average(bmp) }.getOrDefault(Color.Black))
            }

            /**
             * Cor média numa grade de 16×16 pontos. (Reduzir o quadro para 1×1 amostra só o centro,
             * onde fica o marcador azul de localização: a borda saía azul forte, visto no carro.)
             */
            private fun average(bmp: Bitmap): Color {
                var r = 0L; var g = 0L; var b = 0L; var n = 0
                for (iy in 0 until GRID) for (ix in 0 until GRID) {
                    val p = bmp.getPixel((ix * 2 + 1) * bmp.width / (GRID * 2), (iy * 2 + 1) * bmp.height / (GRID * 2))
                    r += (p shr 16) and 0xff; g += (p shr 8) and 0xff; b += p and 0xff; n++
                }
                return Color((r / n).toInt(), (g / n).toInt(), (b / n).toInt())
            }

            private const val GRID = 16
        }
    }

    private companion object {
        /** Espera o card parar de mudar de tamanho antes de redimensionar o display. */
        const val SETTLE_MS = 250L
        const val COVER_MIN_MS = 120L
        const val COVER_MAX_MS = 1500L
        const val COVER_POLL_MS = 50L
        const val COVER_FADE_MS = 180
        const val PROBE_W = 48
        const val PROBE_H = 27
        const val RESTART_MAX = 3
        const val RESTART_WINDOW_MS = 60_000L

        /**
         * O quadro atual já é o app redesenhado no tamanho novo? Enquanto não, parte do display fica
         * preta (o app ainda ocupa só o tamanho antigo). Amostra reduzida: poucos ms na GPU.
         */
        fun isFreshFrame(view: TextureView, probe: Bitmap): Boolean {
            if (!view.isAvailable) return true
            runCatching { view.getBitmap(probe) }.onFailure { return true }
            var black = 0
            for (y in 0 until probe.height) for (x in 0 until probe.width) {
                val p = probe.getPixel(x, y)
                val m = maxOf((p shr 16) and 0xff, (p shr 8) and 0xff, p and 0xff)
                if (m < BLACK_MAX) black++
            }
            return black <= probe.width * probe.height * BLACK_TOLERANCE
        }

        const val BLACK_MAX = 10
        const val BLACK_TOLERANCE = 0.03f
    }
}
