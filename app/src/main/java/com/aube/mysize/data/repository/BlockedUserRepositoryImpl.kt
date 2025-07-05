package com.aube.mysize.data.repository

import com.aube.mysize.domain.model.follow.UserSummary
import com.aube.mysize.domain.repository.BlockedUserRepository
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class BlockedUserRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : BlockedUserRepository {

    private fun requireUid(): String {
        return auth.currentUser?.uid ?: throw IllegalStateException("User is not logged in.")
    }

    override suspend fun insertBlockedUser(blockedUid: String) {
        val uid = requireUid()
        val now = Timestamp.now()
        val data = mapOf("blockedAt" to now)

        val batch = firestore.batch()
        batch.set(firestore.collection("blockedUsers").document(uid).collection("users").document(blockedUid), data)
        batch.set(firestore.collection("blockedByUsers").document(blockedUid).collection("users").document(uid), data)
        batch.commit().await()
    }

    override suspend fun deleteBlockedUser(blockedUid: String) {
        val uid = requireUid()

        val batch = firestore.batch()
        batch.delete(firestore.collection("blockedUsers").document(uid).collection("users").document(blockedUid))
        batch.delete(firestore.collection("blockedByUsers").document(blockedUid).collection("users").document(uid))
        batch.commit().await()
    }

    override suspend fun getBlockedUsers(): List<UserSummary> = kotlinx.coroutines.coroutineScope {
        val uid = requireUid()
        val snapshot = firestore.collection("blockedUsers")
            .document(uid)
            .collection("users")
            .get()
            .await()

        snapshot.documents.map { it.id }.map { blockedUid ->
            async {
                try {
                    val userDoc = firestore.collection("users").document(blockedUid).get().await()
                    val nickname = userDoc.getString("nickname") ?: return@async null
                    val profileImageUrl = userDoc.getString("profileImageUrl") ?: ""
                    UserSummary(blockedUid, nickname, profileImageUrl)
                } catch (e: Exception) {
                    null
                }
            }
        }.awaitAll().filterNotNull()
    }

    override suspend fun checkIfBlockedByUser(targetUid: String): Boolean {
        val uid = requireUid()
        val doc = firestore.collection("blockedByUsers")
            .document(uid)
            .collection("users")
            .document(targetUid)
            .get()
            .await()
        return doc.exists()
    }

    override suspend fun getUsersWhoBlockedMe(): Set<String> {
        val uid = requireUid()
        return firestore.collection("blockedByUsers").document(uid).collection("users").get().await()
            .documents.map { it.id }.toSet()
    }
}

