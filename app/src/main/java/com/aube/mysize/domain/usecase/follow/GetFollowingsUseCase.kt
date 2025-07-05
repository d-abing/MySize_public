package com.aube.mysize.domain.usecase.follow

import com.aube.mysize.domain.model.follow.UserSummary
import com.aube.mysize.domain.repository.FollowRepository
import javax.inject.Inject

class GetFollowingsUseCase @Inject constructor(
    private val repo: FollowRepository
) {
    suspend operator fun invoke(uid: String): List<UserSummary> {
        return repo.getFollowings(uid)
    }
}