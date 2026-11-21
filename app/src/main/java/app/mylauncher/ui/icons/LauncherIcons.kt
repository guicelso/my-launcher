package app.mylauncher.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.addPathNodes
import androidx.compose.ui.unit.dp

/**
 * Ícones próprios (traço 2 em grade 24×24), os mesmos do protótipo em design/wireframes.
 * Sem ativos do Google (docs/02-design.md).
 */
object LauncherIcons {
    private fun circle(cx: Float, cy: Float, r: Float) =
        "M${cx - r},${cy}a$r,$r 0 1,0 ${2 * r},0a$r,$r 0 1,0 ${-2 * r},0"

    private fun rect(x: Float, y: Float, w: Float, h: Float, rx: Float): String {
        val iw = w - 2 * rx
        val ih = h - 2 * rx
        return "M${x + rx},${y}h${iw}a$rx,$rx 0 0 1 $rx,$rx" + "v${ih}a$rx,$rx 0 0 1 ${-rx},$rx" +
            "h${-iw}a$rx,$rx 0 0 1 ${-rx},${-rx}" + "v${-ih}a$rx,$rx 0 0 1 $rx,${-rx}z"
    }

    private class P(val d: String, val filled: Boolean = false)

    private fun icon(name: String, vararg paths: P, strokeWidth: Float = 2f): ImageVector {
        val b = ImageVector.Builder(name, 24.dp, 24.dp, 24f, 24f)
        paths.forEach { p ->
            if (p.filled) {
                b.addPath(addPathNodes(p.d), fill = SolidColor(Color.Black))
            } else {
                b.addPath(
                    addPathNodes(p.d),
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = strokeWidth,
                    strokeLineCap = StrokeCap.Round,
                    strokeLineJoin = StrokeJoin.Round,
                )
            }
        }
        return b.build()
    }

    private fun s(d: String) = P(d)
    private fun f(d: String) = P(d, filled = true)

    val Dashboard = icon("dashboard", s(rect(3f, 3f, 10f, 18f, 2.5f)), s(rect(15f, 3f, 6f, 8f, 2f)), s(rect(15f, 13f, 6f, 8f, 2f)))
    val Apps = icon(
        "apps",
        *listOf(5f, 12f, 19f).flatMap { y -> listOf(5f, 12f, 19f).map { x -> f(circle(x, y, 2.2f)) } }.toTypedArray(),
    )
    val Bell = icon("bell", s("M6 8a6 6 0 0 1 12 0c0 7 3 9 3 9H3s3-2 3-9"), s("M10.3 21a1.9 1.9 0 0 0 3.4 0"))
    val Mic = icon("mic", s(rect(9f, 3f, 6f, 11f, 3f)), s("M5 11a7 7 0 0 0 14 0M12 18v3"))
    val Search = icon("search", s(circle(11f, 11f, 7f)), s("m20 20-3.5-3.5"))
    val Home = icon("home", s("M3 10.5 12 3l9 7.5"), s("M5 9.5V21h14V9.5"))
    val Work = icon("work", s(rect(3f, 7f, 18f, 13f, 2f)), s("M9 7V5a2 2 0 0 1 2-2h2a2 2 0 0 1 2 2v2"))
    val Fuel = icon("fuel", s("M3 22V8l6-5 6 5v14"), s("M15 12h6v10"), s("M7 12h4M7 16h4"))
    val Play = icon("play", f("M7 4v16l13-8z"))
    val Pause = icon("pause", f(rect(6f, 4f, 4f, 16f, 1f)), f(rect(14f, 4f, 4f, 16f, 1f)))
    val Previous = icon("previous", f("M19 20 9 12l10-8z"), s("M5 19V5"))
    val Next = icon("next", f("M5 4l10 8-10 8z"), s("M19 5v14"))
    val Shuffle = icon("shuffle", s("M16 3h5v5"), s("M4 20 21 3"), s("M21 16v5h-5"), s("M15 15l6 6"), s("M4 4l5 5"))
    val Repeat = icon("repeat", s("M17 2l4 4-4 4"), s("M3 11V9a3 3 0 0 1 3-3h15"), s("M7 22l-4-4 4-4"), s("M21 13v2a3 3 0 0 1-3 3H3"))
    val Queue = icon("queue", s("M3 6h13M3 12h13M3 18h8"), f("M17 15v6l4-3z"))
    val Locate = icon("locate", s(circle(12f, 12f, 4f)), s("M12 2v3M12 19v3M2 12h3M19 12h3"))
    val Plus = icon("plus", s("M12 5v14M5 12h14"))
    val Minus = icon("minus", s("M5 12h14"))
    val Compass = icon("compass", s(circle(12f, 12f, 9f)), f("M12 5 15 13H9z"))
    val External = icon("external", s("M14 4h6v6"), s("M20 4 10 14"), s("M19 14v5a1 1 0 0 1-1 1H5a1 1 0 0 1-1-1V6a1 1 0 0 1 1-1h5"))
    val Expand = icon("expand", s("M15 3h6v6"), s("M9 21H3v-6"), s("M21 3l-7 7"), s("M3 21l7-7"))
    val Close = icon("close", s("M6 6l12 12M18 6 6 18"))
    val Back = icon("back", s("M15 18l-6-6 6-6"))
    val Chevron = icon("chevron", s("M9 18l6-6-6-6"))
    val Signal = icon("signal", s("M4 20v-3M9 20v-6M14 20v-9M19 20V5"), strokeWidth = 2.4f)
    val Wifi = icon("wifi", s("M5 12.5a10 10 0 0 1 14 0"), s("M8.5 16a5 5 0 0 1 7 0"), s("M12 19.5h.01"), strokeWidth = 2.4f)
    val Bluetooth = icon("bluetooth", s("m7 7 10 10-5 5V2l5 5L7 17"), strokeWidth = 2.4f)
    val Phone = icon(
        "phone",
        s("M22 16.9v3a2 2 0 0 1-2.2 2 19.8 19.8 0 0 1-8.6-3.1 19.5 19.5 0 0 1-6-6A19.8 19.8 0 0 1 2.1 4.2 2 2 0 0 1 4.1 2h3a2 2 0 0 1 2 1.7c.1.9.4 1.8.7 2.7a2 2 0 0 1-.5 2.1L8 9.8a16 16 0 0 0 6 6l1.3-1.3a2 2 0 0 1 2.1-.4c.9.3 1.8.6 2.7.7a2 2 0 0 1 1.7 2z"),
    )
    val Music = icon("music", s("M9 18V5l12-2v13"), s(circle(6f, 18f, 3f)), s(circle(18f, 16f, 3f)))
    val Message = icon("message", s("M21 12a8 8 0 0 1-11.8 7L3 21l2-6.2A8 8 0 1 1 21 12z"))
    val Battery = icon("battery", s(rect(2f, 7f, 18f, 11f, 2f)), s("M22 11v3"))
    val Headlight = icon("headlight", s("M14 5c-4 0-6 3-6 7s2 7 6 7c2 0 3-3 3-7s-1-7-3-7z"), s("M2 8h4M2 12h4M2 16h4"))
    val Moon = icon("moon", s("M20 14.5A8 8 0 1 1 9.5 4a6.5 6.5 0 0 0 10.5 10.5z"))
    val Settings = icon(
        "settings",
        s("M4 6h10M18 6h2M4 12h4M12 12h8M4 18h12M20 18h0"),
        s(circle(16f, 6f, 2f)), s(circle(10f, 12f, 2f)), s(circle(18f, 18f, 2f)),
    )
    val Car = icon("car", s("M5 17h14v-5l-2-5H7l-2 5z"), s("M5 12h14"), s(circle(8f, 17f, 2f)), s(circle(16f, 17f, 2f)))
    val Pin = icon("pin", s("M12 17v5"), s("M9 3h6l-1 6 4 4H6l4-4z"))
    val Check = icon("check", s("M5 12l5 5L20 7"))

