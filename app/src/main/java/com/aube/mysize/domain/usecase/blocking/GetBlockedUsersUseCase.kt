package com.aube.mysize.domain.usecase.blocking

import com.aube.mysize.domain.model.follow.UserSummary
import com.aube.mysize.domain.repository.BlockedUserRepository
import javax.inject.Inject

class GetBlockedUsersUseCase @Inject constructor(
    private val repo: BlockedUserRepository
) {
    suspend operator fun invoke(): List<UserSummary> {
        return repo.getBlockedUsers()
    }
}
