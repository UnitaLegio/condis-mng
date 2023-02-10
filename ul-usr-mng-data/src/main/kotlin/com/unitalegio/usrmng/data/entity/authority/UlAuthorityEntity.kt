package com.unitalegio.usrmng.data.entity.authority

import com.unitalegio.usrmng.data.entity.BaseNumberIdentityEntity
import jakarta.persistence.*
import jakarta.validation.constraints.Size
import lombok.Builder
import lombok.Data
import org.springframework.security.core.GrantedAuthority

enum class UlAuthorityType(val priority: Int) {
    SCOPE(1),
    ROLE(2),
    GROUP(3)
}

@MappedSuperclass
abstract class AbstractUlAuthorityEntity(
    id: Long,
    @Size(min = 1, max = 150)
    @Column(name = "name", unique = true) val name: String,
    @Column(name = "description") val description: String?,
    @Transient val type: UlAuthorityType
) : BaseNumberIdentityEntity<Long>(id), GrantedAuthority {
    override fun getAuthority(): String {
        return StringBuilder(type.name).append('_').append(name).toString()
    }
}

@MappedSuperclass
@Entity
@Table(schema = "usr_mng", name = "v_collective_authorities")
open class CollectiveAuthorityEntity(
    name: String,
    description: String,
    type: String
) :
    AbstractUlAuthorityEntity(-1, name, description, UlAuthorityType.valueOf(type)) {
    override fun getAuthority(): String = type.name + "_" + name
}

@Entity
// TODO: Create view which will parse and add role and group authorities;
@Table(schema = "usr_mng", name = "v_user_authorities")
class UserAuthorityViewEntity(
    name: String,
    type: String,
    desctiption: String,
    @Column(name = "username")
    val username: String,
    @Column(name = "restricting")
    val restricting: Boolean?
) : CollectiveAuthorityEntity(name, desctiption, type)

@Builder
@Data
@Entity
@Table(schema = "usr_mng", name = "scopes")
open class UlScopeEntity(
    id: Long,
    name: String,
    description: String?
) : AbstractUlAuthorityEntity(id, name, description, UlAuthorityType.SCOPE)

@Entity
@Table(schema = "usr_mng", name = "roles")
class UlRoleEntity(
    id: Long,
    name: String,
    description: String?,
) : AbstractUlAuthorityEntity(id, name, description, UlAuthorityType.ROLE) {
    @OneToMany
    @JoinColumn(name = "id", referencedColumnName = "id")
    lateinit var scopes: Collection<UlRoleScopeEntity>
}

@Entity
@Table(schema = "usr_mng", name = "groups")
class UlGroupEntity(
    id: Long,
    name: String,
    description: String?,
) : AbstractUlAuthorityEntity(id, name, description, UlAuthorityType.GROUP) {
    @OneToMany
    @JoinColumn(name = "id", referencedColumnName = "id")
    lateinit var scopes: Collection<UlGroupScopeEntity>

    @ManyToMany
    @JoinTable(
        name = "groups_roles",
        joinColumns = [JoinColumn(name = "group_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")],
    )
    lateinit var roles: Collection<UlRoleEntity>
}
