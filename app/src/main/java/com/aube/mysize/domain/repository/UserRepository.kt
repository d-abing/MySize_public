package com.aube.mysize.domain.repository

import android.net.Uri
import com.aube.mysize.domain.model.user.User
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun signInWithEmail(email: String, password: String): Result<FirebaseUser>
    suspend fun signInWithGoogle(idToken: String): Result<FirebaseUser>
    suspend fun signOut(): Result<Unit>
    suspend fun signUp(email: String, password: String, nickname: String): Result<FirebaseUser>
    suspend fun saveUserToFirestore(uid: String, email: String, nickname: String, photoUrl: String?): Result<Unit>
    suspend fun sendEmailVerification(): Result<Unit>
    suspend fun isEmailVerified(): Result<Boolean>
    suspend fun reauthenticateAndChangePassword(currentPassword: String, newPassword: String): Result<Unit>
    suspend fun reauthenticateAndDeleteWithEmail(password: String): Result<Unit>
    suspend fun reauthenticateAndDeleteWithGoogle(): Result<Unit>
    fun getUserFromCache(): Flow<User?>
    suspend fun fetchUserFromFirebase(uid: String): Result<User>
    suspend fun saveUserToCache(user: User)
    suspend fun searchUsers(query: String): List<User>
    suspend fun getUserNickname(uid: String): String
    suspend fun getUserProfileImageUrl(uid: String): String
    suspend fun uploadProfileImage(uid: String, imageUri: Uri): Result<String>
    suspend fun deleteProfileImage(uid: String): Result<Unit>
    suspend fun updateNickname(uid: String, nickname: String): Result<Unit>
}