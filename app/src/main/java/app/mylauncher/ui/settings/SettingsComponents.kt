package app.mylauncher.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import app.mylauncher.ui.components.PillShape
import app.mylauncher.ui.icons.LauncherIcons
import app.mylauncher.ui.theme.Dimens
import app.mylauncher.ui.theme.LocalLauncherColors

private val RowShape = RoundedCornerShape(20.dp)

@Composable
fun SettingsGroup(title: String, description: String? = null, content: @Composable ColumnScope.() -> Unit) {
    val c = LocalLauncherColors.current
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(title, style = MaterialTheme.typography.headlineSmall, color = c.onSurface)
        if (description != null) {
            Text(description, style = MaterialTheme.typography.bodyMedium, color = c.onSurfaceVariant)
        }
        Column(Modifier.padding(top = 8.dp), verticalArrangement = Arrangement.spacedBy(8.dp), content = content)
    }
}

@Composable
fun SettingRow(
    title: String,
    subtitle: String? = null,
    onClick: (() -> Unit)? = null,
    trailing: @Composable RowScope.() -> Unit = {},
) {
    val c = LocalLauncherColors.current
    Row(
        Modifier
            .fillMaxWidth()
            .heightIn(min = Dimens.touch)
            .clip(RowShape)
            .background(c.surface)
            .then(if (onClick != null) Modifier.clickable(role = Role.Button, onClick = onClick) else Modifier)
            .padding(horizontal = 24.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Column(Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.titleMedium, color = c.onSurface)
            if (subtitle != null) Text(subtitle, style = MaterialTheme.typography.bodyMedium, color = c.onSurfaceVariant)
        }
        trailing()
    }
}

@Composable
fun ToggleRow(title: String, checked: Boolean, subtitle: String? = null, onChange: (Boolean) -> Unit) {
    val c = LocalLauncherColors.current
    SettingRow(title, subtitle, onClick = { onChange(!checked) }) {
        Switch(
            checked, onChange,
            colors = SwitchDefaults.colors(checkedTrackColor = c.primary, checkedThumbColor = c.onPrimary),
        )
    }
}

/** Opções em pílulas, como no protótipo (Tema: Automático / Dia / Noite). */
@Composable
fun <T> ChoicePills(options: List<Pair<T, String>>, selected: T, onSelect: (T) -> Unit) {
    val c = LocalLauncherColors.current
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        options.forEach { (value, label) ->
            val on = value == selected
            Box(
                Modifier
                    .height(Dimens.touch)
                    .clip(PillShape)
                    .background(if (on) c.selectedBg else c.surface)
                    .border(2.dp, if (on) c.primary else c.outline, PillShape)
                    .clickable(role = Role.RadioButton) { onSelect(value) }
                    .padding(horizontal = 28.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(label, style = MaterialTheme.typography.titleMedium, color = if (on) c.onSelected else c.onSurface)
            }
        }
    }
}

/** Linha de escolha única (ex.: app de navegação padrão). */
@Composable
fun RadioRow(title: String, selected: Boolean, leading: @Composable () -> Unit = {}, onClick: () -> Unit) {
    val c = LocalLauncherColors.current
    Row(
        Modifier
            .fillMaxWidth()
            .height(Dimens.touch)
            .clip(RowShape)
            .background(if (selected) c.selectedBg else c.surface)
            .clickable(role = Role.RadioButton, onClick = onClick)
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        leading()
        Text(title, style = MaterialTheme.typography.titleMedium, color = if (selected) c.onSelected else c.onSurface, modifier = Modifier.weight(1f))
        if (selected) Icon(LauncherIcons.Check, "Selecionado", Modifier.size(28.dp), tint = c.primary)
    }
}

@Composable
fun TextInput(value: String, onChange: (String) -> Unit, placeholder: String, modifier: Modifier = Modifier) {
    val c = LocalLauncherColors.current
    Box(
        modifier
            .height(64.dp)
            .clip(PillShape)
            .background(c.surface)
            .border(1.dp, c.outline, PillShape)
            .padding(horizontal = 22.dp),
        contentAlignment = Alignment.CenterStart,
    ) {
        if (value.isEmpty()) Text(placeholder, style = MaterialTheme.typography.bodyLarge, color = c.onSurfaceVariant)
        BasicTextField(
            value = value,
            onValueChange = onChange,
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = c.onSurface),
            cursorBrush = SolidColor(c.primary),
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
