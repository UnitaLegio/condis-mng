package com.unitalegio.usrmng.data.repository

import com.unitalegio.usrmng.data.entity.BaseNumberIdentityEntity
import com.unitalegio.usrmng.data.entity.authority.UlGroupEntity
import com.unitalegio.usrmng.data.entity.authority.UlRoleEntity
import com.unitalegio.usrmng.data.entity.authority.UserAuthorityViewEntity
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

interface ReactiveAuthorityRepo<T: BaseNumberIdentityEntity<Long>>:ReactiveCrudRepository<T, Long>

@Repository
interface UserAuthorityRepo: ReactiveCrudRepository<UserAuthorityViewEntity, Long> {
    fun findByUsername(username: String): Flux<UserAuthorityViewEntity>
}

@Repository
interface RoleAuthorityRepo: ReactiveAuthorityRepo<UlRoleEntity>

@Repository
interface GroupAuthorityRepo: ReactiveAuthorityRepo<UlGroupEntity>