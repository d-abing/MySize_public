package com.aube.mysize.domain.repository

import com.aube.mysize.domain.model.BodyProfile
import kotlinx.coroutines.flow.Flow

interface BodyProfileRepository {
    suspend fun insertProfile(profile: BodyProfile)
    fun getAllProfiles(): Flow<List<BodyProfile>>
}