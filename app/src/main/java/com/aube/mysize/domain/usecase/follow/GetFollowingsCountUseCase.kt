package com.aube.mysize.domain.usecase.follow

import com.aube.mysize.domain.repository.FollowRepository
import javax.inject.Inject

class GetFollowingsCountUseCase @Inject constructor(
    private val repo: FollowRepository
) {
    suspend operator fun invoke(userUid: String): Int {
        return repo.getFollowingsCount(userUid)
    }
}
