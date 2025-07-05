package com.aube.mysize.domain.model.user

import java.time.LocalDateTime


data class User(
    val uid: String,
    val email: String,
    val nickname: String,
    val profileImageUrl: String?,
    val createdAt: LocalDateTime,
)