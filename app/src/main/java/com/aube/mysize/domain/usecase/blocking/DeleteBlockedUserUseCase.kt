package com.aube.mysize.domain.usecase.blocking

import com.aube.mysize.domain.repository.BlockedUserRepository
import javax.inject.Inject

class DeleteBlockedUserUseCase @Inject constructor(
    private val repo: BlockedUserRepository
) {
    suspend operator fun invoke(blockedUid: String) {
        repo.deleteBlockedUser(blockedUid)
    }
}
