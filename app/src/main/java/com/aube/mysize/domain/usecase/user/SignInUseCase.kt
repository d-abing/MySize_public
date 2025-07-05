package com.aube.mysize.domain.usecase.user

import com.aube.mysize.domain.repository.UserRepository
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val repo: UserRepository
) {
    suspend fun withEmail(email: String, password: String): Result<FirebaseUser> {
        return repo.signInWithEmail(email, password)
    }

    suspend fun withGoogle(idToken: String): Result<FirebaseUser> {
        return repo.signInWithGoogle(idToken)
    }
}
