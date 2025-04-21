package com.aube.mysize.presentation.viewmodel.size

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aube.mysize.domain.model.size.TopSize
import com.aube.mysize.domain.usecase.brand.DeleteBrandUseCase
import com.aube.mysize.domain.usecase.brand.GetBrandListByCategoryUseCase
import com.aube.mysize.domain.usecase.brand.InsertBrandUseCase
import com.aube.mysize.domain.usecase.size.DeleteSizeUseCase
import com.aube.mysize.domain.usecase.size.GetSizeListUseCase
import com.aube.mysize.domain.usecase.size.InsertSizeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopSizeViewModel @Inject constructor(
    private val insertSizeUseCase: InsertSizeUseCase<TopSize>,
    private val getSizeListUseCase: GetSizeListUseCase<TopSize>,
    private val deleteSizeUseCase: DeleteSizeUseCase<TopSize>,
    private val insertBrandUseCase: InsertBrandUseCase,
    private val getBrandListByCategoryUseCase: GetBrandListByCategoryUseCase,
    private val deleteBrandUseCase: DeleteBrandUseCase
) : ViewModel() {

    private val _sizes = MutableStateFlow<List<TopSize>>(emptyList())
    val sizes: StateFlow<List<TopSize>> = _sizes.asStateFlow()
    private val _brandList = MutableStateFlow<List<String>>(emptyList())
    val brandList: StateFlow<List<String>> = _brandList

    init {
        viewModelScope.launch {
            getSizeListUseCase().collect { _sizes.value = it }
        }
        viewModelScope.launch {
            getBrandListByCategoryUseCase("상의").collect { _brandList.value = it }
        }
    }

    fun insert(item: TopSize, onInserted: (Int) -> Unit) {
        viewModelScope.launch {
            val id = insertSizeUseCase(item).toInt()
            onInserted(id)
        }
    }

    fun delete(item: TopSize) {
        viewModelScope.launch { deleteSizeUseCase(item) }
    }

    fun insertBrand(brand: String) {
        viewModelScope.launch {
            insertBrandUseCase(brand, "상의")
        }
    }

    fun deleteBrand(brand: String) {
        viewModelScope.launch {
            deleteBrandUseCase(brand)
        }
    }
}
