package app.mylauncher.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/**
 * Telas do launcher (spec 010). DASHBOARD, SPLIT_MEDIA, MEDIA e MAP são modos da área de
 * trabalho (mapa sempre vivo, cards crescem/encolhem); APPS e SETTINGS abrem por cima dela.
 */
enum class Screen(val overlay: Boolean) {
    DASHBOARD(false),
    SPLIT_MEDIA(false),
    MAP(false),
    MEDIA(false),
    APPS(true),
    SETTINGS(true),
}

class Navigator {
    /** Modo atual da área de trabalho. */
    var workspace by mutableStateOf(Screen.DASHBOARD)
        private set

    /** Tela sobreposta à área de trabalho, se houver. */
    var overlay by mutableStateOf<Screen?>(null)
        private set

    /** Seção inicial ao abrir os Ajustes (ex.: "Apps" a partir do link de apps ocultos). */
    var settingsSection by mutableStateOf<String?>(null)
        private set

    fun openSettings(section: String? = null) {
        settingsSection = section
        go(Screen.SETTINGS)
    }

    /** Tela visível (para destacar a barra lateral). */
    val screen: Screen get() = overlay ?: workspace

    fun go(target: Screen) {
        if (target.overlay) {
            overlay = target
        } else {
            workspace = target
            overlay = null
        }
    }

    /** HOME: sempre volta ao painel. @return true se mudou algo. */
    fun home(): Boolean {
        val changed = overlay != null || workspace != Screen.DASHBOARD
        overlay = null
        workspace = Screen.DASHBOARD
        return changed
    }

    /** Voltar: fecha a tela sobreposta; senão, volta ao painel. */
    fun back() {
        when {
            overlay != null -> overlay = null
            workspace == Screen.MEDIA -> workspace = Screen.SPLIT_MEDIA
            else -> workspace = Screen.DASHBOARD
        }
    }
}
