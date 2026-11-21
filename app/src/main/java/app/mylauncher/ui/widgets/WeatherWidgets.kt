package app.mylauncher.ui.widgets

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.mylauncher.data.weather.Weather
import app.mylauncher.data.weather.WeatherKind
import app.mylauncher.ui.icons.LauncherIcons
import app.mylauncher.ui.theme.Dimens
import app.mylauncher.ui.theme.LocalLauncherColors
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val PtBr = Locale("pt", "BR")
private val CardShape = RoundedCornerShape(Dimens.cardRadius)
private val TileShape = RoundedCornerShape(18.dp)

/** Céu de fundo por condição e dia/noite (mesma linguagem de vidro do player, spec 010/013). */
private fun sky(kind: WeatherKind?, isDay: Boolean): Pair<Color, Color> = when (kind) {
    WeatherKind.CLEAR -> if (isDay) Color(0xFF2F6FB8) to Color(0xFF6FA8DC) else Color(0xFF0B1A33) to Color(0xFF1F3A66)
    WeatherKind.PARTLY -> if (isDay) Color(0xFF3E6A9C) to Color(0xFF86A9CF) else Color(0xFF111C30) to Color(0xFF2A3A57)
    WeatherKind.CLOUDY -> if (isDay) Color(0xFF4E5C6B) to Color(0xFF8795A3) else Color(0xFF1A2029) to Color(0xFF353F4C)
    WeatherKind.FOG -> if (isDay) Color(0xFF5E6974) to Color(0xFF97A1AA) else Color(0xFF1E2329) to Color(0xFF3B424A)
    WeatherKind.DRIZZLE, WeatherKind.RAIN -> if (isDay) Color(0xFF34495E) to Color(0xFF627A90) else Color(0xFF121A24) to Color(0xFF2A3644)
    WeatherKind.STORM -> Color(0xFF1D2033) to Color(0xFF3E4262)
    WeatherKind.SNOW -> if (isDay) Color(0xFF5F7890) to Color(0xFFA9BCCD) else Color(0xFF1B2533) to Color(0xFF3C4C60)
    null -> Color(0xFF26303A) to Color(0xFF3A4652)
}

/** Conteúdo sobre o vidro: sempre claro (como no player com capa). */
private val OnGlass = Color(0xFFF5F6F7)
private val OnGlassVariant = Color(0xFFDCE2E8)

/**
 * Card de clima em vidro fosco: céu em degradê da condição, um ícone grande e esmaecido dando
 * profundidade, película escura embaixo e fio de luz na borda. O texto fica claro.
 */
@Composable
fun WeatherGlassCard(weather: Weather?, modifier: Modifier = Modifier, content: @Composable BoxScope.() -> Unit) {
    val c = LocalLauncherColors.current
    val (top, bottom) = sky(weather?.kind, weather?.isDay ?: c.isNight.not())
    val a by animateColorAsState(top, tween(600), label = "skyTop")
    val b by animateColorAsState(bottom, tween(600), label = "skyBottom")
    Box(
        modifier
            .clip(CardShape)
            .background(Brush.linearGradient(listOf(b, a)))
            .border(1.dp, Color.White.copy(alpha = 0.10f), CardShape),
    ) {
        if (weather != null) {
            Icon(
                weatherIcon(weather.kind, weather.isDay), null,
                Modifier.align(Alignment.TopEnd).offset(x = 36.dp, y = (-28).dp).size(200.dp),
                tint = Color.White.copy(alpha = 0.10f),
            )
        }
        Box(Modifier.fillMaxSize().background(Brush.verticalGradient(0f to Color.Transparent, 1f to Color.Black.copy(alpha = 0.35f))))
        CompositionLocalProvider(
            LocalLauncherColors provides c.copy(onSurface = OnGlass, onSurfaceVariant = OnGlassVariant, primary = OnGlass, outline = Color.White.copy(alpha = 0.25f)),
        ) { content() }
    }
}

/** "Ladrilho" de vidro dentro do card (horas, dias, detalhes). */
@Composable
private fun GlassTile(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Box(
        modifier
            .clip(TileShape)
            .background(Color.White.copy(alpha = 0.10f))
            .border(1.dp, Color.White.copy(alpha = 0.12f), TileShape),
        contentAlignment = Alignment.Center,
    ) { content() }
}

