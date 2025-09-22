package dev.iliya.passwordless.auth.oauthclient.controller

import dev.iliya.passwordless.auth.oauthclient.dto.NotificationDto
import dev.iliya.passwordless.auth.oauthclient.dto.ResponseOptions
import dev.iliya.passwordless.auth.oauthclient.service.NotificationService
import jakarta.validation.Valid
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/passwordless/notification", produces = [MediaType.APPLICATION_JSON_VALUE])
class NotificationController(
    private val notificationService: NotificationService
) {
    @PostMapping("/responseOptions")
    fun responseOptions(
        @Valid @RequestBody responseOptions: ResponseOptions,
    ) {
        notificationService.saveResponseOptions(responseOptions)
    }

    @PostMapping("/delivered")
    fun deliveredNotification(username: String, messageId: Int): Boolean {
        notificationService.setDelivered(username, messageId)
        return true
    }

    @GetMapping("/latest")
    fun getLatestNotification(username: String): NotificationDto? {
        return notificationService.getLatestNotification(username)?.let { NotificationDto(it) }
    }
}
