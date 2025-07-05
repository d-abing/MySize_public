package com.aube.mysize.domain.usecase.user

import com.aube.mysize.domain.model.user.User
import com.aube.mysize.domain.repository.UserRepository
import javax.inject.Inject

class FetchUserFromFirestoreUseCase @Inject constructor(
    private val repo: UserRepository
) {
    suspend operator fun invoke(uid: String): Result<User> {
        return repo.fetchUserFromFirebase(uid)
    }
}
