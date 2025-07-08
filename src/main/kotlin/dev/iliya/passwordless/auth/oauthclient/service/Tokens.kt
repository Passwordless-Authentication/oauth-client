package dev.iliya.passwordless.auth.oauthclient.service

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Tokens(
    @field:JsonProperty("id_token")
    val idToken: String,

    @field:JsonProperty("access_token")
    val accessToken: String,

    @field:JsonProperty("refresh_token")
    val refreshToken: String,

    @field:JsonProperty("expires_at")
    val expiresAt: Long,
)
