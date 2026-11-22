package app.mylauncher.ui.media

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import app.mylauncher.data.media.browse.BrowseItem

/** Páginas da mídia em tela cheia (spec 018): os wireframes Mx*. */
sealed interface MediaPage {
    /** MxPlayer (tocando agora). */
    data object Player : MediaPage
    /** MxQueue. */
    data object Queue : MediaPage
    /** MxSearch. */
    data object Search : MediaPage
    /** MxSources (trocar app de mídia). */
    data object Sources : MediaPage
    /** MxBrowse (pilha vazia = abas) e MxAlbum (nível 2 em diante). */
    data class Browse(val stack: List<BrowseItem> = emptyList()) : MediaPage
}

/**
 * Estado de navegação da mídia: página atual, app aberto (pode não ser o que toca, como no Android
 * Auto ao trocar de app) e o histórico para o Voltar.
 */
class MediaNav {
    var page by mutableStateOf<MediaPage>(MediaPage.Player)
        private set

    /** App de mídia aberto; null = o que está tocando (ou o app de música padrão). */
    var app by mutableStateOf<String?>(null)

    /** Aba aberta por app (lembrada ao voltar do player). */
    var tabs by mutableStateOf<Map<String, String>>(emptyMap())

    /** "Mais" aberto ao entrar no player (botão Mais do player dividido). */
    var moreOpen by mutableStateOf(false)

    private val history = ArrayDeque<MediaPage>()

    fun go(p: MediaPage) {
        if (p == page) return
        history.addLast(page)
        page = p
    }

    /** Começa do zero numa página (entrada pela tela dividida, pelo card ou pelo seletor). */
    fun reset(p: MediaPage = MediaPage.Player) {
        history.clear()
        page = p
    }

    /** Volta uma página. @return false quando não há para onde voltar (sai da tela cheia). */
    fun back(): Boolean {
        val prev = history.removeLastOrNull() ?: return false
        page = prev
        return true
    }

    fun setTab(pkg: String, tabId: String) {
        tabs = tabs + (pkg to tabId)
    }
}
