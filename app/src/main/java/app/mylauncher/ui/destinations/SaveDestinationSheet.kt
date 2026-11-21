package app.mylauncher.ui.destinations

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import app.mylauncher.data.destinations.SharedPlace
import app.mylauncher.data.prefs.Destination
import app.mylauncher.ui.components.PillShape
import app.mylauncher.ui.components.pressable
import app.mylauncher.ui.icons.LauncherIcons
import app.mylauncher.ui.media.PillButton
import app.mylauncher.ui.settings.TextInput
import app.mylauncher.ui.theme.LocalLauncherColors

/**
 * Painel "Salvar como…" (spec 012): toques grandes para Casa e Trabalho, favorito com nome
 * opcional. Desenhado dentro da janela do launcher (não é Dialog) para não mostrar as barras.
 */
@Composable
fun SaveDestinationSheet(
    place: SharedPlace,
    onSaveHome: (Destination) -> Unit,
    onSaveWork: (Destination) -> Unit,
    onSaveFavorite: (Destination) -> Unit,
    onDismiss: () -> Unit,
) {
    val c = LocalLauncherColors.current
    var name by remember(place) { mutableStateOf(place.title.orEmpty()) }
    BackHandler(onBack = onDismiss)
    Box(
        Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.55f))
            .pressable(role = null, onClick = onDismiss),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            Modifier
                .width(640.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(c.surfaceElevated)
                .pressable(role = null) { }
                .padding(28.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp),
        ) {
            Text("Salvar destino", style = MaterialTheme.typography.headlineSmall, color = c.onSurface)
            Text(
                place.title ?: place.query, style = MaterialTheme.typography.bodyLarge, color = c.onSurfaceVariant,
                maxLines = 2, overflow = TextOverflow.Ellipsis,
            )
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                BigChoice("Casa", LauncherIcons.Home, Modifier.weight(1f)) { onSaveHome(Destination("Casa", place.query)) }
                BigChoice("Trabalho", LauncherIcons.Work, Modifier.weight(1f)) { onSaveWork(Destination("Trabalho", place.query)) }
            }
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                TextInput(name, { name = it }, "Nome do favorito (ex.: Academia)", Modifier.weight(1f))
                PillButton("Salvar favorito") {
                    onSaveFavorite(Destination(name.trim().ifBlank { place.title ?: "Favorito" }, place.query))
                }
            }
            PillButton("Cancelar", primary = false, onClick = onDismiss)
        }
    }
}

@Composable
private fun BigChoice(label: String, icon: ImageVector, modifier: Modifier, onClick: () -> Unit) {
    val c = LocalLauncherColors.current
    Row(
        modifier
            .clip(RoundedCornerShape(20.dp))
            .background(c.surface2)
            .pressable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 22.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Box(Modifier.size(52.dp).clip(PillShape).background(c.surface), contentAlignment = Alignment.Center) {
            Icon(icon, null, Modifier.size(28.dp), tint = c.primary)
        }
        Text(label, style = MaterialTheme.typography.titleLarge, color = c.onSurface, modifier = Modifier.fillMaxWidth())
    }
}
