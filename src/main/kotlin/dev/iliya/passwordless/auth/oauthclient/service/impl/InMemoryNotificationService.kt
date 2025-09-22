package dev.iliya.passwordless.auth.oauthclient.service.impl

import dev.iliya.passwordless.auth.oauthclient.dto.ResponseOptions
import dev.iliya.passwordless.auth.oauthclient.model.NotificationMessage
import dev.iliya.passwordless.auth.oauthclient.service.NotificationService
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.concurrent.ConcurrentHashMap

@Service
class InMemoryNotificationService : NotificationService {
    private val notificationsMap = ConcurrentHashMap<String, NotificationMessage>()
    private var lastGeneratedId: Int? = null

    override fun saveResponseOptions(responseOptions: ResponseOptions) {
        val username = responseOptions.username
        requireNotNull(username) { "username must not be null." }

        val newMessage = NotificationMessage(
            id = generateId(),
            username = username,
            options = responseOptions.options!!,
            expiresAt = Instant.ofEpochMilli(responseOptions.expiresAt!!),
            createdAt = Instant.now(),
        )
        notificationsMap[username] = newMessage
    }

    override fun getLatestNotification(username: String): NotificationMessage? {
        return synchronized(username) {
            notificationsMap[username]?.takeIf { it.eligibleToBeSent }
        }
    }

    override fun setDelivered(username: String, messageId: Int) {
        synchronized(username) {
            notificationsMap[username]?.takeIf { !it.expired && it.id == messageId }?.let { notificationMessage ->
                notificationMessage.delivered = true
            }
        }
    }

    @Synchronized
    private fun generateId(): Int {
        val newId = lastGeneratedId?.plus(1) ?: 1
        lastGeneratedId = newId
        return newId
    }
}
