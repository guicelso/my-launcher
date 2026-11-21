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

/** Tokens de docs/02-design.md. */
@Immutable
data class LauncherColors(
    val isNight: Boolean,
    val background: Color,
    val rail: Color,
    val surface: Color,
    val surface2: Color,
    val outline: Color,
    val onSurface: Color,
    val onSurfaceVariant: Color,
    val primary: Color,
    val onPrimary: Color,
    val activeBg: Color,
    val selectedBg: Color,
    val onSelected: Color,
    val danger: Color,
    val success: Color,
) {
    companion object {
        val Night = LauncherColors(
            isNight = true,
            background = Color(0xFF000000), rail = Color(0xFF000000),
            surface = Color(0xFF1E1F22), surface2 = Color(0xFF2B2C30), outline = Color(0xFF3A3B40),
            onSurface = Color(0xFFE8EAED), onSurfaceVariant = Color(0xFFA9ADB3),
            primary = Color(0xFF8AB4F8), onPrimary = Color(0xFF0B1A33),
            activeBg = Color(0xFF2B2C30), selectedBg = Color(0xFF2B3A55), onSelected = Color(0xFFD2E3FC),
            danger = Color(0xFFF28B82), success = Color(0xFF81C995),
        )
        val Day = LauncherColors(
            isNight = false,
            background = Color(0xFFE8EAED), rail = Color(0xFFFFFFFF),
            surface = Color(0xFFFFFFFF), surface2 = Color(0xFFF1F3F4), outline = Color(0xFFDADCE0),
            onSurface = Color(0xFF202124), onSurfaceVariant = Color(0xFF5F6368),
            primary = Color(0xFF1A73E8), onPrimary = Color(0xFFFFFFFF),
            activeBg = Color(0xFFE8F0FE), selectedBg = Color(0xFFD2E3FC), onSelected = Color(0xFF0B1A33),
            danger = Color(0xFFC5221F), success = Color(0xFF188038),
        )
    }
}

/** Medidas de direção (Design for Driving): 1 dp = 1 px na multimídia (160 dpi). */
object Dimens {
    val touch = 76.dp
    val iconPrimary = 44.dp
    val iconSecondary = 36.dp
    val iconTertiary = 24.dp
    val railWidth = 104.dp
    val cardRadius = 28.dp
    val gap = 16.dp
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
        surface = animated(t.surface), surface2 = animated(t.surface2), outline = animated(t.outline),
        onSurface = animated(t.onSurface), onSurfaceVariant = animated(t.onSurfaceVariant),
        primary = animated(t.primary), onPrimary = animated(t.onPrimary),
        activeBg = animated(t.activeBg), selectedBg = animated(t.selectedBg), onSelected = animated(t.onSelected),
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
