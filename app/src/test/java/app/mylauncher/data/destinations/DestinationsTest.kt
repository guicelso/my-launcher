package app.mylauncher.data.destinations

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test

class DestinationsTest {
    @Test fun `geo uri`() {
        assertEquals("-23.550500,-46.633300", SharedPlaceParser.parse("geo:-23.5505,-46.6333?z=15")?.query)
    }

    @Test fun `link do google maps com arroba`() {
        val p = SharedPlaceParser.parse("https://www.google.com/maps/place/Parque+Ibirapuera/@-23.5874,-46.6576,17z")
        assertEquals("-23.587400,-46.657600", p?.query)
        assertEquals("Parque Ibirapuera", p?.title)
    }

    @Test fun `link do waze`() {
        assertEquals("-27.595400,-48.548000", SharedPlaceParser.parse("Me encontre aqui https://waze.com/ul?ll=-27.5954%2C-48.5480&navigate=yes")?.query)
    }

    @Test fun `localizacao do whatsapp`() {
        assertEquals("-23.000000,-46.000000", SharedPlaceParser.parse("https://maps.google.com/?q=-23.000000,-46.000000")?.query)
    }

    @Test fun `endereco em texto`() {
        assertEquals("Av. Paulista, 1000 - São Paulo", SharedPlaceParser.parse("Av. Paulista, 1000 - São Paulo")?.query)
        assertNull(SharedPlaceParser.parse("  "))
    }

    @Test fun `agrupa paradas noturnas`() {
        val night = 1_700_000_000_000L
        val visits = (0 until 4).map { Visit(-23.5505 + it * 0.0002, -46.6333, night + it * 86_400_000L) } +
            Visit(-22.9, -43.2, night)
        val best = PlaceRules.best(visits, { true })
        assertNotNull(best)
        assertEquals(4, best!!.third)
    }
}
