package app.mylauncher

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

/** Injeção de dependências manual (docs/01-architecture.md, D8). */
class AppContainer(val context: Context) {
    /** Escopo de vida do processo para repositórios que vivem enquanto o launcher existir. */
    val appScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
}
