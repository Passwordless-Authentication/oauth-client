package dev.iliya.passwordless.auth.oauthclient

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class OauthClientApplication

fun main(args: Array<String>) {
    runApplication<OauthClientApplication>(*args)
}
