package com.aube.mysize.data.repository

import com.aube.mysize.data.model.dto.follow.FollowTimestampDTO
import com.aube.mysize.domain.model.follow.UserSummary
import com.aube.mysize.domain.repository.FollowRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await

class FollowRepositoryImpl(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : FollowRepository {

    private val currentUid get() = auth.currentUser?.uid ?: throw IllegalStateException("User not logged in")

    override suspend fun follow(targetUid: String) {
        val data = FollowTimestampDTO()

        firestore.collection("users").document(currentUid)
            .collection("followings").document(targetUid).set(data).await()

        firestore.collection("users").document(targetUid)
            .collection("followers").document(currentUid).set(data).await()
    }

    override suspend fun unfollow(uid: String?, targetUid: String) {
        val fromUid = uid ?: currentUid

        firestore.collection("users").document(fromUid)
            .collection("followings").document(targetUid).delete().await()

        firestore.collection("users").document(targetUid)
            .collection("followers").document(fromUid).delete().await()
    }

    override suspend fun getFollowersCount(userUid: String): Int =
        firestore.collection("users").document(userUid)
            .collection("followers").get().await().size()

    override suspend fun getFollowingsCount(userUid: String): Int =
        firestore.collection("users").document(userUid)
            .collection("followings").get().await().size()

    override suspend fun getFollowers(userUid: String): List<UserSummary> {
        val snapshot = firestore.collection("users").document(userUid)
            .collection("followers").get().await()
        return fetchUserSummaries(snapshot.documents.map { it.id })
    }

    override suspend fun getFollowings(userUid: String): List<UserSummary> {
        val snapshot = firestore.collection("users").document(userUid)
            .collection("followings").get().await()
        return fetchUserSummaries(snapshot.documents.map { it.id })
    }

    private suspend fun fetchUserSummaries(uids: List<String>): List<UserSummary> = coroutineScope {
        uids.map { uid ->
            async {
                val doc = firestore.collection("users").document(uid).get().await()
                val nickname = doc.getString("nickname") ?: return@async null
                val profileUrl = doc.getString("profileImageUrl") ?: ""
                UserSummary(uid, nickname, profileUrl)
            }
        }.awaitAll().filterNotNull()
    }
}