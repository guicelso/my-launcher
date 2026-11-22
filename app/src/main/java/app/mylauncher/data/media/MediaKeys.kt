package app.mylauncher.data.media

import androidx.media.utils.MediaConstants as C

/**
 * Chaves de extras que o Android Auto lê das sessões e dos itens de navegação (spec 018; nomes
 * reais levantados em `docs/05-media-android-auto.md`, seções 2.1 e 2.2).
 */
object MediaKeys {
    // Sessão / PlaybackState
    const val RESERVE_PREVIOUS = C.SESSION_EXTRAS_KEY_SLOT_RESERVATION_SKIP_TO_PREV
    const val RESERVE_NEXT = C.SESSION_EXTRAS_KEY_SLOT_RESERVATION_SKIP_TO_NEXT
    const val ERROR_RESOLUTION_LABEL = C.PLAYBACK_STATE_EXTRAS_KEY_ERROR_RESOLUTION_ACTION_LABEL
    const val ERROR_RESOLUTION_INTENT = C.PLAYBACK_STATE_EXTRAS_KEY_ERROR_RESOLUTION_ACTION_INTENT
    const val PLAYBACK_MEDIA_ID = C.PLAYBACK_STATE_EXTRAS_KEY_MEDIA_ID

    // Metadados / itens
    const val IS_EXPLICIT = C.METADATA_KEY_IS_EXPLICIT
    const val IS_ADVERTISEMENT = C.METADATA_KEY_IS_ADVERTISEMENT
    const val DOWNLOAD_STATUS = "android.media.extra.DOWNLOAD_STATUS"
    const val SUBTITLE_LINK = "androidx.car.app.mediaextensions.KEY_SUBTITLE_LINK_MEDIA_ID"
    const val COMPLETION_STATUS = C.DESCRIPTION_EXTRAS_KEY_COMPLETION_STATUS
    const val COMPLETION_PERCENTAGE = C.DESCRIPTION_EXTRAS_KEY_COMPLETION_PERCENTAGE
    const val STYLE_BROWSABLE = C.DESCRIPTION_EXTRAS_KEY_CONTENT_STYLE_BROWSABLE
    const val STYLE_PLAYABLE = C.DESCRIPTION_EXTRAS_KEY_CONTENT_STYLE_PLAYABLE
    const val STYLE_SINGLE = C.DESCRIPTION_EXTRAS_KEY_CONTENT_STYLE_SINGLE_ITEM
    const val GROUP_TITLE = C.DESCRIPTION_EXTRAS_KEY_CONTENT_STYLE_GROUP_TITLE
    const val BROWSE_ACTION_IDS = C.DESCRIPTION_EXTRAS_KEY_CUSTOM_BROWSER_ACTION_ID_LIST

    // Root
    const val SEARCH_SUPPORTED = C.BROWSER_SERVICE_EXTRAS_KEY_SEARCH_SUPPORTED
    const val ROOT_CHILDREN_LIMIT = C.BROWSER_ROOT_HINTS_KEY_ROOT_CHILDREN_LIMIT
    const val ROOT_CHILDREN_FLAGS = C.BROWSER_ROOT_HINTS_KEY_ROOT_CHILDREN_SUPPORTED_FLAGS
    const val ART_SIZE_HINT = C.BROWSER_ROOT_HINTS_KEY_MEDIA_ART_SIZE_PIXELS
    const val BROWSE_ACTION_LIMIT = C.BROWSER_ROOT_HINTS_KEY_CUSTOM_BROWSER_ACTION_LIMIT
    const val BROWSE_ACTION_ROOT_LIST = C.BROWSER_SERVICE_EXTRAS_KEY_CUSTOM_BROWSER_ACTION_ROOT_LIST
    const val BROWSE_ACTION_ID = C.EXTRAS_KEY_CUSTOM_BROWSER_ACTION_ID
    const val BROWSE_ACTION_LABEL = C.EXTRAS_KEY_CUSTOM_BROWSER_ACTION_LABEL
    const val BROWSE_ACTION_ICON = C.EXTRAS_KEY_CUSTOM_BROWSER_ACTION_ICON_URI
    const val BROWSE_ACTION_ITEM_ID = C.EXTRAS_KEY_CUSTOM_BROWSER_ACTION_MEDIA_ITEM_ID
    const val BROWSE_ACTION_RESULT_MESSAGE = C.EXTRAS_KEY_CUSTOM_BROWSER_ACTION_RESULT_MESSAGE
    const val BROWSE_ACTION_RESULT_REFRESH = C.EXTRAS_KEY_CUSTOM_BROWSER_ACTION_RESULT_REFRESH_ITEM
    const val BROWSE_ACTION_RESULT_SHOW_PLAYING = C.EXTRAS_KEY_CUSTOM_BROWSER_ACTION_RESULT_SHOW_PLAYING_ITEM
    const val SHUFFLE_EXTRA = C.TRANSPORT_CONTROLS_EXTRAS_KEY_SHUFFLE

