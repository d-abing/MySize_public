package com.aube.mysize.domain.usecase.follow

import com.aube.mysize.domain.repository.FollowRepository
import javax.inject.Inject

class FollowUserUseCase @Inject constructor(
    private val repo: FollowRepository
) {
    suspend operator fun invoke(targetUid: String) {
        repo.follow(targetUid)
    }
}
