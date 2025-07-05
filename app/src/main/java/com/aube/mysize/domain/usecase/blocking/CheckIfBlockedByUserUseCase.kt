package com.aube.mysize.domain.usecase.blocking

import com.aube.mysize.domain.repository.BlockedUserRepository
import javax.inject.Inject

class CheckIfBlockedByUserUseCase @Inject constructor(
    private val repo: BlockedUserRepository
) {
    suspend operator fun invoke(targetUid: String): Boolean {
        return repo.checkIfBlockedByUser(targetUid)
    }
}
