package app.mylauncher.data.media

import android.app.Notification
import android.app.PendingIntent
import android.service.notification.StatusBarNotification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class MessageNotification(
    val packageName: String,
    val sender: String,
    val text: String,
    val postedAt: Long,
    val contentIntent: PendingIntent?,
    /** Chave da notificação no sistema (para dispensar). */
    val key: String = "",
    val isMessage: Boolean = false,
)

data class NotificationsSummary(
    val count: Int = 0,
    val lastMessage: MessageNotification? = null,
    /** Até 12 notificações mais recentes (Central de notificações; o widget mostra 4). */
    val recent: List<MessageNotification> = emptyList(),
)

/** Notificações para o sino da barra lateral e a mensagem do card de sugestões (R2.9). */
class MessagesRepository(scope: CoroutineScope, private val selfPackage: String) {
    val summary: StateFlow<NotificationsSummary> = MediaListenerService.notifications
        .map(::summarize)
        .stateIn(scope, SharingStarted.Eagerly, NotificationsSummary())

    private fun summarize(list: List<StatusBarNotification>): NotificationsSummary {
        val visible = list.filter { it.packageName != selfPackage && it.isClearable && !it.isOngoing }
        val last = visible
            .filter { isMessage(it) }
            .maxByOrNull { it.postTime }
            ?.let { sbn ->
                val extras = sbn.notification.extras
                MessageNotification(
                    packageName = sbn.packageName,
                    sender = extras.getCharSequence(Notification.EXTRA_TITLE)?.toString().orEmpty(),
                    text = extras.getCharSequence(Notification.EXTRA_TEXT)?.toString().orEmpty(),
                    postedAt = sbn.postTime,
                    contentIntent = sbn.notification.contentIntent,
                )
            }
        val recent = visible.sortedByDescending { it.postTime }.take(MAX_RECENT).map { sbn ->
            val extras = sbn.notification.extras
            MessageNotification(
                packageName = sbn.packageName,
                sender = extras.getCharSequence(Notification.EXTRA_TITLE)?.toString().orEmpty(),
                text = extras.getCharSequence(Notification.EXTRA_TEXT)?.toString().orEmpty(),
                postedAt = sbn.postTime,
                contentIntent = sbn.notification.contentIntent,
                key = sbn.key,
                isMessage = isMessage(sbn),
            )
        }
        return NotificationsSummary(visible.size, last, recent)
    }

    private fun isMessage(sbn: StatusBarNotification): Boolean =
        sbn.notification.category == Notification.CATEGORY_MESSAGE || sbn.packageName in MESSAGING_APPS

    companion object {
        const val MAX_RECENT = 12
        val MESSAGING_APPS = setOf("com.whatsapp", "com.whatsapp.w4b", "org.telegram.messenger", "com.google.android.apps.messaging")
    }
}
