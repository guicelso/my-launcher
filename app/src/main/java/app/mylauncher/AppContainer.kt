package app.mylauncher

import android.content.Context
import app.mylauncher.data.apps.AppsRepository
import app.mylauncher.data.prefs.SettingsRepository
import app.mylauncher.data.status.StatusRepository
import app.mylauncher.data.vehicle.FakeVehicleSource
import app.mylauncher.data.vehicle.UnavailableVehicleSource
import app.mylauncher.data.vehicle.VehicleSource
import app.mylauncher.data.vehicle.VehicleState
import app.mylauncher.ui.SystemActions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

/** Injeção de dependências manual (docs/01-architecture.md, D8). */
class AppContainer(val context: Context) {
    /** Escopo de vida do processo para repositórios que vivem enquanto o launcher existir. */
    val appScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    val settings = SettingsRepository(context, appScope)
    val apps = AppsRepository(context, settings, appScope)
    val status = StatusRepository(context)
    val systemActions = SystemActions(context)

    val fakeVehicle = FakeVehicleSource()
    val realVehicle: VehicleSource = UnavailableVehicleSource()

    /** Estado do veículo em uso: simulado (Ajustes/depuração) ou real. */
    val vehicle: StateFlow<VehicleState> = combine(
        settings.settings, fakeVehicle.state, realVehicle.state,
    ) { s, fake, real -> if (s.simulateVehicle) fake else real }
        .stateIn(appScope, SharingStarted.Eagerly, VehicleState())

    fun start() {
        apps.start()
        status.start()
        realVehicle.start()
    }
}
