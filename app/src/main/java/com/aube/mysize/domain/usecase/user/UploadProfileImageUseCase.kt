package com.aube.mysize.domain.usecase.user

import android.net.Uri
import com.aube.mysize.domain.repository.UserRepository
import javax.inject.Inject

class UploadProfileImageUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(uid: String, uri: Uri): Result<String> {
        return userRepository.uploadProfileImage(uid, uri)
    }
}
