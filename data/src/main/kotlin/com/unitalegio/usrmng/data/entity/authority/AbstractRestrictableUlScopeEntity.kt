package com.unitalegio.usrmng.data.entity.authority

import jakarta.persistence.*
import java.io.Serializable

@Embeddable
class RestrictableUlScopePK(
    @Column(name = "id")
    val id: Long,
    @ManyToOne(cascade = [CascadeType.DETACH])
    @JoinColumn(name = "id", referencedColumnName = "scope_id")
    val scope: UlScopeEntity
) : Serializable

@MappedSuperclass
abstract class AbstractRestrictableUlScopeEntity(
    @EmbeddedId
    val id: RestrictableUlScopePK,
    @Column(name = "restricting")
    val restricting: Boolean
)

@Entity
@Table(schema = "usr_mng", name = "users_scopes")
class UlUserScopeEntity(
    id: RestrictableUlScopePK,
    restricting: Boolean
) : AbstractRestrictableUlScopeEntity(id, restricting)

@Entity
@Table(schema = "usr_mng", name = "roles_scopes")
class UlRoleScopeEntity(
    id: RestrictableUlScopePK,
    restricting: Boolean
) : AbstractRestrictableUlScopeEntity(id, restricting)

@Entity
@Table(schema = "usr_mng", name = "groups_scopes")
class UlGroupScopeEntity(
    id: RestrictableUlScopePK,
    restricting: Boolean
) : AbstractRestrictableUlScopeEntity(id, restricting)