@Composable
private fun Header(weather: Weather) {
    val c = LocalLauncherColors.current
    Text(
        listOfNotNull(weather.city ?: "Clima", if (weather.simulated) "simulado" else null).joinToString(" · "),
        style = MaterialTheme.typography.labelLarge, color = c.onSurfaceVariant, maxLines = 1,
    )
}

@Composable
private fun Waiting(modifier: Modifier) {
    WeatherGlassCard(null, modifier) {
        Column(Modifier.fillMaxSize().padding(20.dp), verticalArrangement = Arrangement.Center) {
            Text("Clima", style = MaterialTheme.typography.labelLarge, color = OnGlassVariant)
            Text("Aguardando localização e internet", style = MaterialTheme.typography.bodyLarge, color = OnGlass)
        }
    }
}

@Composable
private fun BigNow(weather: Weather, tempSize: Int, iconSize: Dp) {
    val c = LocalLauncherColors.current
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(14.dp)) {
        Icon(weatherIcon(weather.kind, weather.isDay), weather.description, Modifier.size(iconSize), tint = c.onSurface)
        Text("${weather.temperature}°", fontSize = tempSize.sp, fontWeight = FontWeight.Light, color = c.onSurface)
    }
}

/** Modo 1 — Clima agora: temperatura, condição, mínima/máxima e sensação. */
@Composable
fun WeatherNowWidget(weather: Weather?, modifier: Modifier = Modifier) {
    if (weather == null) return Waiting(modifier)
    WeatherGlassCard(weather, modifier) {
        BoxWithConstraints(Modifier.fillMaxSize().padding(20.dp)) {
            val compact = maxWidth < 300.dp || maxHeight < 220.dp
            val c = LocalLauncherColors.current
            Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
                Header(weather)
                BigNow(weather, if (compact) 46 else 68, if (compact) 44.dp else 64.dp)
                Column {
                    Text(weather.description, style = MaterialTheme.typography.titleMedium, color = c.onSurface, maxLines = 1)
                    val extra = listOfNotNull(
                        if (weather.min != null && weather.max != null) "${weather.min}° / ${weather.max}°" else null,
                        weather.feelsLike?.let { "Sensação $it°" }.takeIf { !compact },
                    ).joinToString(" · ")
                    if (extra.isNotBlank()) Text(extra, style = MaterialTheme.typography.bodyMedium, color = c.onSurfaceVariant, maxLines = 1)
                }
            }
        }
    }
}

/** Modo 2 — Clima detalhado: agora + sensação, umidade, vento, chuva e nascer/pôr do sol. */
@Composable
fun WeatherDetailedWidget(weather: Weather?, modifier: Modifier = Modifier) {
    if (weather == null) return Waiting(modifier)
    WeatherGlassCard(weather, modifier) {
        BoxWithConstraints(Modifier.fillMaxSize().padding(20.dp)) {
            if (maxWidth < 300.dp) {
                // Estreito: detalhes em lista curta.
                Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Header(weather)
                    BigNow(weather, 44, 40.dp)
                    Detail(LauncherIcons.Thermometer, "Sensação", weather.feelsLike?.let { "$it°" })
                    Detail(LauncherIcons.Droplet, "Umidade", weather.humidity?.let { "$it%" })
                    Detail(LauncherIcons.Umbrella, "Chuva", weather.rainChance?.let { "$it%" })
                }
                return@BoxWithConstraints
            }
            val c = LocalLauncherColors.current
            val showSun = weather.sunrise != null && weather.sunset != null && maxHeight > 300.dp
            Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Header(weather)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    BigNow(weather, 60, 56.dp)
                    Spacer(Modifier.weight(1f))
                    Column(horizontalAlignment = Alignment.End) {
                        Text(weather.description, style = MaterialTheme.typography.titleMedium, color = c.onSurface, maxLines = 1)
                        if (weather.min != null && weather.max != null) {
                            Text("${weather.min}° / ${weather.max}°", style = MaterialTheme.typography.bodyMedium, color = c.onSurfaceVariant)
                        }
                    }
                }
                Row(Modifier.fillMaxWidth().weight(1f), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    StatTile(LauncherIcons.Thermometer, "Sensação", weather.feelsLike?.let { "$it°" }, Modifier.weight(1f))
                    StatTile(LauncherIcons.Droplet, "Umidade", weather.humidity?.let { "$it%" }, Modifier.weight(1f))
                    StatTile(LauncherIcons.Wind, "Vento km/h", weather.windKmh?.let { "$it" }, Modifier.weight(1f))
                    StatTile(LauncherIcons.Umbrella, "Chuva", weather.rainChance?.let { "$it%" }, Modifier.weight(1f))
                }
                if (showSun) {
                    Row(horizontalArrangement = Arrangement.spacedBy(20.dp), verticalAlignment = Alignment.CenterVertically) {
                        SunTime(LauncherIcons.Sunrise, weather.sunrise!!)
                        SunTime(LauncherIcons.Sunset, weather.sunset!!)
                    }
                }
            }
        }
    }
}

