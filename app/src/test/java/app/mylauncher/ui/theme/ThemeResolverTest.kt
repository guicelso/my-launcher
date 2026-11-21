package app.mylauncher.ui.theme

import app.mylauncher.data.prefs.ThemePref
import app.mylauncher.data.vehicle.VehicleState
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ThemeResolverTest {
    private val noon = 12
    private val night = 22

    @Test fun `preferencia fixa ignora o veiculo`() {
        assertFalse(ThemeResolver.isNight(ThemePref.DAY, VehicleState(headlightsOn = true), night))
        assertTrue(ThemeResolver.isNight(ThemePref.NIGHT, VehicleState(headlightsOn = false), noon))
    }

    @Test fun `auto segue os farois`() {
        assertTrue(ThemeResolver.isNight(ThemePref.AUTO, VehicleState(headlightsOn = true), noon))
        assertFalse(ThemeResolver.isNight(ThemePref.AUTO, VehicleState(headlightsOn = false), night))
    }

    @Test fun `auto sem farois usa o tema do sistema`() {
        assertTrue(ThemeResolver.isNight(ThemePref.AUTO, VehicleState(systemNight = true), noon))
    }

    @Test fun `auto sem nenhum sinal usa o horario`() {
        assertTrue(ThemeResolver.isNight(ThemePref.AUTO, VehicleState(), night))
        assertTrue(ThemeResolver.isNight(ThemePref.AUTO, VehicleState(), 3))
        assertFalse(ThemeResolver.isNight(ThemePref.AUTO, VehicleState(), noon))
    }
}
