package com.unitalegio.usrmng.web.api

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

// TODO: Comment on UserDetailsApi.
// TODO: OpenAPI docs for UserDetailsApi.
// TODO: Test UserDetailsApi.
@RestController("/user")
class UsersApi(private val userDetailsService: ReactiveUserDetailsService) {

    @PreAuthorize("hasAuthority('SCOPE_user.details')")
    @PostMapping("/details")
    suspend fun userDetailsByName(@RequestBody username: String): Mono<UserDetails> {
        return userDetailsService.findByUsername(username)
    }
}