@Composable
private fun Detail(icon: ImageVector, label: String, value: String?) {
    val c = LocalLauncherColors.current
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        Icon(icon, null, Modifier.size(22.dp), tint = c.onSurfaceVariant)
        Text(label, style = MaterialTheme.typography.bodyMedium, color = c.onSurfaceVariant, modifier = Modifier.weight(1f))
        Text(value ?: "—", style = MaterialTheme.typography.titleMedium, color = c.onSurface)
    }
}

@Composable
private fun StatTile(icon: ImageVector, label: String, value: String?, modifier: Modifier) {
    val c = LocalLauncherColors.current
    GlassTile(modifier.fillMaxHeight()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.padding(8.dp)) {
            Icon(icon, null, Modifier.size(26.dp), tint = c.onSurface)
            Text(value ?: "—", style = MaterialTheme.typography.titleMedium, color = c.onSurface, maxLines = 1)
            Text(label, style = MaterialTheme.typography.labelSmall, color = c.onSurfaceVariant, maxLines = 1)
        }
    }
}

@Composable
private fun SunTime(icon: ImageVector, time: Long) {
    val c = LocalLauncherColors.current
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Icon(icon, null, Modifier.size(24.dp), tint = c.onSurface)
        Text(SimpleDateFormat("HH:mm", PtBr).format(Date(time)), style = MaterialTheme.typography.titleMedium, color = c.onSurface)
    }
}

/** Modo 3 — Clima por hora: agora + próximas horas em ladrilhos (quantos couberem). */
@Composable
fun WeatherHourlyWidget(weather: Weather?, modifier: Modifier = Modifier) {
    if (weather == null) return Waiting(modifier)
    WeatherGlassCard(weather, modifier) {
        BoxWithConstraints(Modifier.fillMaxSize().padding(20.dp)) {
            val c = LocalLauncherColors.current
            val count = ((maxWidth + 8.dp) / (72.dp + 8.dp)).toInt().coerceIn(2, 8)
            Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Header(weather)
                        Text(weather.description, style = MaterialTheme.typography.titleMedium, color = c.onSurface, maxLines = 1)
                    }
                    BigNow(weather, 44, 40.dp)
                }
                Row(Modifier.fillMaxWidth().weight(1f), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    weather.hourly.take(count).forEachIndexed { i, h ->
                        GlassTile(Modifier.weight(1f).fillMaxHeight()) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxHeight().padding(vertical = 8.dp)) {
                                Text(
                                    if (i == 0) "Agora" else SimpleDateFormat("HH'h'", PtBr).format(Date(h.time)),
                                    style = MaterialTheme.typography.labelMedium, color = c.onSurfaceVariant,
                                )
                                Icon(weatherIcon(h.kind, h.isDay), null, Modifier.size(30.dp), tint = c.onSurface)
                                Text("${h.temperature}°", style = MaterialTheme.typography.titleMedium, color = c.onSurface)
                            }
                        }
                    }
                }
            }
        }
    }
}

