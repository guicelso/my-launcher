package app.mylauncher.core.debug

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat.MediaItem
import android.support.v4.media.MediaDescriptionCompat
import app.mylauncher.data.media.MediaKeys

/**
 * Catálogo do app de mídia simulado (spec 018), com o mesmo conteúdo dos wireframes Mx*:
 * abas Início (grade com grupos), Biblioteca (lista com indicadores), Podcasts (progresso e novos)
 * e Explorar (grade de categorias). Usado pelo [DebugMediaBrowserService] e pelo [DebugMediaPlayer].
 */
object DebugCatalog {
    data class Art(val a: Int, val b: Int, val c: Int)

    data class Entry(
        val id: String,
        val title: String,
        val subtitle: String,
        val art: Art,
        val durationMs: Long = 0,
        val explicit: Boolean = false,
        val downloaded: Boolean = false,
        val podcast: Boolean = false,
        val completion: Int? = null,
        val pct: Double? = null,
        val description: String = "",
    )

    private fun c(hex: String) = android.graphics.Color.parseColor(hex)
    private val P = listOf(
        Art(c("#1D5C63"), c("#F6B26B"), c("#E98A7B")), Art(c("#3A2E5C"), c("#A8C7FA"), c("#F28B82")),
        Art(c("#5B3A1E"), c("#FDD663"), c("#81C995")), Art(c("#0F3D2A"), c("#81C995"), c("#FDE293")),
        Art(c("#4A1D3B"), c("#F28B82"), c("#A8C7FA")), Art(c("#20344F"), c("#8AB4F8"), c("#FCAD70")),
    )

    // Álbum "Horizonte" (wireframe MxAlbum): faixa 2 é a que toca.
    val album = listOf(
        Entry("t:abertura", "Abertura", "Banda Fictícia", P[0], 58_000, downloaded = true),
        Entry("t:estrada", "Estrada de terra ao entardecer", "Banda Fictícia", P[0], 225_000, explicit = true, downloaded = true),
        Entry("t:posto", "Posto 24 horas", "Banda Fictícia", P[0], 192_000, downloaded = true),
        Entry("t:serra", "Serra acima", "Banda Fictícia", P[0], 241_000, explicit = true, downloaded = true),
        Entry("t:retrovisor", "Retrovisor", "Banda Fictícia", P[0], 169_000, downloaded = true),
        Entry("t:chuva", "Chuva na pista", "Banda Fictícia", P[0], 210_000, downloaded = true),
        Entry("t:destino", "Destino final", "Banda Fictícia", P[0], 262_000, downloaded = true),
    )

    // Playlist "Viagem longa" (fila do wireframe MxQueue).
    val roadTrip = listOf(
        album[1],
        album[2],
        Entry("t:fimdetarde", "Fim de tarde", "Duo Litoral", P[3], 245_000),
        Entry("t:lugar", "Lugar nenhum", "Alice Brito", P[4], 208_000),
        Entry("t:movimento", "Em movimento", "Banda da Estrada", P[1], 177_000),
        Entry("t:retorno", "Retorno", "Duo Litoral", P[3], 220_000),
    )

    val episodes = listOf(
        Entry("e:215", "Ep. 215 — Postos com boa comida", "Conversa de Estrada · hoje · 47 min", P[1], 2_820_000, downloaded = true, podcast = true, completion = 0, description = "Conversa de Estrada"),
        Entry("e:resumo", "Resumo do dia", "Notícias em 10 · hoje · 10 min", P[5], 600_000, podcast = true, completion = 0, description = "Notícias em 10"),
        Entry("e:214", "Ep. 214 — Como fazer viagens longas sem cansaço", "Conversa de Estrada · faltam 34 min", P[1], 3_120_000, downloaded = true, podcast = true, completion = 1, pct = 0.35, description = "Conversa de Estrada"),
        Entry("e:cap7", "Capítulo 7", "Audiolivro · O mapa · faltam 21 min", P[2], 4_500_000, podcast = true, completion = 1, pct = 0.72, description = "O mapa"),
        Entry("e:213", "Ep. 213 — Revisão antes de viajar", "Conversa de Estrada · 41 min", P[1], 2_460_000, podcast = true, completion = 2, description = "Conversa de Estrada"),
    )

    val allTracks: List<Entry> get() = (album + roadTrip).distinctBy { it.id }
    fun entry(id: String): Entry? = (allTracks + episodes).firstOrNull { it.id == id }

    /** Lista que vira a fila ao tocar um item (o álbum, a playlist ou os episódios). */
    fun queueFor(id: String): List<Entry> = when {
        episodes.any { it.id == id } -> episodes
        roadTrip.any { it.id == id } && album.none { it.id == id } -> roadTrip
        id.startsWith("pl:") -> roadTrip
        id == "al:horizonte" -> album
        else -> album
    }

