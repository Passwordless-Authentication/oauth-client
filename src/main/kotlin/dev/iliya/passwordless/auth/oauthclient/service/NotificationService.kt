package dev.iliya.passwordless.auth.oauthclient.service

import dev.iliya.passwordless.auth.oauthclient.dto.ResponseOptions
import dev.iliya.passwordless.auth.oauthclient.model.NotificationMessage

interface NotificationService {
    fun saveResponseOptions(responseOptions: ResponseOptions)

    fun getLatestNotification(username: String): NotificationMessage?

    fun setDelivered(username: String, messageId: Int)
}
