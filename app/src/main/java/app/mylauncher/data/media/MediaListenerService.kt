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
        publish()
    }

    override fun onListenerDisconnected() {
        FileLog.w("NotificationListener desconectado")
        connected.value = false
        _notifications.value = emptyList()
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
    }
}
