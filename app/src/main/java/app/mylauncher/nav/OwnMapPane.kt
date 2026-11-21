package app.mylauncher.nav

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.view.Gravity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.mylauncher.AppContainer
import app.mylauncher.data.location.LocationRepository
import app.mylauncher.data.prefs.Destination
import app.mylauncher.ui.components.PillShape
import app.mylauncher.ui.components.TouchIconButton
import app.mylauncher.ui.icons.LauncherIcons
import app.mylauncher.ui.theme.LocalLauncherColors
import org.maplibre.android.camera.CameraPosition
import org.maplibre.android.camera.CameraUpdateFactory
import org.maplibre.android.geometry.LatLng
import org.maplibre.android.location.LocationComponentActivationOptions
import org.maplibre.android.location.OnCameraTrackingChangedListener
import org.maplibre.android.location.modes.CameraMode
import org.maplibre.android.location.modes.RenderMode
import org.maplibre.android.maps.MapLibreMap
import org.maplibre.android.maps.MapLibreMapOptions
import org.maplibre.android.maps.MapView
import org.maplibre.android.maps.Style
import org.maplibre.android.offline.OfflineManager

/** Estilos vetoriais (R5.1/R5.2). OpenFreeMap: sem chave; dados © OpenStreetMap (ODbL). */
object MapStyles {
    const val DAY = "https://tiles.openfreemap.org/styles/liberty"
    const val NIGHT = "https://tiles.openfreemap.org/styles/dark"
    const val CACHE_BYTES = 200L * 1024 * 1024 // R5.7
    const val MAX_FPS = 30
    val FALLBACK_CENTER = LatLng(-15.78, -47.93) // Brasil, até o primeiro fix

    fun configureCache(context: Context) {
        OfflineManager.getInstance(context).setMaximumAmbientCacheSize(CACHE_BYTES, null)
    }
}

/**
 * Mapa próprio (spec 005): posição e rumo do carro; a navegação é delegada ao app padrão.
 * Artboards "Mapa em tela cheia" e card de mapa do painel.
 */
class OwnMapPane(private val container: AppContainer) : NavigationPane {

