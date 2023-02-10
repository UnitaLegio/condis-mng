package com.unitalegio.usrmng.data.repository

import com.unitalegio.usrmng.data.entity.UlUserEntity
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono

interface ReactiveUserRepo: ReactiveCrudRepository<UlUserEntity, Long> {
    fun findByUsername(username: String): Mono<UlUserEntity>
}