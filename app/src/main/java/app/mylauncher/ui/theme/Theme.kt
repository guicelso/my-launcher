package app.mylauncher.ui.theme

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Tokens de docs/02-design.md, medidos nas imagens oficiais do Android Auto (Coolwalk).
 * O tema dia é nosso: o Android Auto só tem tema escuro.
 */
@Immutable
data class LauncherColors(
    val isNight: Boolean,
    /** Fundo da tela, da barra e dos espaços entre os cards. */
    val background: Color,
    val rail: Color,
    /** Cards do painel, da gaveta e dos Ajustes. */
    val surface: Color,
    /** Pílulas secundárias, fundos de ícone e trilhas. */
    val surface2: Color,
    /** Linhas de lista dentro de um card (Ajustes). */
    val surfaceRow: Color,
    /** Menus, folhas e alertas por cima do conteúdo. */
    val surfaceElevated: Color,
    val outline: Color,
    val onSurface: Color,
    val onSurfaceVariant: Color,
    /** Destaque (ícones ativos, links, anel de foco). */
    val primary: Color,
    val onPrimary: Color,
    /** Botão principal em pílula (ex.: "Ler em voz alta", "Concluir"). */
    val primaryContainer: Color,
    val onPrimaryContainer: Color,
    val activeBg: Color,
    val selectedBg: Color,
    val onSelected: Color,
    /** Selo de notificações da barra. */
    val badge: Color,
    val onBadge: Color,
    /** Botão tocar/pausar. */
    val playContainer: Color,
    val onPlayContainer: Color,
    /** Botões e painéis flutuando sobre o mapa. */
    val mapPanel: Color,
    val mapPanelLine: Color,
    val danger: Color,
    val success: Color,
) {
    companion object {
        val Night = LauncherColors(
            isNight = true,
            background = Color(0xFF151616), rail = Color(0xFF151616),
            surface = Color(0xFF252626), surface2 = Color(0xFF3C3E3D), surfaceRow = Color(0xFF2F3130),
            surfaceElevated = Color(0xFF292A2A), outline = Color(0xFF3C3E3D),
            onSurface = Color(0xFFE3E3E3), onSurfaceVariant = Color(0xFFA8ABAE),
            primary = Color(0xFF8AB4F8), onPrimary = Color(0xFF0B1A33),
            primaryContainer = Color(0xFFC5D4FF), onPrimaryContainer = Color(0xFF0B2A66),
            activeBg = Color(0xFF3C3E3D), selectedBg = Color(0xFF004A77), onSelected = Color(0xFFC2E7FF),
            badge = Color(0xFF83EA8F), onBadge = Color(0xFF0B3D17),
            playContainer = Color(0xFFCFD6D3), onPlayContainer = Color(0xFF151616),
            mapPanel = Color(0xFF171E24), mapPanelLine = Color(0xFF2E3944),
            danger = Color(0xFFF2B8B5), success = Color(0xFF81C995),
        )
        val Day = LauncherColors(
            isNight = false,
            background = Color(0xFFE9EBEE), rail = Color(0xFFE9EBEE),
            surface = Color(0xFFFFFFFF), surface2 = Color(0xFFE8EAED), surfaceRow = Color(0xFFF1F3F4),
            surfaceElevated = Color(0xFFFFFFFF), outline = Color(0xFFDADCE0),
            onSurface = Color(0xFF1F1F1F), onSurfaceVariant = Color(0xFF5F6368),
            primary = Color(0xFF0B57D0), onPrimary = Color(0xFFFFFFFF),
            primaryContainer = Color(0xFFD3E3FD), onPrimaryContainer = Color(0xFF041E49),
            activeBg = Color(0xFFDDE3EA), selectedBg = Color(0xFFC2E7FF), onSelected = Color(0xFF001D35),
            badge = Color(0xFF137333), onBadge = Color(0xFFFFFFFF),
            playContainer = Color(0xFF1F1F1F), onPlayContainer = Color(0xFFFFFFFF),
            mapPanel = Color(0xFFFFFFFF), mapPanelLine = Color(0xFFDADCE0),
            danger = Color(0xFFB3261E), success = Color(0xFF137333),
        )
    }
}