/** Modo 4 — Previsão da semana: dias em ladrilhos (largo) ou em lista (estreito). */
@Composable
fun WeatherDailyWidget(weather: Weather?, modifier: Modifier = Modifier) {
    if (weather == null) return Waiting(modifier)
    WeatherGlassCard(weather, modifier) {
        BoxWithConstraints(Modifier.fillMaxSize().padding(20.dp)) {
            val c = LocalLauncherColors.current
            val days = weather.daily
            fun dayName(i: Int, t: Long) = when (i) {
                0 -> "Hoje"
                1 -> "Amanhã"
                else -> SimpleDateFormat("EEE", PtBr).format(Date(t)).trimEnd('.').replaceFirstChar { it.uppercase() }
            }
            if (maxWidth >= 520.dp) {
                val count = ((maxWidth + 8.dp) / (78.dp + 8.dp)).toInt().coerceIn(3, 7)
                Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Header(weather)
                    Row(Modifier.fillMaxWidth().weight(1f), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        days.take(count).forEachIndexed { i, d ->
                            GlassTile(Modifier.weight(1f).fillMaxHeight()) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxHeight().padding(vertical = 8.dp)) {
                                    Text(dayName(i, d.date), style = MaterialTheme.typography.labelMedium, color = c.onSurfaceVariant, maxLines = 1)
                                    Icon(weatherIcon(d.kind, true), null, Modifier.size(32.dp), tint = c.onSurface)
                                    Text("${d.max}°", style = MaterialTheme.typography.titleMedium, color = c.onSurface)
                                    Text("${d.min}°", style = MaterialTheme.typography.bodyMedium, color = c.onSurfaceVariant)
                                }
                            }
                        }
                    }
                }
            } else {
                val rows = ((maxHeight - 28.dp) / 44.dp).toInt().coerceIn(2, 7)
                Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Header(weather)
                    days.take(rows).forEachIndexed { i, d ->
                        Row(Modifier.fillMaxWidth().height(40.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            Text(dayName(i, d.date), style = MaterialTheme.typography.bodyLarge, color = c.onSurface, modifier = Modifier.width(78.dp), maxLines = 1)
                            Icon(weatherIcon(d.kind, true), null, Modifier.size(26.dp), tint = c.onSurface)
                            Spacer(Modifier.weight(1f))
                            Text("${d.max}°", style = MaterialTheme.typography.titleMedium, color = c.onSurface)
                            Text("${d.min}°", style = MaterialTheme.typography.bodyMedium, color = c.onSurfaceVariant, textAlign = TextAlign.End, modifier = Modifier.width(36.dp))
                        }
                    }
                }
            }
        }
    }
}

/** Data, hora e clima juntos, centralizados, no mesmo vidro. */
@Composable
fun ClockWeatherWidget(now: Long, weather: Weather?, modifier: Modifier = Modifier) {
    WeatherGlassCard(weather, modifier) {
        val c = LocalLauncherColors.current
        BoxWithConstraints(Modifier.fillMaxSize().padding(20.dp), contentAlignment = Alignment.Center) {
            val compact = maxWidth < 300.dp || maxHeight < 260.dp
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(if (compact) 6.dp else 10.dp)) {
                Text(SimpleDateFormat("HH:mm", PtBr).format(Date(now)), fontSize = if (compact) 48.sp else 76.sp, fontWeight = FontWeight.Light, color = c.onSurface)
                Text(dateLabel(now), style = MaterialTheme.typography.titleMedium, color = c.onSurfaceVariant, textAlign = TextAlign.Center, maxLines = 1)
                Box(Modifier.padding(vertical = if (compact) 2.dp else 6.dp).size(width = 48.dp, height = 1.dp).background(c.outline))
                if (weather != null) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Icon(weatherIcon(weather.kind, weather.isDay), weather.description, Modifier.size(if (compact) 32.dp else 44.dp), tint = c.onSurface)
                        Text("${weather.temperature}°", fontSize = if (compact) 28.sp else 36.sp, fontWeight = FontWeight.Light, color = c.onSurface)
                        if (!compact) {
                            Column {
                                Text(weather.description, style = MaterialTheme.typography.bodyLarge, color = c.onSurface, maxLines = 1)
                                if (weather.min != null && weather.max != null) {
                                    Text("${weather.min}° / ${weather.max}°", style = MaterialTheme.typography.bodyMedium, color = c.onSurfaceVariant)
                                }
                            }
                        }
                    }
                } else {
                    Text("Clima aguardando localização", style = MaterialTheme.typography.bodyMedium, color = c.onSurfaceVariant)
                }
            }
        }
    }
}
