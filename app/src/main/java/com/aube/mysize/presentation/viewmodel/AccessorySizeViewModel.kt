package com.aube.mysize.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aube.mysize.domain.model.AccessorySize
import com.aube.mysize.domain.usecase.DeleteSizeUseCase
import com.aube.mysize.domain.usecase.GetSizeListUseCase
import com.aube.mysize.domain.usecase.InsertSizeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccessorySizeViewModel @Inject constructor(
    private val insertUseCase: InsertSizeUseCase<AccessorySize>,
    private val getAllUseCase: GetSizeListUseCase<AccessorySize>,
    private val deleteUseCase: DeleteSizeUseCase<AccessorySize>
) : ViewModel() {

    private val _sizes = MutableStateFlow<List<AccessorySize>>(emptyList())
    val sizes: StateFlow<List<AccessorySize>> = _sizes.asStateFlow()

    init {
        viewModelScope.launch {
            getAllUseCase().collect { _sizes.value = it }
        }
    }

    fun insert(item: AccessorySize) {
        viewModelScope.launch { insertUseCase(item) }
    }

    fun delete(item: AccessorySize) {
        viewModelScope.launch { deleteUseCase(item) }
    }
}
