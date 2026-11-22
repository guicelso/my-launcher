package app.mylauncher.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.mylauncher.AppContainer
import app.mylauncher.nav.embedded.EmbeddedAppPane
import app.mylauncher.nav.embedded.EmbeddedNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * [NavigationPane] de topo (spec 008): escolhe, pela preferência do usuário, entre o mapa próprio
 * ([OwnMapPane], spec 005) e o app de navegação embutido ([EmbeddedAppPane]). Composto uma única
 * vez pela área de trabalho (spec 010).
 */
class NavigationPaneHost(container: AppContainer) : NavigationPane {

    private val settings = container.settings
    private val own = OwnMapPane(container)
    private val controller = EmbeddedNavController(container.context)
    private val embedded = EmbeddedAppPane(controller, container.wake)

    @Composable
    override fun Content(mode: PaneMode, modifier: Modifier, active: Boolean, onExpand: () -> Unit) {
        val s by settings.settings.collectAsStateWithLifecycle()
        val src = s.mapSource
        val pkg = src.pkg

        LaunchedEffect(s.mapUiScale) { controller.setDensity(s.mapUiScale.dpi) }

        if (src.isEmbedded && pkg != null) {
            embedded.Content(
                pkg = pkg,
                startUri = src.startUri.takeIf { s.mapFreeDrive },
                mode = mode,
                modifier = modifier,
                active = active,
                onExpand = onExpand,
                fallback = { own.Content(mode, modifier, active, onExpand) },
            )
        } else {
            // Voltou ao mapa próprio: garante que o embutido seja encerrado.
            LaunchedEffect(Unit) { withContext(Dispatchers.IO) { controller.stop() } }
            own.Content(mode, modifier, active, onExpand)
        }
    }
}
