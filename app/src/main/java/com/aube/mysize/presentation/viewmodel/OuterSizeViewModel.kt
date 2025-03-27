package com.aube.mysize.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aube.mysize.domain.model.OuterSize
import com.aube.mysize.domain.usecase.DeleteBrandUseCase
import com.aube.mysize.domain.usecase.DeleteSizeUseCase
import com.aube.mysize.domain.usecase.GetBrandListByCategoryUseCase
import com.aube.mysize.domain.usecase.GetSizeListUseCase
import com.aube.mysize.domain.usecase.InsertBrandUseCase
import com.aube.mysize.domain.usecase.InsertSizeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OuterSizeViewModel @Inject constructor(
    private val insertSizeUseCase: InsertSizeUseCase<OuterSize>,
    private val getSizeListUseCase: GetSizeListUseCase<OuterSize>,
    private val deleteSizeUseCase: DeleteSizeUseCase<OuterSize>,
    private val insertBrandUseCase: InsertBrandUseCase,
    private val getBrandListByCategoryUseCase: GetBrandListByCategoryUseCase,
    private val deleteBrandUseCase: DeleteBrandUseCase
) : ViewModel() {

    private val _sizes = MutableStateFlow<List<OuterSize>>(emptyList())
    val sizes: StateFlow<List<OuterSize>> = _sizes.asStateFlow()

    private val _brandList = MutableStateFlow<List<String>>(emptyList())
    val brandList: StateFlow<List<String>> = _brandList

    init {
        viewModelScope.launch {
            getSizeListUseCase().collect { _sizes.value = it }
        }
        viewModelScope.launch {
            getBrandListByCategoryUseCase("아우터").collect { _brandList.value = it }
        }
    }

    fun insert(item: OuterSize) {
        viewModelScope.launch { insertSizeUseCase(item) }
    }

    fun delete(item: OuterSize) {
        viewModelScope.launch { deleteSizeUseCase(item) }
    }

    fun insertBrand(brand: String, category: String) {
        viewModelScope.launch {
            insertBrandUseCase(brand, category)
        }
    }

    fun deleteBrand(brand: String) {
        viewModelScope.launch {
            deleteBrandUseCase(brand)
        }
    }
}
