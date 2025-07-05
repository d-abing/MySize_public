package com.aube.mysize.presentation.model.user

import com.aube.mysize.domain.model.user.User
import java.time.LocalDateTime

data class UserInfo(
    val uid: String = "",
    val email: String = "",
    var nickname: String = "",
    var profileImageUrl: String = "",
    val createdAt: LocalDateTime = LocalDateTime.now()
)

fun UserInfo.toDomain(): User {
    return User(
        uid = this.uid,
        email = this.email,
        nickname = this.nickname,
        profileImageUrl = this.profileImageUrl,
        createdAt = this.createdAt
    )
}

fun User.toUi(): UserInfo {
    return UserInfo(
        uid = uid,
        email = email,
        nickname = nickname,
        profileImageUrl = profileImageUrl ?: "",
        createdAt = createdAt
    )
}