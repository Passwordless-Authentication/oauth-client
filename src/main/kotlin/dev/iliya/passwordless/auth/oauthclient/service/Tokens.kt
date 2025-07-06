package dev.iliya.passwordless.auth.oauthclient.service

data class Tokens(
    val idToken: String?,
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Long,
)
