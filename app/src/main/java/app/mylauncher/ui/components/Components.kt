package app.mylauncher.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.mylauncher.data.apps.AppsRepository
import app.mylauncher.ui.theme.Dimens
import app.mylauncher.ui.theme.LocalLauncherColors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/** Card padrão (raio 28, fundo surface). */
@Composable
fun LauncherCard(modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) {
    val c = LocalLauncherColors.current
    Column(
        modifier
            .clip(RoundedCornerShape(Dimens.cardRadius))
            .background(c.surface),
        content = content,
    )
}

/** Botão de ícone com alvo de toque de 76 dp (Design for Driving). */
@Composable
fun TouchIconButton(
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: Dp = Dimens.touch,
    iconSize: Dp = Dimens.iconSecondary,
    tint: Color = LocalLauncherColors.current.onSurface,
    background: Color = Color.Transparent,
    shape: androidx.compose.ui.graphics.Shape = CircleShape,
    enabled: Boolean = true,
) {
    Box(
        modifier
            .size(size)
            .clip(shape)
            .background(background)
            .pressable(enabled = enabled, onClickLabel = contentDescription, onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Icon(icon, contentDescription, Modifier.size(iconSize), tint = if (enabled) tint else tint.copy(alpha = 0.38f))
    }
}

/** Ícone real do app (PackageManager), carregado fora da thread principal. */
@Composable
fun AppIcon(apps: AppsRepository, pkg: String, size: Dp, modifier: Modifier = Modifier) {
    val px = with(LocalDensity.current) { size.roundToPx() }
    val style by apps.iconStyle.collectAsState()
    val bitmap by produceState<ImageBitmap?>(null, pkg, px, style) {
        value = withContext(Dispatchers.IO) { apps.icon(pkg, px) }
    }
    val bmp = bitmap
    if (bmp != null) {
        Image(bmp, contentDescription = null, modifier = modifier.size(size))
    } else {
        LetterIcon(apps.label(pkg), size, modifier)
    }
}

/** Ícone de reserva: círculo com a inicial (como no protótipo). */
@Composable
fun LetterIcon(label: String, size: Dp, modifier: Modifier = Modifier) {
    val c = LocalLauncherColors.current
    Box(
        modifier.size(size).clip(CircleShape).background(c.surface2),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            label.firstOrNull()?.uppercase() ?: "?",
            color = c.onSurface,
            fontSize = (size.value * 0.4f).sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

val PillShape = RoundedCornerShape(50)

/**
 * Telas que ocupam a área de conteúdo (gaveta, Ajustes): um card grande, com a mesma margem
 * dos cards do painel, como o Acesso rápido aos apps do Android Auto.
 */
@Composable
fun ScreenPanel(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    val c = LocalLauncherColors.current
    Box(modifier.padding(start = 8.dp, top = Dimens.gap, end = Dimens.gap, bottom = Dimens.gap)) {
        Box(
            Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(Dimens.cardRadius))
                .background(c.surface),
        ) { content() }
    }
}
