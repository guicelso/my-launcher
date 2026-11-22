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
import app.mylauncher.data.weather.WeatherRepository
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
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import app.mylauncher.core.FileLog
import app.mylauncher.data.vehicle.topway.SleepKeepList
import app.mylauncher.data.vehicle.topway.UnisocPowerConfig

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
    val weather = WeatherRepository(context, appScope, location.location)

    val fakeVehicle = FakeVehicleSource()
    val topWay = TopWayVehicleSource(context, appScope)

    /** Estado do veículo em uso: simulado (Ajustes/depuração) ou real (TopWay). */
    val vehicle: StateFlow<VehicleState> = combine(
        settings.settings, fakeVehicle.state, topWay.state,
    ) { s, fake, real -> if (s.simulateVehicle) fake else real }
        .stateIn(appScope, SharingStarted.Eagerly, VehicleState())

    val mediaApps = MediaApps(context)
    val media = MediaRepository(context, apps, appScope, mediaApps)
    val debugPlayer = DebugMediaPlayer(context)
    /** Navegação nos apps de mídia, como o host do Android Auto (spec 018). */
    val mediaLibrary = app.mylauncher.data.media.browse.MediaLibrary(context)
    private val topWayMedia = TopWayMediaBridge(context, appScope, vehicle, topWay)
    private val reverseDucker = ReverseDucker(context, appScope, vehicle, settings.settings)
    val destinationRequests = DestinationRequests()
    val placesLearner = PlacesLearner(context, appScope, vehicle, settings.settings) { location.location.value }

    private val _wake = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    /** A chave foi ligada (ACC desligado → ligado): a interface volta ao painel (spec 016, R16.3). */
    val wake: SharedFlow<Unit> = _wake.asSharedFlow()

    private val accPrefs = context.getSharedPreferences("acc_state", Context.MODE_PRIVATE)

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
        weather.start()
        keepAliveOnSleep()
        watchAcc()
    }

    /**
     * Põe o launcher, o app de música padrão (se o ajuste estiver ligado) e o app de navegação
     * embutido no card na lista de apps que a TopWay não fecha ao entrar em espera (spec 016,
     * R16.1/R16.2; spec 008, R8.9: fechado, o Maps deixava o card preto ao religar a chave).
     */
    private fun keepAliveOnSleep() {
        // Gerenciamento de energia da Unisoc: "não otimizar" o launcher, senão o sistema nega a
        // conexão do listener de notificações no boot (spec 016).
        appScope.launch(Dispatchers.IO) { UnisocPowerConfig.ensureNoOptimize(context.packageName) }
        appScope.launch {
            settings.settings
                .map { s ->
                    listOfNotNull(
                        context.packageName,
                        s.musicApp.takeIf { s.keepMusicAlive },
                        s.mapSource.pkg.takeIf { s.mapSource.isEmbedded },
                    )
                }
                .distinctUntilChanged()
                .collect { pkgs -> withContext(Dispatchers.IO) { SleepKeepList.ensure(pkgs) } }
        }
    }

    /**
     * Chave desligada → ligada (spec 016, R16.3/R16.6). O estado do ACC é gravado para valer mesmo
     * se o processo for recriado durante a espera.
     */
    private fun watchAcc() {
        appScope.launch {
            vehicle.map { it.accOn }.filterNotNull().distinctUntilChanged().collect { on ->
                val prev = if (accPrefs.contains(KEY_ACC_ON)) accPrefs.getBoolean(KEY_ACC_ON, true) else null
                if (prev == true && !on) {
                    val now = System.currentTimeMillis()
                    val wasPlaying = media.lastSession.value?.wasPlayingAt(now) == true
                    FileLog.i("ACC desligado; música tocando: $wasPlaying")
                    accPrefs.edit().putBoolean(KEY_ACC_ON, false).putBoolean(KEY_RESUME, wasPlaying).apply()
                } else if (prev == false && on) {
                    val resume = accPrefs.getBoolean(KEY_RESUME, false) && settings.settings.value.resumeOnWake
                    FileLog.i("ACC ligado; voltando ao painel" + if (resume) " e retomando a música" else "")
                    accPrefs.edit().putBoolean(KEY_ACC_ON, true).putBoolean(KEY_RESUME, false).apply()
                    _wake.tryEmit(Unit)
                    if (resume) {
                        // Dá tempo ao áudio da TopWay de voltar da espera antes de tocar.
                        delay(RESUME_DELAY_MS)
                        media.resumeLast()
                    }
                } else {
                    accPrefs.edit().putBoolean(KEY_ACC_ON, on).apply()
                }
            }
        }
    }

    private companion object {
        const val KEY_ACC_ON = "acc_on"
        const val KEY_RESUME = "resume_on_wake"
        const val RESUME_DELAY_MS = 3_000L
    }
}
