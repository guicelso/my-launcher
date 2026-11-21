package app.mylauncher

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import app.mylauncher.core.FileLog
import app.mylauncher.data.destinations.SharedPlaceParser

/**
 * Recebe "Compartilhar local" de outros apps (Google Maps, Waze, WhatsApp) e `geo:` (spec 012,
 * R12.3). Repassa ao launcher, que mostra o painel "Salvar como…", e fecha.
 */
class ShareActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val text = when (intent?.action) {
            Intent.ACTION_SEND -> intent.getStringExtra(Intent.EXTRA_TEXT)
            Intent.ACTION_VIEW -> intent.dataString
            else -> null
        }
        val place = SharedPlaceParser.parse(text, intent?.getStringExtra(Intent.EXTRA_SUBJECT))
        if (place != null) {
            FileLog.i("Local compartilhado recebido")
            (application as LauncherApp).container.destinationRequests.request(place)
            startActivity(Intent(this, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_REORDER_TO_FRONT))
        } else {
            Toast.makeText(this, "Não reconheci um local nesse conteúdo", Toast.LENGTH_LONG).show()
        }
        finish()
    }
}
