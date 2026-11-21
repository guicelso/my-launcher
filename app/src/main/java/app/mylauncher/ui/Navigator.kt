package app.mylauncher.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

enum class Screen { DASHBOARD, MAP, MEDIA, APPS, SETTINGS }

/** Navegação interna simples: um nível abaixo do painel. Voltar/HOME sempre levam ao painel. */
class Navigator {
    var screen by mutableStateOf(Screen.DASHBOARD)
        private set

    fun go(target: Screen) {
        screen = target
    }

    /** @return true se estava em outra tela (o evento foi consumido). */
    fun home(): Boolean {
        val wasElsewhere = screen != Screen.DASHBOARD
        screen = Screen.DASHBOARD
        return wasElsewhere
    }
}
