package com.aube.mysize.domain.repository

import com.aube.mysize.domain.model.follow.UserSummary

interface BlockedUserRepository {
    suspend fun insertBlockedUser(blockedUid: String)
    suspend fun deleteBlockedUser(blockedUid: String)
    suspend fun getBlockedUsers(): List<UserSummary>
    suspend fun checkIfBlockedByUser(targetUid: String): Boolean
    suspend fun getUsersWhoBlockedMe(): Set<String>
}
