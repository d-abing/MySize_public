package com.aube.mysize.data.model.dto.user

import androidx.annotation.Keep
import com.aube.mysize.domain.model.user.User
import com.aube.mysize.utils.toLocalDateTime
import com.aube.mysize.utils.toTimestamp
import com.google.firebase.Timestamp

@Keep
data class UserDTO(
    val uid: String = "",
    val email: String = "",
    val nickname: String = "",
    val profileImageUrl: String? = null,
    val createdAt: Timestamp = Timestamp.now()
)

fun UserDTO.toDomain(): User {
    return User(
        uid = uid,
        email = email,
        nickname = nickname,
        profileImageUrl = profileImageUrl,
        createdAt = createdAt.toLocalDateTime()
    )
}

fun User.toDTO(): UserDTO {
    return UserDTO(
        uid = uid,
        email = email,
        nickname = nickname,
        profileImageUrl = profileImageUrl,
        createdAt = createdAt.toTimestamp()
    )
}