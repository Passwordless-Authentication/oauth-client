package dev.iliya.passwordless.auth.oauthclient.config

import dev.iliya.passwordless.auth.oauthclient.service.TokenService
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    private val clientRegistrationRepository: ClientRegistrationRepository,
    private val tokenService: TokenService,
) {
    @Value("\${app.redirectUri}")
    private lateinit var redirectUri: String

    @Bean
    fun oAuthClientService(): OAuth2AuthorizedClientService {
        return InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository)
    }

    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(
        httpSecurity: HttpSecurity,
        clientService: OAuth2AuthorizedClientService,
    ): SecurityFilterChain {
        httpSecurity {
            authorizeHttpRequests {
                authorize("/", permitAll)
                authorize("passwordless/notification/**", permitAll)
                authorize("/token/code/**", permitAll)
                authorize(anyRequest, authenticated)
            }
            csrf { disable() }
            oauth2Login {
                authorizedClientService = clientService
                authenticationSuccessHandler =
                    RedirectAuthenticationSuccessHandler(clientService, tokenService, redirectUri)
            }
            sessionManagement {
                sessionCreationPolicy = SessionCreationPolicy.STATELESS
            }
        }

        return httpSecurity.build()
    }
}