    @Composable
    override fun Content(mode: PaneMode, modifier: Modifier, active: Boolean, onExpand: () -> Unit) {
        val c = LocalLauncherColors.current
        val location by container.location.location.collectAsStateWithLifecycle()
        val granted by container.location.granted.collectAsStateWithLifecycle()
        val settings by container.settings.settings.collectAsStateWithLifecycle()
        val apps = container.apps
        var map by remember { mutableStateOf<MapLibreMap?>(null) }
        var following by remember { mutableStateOf(true) }
        var headingUp by remember { mutableStateOf(true) }
        val styleUrl = if (c.isNight) MapStyles.NIGHT else MapStyles.DAY

        Box(modifier) {
            MapViewHost(
                styleUrl = styleUrl,
                granted = granted,
                active = active,
                onMap = { map = it },
                onTrackingDismissed = { following = false },
            )

            LaunchedEffect(map, location) {
                val l = location ?: return@LaunchedEffect
                map?.locationComponentOrNull()?.forceLocationUpdate(l)
            }
            LaunchedEffect(map, following, headingUp) {
                val comp = map?.locationComponentOrNull() ?: return@LaunchedEffect
                comp.cameraMode = when {
                    !following -> CameraMode.NONE
                    headingUp -> CameraMode.TRACKING_GPS
                    else -> CameraMode.TRACKING_GPS_NORTH
                }
            }

            val navApp = settings.navApp
            val openSearch: () -> Unit = { apps.launch(navApp) }
            val pad = 16.dp

            when (mode) {
                PaneMode.CARD -> {
                    SearchPill(
                        Modifier.align(Alignment.TopStart).padding(pad).fillMaxWidth().height(64.dp),
                        onClick = openSearch,
                    )
                    SpeedBubble(location, 76.dp, Modifier.align(Alignment.BottomStart).padding(pad))
                    Row(Modifier.align(Alignment.BottomEnd).padding(pad), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        MapButton(LauncherIcons.Expand, "Mapa em tela cheia", onExpand)
                        MapButton(LauncherIcons.Locate, "Centralizar na posição atual", { following = true; map?.recenter(location) }, tint = c.primary)
                    }
                }
                PaneMode.FULL -> {
                    Column(Modifier.align(Alignment.TopStart).padding(pad).width(520.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        SearchPill(Modifier.fillMaxWidth().height(76.dp), onClick = openSearch)
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            settings.home?.let { d -> Chip("Casa", LauncherIcons.Home) { navigate(d) } }
                            settings.work?.let { d -> Chip("Trabalho", LauncherIcons.Work) { navigate(d) } }
                            Chip("Postos", LauncherIcons.Fuel) { apps.launchIntent(NavIntents.search(navApp, "posto de combustível")); Unit }
                        }
                    }
                    Column(Modifier.align(Alignment.TopEnd).padding(pad), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        MapButton(LauncherIcons.Compass, if (headingUp) "Orientação: rumo para cima" else "Orientação: norte para cima", { headingUp = !headingUp })
                        MapButton(LauncherIcons.Plus, "Aproximar", { map?.animateCamera(CameraUpdateFactory.zoomIn()) })
                        MapButton(LauncherIcons.Minus, "Afastar", { map?.animateCamera(CameraUpdateFactory.zoomOut()) })
                    }
                    SpeedBubble(location, 84.dp, Modifier.align(Alignment.BottomStart).padding(pad))
                    Row(Modifier.align(Alignment.BottomEnd).padding(pad), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        MapButton(LauncherIcons.Locate, "Centralizar na posição atual", { following = true; map?.recenter(location) }, tint = c.primary)
                        Row(
                            Modifier
                                .height(76.dp)
                                .shadow(8.dp, PillShape)
                                .clip(PillShape)
                                .background(c.primary)
                                .clickable(role = Role.Button, onClick = openSearch)
                                .padding(horizontal = 28.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                        ) {
                            Text("Abrir no ${apps.label(navApp)}", style = MaterialTheme.typography.titleMedium, color = c.onPrimary)
                            Icon(LauncherIcons.External, null, Modifier.size(24.dp), tint = c.onPrimary)
                        }
                    }
                }
            }

            if (!granted) {
                Box(
                    Modifier.align(Alignment.Center).clip(PillShape).background(c.surface).padding(horizontal = 24.dp, vertical = 16.dp),
                ) { Text("Localização não autorizada", style = MaterialTheme.typography.titleMedium, color = c.onSurfaceVariant) }
            }
        }
    }

    private fun navigate(d: Destination) {
        val app = container.settings.settings.value.navApp
        container.apps.launchIntent(NavIntents.navigate(app, d))
    }
}

@SuppressLint("MissingPermission")
private fun MapLibreMap.locationComponentOrNull() =
    locationComponent.takeIf { runCatching { it.isLocationComponentActivated }.getOrDefault(false) }

private fun MapLibreMap.recenter(l: Location?) {
    l ?: return
    animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(l.latitude, l.longitude), 16.0))
}

