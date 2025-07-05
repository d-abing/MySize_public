package com.aube.mysize.domain.usecases

import com.aube.mysize.domain.usecase.size.DeleteSizeUseCase
import com.aube.mysize.domain.usecase.size.GetAllSizesUseCase
import com.aube.mysize.domain.usecase.size.GetSizeByIdUseCase
import com.aube.mysize.domain.usecase.size.InsertSizeUseCase

data class SizeUseCases(
    val insertSize: InsertSizeUseCase,
    val deleteSize: DeleteSizeUseCase,
    val getSizeById: GetSizeByIdUseCase,
    val getAllSizes: GetAllSizesUseCase
)
