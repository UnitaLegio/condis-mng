package com.unitalegio.usrmng.domain.service

import com.unitalegio.usrmng.domain.service.model.UlUserModel
import reactor.core.publisher.Mono

interface UserDetailsService {
    fun findByUsername(username: String): Mono<UlUserModel>
}