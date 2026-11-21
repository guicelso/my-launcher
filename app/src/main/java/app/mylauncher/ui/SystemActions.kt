package app.mylauncher.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.speech.RecognizerIntent
import app.mylauncher.core.FileLog

/** Ações de sistema da barra lateral: voz (R2.5) e painel de notificações. */
class SystemActions(private val context: Context) {

    private val voiceIntents = listOf(
        Intent(Intent.ACTION_VOICE_COMMAND),
        Intent(RecognizerIntent.ACTION_VOICE_SEARCH_HANDS_FREE),
        Intent(RecognizerIntent.ACTION_WEB_SEARCH),
    )

    /** Primeiro intent de voz que algum app trata, ou null (botão desabilitado). */
    val voiceIntent: Intent? by lazy {
        voiceIntents.firstOrNull { context.packageManager.resolveActivity(it, 0) != null }
    }

    fun startVoice(): Boolean {
        val intent = voiceIntent ?: return false
        return runCatching {
            context.startActivity(Intent(intent).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
            true
        }.getOrElse { FileLog.e("Falha ao abrir voz", it); false }
    }

    /** Abre a cortina de notificações (permissão normal EXPAND_STATUS_BAR, API oculta via reflexão). */
    @SuppressLint("WrongConstant", "PrivateApi")
    fun expandNotifications(): Boolean = runCatching {
        val sbm = context.getSystemService("statusbar")
        Class.forName("android.app.StatusBarManager").getMethod("expandNotificationsPanel").invoke(sbm)
        true
    }.getOrElse { FileLog.w("expandNotificationsPanel indisponível", it); false }

    /** Abre a tela do sistema para conceder acesso às notificações (R4.2, alternativa ao adb). */
    fun openNotificationAccess(): Boolean = runCatching {
        context.startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        true
    }.getOrElse { FileLog.e("Falha ao abrir acesso às notificações", it); false }
}
