package app.mylauncher.data.apps

import org.junit.Assert.assertEquals
import org.junit.Test

class AppsFilterTest {
    @Test fun `normaliza acentos e caixa`() {
        assertEquals("musica usb", AppsRepository.normalize("Música USB"))
        assertEquals("radio", AppsRepository.normalize("RÁDIO"))
    }
}
