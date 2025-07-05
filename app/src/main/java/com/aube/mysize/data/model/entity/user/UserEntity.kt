package com.aube.mysize.data.model.entity.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aube.mysize.domain.model.user.User
import java.time.LocalDateTime

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val uid: String,
    val email: String,
    val nickname: String,
    val profileImageUrl: String?,
    val createdAt: LocalDateTime
)

fun UserEntity.toDomain(): User {
    return User(
        uid = uid,
        email = email,
        nickname = nickname,
        profileImageUrl = profileImageUrl,
        createdAt = createdAt
    )
}

fun User.toEntity(): UserEntity {
    return UserEntity(
        uid = uid,
        email = email,
        nickname = nickname,
        profileImageUrl = profileImageUrl,
        createdAt = createdAt
    )
}