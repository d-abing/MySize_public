package com.aube.mysize.domain.repository

import com.aube.mysize.domain.model.follow.UserSummary

interface FollowRepository {
    suspend fun follow(targetUid: String)
    suspend fun unfollow(uid: String?, targetUid: String)
    suspend fun getFollowersCount(userUid: String): Int
    suspend fun getFollowingsCount(userUid: String): Int
    suspend fun getFollowers(userUid: String): List<UserSummary>
    suspend fun getFollowings(userUid: String): List<UserSummary>
}
