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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import app.mylauncher.data.vehicle.FakeVehicleSource
import app.mylauncher.data.vehicle.VehicleState
import app.mylauncher.ui.components.PillShape
import app.mylauncher.ui.theme.LocalLauncherColors

/**
 * Painel de depuração (toque longo no relógio): simula o veículo (spec 002, R2.11)
 * e força crash para validar o CrashGuard (spec 001, A7).
 */
@Composable
fun DebugDialog(
    simulate: Boolean,
    vehicle: VehicleState,
    fake: FakeVehicleSource,
    onSimulate: (Boolean) -> Unit,
    onDismiss: () -> Unit,
) {
    val c = LocalLauncherColors.current
    Dialog(onDismissRequest = onDismiss) {
        Column(
            Modifier
                .width(560.dp)
                .clip(RoundedCornerShape(28.dp))
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
