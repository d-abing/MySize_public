package com.aube.mysize.domain.usecase.user

import com.aube.mysize.domain.repository.UserRepository
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(
    private val repo: UserRepository
) {
    suspend fun deleteWithEmail(password: String): Result<Unit> {
        return repo.reauthenticateAndDeleteWithEmail(password)
    }

    suspend fun deleteWithGoogle(): Result<Unit> {
        return repo.reauthenticateAndDeleteWithGoogle()
    }
}
