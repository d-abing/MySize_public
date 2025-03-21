package com.aube.mysize.domain.usecase

import com.aube.mysize.domain.model.BodyProfile
import com.aube.mysize.domain.repository.BodyProfileRepository
import javax.inject.Inject

class InsertBodyProfileUseCase @Inject constructor(
    private val repository: BodyProfileRepository
) {
    suspend operator fun invoke(profile: BodyProfile) {
        repository.insertProfile(profile)
    }
}
