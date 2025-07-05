package com.aube.mysize.domain.usecase.user

import com.aube.mysize.domain.model.user.User
import com.aube.mysize.domain.repository.UserRepository
import javax.inject.Inject

class CacheUserUseCase @Inject constructor(
    private val repo: UserRepository
) {
    suspend operator fun invoke(user: User) {
        repo.saveUserToCache(user)
    }
}
