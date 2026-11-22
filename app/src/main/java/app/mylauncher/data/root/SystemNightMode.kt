package app.mylauncher.data.root

import app.mylauncher.core.FileLog

/**
 * Faz o modo noturno do **sistema** seguir o tema do Lumia (faróis → dia/noite, spec 002).
 *
 * Neste firmware o modo noturno do Android fica travado em "sim" (`mNightModeLocked`), então os
 * apps que seguem o sistema (ex.: Google Maps embutido, spec 008) ficavam sempre escuros, mesmo de
 * dia com o Lumia claro. Um app comum não pode mudar isso; como root (`cmd uimode night`), pode.
 * Sem root, não faz nada.
 */
object SystemNightMode {
    @Volatile private var applied: Boolean? = null

    /** Bloqueante (comando root): chame fora da main thread. */
    fun apply(night: Boolean) {
        if (applied == night) return
        val out = RootShell.exec("cmd uimode night ${if (night) "yes" else "no"}") ?: return
        applied = night
        FileLog.i("SystemNightMode: noite=$night ($out)")
    }
}
