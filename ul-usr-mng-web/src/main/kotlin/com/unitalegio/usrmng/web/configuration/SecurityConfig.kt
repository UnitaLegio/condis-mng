package com.unitalegio.usrmng.web.configuration

import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain

// TODO: Comment on SecurityConfig.
@EnableWebFluxSecurity
class SecurityConfig {

    @Bean
    fun securityWebFilterChain(httpSecurity: ServerHttpSecurity): SecurityWebFilterChain {
        // @formatter:off
        httpSecurity
            .authorizeExchange()
            .anyExchange().authenticated()
            .and().oauth2ResourceServer().jwt()
        // @formatter:on

        return httpSecurity.build()
    }
}