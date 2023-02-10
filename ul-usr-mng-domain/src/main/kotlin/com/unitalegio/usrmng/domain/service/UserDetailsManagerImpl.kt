package com.unitalegio.usrmng.domain.service

import com.unitalegio.usrmng.data.repository.ReactiveUserRepo
import com.unitalegio.usrmng.domain.service.mapper.UserEntityUserDetailsMapper
import com.unitalegio.usrmng.domain.service.model.UlUserModel
import kotlinx.coroutines.*
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.util.*

// TODO: Comment on UserDetailsManager
// TODO: Implement UserDetailsManager
@OptIn(DelicateCoroutinesApi::class)
@Service
class UserDetailsManagerImpl(
    private val userRepo: ReactiveUserRepo,
    private val userMapper: UserEntityUserDetailsMapper
) : UserDetailsService {

    override fun findByUsername(username: String): Mono<UlUserModel> {
        return userRepo.findByUsername(username)
            .mapNotNull { userMapper.map(it) }
    }

}