    // Clima (spec 013)
    val Sun = icon("sun", s(circle(12f, 12f, 4f)), s("M12 2v2M12 20v2M4.9 4.9l1.4 1.4M17.7 17.7l1.4 1.4M2 12h2M20 12h2M4.9 19.1l1.4-1.4M17.7 6.3l1.4-1.4"))
    val Cloud = icon("cloud", s("M7 18h10a4 4 0 0 0 .5-7.97A6 6 0 0 0 6.1 11 3.5 3.5 0 0 0 7 18z"))
    val CloudSun = icon(
        "cloudSun",
        s("M9 19h8a3.5 3.5 0 0 0 .4-6.98A5 5 0 0 0 8 12.3 3.4 3.4 0 0 0 9 19z"),
        s("M7 3v1.5M2.8 5.8l1 1M1.5 10H3M5.5 9.2a3 3 0 0 1 4.6-2.7"),
    )
    val Rain = icon("rain", s("M7 15h10a4 4 0 0 0 .5-7.97A6 6 0 0 0 6.1 8 3.5 3.5 0 0 0 7 15z"), s("M8 18l-1 3M12 18l-1 3M16 18l-1 3"))
    val Storm = icon("storm", s("M7 15h10a4 4 0 0 0 .5-7.97A6 6 0 0 0 6.1 8 3.5 3.5 0 0 0 7 15z"), s("M12 15l-2 4h3l-2 4"))
    val Fog = icon("fog", s("M4 10h16M3 14h18M5 18h14"), s("M7 6h10"))
    val Snow = icon("snow", s("M12 3v18M4.2 7.5l15.6 9M4.2 16.5l15.6-9"))
    val Speed = icon("speed", s("M4 16a8 8 0 1 1 16 0"), s("M12 16l4-5"))
    val Reverse = icon("reverse", s("M9 14 4 9l5-5"), s("M4 9h10a6 6 0 0 1 0 12h-3"))
}
