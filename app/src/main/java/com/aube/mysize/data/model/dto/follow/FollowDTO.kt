package com.aube.mysize.data.model.dto.follow

import androidx.annotation.Keep
import com.google.firebase.Timestamp

@Keep
data class FollowTimestampDTO(
    val followedAt: Timestamp = Timestamp.now()
)