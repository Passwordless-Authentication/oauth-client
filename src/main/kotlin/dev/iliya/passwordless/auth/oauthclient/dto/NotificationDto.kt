package dev.iliya.passwordless.auth.oauthclient.dto

import dev.iliya.passwordless.auth.oauthclient.model.NotificationMessage

data class NotificationDto(
    val id: Int,
    val options: List<Int>,
) {
    constructor(message: NotificationMessage) : this(
        id = message.id,
        options = message.options,
    )
}
