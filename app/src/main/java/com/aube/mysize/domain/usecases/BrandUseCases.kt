package com.aube.mysize.domain.usecases

import com.aube.mysize.domain.usecase.brand.DeleteBrandUseCase
import com.aube.mysize.domain.usecase.brand.GetBrandListByCategoryUseCase
import com.aube.mysize.domain.usecase.brand.InsertBrandUseCase

data class BrandUseCases(
    val insertBrand: InsertBrandUseCase,
    val deleteBrand: DeleteBrandUseCase,
    val getBrandListByCategory: GetBrandListByCategoryUseCase
)
