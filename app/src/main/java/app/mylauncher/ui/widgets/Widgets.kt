package app.mylauncher.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.mylauncher.data.apps.AppsRepository
import app.mylauncher.data.media.NotificationsSummary
import app.mylauncher.data.vehicle.VehicleState
import app.mylauncher.data.weather.Weather
import app.mylauncher.data.weather.WeatherKind
import app.mylauncher.ui.components.AppIcon
import app.mylauncher.ui.components.LauncherCard
import app.mylauncher.ui.components.pressable
import app.mylauncher.ui.icons.LauncherIcons
import app.mylauncher.ui.theme.LocalLauncherColors
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val PtBr = Locale("pt", "BR")

fun weatherIcon(kind: WeatherKind, isDay: Boolean): ImageVector = when (kind) {
    WeatherKind.CLEAR -> if (isDay) LauncherIcons.Sun else LauncherIcons.Moon
    WeatherKind.PARTLY -> if (isDay) LauncherIcons.CloudSun else LauncherIcons.Cloud
    WeatherKind.CLOUDY -> LauncherIcons.Cloud
    WeatherKind.FOG -> LauncherIcons.Fog
    WeatherKind.DRIZZLE, WeatherKind.RAIN -> LauncherIcons.Rain
    WeatherKind.SNOW -> LauncherIcons.Snow
    WeatherKind.STORM -> LauncherIcons.Storm
}

@Composable
private fun WidgetHeader(title: String) {
    val c = LocalLauncherColors.current
    Text(title, style = MaterialTheme.typography.labelLarge, color = c.onSurfaceVariant, maxLines = 1, overflow = TextOverflow.Ellipsis)
}

/** Resumo do veículo pela MCU (spec 006/013). */
@Composable
fun VehicleWidget(vehicle: VehicleState, modifier: Modifier = Modifier) {
    LauncherCard(modifier) {
        Column(Modifier.fillMaxSize().padding(20.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            WidgetHeader("Veículo")
            VehicleRow(LauncherIcons.Battery, "Bateria", vehicle.batteryV?.let { String.format(PtBr, "%.1f V", it) })
            VehicleRow(LauncherIcons.Speed, "Velocidade", vehicle.speedKmh?.let { "$it km/h" })
            VehicleRow(LauncherIcons.Headlight, "Faróis", vehicle.headlightsOn?.let { if (it) "Ligados" else "Desligados" })
            VehicleRow(LauncherIcons.Reverse, "Marcha à ré", vehicle.reverse?.let { if (it) "Engatada" else "Não" })
        }
    }
}

@Composable
private fun VehicleRow(icon: ImageVector, label: String, value: String?) {
    val c = LocalLauncherColors.current
    Row(Modifier.fillMaxWidth().height(36.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        Icon(icon, null, Modifier.size(24.dp), tint = c.onSurfaceVariant)
        Text(label, style = MaterialTheme.typography.bodyLarge, color = c.onSurface, modifier = Modifier.weight(1f), maxLines = 1)
        Text(value ?: "—", style = MaterialTheme.typography.titleMedium, color = if (value != null) c.onSurface else c.onSurfaceVariant)
    }
}

/** Relógio e data grandes, centralizados. */
@Composable
fun ClockWidget(now: Long, modifier: Modifier = Modifier) {
    val c = LocalLauncherColors.current
    LauncherCard(modifier) {
        BoxWithConstraints(Modifier.fillMaxSize().padding(20.dp), contentAlignment = Alignment.Center) {
            val compact = maxWidth < 300.dp
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(SimpleDateFormat("HH:mm", PtBr).format(Date(now)), fontSize = if (compact) 52.sp else 80.sp, fontWeight = FontWeight.Medium, color = c.onSurface)
                Text(
                    dateLabel(now), style = MaterialTheme.typography.titleMedium, color = c.onSurfaceVariant,
                    textAlign = TextAlign.Center, maxLines = 2,
                )
            }
        }
    }
}

internal fun dateLabel(now: Long) =
    SimpleDateFormat("EEEE, d 'de' MMMM", PtBr).format(Date(now)).replaceFirstChar { it.uppercase() }

/** Últimas notificações (spec 004/013). */
@Composable
fun NotificationsWidget(summary: NotificationsSummary, apps: AppsRepository, onOpen: (String, android.app.PendingIntent?) -> Unit, modifier: Modifier = Modifier) {
    val c = LocalLauncherColors.current
    LauncherCard(modifier) {
        Column(Modifier.fillMaxSize().padding(horizontal = 12.dp, vertical = 16.dp)) {
            Row(Modifier.padding(horizontal = 8.dp)) { WidgetHeader(if (summary.count > 0) "Notificações · ${summary.count}" else "Notificações") }
            Spacer(Modifier.height(8.dp))
            if (summary.recent.isEmpty()) {
                Text("Nenhuma notificação", style = MaterialTheme.typography.bodyLarge, color = c.onSurfaceVariant, modifier = Modifier.padding(8.dp))
            }
            summary.recent.forEach { n ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(72.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .pressable { onOpen(n.packageName, n.contentIntent) }
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    AppIcon(apps, n.packageName, 40.dp)
                    Column(Modifier.weight(1f)) {
                        Text(n.sender.ifBlank { apps.label(n.packageName) }, style = MaterialTheme.typography.titleMedium, color = c.onSurface, maxLines = 1, overflow = TextOverflow.Ellipsis)
                        if (n.text.isNotBlank()) {
                            Text(n.text, style = MaterialTheme.typography.bodyMedium, color = c.onSurfaceVariant, maxLines = 1, overflow = TextOverflow.Ellipsis)
                        }
                    }
                }
            }
        }
    }
}
