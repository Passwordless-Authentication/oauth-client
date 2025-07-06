package dev.iliya.passwordless.auth.oauthclient.service

import java.util.UUID

interface TokenService {
    fun store(tokens: Tokens): UUID

    fun load(id: UUID): Tokens?
}
