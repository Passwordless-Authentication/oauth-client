package dev.iliya.passwordless.auth.oauthclient.model

import java.time.Instant

data class NotificationMessage(
    val id: Int,
    val username: String,
    val options: List<Int>,
    val expiresAt: Instant,
    val createdAt: Instant,
    var delivered: Boolean = false,
) {
    val expired: Boolean
        get() = Instant.now().isAfter(expiresAt)

    val eligibleToBeSent: Boolean
        get() = !delivered && !expired
}
