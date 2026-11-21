package app.mylauncher.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

enum class PaneMode { CARD, FULL }

/**
 * Painel de navegação trocável (docs/01-architecture.md, D3).
 * Implementações: [OwnMapPane] (spec 005) e, no futuro, EmbeddedAppPane (spec 008).
 * Composto uma única vez pela área de trabalho (spec 010); [active] = false enquanto
 * há uma tela por cima, e a implementação deve parar de renderizar.
 */
interface NavigationPane {
    @Composable
    fun Content(mode: PaneMode, modifier: Modifier, active: Boolean, onExpand: () -> Unit)
}
