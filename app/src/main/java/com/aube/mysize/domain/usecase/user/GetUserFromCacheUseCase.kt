package com.aube.mysize.domain.usecase.user

import com.aube.mysize.domain.model.user.User
import com.aube.mysize.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserFromCacheUseCase @Inject constructor(
    private val repo: UserRepository
) {
    operator fun invoke(): Flow<User?> {
        return repo.getUserFromCache()
    }
}