    const val ACTION_DOWNLOAD = "dbg.download"
    const val ACTION_SAVE = "dbg.save"

    fun rootActions(): ArrayList<Bundle> = arrayListOf(
        Bundle().apply { putString(MediaKeys.BROWSE_ACTION_ID, ACTION_DOWNLOAD); putString(MediaKeys.BROWSE_ACTION_LABEL, "Baixado") },
        Bundle().apply { putString(MediaKeys.BROWSE_ACTION_ID, ACTION_SAVE); putString(MediaKeys.BROWSE_ACTION_LABEL, "Salvar") },
    )

    // ---- Árvore ----

    private fun extras(block: Bundle.() -> Unit) = Bundle().apply(block)

    private fun node(
        id: String, title: String, subtitle: String = "", art: Art? = null, group: String? = null,
        browsableStyle: Int = 0, playableStyle: Int = 0, single: Int = 0, playable: Boolean = false, actions: List<String> = emptyList(),
        downloaded: Boolean = false, explicit: Boolean = false,
    ): MediaItem {
        val e = extras {
            group?.let { putString(MediaKeys.GROUP_TITLE, it) }
            if (browsableStyle != 0) putInt(MediaKeys.STYLE_BROWSABLE, browsableStyle)
            if (playableStyle != 0) putInt(MediaKeys.STYLE_PLAYABLE, playableStyle)
            if (single != 0) putInt(MediaKeys.STYLE_SINGLE, single)
            if (actions.isNotEmpty()) putStringArrayList(MediaKeys.BROWSE_ACTION_IDS, ArrayList(actions))
            if (downloaded) putLong(MediaKeys.DOWNLOAD_STATUS, 2)
            if (explicit) putLong(MediaKeys.IS_EXPLICIT, 1)
        }
        val d = MediaDescriptionCompat.Builder().setMediaId(id).setTitle(title).setSubtitle(subtitle)
            .setIconBitmap(art?.let { bitmap(it, 128) }).setExtras(e).build()
        return MediaItem(d, MediaItem.FLAG_BROWSABLE or if (playable) MediaItem.FLAG_PLAYABLE else 0)
    }

    private fun track(t: Entry, group: String? = null, subtitle: String = t.subtitle): MediaItem {
        val e = extras {
            group?.let { putString(MediaKeys.GROUP_TITLE, it) }
            if (t.explicit) putLong(MediaKeys.IS_EXPLICIT, 1)
            if (t.downloaded) putLong(MediaKeys.DOWNLOAD_STATUS, 2)
            t.completion?.let { putInt(MediaKeys.COMPLETION_STATUS, it) }
            t.pct?.let { putDouble(MediaKeys.COMPLETION_PERCENTAGE, it) }
            putStringArrayList(MediaKeys.BROWSE_ACTION_IDS, arrayListOf(ACTION_DOWNLOAD, ACTION_SAVE))
        }
        val d = MediaDescriptionCompat.Builder().setMediaId(t.id).setTitle(t.title).setSubtitle(subtitle)
            .setIconBitmap(bitmap(t.art, 128)).setExtras(e).build()
        return MediaItem(d, MediaItem.FLAG_PLAYABLE)
    }

    private fun fmt(ms: Long) = "%d:%02d".format(ms / 60_000, ms / 1000 % 60)

