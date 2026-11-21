package app.mylauncher.nav

import android.content.Intent
import android.net.Uri
import app.mylauncher.data.prefs.Destination

/**
 * Deep links para os apps de navegação (spec 005, R5.6). A navegação em si é sempre delegada.
 */
object NavIntents {
    const val WAZE = "com.waze"
    const val GOOGLE_MAPS = "com.google.android.apps.maps"
    const val HERE = "com.here.app.maps"
    val KNOWN = listOf(WAZE, GOOGLE_MAPS, HERE)

    /** Navegar até um destino salvo. */
    fun navigate(app: String, dest: Destination): Intent {
        val ll = dest.latLon
        val uri = when (app) {
            WAZE -> if (ll != null) {
                "https://waze.com/ul?ll=${ll.first},${ll.second}&navigate=yes"
            } else {
                "https://waze.com/ul?q=${Uri.encode(dest.query)}&navigate=yes"
            }
            GOOGLE_MAPS -> "google.navigation:q=" + (ll?.let { "${it.first},${it.second}" } ?: Uri.encode(dest.query))
            else -> if (ll != null) "geo:${ll.first},${ll.second}?q=${ll.first},${ll.second}" else "geo:0,0?q=${Uri.encode(dest.query)}"
        }
        return Intent(Intent.ACTION_VIEW, Uri.parse(uri)).setPackage(app)
    }

    /** Busca por categoria/termo (ex.: "posto de combustível"). */
    fun search(app: String, query: String): Intent {
        val uri = when (app) {
            WAZE -> "https://waze.com/ul?q=${Uri.encode(query)}"
            else -> "geo:0,0?q=${Uri.encode(query)}"
        }
        return Intent(Intent.ACTION_VIEW, Uri.parse(uri)).setPackage(app)
    }
}
