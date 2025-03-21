package com.aube.mysize.domain.usecase

import com.aube.mysize.domain.model.BodyProfile
import com.aube.mysize.domain.repository.BodyProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBodyProfilesUseCase @Inject constructor(
    private val repository: BodyProfileRepository
) {
    operator fun invoke(): Flow<List<BodyProfile>> {
        return repository.getAllProfiles()
    }
}
