package com.aube.mysize.domain.usecase.user

import com.aube.mysize.domain.repository.UserRepository
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val repo: UserRepository
) {
    suspend operator fun invoke(email: String, password: String, nickname: String): Result<FirebaseUser> {
        return repo.signUp(email, password, nickname)
    }
}
