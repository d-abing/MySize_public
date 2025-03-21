package com.aube.mysize.data.repository

import com.aube.mysize.data.database.BodyProfileDao
import com.aube.mysize.data.model.toDomain
import com.aube.mysize.domain.model.BodyProfile
import com.aube.mysize.domain.model.toEntity
import com.aube.mysize.domain.repository.BodyProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BodyProfileProfileRepositoryImpl @Inject constructor(
    private val dao: BodyProfileDao
) : BodyProfileRepository {

    override suspend fun insertProfile(profile: BodyProfile) {
        dao.insert(profile.toEntity())
    }

    override fun getAllProfiles(): Flow<List<BodyProfile>> {
        return dao.getAll().map { list ->
            list.map { it.toDomain() }
        }
    }
}
