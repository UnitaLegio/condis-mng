package com.unitalegio.usrmng.domain.service.model

import com.unitalegio.usrmng.data.entity.authority.UlGroupEntity
import com.unitalegio.usrmng.data.entity.authority.UlRoleEntity
import com.unitalegio.usrmng.data.entity.authority.UlUserScopeEntity
import lombok.Builder
import org.springframework.security.core.GrantedAuthority
import java.time.LocalDateTime

@Builder
class UlUserModel(
    /* All authorities: only allowed scopes (also from roles and groups),
     * roles and groups as simple authorities
     */
    private val authorities: MutableCollection<out GrantedAuthority>,
    private val password: String,
    private val username: String,
    private val accountExpireDate: LocalDateTime?,
    private val isNonLocked: Boolean,
    private val credentialsExpireDate: LocalDateTime?,
    private val isEnabled: Boolean,
    private val scopes: Collection<UlUserScopeEntity>,
    private val roles: Collection<UlRoleEntity>,
    private val groups: Collection<UlGroupEntity>,
)