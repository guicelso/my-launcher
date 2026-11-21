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

    @Test fun `le resposta completa do open-meteo`() {
        val json = """
            {"utc_offset_seconds":-10800,
             "current":{"temperature_2m":24.4,"apparent_temperature":25.1,"relative_humidity_2m":70,"wind_speed_10m":12.3,"weather_code":2,"is_day":1},
             "hourly":{"time":["2026-11-21T14:00","2026-11-21T15:00"],"temperature_2m":[24.0,25.0],"weather_code":[2,3],"is_day":[1,1]},
             "daily":{"time":["2026-11-21","2026-11-22"],"weather_code":[2,61],"temperature_2m_max":[27.0,22.0],"temperature_2m_min":[18.0,16.0],
                      "sunrise":["2026-11-21T05:10","2026-11-22T05:10"],"sunset":["2026-11-21T18:40","2026-11-22T18:41"],
                      "precipitation_probability_max":[10,80]}}
        """.trimIndent()
        val w = WeatherCodes.parse(json, "Florianópolis", 0L)
        assertEquals(24, w.temperature)
        assertEquals(25, w.feelsLike)
        assertEquals(70, w.humidity)
        assertEquals(27, w.max)
        assertEquals(2, w.daily.size)
        assertEquals(WeatherKind.RAIN, w.daily[1].kind)
        assertEquals(80, w.daily[1].rainChance)
        assertEquals(2, w.hourly.size)
    }
}
