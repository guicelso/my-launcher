package app.mylauncher

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import app.mylauncher.core.FileLog
import app.mylauncher.nav.NavigationPane
import app.mylauncher.nav.PlaceholderMapPane
import app.mylauncher.ui.LauncherRoot
import app.mylauncher.ui.Navigator

class MainActivity : ComponentActivity() {

    private val navigator = Navigator()
    private val container get() = (application as LauncherApp).container
    private val navPane: NavigationPane by lazy { PlaceholderMapPane() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        hideSystemBars()

        // R1.8: o Voltar nunca fecha o launcher; volta ao painel.
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navigator.home()
            }
        })

        setContent { LauncherRoot(container, navigator, navPane) }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        // R1.4: HOME com o launcher já aberto chega aqui (singleTask), sem recriar a activity.
        if (intent.hasCategory(Intent.CATEGORY_HOME)) {
            FileLog.i("HOME → painel")
            navigator.home()
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideSystemBars()
    }

    /** R1.3: modo imersivo; as barras reaparecem temporariamente com um deslize. */
    private fun hideSystemBars() {
        WindowInsetsControllerCompat(window, window.decorView).apply {
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            hide(WindowInsetsCompat.Type.systemBars())
        }
    }
}