/** MapView do MapLibre ligado ao ciclo de vida do Compose. */
@SuppressLint("MissingPermission")
@Composable
private fun MapViewHost(
    styleUrl: String,
    granted: Boolean,
    active: Boolean,
    onMap: (MapLibreMap) -> Unit,
    onTrackingDismissed: () -> Unit,
) {
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val loadColor = LocalLauncherColors.current.surface2.toArgb()
    val mapView = remember {
        // SurfaceView (padrão): o mapa desenha numa camada própria, numa thread separada,
        // sem forçar o redesenho da interface a cada quadro. Como a superfície tem tamanho fixo
        // (LocalMapCanvasSize) e só é recortada pelo card, ela nunca é redimensionada.
        val options = MapLibreMapOptions.createFromAttributes(context)
            .foregroundLoadColor(loadColor)
        MapView(context, options).apply {
            onCreate(null)
            // 30 fps bastam para navegação e reduzem pela metade o custo na GPU PowerVR GE8322.
            setMaximumFps(MapStyles.MAX_FPS)
        }
    }
    var mapRef by remember { mutableStateOf<MapLibreMap?>(null) }
    val runner = remember(mapView) { MapRunner(mapView) }
    val currentActive by rememberUpdatedState(active)

    // Renderiza só com a activity visível E o mapa sem tela por cima (spec 010, R10.5).
    DisposableEffect(lifecycle, mapView) {
        val observer = LifecycleEventObserver { _, _ ->
            runner.sync(lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED) && currentActive)
        }
        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
            runner.sync(false)
            mapView.onDestroy()
        }
    }
    LaunchedEffect(active) {
        runner.sync(lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED) && active)
    }

    LaunchedEffect(mapView) {
        mapView.getMapAsync { m ->
            m.uiSettings.apply {
                isCompassEnabled = false
                isLogoEnabled = false
                isAttributionEnabled = true
                attributionGravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
                isRotateGesturesEnabled = true
                isTiltGesturesEnabled = false
            }
            m.cameraPosition = CameraPosition.Builder().target(MapStyles.FALLBACK_CENTER).zoom(4.0).build()
            m.addOnCameraMoveStartedListener { reason ->
                if (reason == MapLibreMap.OnCameraMoveStartedListener.REASON_API_GESTURE) onTrackingDismissed()
            }
            mapRef = m
        }
    }

    LaunchedEffect(mapRef, styleUrl, granted) {
        val m = mapRef ?: return@LaunchedEffect
        m.setStyle(Style.Builder().fromUri(styleUrl)) { style ->
            if (granted) {
                runCatching {
                    val comp = m.locationComponent
                    comp.activateLocationComponent(
                        LocationComponentActivationOptions.builder(context, style)
                            .useDefaultLocationEngine(false)
                            .build(),
                    )
                    comp.isLocationComponentEnabled = true
                    comp.renderMode = RenderMode.GPS
                    comp.cameraMode = CameraMode.TRACKING_GPS
                    comp.zoomWhileTracking(16.0)
                    comp.addOnCameraTrackingChangedListener(object : OnCameraTrackingChangedListener {
                        override fun onCameraTrackingDismissed() = onTrackingDismissed()
                        override fun onCameraTrackingChanged(currentMode: Int) = Unit
                    })
                }
            }
            onMap(m)
        }
    }

    val canvas = LocalMapCanvasSize.current
    // Tamanho fixo (centralizado e recortado pelo card) para não redesenhar o mapa a cada quadro.
    AndroidView(factory = { mapView }, modifier = if (canvas != null) Modifier.requiredSize(canvas) else Modifier.fillMaxSize())
}

/** Liga/desliga a renderização do MapView sem chamadas duplicadas. */
private class MapRunner(private val mapView: MapView) {
    private var running = false

    fun sync(shouldRun: Boolean) {
        if (shouldRun && !running) {
            mapView.onStart()
            mapView.onResume()
            running = true
        } else if (!shouldRun && running) {
            mapView.onPause()
            mapView.onStop()
            running = false
        }
    }
}

@Composable
private fun SearchPill(modifier: Modifier, onClick: () -> Unit) {
    val c = LocalLauncherColors.current
    Row(
        modifier
            .shadow(8.dp, PillShape)
            .clip(PillShape)
            .background(c.surface)
            .clickable(role = Role.Button, onClickLabel = "Buscar destino", onClick = onClick)
            .padding(horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Icon(LauncherIcons.Search, null, Modifier.size(28.dp), tint = c.onSurfaceVariant)
        Text("Para onde?", style = MaterialTheme.typography.titleMedium, color = c.onSurfaceVariant, modifier = Modifier.weight(1f))
        Icon(LauncherIcons.Mic, null, Modifier.size(28.dp), tint = c.onSurfaceVariant)
    }
}

@Composable
private fun SpeedBubble(location: Location?, size: Dp, modifier: Modifier) {
    val c = LocalLauncherColors.current
    val kmh = LocationRepository.speedKmh(location)
    Column(
        modifier.size(size).shadow(8.dp, CircleShape).clip(CircleShape).background(c.surface),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(kmh?.toString() ?: "--", style = MaterialTheme.typography.headlineSmall, color = c.onSurface)
        Text("km/h", style = MaterialTheme.typography.labelSmall, color = c.onSurfaceVariant)
    }
}

@Composable
private fun MapButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    onClick: () -> Unit,
    tint: androidx.compose.ui.graphics.Color = LocalLauncherColors.current.onSurface,
) {
    val c = LocalLauncherColors.current
    TouchIconButton(
        icon, label, onClick,
        modifier = Modifier.shadow(8.dp, CircleShape),
        background = c.surface, tint = tint, iconSize = 32.dp,
    )
}

@Composable
private fun Chip(label: String, icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    val c = LocalLauncherColors.current
    Row(
        Modifier
            .height(56.dp)
            .shadow(8.dp, PillShape)
            .clip(PillShape)
            .background(c.surface)
            .clickable(role = Role.Button, onClick = onClick)
            .padding(horizontal = 22.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Icon(icon, null, Modifier.size(22.dp), tint = c.primary)
        Text(label, style = MaterialTheme.typography.bodyMedium, color = c.onSurface)
    }
}
