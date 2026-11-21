package app.mylauncher.data.destinations

import java.net.URLDecoder

/** Local recebido de outro app (spec 012, R12.3). [query] é "lat,lon" ou um endereço. */
data class SharedPlace(val query: String, val title: String?)

/**
 * Extrai coordenadas ou endereço de textos compartilhados pelo Google Maps, Waze, WhatsApp
 * (localização) e URIs `geo:`. Puro e testável.
 */
object SharedPlaceParser {
    private val number = """-?\d{1,3}\.\d+"""
    private val patterns = listOf(
        Regex("""geo:($number),($number)"""),
        Regex("""@($number),($number)"""),
        Regex("""[?&](?:q|ll|query|destination|daddr|center)=($number)(?:,|%2C)\s*($number)"""),
        Regex("""\b($number),\s*($number)\b"""),
    )
    private val placeName = Regex("""/maps/place/([^/@?]+)""")
    private val geoQuery = Regex("""geo:[^?]*\?q=([^&]+)""")
    private val url = Regex("""https?://\S+""")

    fun parse(text: String?, subject: String? = null): SharedPlace? {
        if (text.isNullOrBlank()) return null
        val title = subject?.takeIf { it.isNotBlank() }
            ?: placeName.find(text)?.groupValues?.get(1)?.let(::decode)
            ?: text.substringBefore("http").lines().firstOrNull { it.isNotBlank() }?.trim()?.takeIf { it.length in 2..80 }
        for (p in patterns) {
            val m = p.find(text) ?: continue
            val lat = m.groupValues[1].toDoubleOrNull() ?: continue
            val lon = m.groupValues[2].toDoubleOrNull() ?: continue
            if (lat in -90.0..90.0 && lon in -180.0..180.0 && !(lat == 0.0 && lon == 0.0)) {
                return SharedPlace("%.6f,%.6f".format(java.util.Locale.US, lat, lon), title)
            }
        }
        geoQuery.find(text)?.let { return SharedPlace(decode(it.groupValues[1]), title) }
        // Sem coordenadas: endereço em texto (ou link curto, que o app de navegação resolve).
        val address = text.replace(url, "").trim().ifBlank { url.find(text)?.value ?: return null }
        return SharedPlace(address, title)
    }

    private fun decode(s: String) = runCatching { URLDecoder.decode(s.replace('+', ' '), "UTF-8") }.getOrDefault(s)
}
