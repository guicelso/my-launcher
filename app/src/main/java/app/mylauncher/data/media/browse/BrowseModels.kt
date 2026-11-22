package app.mylauncher.data.media.browse

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import app.mylauncher.data.media.MediaKeys

/** Estilo de apresentação dos itens (content style hints do Android Auto, R18.6). */
enum class ItemStyle {
    LIST, GRID, CATEGORY_LIST, CATEGORY_GRID;

    val isCategory: Boolean get() = this == CATEGORY_LIST || this == CATEGORY_GRID

    companion object {
        fun of(value: Int): ItemStyle? = when (value) {
            MediaKeys.STYLE_LIST -> LIST
            MediaKeys.STYLE_GRID -> GRID
            MediaKeys.STYLE_CATEGORY_LIST -> CATEGORY_LIST
            MediaKeys.STYLE_CATEGORY_GRID -> CATEGORY_GRID
            else -> null
        }
    }
}

/** Estado de conclusão de um episódio/capítulo (`PLAYBACK_STATUS`). */
enum class Completion { NOT_PLAYED, PARTIAL, PLAYED }

/** Item da árvore de navegação de um app de mídia, já interpretado. */
data class BrowseItem(
    val id: String,
    val title: String,
    val subtitle: String = "",
    val description: String = "",
    val iconUri: Uri? = null,
    val iconBitmap: Bitmap? = null,
    val browsable: Boolean = false,
    val playable: Boolean = false,
    /** Estilo para os filhos navegáveis / tocáveis deste item (vale para o próximo nível). */
    val childBrowsableStyle: ItemStyle? = null,
    val childPlayableStyle: ItemStyle? = null,
    /** Estilo do próprio item (`SINGLE_ITEM_HINT`, tem precedência). */
    val singleStyle: ItemStyle? = null,
    val groupTitle: String? = null,
    val explicit: Boolean = false,
    /** 0 não baixado, 1 baixando, 2 baixado. */
    val download: Int = 0,
    val completion: Completion? = null,
    /** 0.0–1.0, barra de progresso no item (R18.7). */
    val completionPct: Double? = null,
    /** Ações de navegação personalizadas do item (ids da lista do root). */
    val actionIds: List<String> = emptyList(),
) {
    val isNew: Boolean get() = completion == Completion.NOT_PLAYED
    val inProgress: Boolean get() = completion == Completion.PARTIAL || (completionPct != null && completionPct > 0.0 && completionPct < 1.0)
    val done: Boolean get() = completion == Completion.PLAYED

    companion object {
        fun from(item: MediaBrowserCompat.MediaItem): BrowseItem {
            val d = item.description
            val e: Bundle? = d.extras
            fun style(key: String) = e?.getInt(key, 0)?.let { ItemStyle.of(it) }
            val status = e?.takeIf { it.containsKey(MediaKeys.COMPLETION_STATUS) }?.getInt(MediaKeys.COMPLETION_STATUS)
            return BrowseItem(
                id = item.mediaId.orEmpty(),
                title = d.title?.toString().orEmpty(),
                subtitle = d.subtitle?.toString().orEmpty(),
                description = d.description?.toString().orEmpty(),
                iconUri = d.iconUri,
                iconBitmap = d.iconBitmap,
                browsable = item.isBrowsable,
                playable = item.isPlayable,
                childBrowsableStyle = style(MediaKeys.STYLE_BROWSABLE),
                childPlayableStyle = style(MediaKeys.STYLE_PLAYABLE),
                singleStyle = style(MediaKeys.STYLE_SINGLE),
                groupTitle = e?.getString(MediaKeys.GROUP_TITLE)?.takeIf { it.isNotBlank() },
                explicit = e?.getLong(MediaKeys.IS_EXPLICIT, 0L) == 1L,
                download = e?.getLong(MediaKeys.DOWNLOAD_STATUS, 0L)?.toInt() ?: 0,
                completion = when (status) {
                    0 -> Completion.NOT_PLAYED
                    1 -> Completion.PARTIAL
                    2 -> Completion.PLAYED
                    else -> null
                },
                completionPct = e?.takeIf { it.containsKey(MediaKeys.COMPLETION_PERCENTAGE) }?.getDouble(MediaKeys.COMPLETION_PERCENTAGE),
                actionIds = e?.getStringArrayList(MediaKeys.BROWSE_ACTION_IDS).orEmpty(),
            )
        }
    }
}

/** Ação de navegação personalizada declarada no root (custom browse action). */
data class BrowseAction(val id: String, val label: String, val iconUri: Uri?, val extras: Bundle?)

/** Raiz da navegação, com os extras globais. */
data class BrowseRoot(
    val id: String,
    val searchSupported: Boolean,
    val browsableStyle: ItemStyle?,
    val playableStyle: ItemStyle?,
    val actions: List<BrowseAction>,
)

/** Resultado de uma ação de navegação personalizada. */
data class BrowseActionResult(val message: String?, val refresh: Boolean, val showPlaying: Boolean)

/** Situação da navegação de um app (R18.2). */
sealed interface BrowseStatus {
    data object Connecting : BrowseStatus
    data class Ready(val root: BrowseRoot) : BrowseStatus
    /** O app recusou o Lumia, não tem serviço de navegação ou não respondeu: modo só player. */
    data class Unavailable(val reason: String) : BrowseStatus
}

/** Um grupo contíguo de itens com o mesmo título e o estilo de apresentação (R18.6). */
data class BrowseSection(val title: String?, val style: ItemStyle, val items: List<BrowseItem>)

object BrowseLayout {
    /**
     * Agrupa itens contíguos com o mesmo título de grupo (itens não contíguos viram grupos
     * separados, como no Android Auto) e escolhe o estilo de cada grupo pelos hints: o do item
     * (`SINGLE_ITEM`), senão o do pai para navegáveis/tocáveis, senão o padrão do root.
     */
    fun sections(
        items: List<BrowseItem>,
        parent: BrowseItem?,
        root: BrowseRoot?,
    ): List<BrowseSection> {
        val out = mutableListOf<BrowseSection>()
        var current: MutableList<BrowseItem>? = null
        var currentTitle: String? = null
        var currentStyle: ItemStyle? = null
        for (it in items) {
            val style = it.singleStyle
                ?: (if (it.browsable) parent?.childBrowsableStyle ?: root?.browsableStyle else parent?.childPlayableStyle ?: root?.playableStyle)
                ?: ItemStyle.LIST
            if (current == null || it.groupTitle != currentTitle || style != currentStyle) {
                current?.let { list -> out += BrowseSection(currentTitle, currentStyle ?: ItemStyle.LIST, list) }
                current = mutableListOf()
                currentTitle = it.groupTitle
                currentStyle = style
            }
            current.add(it)
        }
        current?.let { out += BrowseSection(currentTitle, currentStyle ?: ItemStyle.LIST, it) }
        return out
    }

    /** Em movimento: corta o total de itens em [max] (valor AOSP, R18.21). @return seções e se cortou. */
    fun limit(sections: List<BrowseSection>, max: Int): Pair<List<BrowseSection>, Boolean> {
        var left = max
        var cut = false
        val out = mutableListOf<BrowseSection>()
        for (s in sections) {
            if (left <= 0) { cut = true; break }
            if (s.items.size > left) {
                out += s.copy(items = s.items.take(left))
                cut = true
                left = 0
            } else {
                out += s
                left -= s.items.size
            }
        }
        return out to cut
    }

    const val DRIVING_MAX_ITEMS = 21
    const val MAX_DEPTH = 3
    const val MAX_TABS = 4
}
