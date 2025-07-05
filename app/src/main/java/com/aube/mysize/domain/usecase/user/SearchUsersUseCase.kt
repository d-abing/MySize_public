package com.aube.mysize.domain.usecase.user

import com.aube.mysize.domain.model.user.User
import com.aube.mysize.domain.repository.UserRepository
import javax.inject.Inject

class SearchUsersUseCase @Inject constructor(
    private val repo: UserRepository
) {
    suspend operator fun invoke(query: String): List<User> {
        return repo.searchUsers(query)
    }
}
