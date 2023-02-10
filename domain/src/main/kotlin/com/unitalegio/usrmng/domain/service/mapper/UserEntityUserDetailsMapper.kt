package com.unitalegio.usrmng.domain.service.mapper

import com.unitalegio.usrmng.data.entity.UlUserEntity
import com.unitalegio.usrmng.data.entity.authority.AbstractRestrictableUlScopeEntity
import com.unitalegio.usrmng.data.entity.authority.AbstractUlAuthorityEntity
import com.unitalegio.usrmng.domain.service.model.UlUserModel
import io.netty.util.Mapping
import kotlinx.coroutines.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.util.*

@DelicateCoroutinesApi
class UserEntityUserDetailsMapper : Mapping<UlUserEntity?, UlUserModel?> {
    override fun map(user: UlUserEntity?): UlUserModel? {
        if (user == null) return null
        // create result GrantAuthority container
        val grantAuthorities = Collections.synchronizedCollection(mutableSetOf<GrantedAuthority>())
        // add roles and groups as authorities themselves
        val mappingJobs = addGroupsAndRolesAsAuthoritiesThemselves(user, grantAuthorities)

        val cleanAuthorities = mutableSetOf<AbstractRestrictableUlScopeEntity>()
        // add only scopes from group roles
        cleanAuthorities.addAll(user.groups.flatMap { group ->
            group.roles.flatMap { it.scopes }
        })
        // find restricting scopes and then subtract them from groups roles
        cleanOffRestricted(cleanAuthorities)
        // then add group non-role scopes
        cleanAuthorities.addAll(user.groups.flatMap { group ->
            group.scopes
        })
        // clean off restrict-ing/ed again
        cleanOffRestricted(cleanAuthorities)
        // so, we have cleaned group scopes, now, we're going to add clean roles scopes
        // add all roles scopes
        cleanAuthorities.addAll(user.roles.flatMap { it.scopes })
        // clean off from restrict-ing/ed one time else
        cleanOffRestricted(cleanAuthorities)
        // And in the end we're going to commit the same procedure with user's individual scopes
        cleanAuthorities.addAll(user.scopes)
        cleanOffRestricted(cleanAuthorities)

        grantAuthorities.addAll(
            cleanAuthorities.map { SimpleGrantedAuthority(it.id.scope.authority) }
        )
        runBlocking {
            mappingJobs.joinAll()
        }

        return UlUserModel(
            authorities = grantAuthorities,
            groups = user.groups,
            roles = user.roles,
            scopes = user.scopes,
            password = user.password,
            username = user.username,
            accountExpireDate = user.accountExpireDate,
            isNonLocked = !user.isAccountLocked,
            credentialsExpireDate = user.credentialsExpireDate,
            isEnabled = user.isEnabled,
        )
    }

    private fun cleanOffRestricted(groupScopes: MutableSet<AbstractRestrictableUlScopeEntity>) {
        val restrictingGroupScopes = groupScopes.filter { it.restricting }.map { it.id.scope.name }
        groupScopes.removeIf {
            restrictingGroupScopes.contains(it.id.scope.name)
        }
    }

    private fun addGroupsAndRolesAsAuthoritiesThemselves(
        entity: UlUserEntity,
        resultContainer: MutableCollection<GrantedAuthority>,
    ): List<Job> = listOf(
        createParsingJob(
            sourceAuthorities = entity.roles,
            mappedAuthorities = resultContainer,
            map = {
                it as AbstractUlAuthorityEntity
                SimpleGrantedAuthority(it.authority)
            }),
        createParsingJob(
            sourceAuthorities = entity.groups,
            mappedAuthorities = resultContainer,
            map = {
                it as AbstractUlAuthorityEntity
                SimpleGrantedAuthority(it.authority)
            })
    )

    private fun createParsingJob(
        sourceAuthorities: Collection<Any>,
        map: (Any) -> GrantedAuthority,
        mappedAuthorities: MutableCollection<GrantedAuthority>,
    ): Job =
        GlobalScope.launch {
            sourceAuthorities
                .stream()
                .parallel()
                .map {
                    map(it)
                }.forEach {
                    mappedAuthorities.add(it)
                }
        }

}