package dev.iliya.passwordless.auth.oauthclient.service.impl

import dev.iliya.passwordless.auth.oauthclient.service.TokenService
import dev.iliya.passwordless.auth.oauthclient.service.Tokens
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@Service
class InMemoryTokenService : TokenService {
    private val registry = ConcurrentHashMap<UUID, Tokens>()

    override fun store(tokens: Tokens): UUID {
        val id = UUID.randomUUID()
        registry[id] = tokens
        return id
    }

    override fun load(id: UUID): Tokens? {
        val tokens = registry[id]
        tokens?.run { registry.remove(id) }
        return tokens
    }
}