    const val STYLE_LIST = C.DESCRIPTION_EXTRAS_VALUE_CONTENT_STYLE_LIST_ITEM
    const val STYLE_GRID = C.DESCRIPTION_EXTRAS_VALUE_CONTENT_STYLE_GRID_ITEM
    const val STYLE_CATEGORY_LIST = C.DESCRIPTION_EXTRAS_VALUE_CONTENT_STYLE_CATEGORY_LIST_ITEM
    const val STYLE_CATEGORY_GRID = C.DESCRIPTION_EXTRAS_VALUE_CONTENT_STYLE_CATEGORY_GRID_ITEM

    /** `MediaBrowserCompat.MediaItem.FLAG_BROWSABLE` / `FLAG_PLAYABLE`. */
    const val FLAG_BROWSABLE = 1
    const val FLAG_PLAYABLE = 2
}

/**
 * Junta as ações personalizadas do app com aleatório e repetir da sessão (o redesenho de 2026 do
 * Android Auto mostra esses botões). Não duplica: se o app já tem uma ação de aleatório ou repetir,
 * vale a dele (R18.13: as ações seguem a ordem do app).
 */
object MediaActionsMerger {
    private val shuffleWords = listOf("shuffle", "aleat")
    private val repeatWords = listOf("repeat", "repet")

    private fun MediaAction.matches(words: List<String>): Boolean {
        val text = (id + " " + label).lowercase()
        return words.any { it in text }
    }

    fun merge(custom: List<MediaAction>, shuffle: Boolean?, repeat: RepeatMode?, pkg: String): List<MediaAction> {
        // As ações do app não dizem se estão ligadas; aleatório/repetir seguem o estado da sessão.
        val out = custom.map { a ->
            when {
                a.matches(shuffleWords) -> a.copy(active = shuffle == true)
                a.matches(repeatWords) -> a.copy(active = repeat != null && repeat != RepeatMode.OFF)
                else -> a
            }
        }.toMutableList()
        if (shuffle != null && custom.none { it.matches(shuffleWords) }) {
            out += MediaAction("lumia.shuffle", "Aleatório", pkg = pkg, builtin = BuiltinAction.SHUFFLE, active = shuffle)
        }
        if (repeat != null && custom.none { it.matches(repeatWords) }) {
            val one = repeat == RepeatMode.ONE
            out += MediaAction(
                "lumia.repeat", if (one) "Repetir uma" else "Repetir", pkg = pkg,
                builtin = if (one) BuiltinAction.REPEAT_ONE else BuiltinAction.REPEAT, active = repeat != RepeatMode.OFF,
            )
        }
        return out
    }
}

/**
 * Distribui as ações na barra de 5 posições do Android Auto (R18.13):
 * `[ação 1] [anterior] [tocar/pausar] [próxima] [ação 2 ou Mais]`.
 * - Sem anterior/próxima, a próxima ação do app ocupa a posição, salvo se a sessão a reservar.
 * - Sobrando mais de uma ação para a última posição, ela vira **Mais** com até 4 secundárias.
 */
object ControlSlots {
    sealed interface Slot {
        data object Empty : Slot
        data object Previous : Slot
        data object Next : Slot
        data class Action(val action: MediaAction) : Slot
        data object More : Slot
    }

    data class Layout(val first: Slot, val previous: Slot, val next: Slot, val last: Slot, val secondary: List<MediaAction>)

    const val MAX_SECONDARY = 4

    fun layout(np: NowPlaying): Layout {
        val pool = ArrayDeque(np.customActions)
        val previous = when {
            np.canPrevious -> Slot.Previous
            np.reservePrevious -> Slot.Empty
            else -> pool.removeFirstOrNull()?.let { Slot.Action(it) } ?: Slot.Empty
        }
        val next = when {
            np.canNext -> Slot.Next
            np.reserveNext -> Slot.Empty
            else -> pool.removeFirstOrNull()?.let { Slot.Action(it) } ?: Slot.Empty
        }
        val first = pool.removeFirstOrNull()?.let { Slot.Action(it) } ?: Slot.Empty
        return when (pool.size) {
            0 -> Layout(first, previous, next, Slot.Empty, emptyList())
            1 -> Layout(first, previous, next, Slot.Action(pool.first()), emptyList())
            else -> Layout(first, previous, next, Slot.More, pool.take(MAX_SECONDARY))
        }
    }
}
