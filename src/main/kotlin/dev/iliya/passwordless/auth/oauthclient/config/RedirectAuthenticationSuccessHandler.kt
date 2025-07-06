package dev.iliya.passwordless.auth.oauthclient.config

import dev.iliya.passwordless.auth.oauthclient.service.TokenService
import dev.iliya.passwordless.auth.oauthclient.service.Tokens
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.web.util.UriComponentsBuilder
import java.time.Duration

class RedirectAuthenticationSuccessHandler(
    private val clientService: OAuth2AuthorizedClientService,
    private val tokenService: TokenService,
    private val redirectUri: String,
) : AuthenticationSuccessHandler {
    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?
    ) {
        val oauthToken = authentication as OAuth2AuthenticationToken
        val oidcUser = oauthToken.principal as DefaultOidcUser
        val client = clientService.loadAuthorizedClient<OAuth2AuthorizedClient>(
            oauthToken.authorizedClientRegistrationId,
            oauthToken.name
        )

        val idToken = oidcUser.idToken
        val accessToken = client.accessToken
        val refreshToken = client.refreshToken!!

        val tokens = Tokens(
            idToken = idToken.tokenValue,
            accessToken = accessToken.tokenValue,
            refreshToken = refreshToken.tokenValue,
            expiresIn = Duration.between(accessToken.expiresAt, accessToken.issuedAt).toMillis()
        )
        val id = tokenService.store(tokens)

        val uri = UriComponentsBuilder.fromUriString(redirectUri)
            .queryParam("username", oauthToken.name)
            .queryParam("code", id.toString())
            .build()
            .toUriString()

        response?.sendRedirect(uri)
    }
}
