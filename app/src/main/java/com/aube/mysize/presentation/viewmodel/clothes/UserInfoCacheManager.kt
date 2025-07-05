package com.aube.mysize.presentation.viewmodel.clothes

import com.aube.mysize.domain.usecases.UserUseCases
import javax.inject.Inject

class UserInfoCacheManager @Inject constructor(
    private val userUseCases: UserUseCases
) {
    private val nicknameMap = mutableMapOf<String, String>()

    suspend fun getNickname(uid: String): String {
        return nicknameMap[uid] ?: userUseCases.getUserNickName(uid).also {
            nicknameMap[uid] = it
        }
    }

    fun clear() {
        nicknameMap.clear()
    }
}

