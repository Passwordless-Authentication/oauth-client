package dev.iliya.passwordless.auth.oauthclient.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class ResponseOptions(
    @field:NotNull(message = "username cannot be null.")
    @field:NotBlank(message = "username cannot be blank.")
    @field:NotEmpty(message = "username cannot be empty.")
    @field:Size(min = 1, max = 255)
    val username: String? = null,

    @field:NotNull(message = "options cannot be null.")
    @field:Size(min = 3, max = 3, message = "options should be exactly of size 3.")
    val options: List<Int>? = null,

    @field:NotNull(message = "expiresAt cannot be null.")
    val expiresAt: Long? = null,
)
