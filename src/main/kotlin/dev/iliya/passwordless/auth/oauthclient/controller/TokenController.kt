package dev.iliya.passwordless.auth.oauthclient.controller

import dev.iliya.passwordless.auth.oauthclient.service.TokenService
import dev.iliya.passwordless.auth.oauthclient.service.Tokens
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/token")
class TokenController(
    private val tokenService: TokenService
) {
    @GetMapping("/code/{code}")
    fun getTokens(@PathVariable code: UUID): Tokens? {
        return tokenService.load(code)
    }
}