/** Medidas de direção (Design for Driving): 1 dp = 1 px na multimídia (160 dpi). */
object Dimens {
    val touch = 76.dp
    val iconPrimary = 44.dp
    val iconSecondary = 36.dp
    val iconTertiary = 24.dp
    val railWidth = 96.dp
    val cardRadius = 24.dp
    val gap = 12.dp
    /** Ícone de app na barra (dock) e na grade de apps. */
    val dockIcon = 56.dp
    val gridIcon = 92.dp
}

val LocalLauncherColors = staticCompositionLocalOf { LauncherColors.Night }

private val LauncherTypography = Typography(
    displaySmall = TextStyle(fontSize = 40.sp, fontWeight = FontWeight.Medium, lineHeight = 46.sp),
    headlineMedium = TextStyle(fontSize = 32.sp, fontWeight = FontWeight.Medium, lineHeight = 38.sp),
    headlineSmall = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.Medium, lineHeight = 34.sp),
    titleLarge = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Medium, lineHeight = 30.sp),
    titleMedium = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Normal, lineHeight = 28.sp),
    bodyLarge = TextStyle(fontSize = 20.sp, lineHeight = 26.sp),
    bodyMedium = TextStyle(fontSize = 18.sp, lineHeight = 24.sp),
    labelLarge = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium),
    labelMedium = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium),
    labelSmall = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium),
)

private const val THEME_ANIM_MS = 300 // R2.12

@Composable
private fun animated(c: Color) = animateColorAsState(c, tween(THEME_ANIM_MS), label = "theme").value

@Composable
fun LauncherTheme(isNight: Boolean, fontScale: Float = 1f, content: @Composable () -> Unit) {
    val t = if (isNight) LauncherColors.Night else LauncherColors.Day
    val colors = LauncherColors(
        isNight = isNight,
        background = animated(t.background), rail = animated(t.rail),
        surface = animated(t.surface), surface2 = animated(t.surface2), surfaceRow = animated(t.surfaceRow),
        surfaceElevated = animated(t.surfaceElevated), outline = animated(t.outline),
        onSurface = animated(t.onSurface), onSurfaceVariant = animated(t.onSurfaceVariant),
        primary = animated(t.primary), onPrimary = animated(t.onPrimary),
        primaryContainer = animated(t.primaryContainer), onPrimaryContainer = animated(t.onPrimaryContainer),
        activeBg = animated(t.activeBg), selectedBg = animated(t.selectedBg), onSelected = animated(t.onSelected),
        badge = animated(t.badge), onBadge = animated(t.onBadge),
        playContainer = animated(t.playContainer), onPlayContainer = animated(t.onPlayContainer),
        mapPanel = animated(t.mapPanel), mapPanelLine = animated(t.mapPanelLine),
        danger = animated(t.danger), success = animated(t.success),
    )
    val scheme = if (isNight) {
        darkColorScheme(
            primary = colors.primary, onPrimary = colors.onPrimary, background = colors.background,
            surface = colors.surface, surfaceVariant = colors.surface2, onSurface = colors.onSurface,
            onSurfaceVariant = colors.onSurfaceVariant, outline = colors.outline, error = colors.danger,
        )
    } else {
        lightColorScheme(
            primary = colors.primary, onPrimary = colors.onPrimary, background = colors.background,
            surface = colors.surface, surfaceVariant = colors.surface2, onSurface = colors.onSurface,
            onSurfaceVariant = colors.onSurfaceVariant, outline = colors.outline, error = colors.danger,
        )
    }
    val density = LocalDensity.current
    CompositionLocalProvider(
        LocalLauncherColors provides colors,
        LocalDensity provides Density(density.density, density.fontScale * fontScale),
    ) {
        MaterialTheme(colorScheme = scheme, typography = LauncherTypography, content = content)
    }
}
