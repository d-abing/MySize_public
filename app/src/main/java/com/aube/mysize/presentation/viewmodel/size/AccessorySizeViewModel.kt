package com.aube.mysize.presentation.viewmodel.size

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aube.mysize.domain.model.size.AccessorySize
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
class AccessorySizeViewModel @Inject constructor(
    private val insertSizeUseCase: InsertSizeUseCase<AccessorySize>,
    private val getSizeListUseCase: GetSizeListUseCase<AccessorySize>,
    private val deleteSizeUseCase: DeleteSizeUseCase<AccessorySize>,
    private val insertBrandUseCase: InsertBrandUseCase,
    private val getBrandListByCategoryUseCase: GetBrandListByCategoryUseCase,
    private val deleteBrandUseCase: DeleteBrandUseCase
) : ViewModel() {

    private val _sizes = MutableStateFlow<List<AccessorySize>>(emptyList())
    val sizes: StateFlow<List<AccessorySize>> = _sizes.asStateFlow()

    private val _brandList = MutableStateFlow<List<String>>(emptyList())
    val brandList: StateFlow<List<String>> = _brandList.asStateFlow()

    init {
        viewModelScope.launch {
            getSizeListUseCase().collect { _sizes.value = it }
        }
        viewModelScope.launch {
            getBrandListByCategoryUseCase("악세사리").collect { _brandList.value = it }
        }
    }

    fun insert(item: AccessorySize, onInserted: (Int) -> Unit) {
        viewModelScope.launch {
            val id = insertSizeUseCase(item).toInt()
            onInserted(id)
        }
    }

    fun delete(item: AccessorySize) {
        viewModelScope.launch { deleteSizeUseCase(item) }
    }

    fun insertBrand(brand: String) {
        viewModelScope.launch {
            insertBrandUseCase(brand, "악세사리")
        }
    }

    fun deleteBrand(brand: String) {
        viewModelScope.launch {
            deleteBrandUseCase(brand)
        }
    }
}

