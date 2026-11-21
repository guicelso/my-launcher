package app.mylauncher.ui.theme

import app.mylauncher.data.prefs.ThemePref
import app.mylauncher.data.vehicle.VehicleState

/**
 * Decide noite/dia (spec 002, R2.11; spec 006, R6.5).
 * AUTO: faróis (MCU) → tema do sistema da TopWay → horário (18h–6h = noite).
 */
object ThemeResolver {
    const val NIGHT_START_HOUR = 18
    const val NIGHT_END_HOUR = 6

    fun isNight(pref: ThemePref, vehicle: VehicleState, hourOfDay: Int): Boolean = when (pref) {
        ThemePref.DAY -> false
        ThemePref.NIGHT -> true
        ThemePref.AUTO -> vehicle.headlightsOn
            ?: vehicle.systemNight
            ?: (hourOfDay >= NIGHT_START_HOUR || hourOfDay < NIGHT_END_HOUR)
    }
}
