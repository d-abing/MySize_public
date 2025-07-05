package com.aube.mysize.domain.usecase.user

import com.aube.mysize.domain.repository.UserRepository
import javax.inject.Inject

class DeleteProfileImageUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(uid: String): Result<Unit> {
        return userRepository.deleteProfileImage(uid)
    }
}
