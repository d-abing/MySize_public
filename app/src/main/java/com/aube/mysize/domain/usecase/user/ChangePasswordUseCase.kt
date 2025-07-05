package com.aube.mysize.domain.usecase.user

import com.aube.mysize.domain.repository.UserRepository
import javax.inject.Inject

class ChangePasswordUseCase @Inject constructor(
    private val repo: UserRepository
) {
    suspend operator fun invoke(currentPassword: String, newPassword: String): Result<Unit> {
        return repo.reauthenticateAndChangePassword(currentPassword, newPassword)
    }
}
