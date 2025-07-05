package com.aube.mysize.data.repository

import android.content.Context
import android.net.Uri
import androidx.datastore.preferences.core.edit
import com.aube.mysize.data.database.MySizeDatabase
import com.aube.mysize.data.database.dao.UserDao
import com.aube.mysize.data.datastore.dataStore
import com.aube.mysize.data.model.dto.size.collectionName
import com.aube.mysize.data.model.dto.user.UserDTO
import com.aube.mysize.data.model.dto.user.toDomain
import com.aube.mysize.data.model.entity.user.toDomain
import com.aube.mysize.data.model.entity.user.toEntity
import com.aube.mysize.domain.model.size.SizeCategory
import com.aube.mysize.domain.model.user.User
import com.aube.mysize.domain.repository.UserRepository
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageException
import com.google.firebase.storage.StorageMetadata
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import timber.log.Timber
import java.time.LocalDateTime
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val dao: UserDao,
    private val appDatabase: MySizeDatabase,
    private val context: Context
) : UserRepository {

    override suspend fun signInWithEmail(email: String, password: String): Result<FirebaseUser> = runCatching {
        val result = auth.signInWithEmailAndPassword(email, password).await()
        result.user ?: throw IllegalStateException("User not found")
    }

    override suspend fun signInWithGoogle(idToken: String): Result<FirebaseUser> = runCatching {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        val result = auth.signInWithCredential(credential).await()
        result.user ?: throw IllegalStateException("User not found")
    }

    override suspend fun signOut(): Result<Unit> = runCatching {
        auth.signOut()
        signOutGoogle()

        withContext(Dispatchers.IO) {
            appDatabase.clearAllTables()
        }
        context.dataStore.edit { it.clear() }
    }

    private suspend fun signOutGoogle() {
        val googleClient = GoogleSignIn.getClient(
            context,
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
        )
        googleClient.signOut().await()
    }

    override suspend fun signUp(email: String, password: String, nickname: String): Result<FirebaseUser> = runCatching {
        val result = auth.createUserWithEmailAndPassword(email, password).await()
        val user = result.user ?: throw IllegalStateException("User not found")
        user.updateProfile(userProfileChangeRequest { displayName = nickname }).await()
        user
    }

    override suspend fun saveUserToFirestore(uid: String, email: String, nickname: String, photoUrl: String?): Result<Unit> = runCatching {
        val userData = UserDTO(
            uid = uid,
            email = email,
            nickname = nickname,
            profileImageUrl = photoUrl,
            createdAt = Timestamp.now()
        )
        firestore.collection("users").document(uid).set(userData).await()
    }

    override suspend fun sendEmailVerification(): Result<Unit> = runCatching {
        val user = auth.currentUser ?: throw IllegalStateException("User not logged in")
        user.sendEmailVerification().await()
    }

    override suspend fun isEmailVerified(): Result<Boolean> = runCatching {
        val user = auth.currentUser ?: throw IllegalStateException("User not logged in")
        user.reload().await()
        user.isEmailVerified
    }

    override suspend fun reauthenticateAndChangePassword(currentPassword: String, newPassword: String): Result<Unit> = runCatching {
        val user = auth.currentUser ?: throw IllegalStateException("User not logged in")
        val email = user.email ?: throw IllegalStateException("Email not found")

        val credential = EmailAuthProvider.getCredential(email, currentPassword)
        user.reauthenticate(credential).await()
        user.updatePassword(newPassword).await()
    }

    override suspend fun reauthenticateAndDeleteWithEmail(password: String): Result<Unit> = runCatching {
        val user = auth.currentUser ?: throw IllegalStateException("User not logged in")
        val email = user.email ?: throw IllegalStateException("Email not found")

        val credential = EmailAuthProvider.getCredential(email, password)
        user.reauthenticate(credential).await()

        val uid = user.uid
        deleteUserData(uid)
        user.delete().await()
        signOut()
    }

    override suspend fun reauthenticateAndDeleteWithGoogle(): Result<Unit> = runCatching {
        val user = auth.currentUser ?: throw IllegalStateException("User not logged in")
        val account = GoogleSignIn.getLastSignedInAccount(context) ?: throw IllegalStateException("Google account not found")

        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        user.reauthenticate(credential).await()

        val uid = user.uid
        deleteUserData(uid)
        user.delete().await()
        signOut()
    }

    suspend fun deleteUserData(uid: String) {
        val storage = FirebaseStorage.getInstance()
        val firestore = Firebase.firestore
        val failedSteps = mutableListOf<String>()

        // 1. 프로필 이미지 삭제
        try {
            storage.reference.child("profile_images/$uid/profile.jpg").delete().await()
            Timber.d("✅ 프로필 이미지 삭제 완료")
        } catch (e: StorageException) {
            if (e.errorCode == StorageException.ERROR_OBJECT_NOT_FOUND) {
                Timber.w("⚠️ 프로필 이미지 없음 (무시)")
            } else {
                Timber.e(e, "❌ 프로필 이미지 삭제 실패")
                failedSteps.add("profileImageDeleteFailed")
            }
        }

        // 2. 옷 이미지 및 문서 삭제
        try {
            val clothesItems = firestore.collection("clothes").document(uid).collection("items").get().await()
            for (doc in clothesItems) {
                val imageUrl = doc.getString("imageUrl") ?: continue
                val encodedPath = imageUrl.substringAfter("/o/").substringBefore("?")
                val decodedPath = Uri.decode(encodedPath)

                try {
                    storage.reference.child(decodedPath).delete().await()
                    Timber.d("✅ 옷 이미지 삭제 완료: $decodedPath")
                } catch (e: Exception) {
                    Timber.e(e, "❌ 옷 이미지 삭제 실패: $decodedPath")
                    failedSteps.add("clothesImageDeleteFailed:$decodedPath")
                }

                doc.reference.delete().await()
            }
            firestore.collection("clothes").document(uid).delete().await()
            Timber.d("✅ 옷 문서 삭제 완료")
        } catch (e: Exception) {
            Timber.e(e, "❌ 옷 데이터 삭제 실패")
            failedSteps.add("clothesDataDeleteFailed")
        }

        // 3. 사이즈 데이터 삭제
        try {
            val categories = SizeCategory.entries.map { it.collectionName() }
            for (category in categories) {
                val sizeDocs = firestore.collection("sizes").document(uid).collection(category).get().await()
                for (doc in sizeDocs) {
                    doc.reference.delete().await()
                }
            }
            firestore.collection("sizes").document(uid).collection("body_sizes").document("current").delete().await()
            firestore.collection("sizes").document(uid).delete().await()
            Timber.d("✅ 사이즈 데이터 삭제 완료")
        } catch (e: Exception) {
            Timber.e(e, "❌ 사이즈 데이터 삭제 실패")
            failedSteps.add("sizeDataDeleteFailed")
        }

        // 4. 차단 관계 삭제
        try {
            val blockedUsers = firestore.collection("blockedUsers").document(uid).collection("users").get().await()
            for (doc in blockedUsers) {
                val targetUid = doc.id
                firestore.collection("blockedByUsers").document(targetUid)
                    .collection("users").document(uid).delete().await()
                doc.reference.delete().await()
            }
            firestore.collection("blockedUsers").document(uid).delete().await()

            val blockedByMe = firestore.collection("blockedByUsers").document(uid).collection("users").get().await()
            for (doc in blockedByMe) {
                doc.reference.delete().await()
            }
            firestore.collection("blockedByUsers").document(uid).delete().await()
            Timber.d("✅ 차단 관계 삭제 완료")
        } catch (e: Exception) {
            Timber.e(e, "❌ 차단 관계 삭제 실패")
            failedSteps.add("blockRelationDeleteFailed")
        }

        // 5. 팔로우/팔로워
        try {
            val followings = firestore.collection("users").document(uid).collection("followings").get().await()
            for (doc in followings) {
                val targetUid = doc.id
                doc.reference.delete().await()
                firestore.collection("users").document(targetUid).collection("followers").document(uid).delete().await()
            }
            val followers = firestore.collection("users").document(uid).collection("followers").get().await()
            for (doc in followers) {
                val targetUid = doc.id
                doc.reference.delete().await()
                firestore.collection("users").document(targetUid).collection("followings").document(uid).delete().await()
            }
            Timber.d("✅ 팔로우/팔로워 삭제 완료")
        } catch (e: Exception) {
            Timber.e(e, "❌ 팔로우/팔로워 삭제 실패")
            failedSteps.add("followRelationDeleteFailed")
        }

        // 6. 최근 본 옷
        try {
            firestore.collection("users").document(uid).collection("recentViews").get().await().forEach {
                it.reference.delete().await()
            }
            Timber.d("✅ 최근 본 옷 삭제 완료")
        } catch (e: Exception) {
            Timber.e(e, "❌ 최근 본 옷 삭제 실패")
            failedSteps.add("recentViewsDeleteFailed")
        }

        // 7. 신고 기록
        try {
            firestore.collection("reports").document(uid).collection("items").get().await().forEach {
                it.reference.delete().await()
            }
            firestore.collection("reports").document(uid).delete().await()
            Timber.d("✅ 신고 기록 삭제 완료")
        } catch (e: Exception) {
            Timber.e(e, "❌ 신고 기록 삭제 실패")
            failedSteps.add("reportDeleteFailed")
        }

        // 8. 사용자 문서
        try {
            firestore.collection("users").document(uid).delete().await()
            Timber.d("✅ 사용자 문서 삭제 완료")
        } catch (e: Exception) {
            Timber.e(e, "❌ 사용자 문서 삭제 실패")
            failedSteps.add("userDocumentDeleteFailed")
        }

        // 🔺 실패한 항목 Firestore에 기록
        if (failedSteps.isNotEmpty()) {
            val errorLog = mapOf(
                "timestamp" to FieldValue.serverTimestamp(),
                "uid" to uid,
                "failures" to failedSteps,
                "status" to "needs_manual_cleanup"
            )
            firestore.collection("errors").document("deleteFailures")
                .collection("users").document(uid).set(errorLog).await()

            Timber.w("❗ 일부 삭제 실패 기록됨: $failedSteps")
        }
    }

    override fun getUserFromCache(): Flow<User?> = dao.get().mapNotNull { it?.toDomain() }

    override suspend fun fetchUserFromFirebase(uid: String): Result<User> = runCatching {
        val snapshot = firestore.collection("users").document(uid).get().await()
        val dto = snapshot.toObject(UserDTO::class.java) ?: throw Exception("User not found")
        dto.toDomain()
    }

    override suspend fun saveUserToCache(user: User) {
        dao.insert(user.toEntity())
    }

    override suspend fun searchUsers(query: String): List<User> {
        val snapshot = firestore
            .collection("users")
            .get()
            .await()

        return snapshot.documents.mapNotNull { doc ->
            val uid = doc.id
            val nickname = doc.getString("nickname") ?: return@mapNotNull null
            if (!nickname.contains(query, ignoreCase = true)) return@mapNotNull null
            val profileImageUrl = doc.getString("profileImageUrl") ?: ""
            User(
                uid = uid,
                email = "",
                nickname = nickname,
                profileImageUrl = profileImageUrl,
                createdAt = LocalDateTime.now(),
            )
        }
    }

    override suspend fun getUserNickname(uid: String): String {
        return try {
            firestore.collection("users").document(uid).get().await()
                .getString("nickname") ?: ""
        } catch (e: Exception) {
            Timber.e(e, "nickname fetch failed for $uid")
            ""
        }
    }

    override suspend fun getUserProfileImageUrl(uid: String): String {
        return try {
            firestore.collection("users").document(uid).get().await()
                .getString("profileImageUrl") ?: ""
        } catch (e: Exception) {
            Timber.e(e, "profile image fetch failed for $uid")
            ""
        }
    }

    override suspend fun uploadProfileImage(uid: String, imageUri: Uri): Result<String> = runCatching {
        val storageRef = FirebaseStorage.getInstance().reference
            .child("profile_images/$uid/profile.jpg")

        val metadata = StorageMetadata.Builder()
            .setCustomMetadata("owner", uid)
            .build()

        withTimeout(10_000L) {
            storageRef.putFile(imageUri, metadata).await()
            val downloadUri = storageRef.downloadUrl.await()

            val profileUpdates = userProfileChangeRequest {
                photoUri = downloadUri
            }
            Firebase.auth.currentUser?.updateProfile(profileUpdates)?.await()

            Firebase.firestore.collection("users")
                .document(uid)
                .update("profileImageUrl", downloadUri.toString())
                .await()

            downloadUri.toString()
        }
    }

    override suspend fun deleteProfileImage(uid: String): Result<Unit> = runCatching {
        val storageRef = FirebaseStorage.getInstance().reference
            .child("profile_images/$uid/profile.jpg")

        withTimeout(10_000L) {
            storageRef.delete().await()

            val profileUpdates = userProfileChangeRequest {
                photoUri = null
            }
            Firebase.auth.currentUser?.updateProfile(profileUpdates)?.await()

            Firebase.firestore.collection("users")
                .document(uid)
                .update("profileImageUrl", FieldValue.delete())
                .await()
        }
    }

    override suspend fun updateNickname(uid: String, nickname: String): Result<Unit> = runCatching {
        val profileUpdate = userProfileChangeRequest {
            displayName = nickname
        }
        Firebase.auth.currentUser?.updateProfile(profileUpdate)?.await()

        Firebase.firestore.collection("users")
            .document(uid)
            .update("nickname", nickname)
            .await()
    }
}
