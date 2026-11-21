package app.mylauncher.data.media

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import app.mylauncher.core.FileLog
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Listener de notificações (spec 004, R4.1). Dá acesso às MediaSessions de outros apps e
 * alimenta o card de sugestões com mensagens. Concedido no deploy via
 * `cmd notification allow_listener` (R4.2).
 */
class MediaListenerService : NotificationListenerService() {

    override fun onListenerConnected() {
        FileLog.i("NotificationListener conectado")
        connected.value = true
        instance = this
        publish()
    }

    override fun onListenerDisconnected() {
        FileLog.w("NotificationListener desconectado")
        connected.value = false
        instance = null
        _notifications.value = emptyList()
    }

    override fun onDestroy() {
        if (instance === this) instance = null
        super.onDestroy()
    }

    override fun onNotificationPosted(sbn: StatusBarNotification) = publish()
    override fun onNotificationRemoved(sbn: StatusBarNotification) = publish()

    private fun publish() {
        _notifications.value = runCatching { activeNotifications?.toList().orEmpty() }.getOrDefault(emptyList())
    }

    companion object {
        val connected = MutableStateFlow(false)
        private val _notifications = MutableStateFlow<List<StatusBarNotification>>(emptyList())
        val notifications: StateFlow<List<StatusBarNotification>> = _notifications.asStateFlow()

        @Volatile private var instance: MediaListenerService? = null

        /** Dispensa uma notificação pela chave (Central de notificações). */
        fun dismiss(key: String) {
            runCatching { instance?.cancelNotification(key) }.onFailure { FileLog.w("Falha ao dispensar notificação: ${it.message}") }
        }

        /** Dispensa todas as notificações que podem ser limpas. */
        fun dismissAll() {
            runCatching { instance?.cancelAllNotifications() }.onFailure { FileLog.w("Falha ao limpar notificações: ${it.message}") }
        }
    }
}
