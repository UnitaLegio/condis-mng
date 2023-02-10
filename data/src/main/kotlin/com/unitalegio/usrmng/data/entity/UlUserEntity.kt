package com.unitalegio.usrmng.data.entity

import com.unitalegio.usrmng.data.entity.authority.UlGroupEntity
import com.unitalegio.usrmng.data.entity.authority.UlRoleEntity
import com.unitalegio.usrmng.data.entity.authority.UlUserScopeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import lombok.Builder
import lombok.Data
import java.time.LocalDateTime

@Builder
@Data
@Entity
@Table(schema = "usr_mng", name = "users")
class UlUserEntity(
    @NotNull
    @Size(min = 3, max = 20)
    @Column(name = "username", unique = true)
    val username: String,
    @NotNull
    @NotBlank
    @Size(min = 7, max = 2048)
    @Column(name = "password")
    val password: String,
    @NotNull
    @Column(name = "account_expire_date")
    val accountExpireDate: LocalDateTime?,
    @NotNull
    @Column(name = "locked")
    val isAccountLocked: Boolean,
    @NotNull
    @Column(name = "credentials_expire_date")
    val credentialsExpireDate: LocalDateTime?,
    @NotNull
    @Column(name = "enabled")
    val isEnabled: Boolean,
    id: Long,
) : BaseNumberIdentityEntity<Long>(id) {
    @ManyToMany
    lateinit var scopes: Collection<UlUserScopeEntity>
    @ManyToMany
    lateinit var roles: Collection<UlRoleEntity>
    @ManyToMany
    lateinit var groups: Collection<UlGroupEntity>
}