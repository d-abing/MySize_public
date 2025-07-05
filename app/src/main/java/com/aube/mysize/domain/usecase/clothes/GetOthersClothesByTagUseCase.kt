package com.aube.mysize.domain.usecase.clothes

import com.aube.mysize.domain.model.clothes.Clothes
import com.aube.mysize.domain.repository.ClothesRepository
import com.google.firebase.firestore.DocumentSnapshot
import javax.inject.Inject

class GetOthersClothesByTagUseCase @Inject constructor(
    private val repo: ClothesRepository
) {
    suspend operator fun invoke(
        tag: String,
        pageSize: Int,
        lastSnapshot: DocumentSnapshot?
    ): Pair<List<Clothes>, DocumentSnapshot?> {
        return repo.getClothesByTag(tag, pageSize, lastSnapshot)
    }
}
