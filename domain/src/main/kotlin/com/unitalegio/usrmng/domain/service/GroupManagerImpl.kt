package com.unitalegio.usrmng.domain.service

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.provisioning.GroupManager

// TODO: Comment on GroupManagerImpl
// TODO: Implement GroupManagerImpl
class GroupManagerImpl : GroupManager {
    override fun findAllGroups(): MutableList<String> {
        TODO("Not yet implemented")
    }

    override fun findUsersInGroup(groupName: String?): MutableList<String> {
        TODO("Not yet implemented")
    }

    override fun createGroup(groupName: String?, authorities: MutableList<GrantedAuthority>?) {
        TODO("Not yet implemented")
    }

    override fun deleteGroup(groupName: String?) {
        TODO("Not yet implemented")
    }

    override fun renameGroup(oldName: String?, newName: String?) {
        TODO("Not yet implemented")
    }

    override fun addUserToGroup(username: String?, group: String?) {
        TODO("Not yet implemented")
    }

    override fun removeUserFromGroup(username: String?, groupName: String?) {
        TODO("Not yet implemented")
    }

    override fun findGroupAuthorities(groupName: String?): MutableList<GrantedAuthority> {
        TODO("Not yet implemented")
    }

    override fun addGroupAuthority(groupName: String?, authority: GrantedAuthority?) {
        TODO("Not yet implemented")
    }

    override fun removeGroupAuthority(groupName: String?, authority: GrantedAuthority?) {
        TODO("Not yet implemented")
    }

}