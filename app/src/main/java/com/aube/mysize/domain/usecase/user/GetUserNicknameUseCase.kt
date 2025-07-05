package com.aube.mysize.domain.usecase.user

import com.aube.mysize.domain.repository.UserRepository
import javax.inject.Inject

class GetUserNicknameUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(uid: String): String {
        return userRepository.getUserNickname(uid)
    }
}
