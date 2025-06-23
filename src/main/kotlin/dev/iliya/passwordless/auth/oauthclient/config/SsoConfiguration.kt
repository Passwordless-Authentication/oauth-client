package dev.iliya.passwordless.auth.oauthclient.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "sso")
data class SsoConfiguration(
    val baseUrl: String,
    val realm: String,
    val verifyUrl: String,
    val challengeQueryParameter: String,
    val reloadWsUrl: String,
)
