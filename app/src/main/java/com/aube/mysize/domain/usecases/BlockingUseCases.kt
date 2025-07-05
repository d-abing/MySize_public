package com.aube.mysize.domain.usecases

import com.aube.mysize.domain.usecase.blocking.CheckIfBlockedByUserUseCase
import com.aube.mysize.domain.usecase.blocking.DeleteBlockedUserUseCase
import com.aube.mysize.domain.usecase.blocking.GetBlockedMeUsersUseCase
import com.aube.mysize.domain.usecase.blocking.GetBlockedUsersUseCase
import com.aube.mysize.domain.usecase.blocking.InsertBlockedUserUseCase

data class BlockingUseCases(
    val insertBlockedUser: InsertBlockedUserUseCase,
    val deleteBlockedUser: DeleteBlockedUserUseCase,
    val getBlockedUsers: GetBlockedUsersUseCase,
    val getBlockedMeUsers: GetBlockedMeUsersUseCase,
    val checkIfBlockedByUser: CheckIfBlockedByUserUseCase
)
