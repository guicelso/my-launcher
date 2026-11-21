package app.mylauncher

import android.content.Context
import app.mylauncher.core.debug.DebugMediaPlayer
import app.mylauncher.data.apps.AppsRepository
import app.mylauncher.data.destinations.DestinationRequests
import app.mylauncher.data.destinations.PlacesLearner
import app.mylauncher.data.location.LocationRepository
import app.mylauncher.data.media.MediaApps
import app.mylauncher.data.media.MediaRepository
import app.mylauncher.data.media.MessagesRepository
import app.mylauncher.data.prefs.SettingsRepository
import app.mylauncher.data.status.StatusRepository
import app.mylauncher.data.vehicle.FakeVehicleSource
import app.mylauncher.data.vehicle.ReverseDucker
import app.mylauncher.data.vehicle.VehicleState
import app.mylauncher.data.vehicle.topway.TopWayMediaBridge
import app.mylauncher.data.vehicle.topway.TopWayVehicleSource
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
    val messages = MessagesRepository(appScope, context.packageName)
    val location = LocationRepository(context)

    val fakeVehicle = FakeVehicleSource()
    val topWay = TopWayVehicleSource(context, appScope)

    /** Estado do veículo em uso: simulado (Ajustes/depuração) ou real (TopWay). */
    val vehicle: StateFlow<VehicleState> = combine(
        settings.settings, fakeVehicle.state, topWay.state,
    ) { s, fake, real -> if (s.simulateVehicle) fake else real }
        .stateIn(appScope, SharingStarted.Eagerly, VehicleState())

    val media = MediaRepository(context, apps, appScope)
    val mediaApps = MediaApps(context)
    val debugPlayer = DebugMediaPlayer(context)
    private val topWayMedia = TopWayMediaBridge(context, appScope, vehicle, topWay)
    private val reverseDucker = ReverseDucker(context, appScope, vehicle, settings.settings)
    val destinationRequests = DestinationRequests()
    val placesLearner = PlacesLearner(context, appScope, vehicle, settings.settings) { location.location.value }

    fun start() {
        apps.start()
        status.start()
        location.start()
        topWay.start()
        topWayMedia.start()
        media.setExternalSource(topWayMedia)
        media.start()
        reverseDucker.start()
        placesLearner.start()
    }
}
