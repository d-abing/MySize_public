package com.aube.mysize.domain.usecase.user

import com.aube.mysize.domain.repository.UserRepository
import javax.inject.Inject

class PersistUserToFirestoreUseCase @Inject constructor(
    private val repo: UserRepository
) {
    suspend operator fun invoke(uid: String, email: String, nickname: String, photoUrl: String?): Result<Unit> {
        return repo.saveUserToFirestore(uid, email, nickname, photoUrl)
    }
}
