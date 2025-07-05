package com.aube.mysize.domain.usecase.follow

import com.aube.mysize.domain.repository.FollowRepository
import javax.inject.Inject

class UnfollowUserUseCase @Inject constructor(
    private val repo: FollowRepository
) {
    suspend operator fun invoke(uid: String? = null, targetUid: String) {
        repo.unfollow(uid = uid, targetUid = targetUid)
    }
}
