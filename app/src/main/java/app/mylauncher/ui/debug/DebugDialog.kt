package app.mylauncher.ui.debug

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import app.mylauncher.core.debug.DebugMediaPlayer
import app.mylauncher.data.vehicle.FakeVehicleSource
import app.mylauncher.data.weather.WeatherKind
import app.mylauncher.data.weather.WeatherRepository
import app.mylauncher.data.vehicle.VehicleState
import app.mylauncher.ui.components.PillShape
import app.mylauncher.ui.theme.LocalLauncherColors

/**
 * Painel de depuração (toque longo no relógio): simula o veículo (spec 002, R2.11)
 * e a mídia (player simulado), e força crash para validar o CrashGuard (spec 001, A7).
 */
@Composable
fun DebugDialog(
    simulate: Boolean,
    vehicle: VehicleState,
    fake: FakeVehicleSource,
    player: DebugMediaPlayer,
    weather: WeatherRepository,
    onSimulate: (Boolean) -> Unit,
    onDismiss: () -> Unit,
) {
    val c = LocalLauncherColors.current
    Dialog(onDismissRequest = onDismiss) {
        Column(
            Modifier
                .width(560.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(c.surface)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text("Depuração", style = MaterialTheme.typography.headlineSmall, color = c.onSurface)
            Row(Modifier.fillMaxWidth().height(76.dp), verticalAlignment = Alignment.CenterVertically) {
                Text("Simular veículo", style = MaterialTheme.typography.titleMedium, color = c.onSurface, modifier = Modifier.weight(1f))
                Switch(simulate, onSimulate)
            }
            if (simulate) {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    DebugButton(if (vehicle.headlightsOn == true) "Desligar faróis" else "Ligar faróis") {
                        fake.setHeadlights(vehicle.headlightsOn != true)
                    }
                    DebugButton(if (vehicle.reverse == true) "Sair da ré" else "Engatar ré") {
                        fake.setReverse(vehicle.reverse != true)
                    }
                    DebugButton("Velocidade +20") { fake.setSpeed(((vehicle.speedKmh ?: 0) + 20) % 140) }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    val low = (vehicle.batteryV ?: 12.3f) < 11.8f
                    DebugButton(if (low) "Bateria normal (12,3 V)" else "Bateria fraca (11,4 V)") { fake.setBattery(if (low) 12.3f else 11.4f) }
                }
            }
            var playerOn by remember { mutableStateOf(player.isRunning) }
            Row(Modifier.fillMaxWidth().height(76.dp), verticalAlignment = Alignment.CenterVertically) {
                Text("Simular player de música", style = MaterialTheme.typography.titleMedium, color = c.onSurface, modifier = Modifier.weight(1f))
                Switch(playerOn, { on -> if (on) player.start() else player.stop(); playerOn = on })
            }
            if (playerOn) {
                // App de mídia simulado (spec 018): "Sonora (simulado)" no seletor de mídia.
                var login by remember { mutableStateOf(player.isLoginRequired) }
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    DebugButton("Tocar/pausar") { player.togglePlay() }
                    DebugButton(if (login) "Concluir login" else "Exigir login") { login = !login; player.setLoginRequired(login) }
                }
            }
            var weatherKind by remember { mutableStateOf(weather.simulatedKind) }
            var weatherDay by remember { mutableStateOf(weather.simulatedDay) }
            Row(Modifier.fillMaxWidth().height(76.dp), verticalAlignment = Alignment.CenterVertically) {
                Text("Simular clima", style = MaterialTheme.typography.titleMedium, color = c.onSurface, modifier = Modifier.weight(1f))
                Switch(weatherKind != null, { on ->
                    weatherKind = if (on) WeatherKind.CLEAR else null
                    weather.simulate(weatherKind, weatherDay)
                })
            }
            if (weatherKind != null) {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    DebugButton("Próxima condição (${weatherKind!!.name.lowercase()})") {
                        val all = WeatherKind.entries
                        weatherKind = all[(all.indexOf(weatherKind!!) + 1) % all.size]
                        weather.simulate(weatherKind, weatherDay)
                    }
                    DebugButton(if (weatherDay) "Noite" else "Dia") {
                        weatherDay = !weatherDay
                        weather.simulate(weatherKind, weatherDay)
                    }
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                DebugButton("Forçar crash") { throw IllegalStateException("Crash forçado pelo painel de depuração") }
                DebugButton("Fechar", onDismiss)
            }
        }
    }
}

@Composable
private fun DebugButton(label: String, onClick: () -> Unit) {
    val c = LocalLauncherColors.current
    Box(
        Modifier
            .height(64.dp)
            .clip(PillShape)
            .background(c.surface2)
            .clickable(role = Role.Button, onClick = onClick)
            .padding(horizontal = 20.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(label, style = MaterialTheme.typography.labelLarge, color = c.onSurface)
    }
}
