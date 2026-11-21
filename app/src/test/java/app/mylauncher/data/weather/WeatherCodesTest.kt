package app.mylauncher.data.weather

import org.junit.Assert.assertEquals
import org.junit.Test

class WeatherCodesTest {
    @Test fun `classifica codigos wmo`() {
        assertEquals(WeatherKind.CLEAR, WeatherCodes.kind(0))
        assertEquals(WeatherKind.PARTLY, WeatherCodes.kind(2))
        assertEquals(WeatherKind.RAIN, WeatherCodes.kind(63))
        assertEquals(WeatherKind.RAIN, WeatherCodes.kind(81))
        assertEquals(WeatherKind.STORM, WeatherCodes.kind(95))
        assertEquals("Garoa", WeatherCodes.description(53))
    }
}
