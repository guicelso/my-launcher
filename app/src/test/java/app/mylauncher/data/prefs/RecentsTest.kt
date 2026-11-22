package app.mylauncher.data.prefs

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class RecentsTest {
    @Test fun `push coloca no topo sem duplicar`() {
        assertEquals(listOf("b", "a", "c"), Recents.push(listOf("a", "b", "c"), "b"))
    }

    @Test fun `push limita o tamanho`() {
        val many = (1..20).map { "p$it" }
        assertEquals(Recents.MAX, Recents.push(many, "x").size)
    }

    @Test fun `barra prioriza fixados, depois recentes, depois padroes`() {
        val rail = Recents.railApps(
            pinned = listOf("waze"),
            recents = listOf("spotify", "waze"),
            defaults = listOf("maps", "phone"),
            isInstalled = { it != "maps" },
        )
        assertEquals(listOf("waze", "spotify", "phone"), rail)
    }

    @Test fun `destino aceita coordenadas`() {
        assertEquals(-23.5 to -46.6, Destination("Casa", "-23.5, -46.6").latLon)
        assertNull(Destination("Casa", "Rua X, 10").latLon)
    }

    @Test fun `widgets normalizados sem repeticao`() {
        assertEquals(3, Widgets.normalize(listOf(WidgetType.MEDIA, WidgetType.MEDIA)).distinct().size)
    }

    @Test fun `ordem personalizada com apps novos no fim`() {
        val alpha = listOf("a", "b", "c", "d")
        assertEquals(listOf("c", "a", "b", "d"), AppOrdering.apply(alpha, listOf("c", "a")) { it })
        assertEquals(listOf("b", "c", "a"), AppOrdering.move(listOf("a", "b", "c"), 0, 2))
    }
}
