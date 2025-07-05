package com.aube.mysize.domain.usecase.blocking

import com.aube.mysize.domain.repository.BlockedUserRepository
import javax.inject.Inject

class GetBlockedMeUsersUseCase @Inject constructor(
    private val repo: BlockedUserRepository
) {
    suspend operator fun invoke(): Set<String> {
        return repo.getUsersWhoBlockedMe()
    }
}