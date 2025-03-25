package com.aube.mysize.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aube.mysize.domain.model.ShoeSize
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
class ShoeSizeViewModel @Inject constructor(
    private val insertUseCase: InsertSizeUseCase<ShoeSize>,
    private val getAllUseCase: GetSizeListUseCase<ShoeSize>,
    private val deleteUseCase: DeleteSizeUseCase<ShoeSize>
) : ViewModel() {

    private val _sizes = MutableStateFlow<List<ShoeSize>>(emptyList())
    val sizes: StateFlow<List<ShoeSize>> = _sizes.asStateFlow()

    init {
        viewModelScope.launch {
            getAllUseCase().collect { _sizes.value = it }
        }
    }

    fun insert(item: ShoeSize) {
        viewModelScope.launch { insertUseCase(item) }
    }

    fun delete(item: ShoeSize) {
        viewModelScope.launch { deleteUseCase(item) }
    }
}
