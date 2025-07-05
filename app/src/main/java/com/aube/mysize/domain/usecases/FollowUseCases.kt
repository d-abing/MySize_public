package com.aube.mysize.domain.usecases

import com.aube.mysize.domain.usecase.follow.FollowUserUseCase
import com.aube.mysize.domain.usecase.follow.GetFollowersCountUseCase
import com.aube.mysize.domain.usecase.follow.GetFollowersUseCase
import com.aube.mysize.domain.usecase.follow.GetFollowingsCountUseCase
import com.aube.mysize.domain.usecase.follow.GetFollowingsUseCase
import com.aube.mysize.domain.usecase.follow.UnfollowUserUseCase

data class FollowUseCases(
    val followUser: FollowUserUseCase,
    val unfollowUser: UnfollowUserUseCase,
    val getFollowers: GetFollowersUseCase,
    val getFollowersCount: GetFollowersCountUseCase,
    val getFollowings: GetFollowingsUseCase,
    val getFollowingsCount: GetFollowingsCountUseCase,
)