    fun children(parentId: String): List<MediaItem>? = when (parentId) {
        "root" -> listOf(
            node("tab:inicio", "Início", browsableStyle = MediaKeys.STYLE_GRID, playableStyle = MediaKeys.STYLE_GRID),
            node("tab:biblioteca", "Biblioteca", browsableStyle = MediaKeys.STYLE_LIST, playableStyle = MediaKeys.STYLE_LIST),
            node("tab:podcasts", "Podcasts", browsableStyle = MediaKeys.STYLE_LIST, playableStyle = MediaKeys.STYLE_LIST),
            node("tab:explorar", "Explorar", browsableStyle = MediaKeys.STYLE_CATEGORY_GRID),
        )
        "tab:inicio" -> listOf(
            node("al:horizonte", "Horizonte", "Banda Fictícia", P[0], "Continuar ouvindo", playable = true, actions = listOf(ACTION_DOWNLOAD, ACTION_SAVE), downloaded = true),
            node("pod:conversa", "Conversa de Estrada", "Podcast", P[1], "Continuar ouvindo"),
            node("pl:viagem", "Viagem longa", "Playlist", P[2], "Continuar ouvindo", playable = true, actions = listOf(ACTION_DOWNLOAD, ACTION_SAVE)),
            node("ar:duo", "Duo Litoral", "Artista", P[3], "Continuar ouvindo"),
            node("pl:manha", "Manhã tranquila", "Mix diário", P[4], "Continuar ouvindo", playable = true),
            node("pl:rodovia", "Rodovia", "Playlist", P[5], "Para a estrada", playable = true),
            node("al:fimdetarde", "Fim de tarde", "Duo Litoral", P[3], "Para a estrada", playable = true),
            node("pl:acustico", "Acústico", "Playlist", P[2], "Para a estrada", playable = true),
            node("al:serra", "Noite na serra", "Álbum", P[0], "Para a estrada", playable = true),
            node("pl:classicos", "Clássicos", "Playlist", P[4], "Para a estrada", playable = true),
        )
        "tab:biblioteca" -> listOf(
            node("pl:viagem", "Viagem longa", "Playlist · 48 músicas", P[2], "Playlists", playable = true, downloaded = true),
            node("pl:rodovia", "Rodovia", "Playlist · 32 músicas", P[5], "Playlists", playable = true),
            node("al:horizonte", "Horizonte", "Banda Fictícia · 2026", P[0], "Álbuns", playable = true, explicit = true, downloaded = true, actions = listOf(ACTION_DOWNLOAD, ACTION_SAVE)),
            node("al:serra", "Noite na serra", "Alice Brito · 2025", P[4], "Álbuns", playable = true),
            node("ar:duo", "Duo Litoral", "Artista", P[3], "Artistas"),
            node("ar:banda", "Banda da Estrada", "Artista", P[1], "Artistas"),
        ) + (1..20).map { i -> node("pl:mix$i", "Mix ${'A' + (i % 26)} $i", "Playlist", P[i % 6], "Mais playlists", playable = true) }
        "tab:podcasts" -> episodes.map { e ->
            track(e, when (e.completion) { 0 -> "Novos episódios"; 1 -> "Em andamento"; else -> "Concluídos" })
        }
        "tab:explorar" -> listOf("Pop", "Rock", "MPB", "Sertanejo", "Samba", "Para dirigir", "Relaxar", "Paradas", "Infantil", "Rádios", "Audiolivros", "Notícias")
            .map { node("cat:$it", it, group = "Gêneros e momentos", single = MediaKeys.STYLE_CATEGORY_GRID) }
        "al:horizonte", "al:serra", "al:fimdetarde" -> album.map { track(it, subtitle = "Banda Fictícia · ${fmt(it.durationMs)}") }
        "pod:conversa" -> episodes.filter { it.description == "Conversa de Estrada" }.map { track(it) }
        else -> when {
            parentId.startsWith("pl:") || parentId.startsWith("cat:") -> roadTrip.map { track(it, subtitle = "${it.subtitle} · ${fmt(it.durationMs)}") }
            parentId.startsWith("ar:") -> listOf(
                node("al:horizonte", "Horizonte", "Álbum · 2026", P[0], "Álbuns", playable = true),
                node("al:fimdetarde", "Fim de tarde", "Álbum · 2024", P[3], "Álbuns", playable = true),
            ) + roadTrip.take(3).map { track(it, "Mais tocadas") }
            else -> null
        }
    }

    fun search(query: String): List<MediaItem> {
        val q = query.trim().lowercase()
        if (q.isEmpty()) return emptyList()
        val tracks = allTracks.filter { q in it.title.lowercase() || q in it.subtitle.lowercase() }.map { track(it, "Músicas") }
        val others = listOf(
            node("ar:banda", "Banda da Estrada", "Artista", P[1], "Artistas e álbuns"),
            node("al:horizonte", "Estradas (álbum)", "Álbum", P[2], "Artistas e álbuns", playable = true),
            node("pl:rodovia", "Rodovia", "Playlist", P[5], "Artistas e álbuns", playable = true),
        ).filter { q.length >= 3 }
        return tracks + others
    }

    // ---- Capas geradas (as mesmas formas dos wireframes) ----
    private val bitmaps = HashMap<String, Bitmap>()

    fun bitmap(art: Art, size: Int): Bitmap = bitmaps.getOrPut("${art.a}-${art.b}-$size") {
        val bmp = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val cv = Canvas(bmp)
        val p = Paint(Paint.ANTI_ALIAS_FLAG)
        cv.drawColor(art.a)
        p.color = art.b
        cv.drawCircle(size * 0.625f, size * 0.325f, size * 0.3f, p)
        p.color = art.c
        cv.drawOval(RectF(size * -0.05f, size * 0.525f, size * 0.6f, size * 0.975f), p)
        bmp
    }
}
