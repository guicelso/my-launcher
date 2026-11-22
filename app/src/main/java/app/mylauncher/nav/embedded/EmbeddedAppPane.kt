package app.mylauncher.nav.embedded

import android.graphics.SurfaceTexture
import android.view.Surface
import android.view.TextureView
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
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntSize
import kotlinx.coroutines.delay
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import app.mylauncher.nav.LocalMapTargetSize
import app.mylauncher.nav.PaneMode
import app.mylauncher.ui.components.TouchIconButton
import app.mylauncher.ui.icons.LauncherIcons
import app.mylauncher.ui.theme.LocalLauncherColors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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
 * Se o root/auxiliar não subir, [fallback] (o mapa próprio) é mostrado no lugar (R8.1).
 */
class EmbeddedAppPane(private val controller: EmbeddedNavController) {

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
        // Começo da transição: se o destino é maior, o display já cresce até ele (o card revela).
        LaunchedEffect(target) {
            val t = target ?: return@LaunchedEffect
            if (t.width <= 0 || t.height <= 0 || surfaceSize == IntSize.Zero) return@LaunchedEffect
            surfaceSize = IntSize(maxOf(surfaceSize.width, t.width), maxOf(surfaceSize.height, t.height))
        }
        // Fim da transição: o card parou de mudar → o display assume o tamanho exato dele.
        LaunchedEffect(paneSize) {
            if (paneSize == IntSize.Zero) return@LaunchedEffect
            if (surfaceSize != IntSize.Zero) delay(SETTLE_MS)
            surfaceSize = paneSize
        }

        var failed by remember(pkg) { mutableStateOf(false) }
        var started by remember(pkg) { mutableStateOf(false) }
        val starting = remember(pkg) { java.util.concurrent.atomic.AtomicBoolean(false) }
        val currentActive by rememberUpdatedState(active)
        val scope = rememberCoroutineScope()

        if (failed) {
            fallback()
            return
        }

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
                                    controller.onUnavailable = { failed = true }
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
                    if (controller.isRunning) controller.resize(width, height)
                }

                override fun onSurfaceTextureDestroyed(st: SurfaceTexture): Boolean {
                    if (controller.isRunning) controller.setSurface(null)
                    surfaceRef[0]?.release()
                    surfaceRef[0] = null
                    return true
                }

                override fun onSurfaceTextureUpdated(st: SurfaceTexture) {}
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

    private companion object {
        /** Espera o card parar de mudar de tamanho antes de redimensionar o display. */
        const val SETTLE_MS = 250L
    }
}
