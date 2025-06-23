package dev.iliya.passwordless.auth.oauthclient.controller

import dev.iliya.passwordless.auth.oauthclient.config.SsoConfiguration
import org.springframework.http.ResponseEntity
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.stomp.StompSessionHandler
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.client.RestTemplate
import org.springframework.web.socket.messaging.WebSocketStompClient
import org.springframework.web.util.UriComponentsBuilder

@Controller
class PasswordlessAuthController(
    val ssoConfiguration: SsoConfiguration,
    val stompClient: WebSocketStompClient,
    val sessionHandler: StompSessionHandler,
    val restTemplate: RestTemplate,
) {
    @GetMapping("/verify")
    @SendTo("/topic/reload")
    @ResponseBody
    fun verify(
        @RequestParam("response", required = true) response: String? = null
    ): ResponseEntity<ResponseData> {
        val uri = UriComponentsBuilder.fromUriString(ssoConfiguration.verifyUrl)
            .queryParam(ssoConfiguration.challengeQueryParameter, response)
            .toUriString()
        val responseData = restTemplate.getForObject(uri, ResponseData::class.java)
        stompClient.connectAsync(ssoConfiguration.reloadWsUrl, sessionHandler)
        return ResponseEntity.ok(responseData)
    }

    data class ResponseData(
        val username: String,
        val verified: Boolean,
    )
}
