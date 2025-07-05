package com.aube.mysize.domain.usecases

import com.aube.mysize.domain.usecase.clothes.ClearRecentViewsUseCase
import com.aube.mysize.domain.usecase.clothes.DeleteClothesUseCase
import com.aube.mysize.domain.usecase.clothes.GetClothesByIdUseCase
import com.aube.mysize.domain.usecase.clothes.GetFollowingsClothesUseCase
import com.aube.mysize.domain.usecase.clothes.GetOthersClothesByTagUseCase
import com.aube.mysize.domain.usecase.clothes.GetOthersClothesUseCase
import com.aube.mysize.domain.usecase.clothes.GetRecentViewsUseCase
import com.aube.mysize.domain.usecase.clothes.GetUserAllClothesUseCase
import com.aube.mysize.domain.usecase.clothes.GetUserPublicClothesUseCase
import com.aube.mysize.domain.usecase.clothes.InsertClothesUseCase
import com.aube.mysize.domain.usecase.clothes.SaveRecentViewUseCase

data class ClothesUseCases(
    val insertClothes: InsertClothesUseCase,
    val deleteClothes: DeleteClothesUseCase,
    val getClothesById: GetClothesByIdUseCase,
    val getUserAllClothes: GetUserAllClothesUseCase,
    val getUserPublicClothes: GetUserPublicClothesUseCase,
    val getFollowingsClothes: GetFollowingsClothesUseCase,
    val getOthersClothesByTag: GetOthersClothesByTagUseCase,
    val getOthersClothes: GetOthersClothesUseCase,
    val getRecentViews: GetRecentViewsUseCase,
    val saveRecentView: SaveRecentViewUseCase,
    val clearRecentViews: ClearRecentViewsUseCase
)
