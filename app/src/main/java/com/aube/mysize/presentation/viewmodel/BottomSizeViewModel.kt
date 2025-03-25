package com.aube.mysize.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aube.mysize.domain.model.BottomSize
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
class BottomSizeViewModel @Inject constructor(
    private val insertUseCase: InsertSizeUseCase<BottomSize>,
    private val getAllUseCase: GetSizeListUseCase<BottomSize>,
    private val deleteUseCase: DeleteSizeUseCase<BottomSize>
) : ViewModel() {

    private val _sizes = MutableStateFlow<List<BottomSize>>(emptyList())
    val sizes: StateFlow<List<BottomSize>> = _sizes.asStateFlow()

    init {
        viewModelScope.launch {
            getAllUseCase().collect { _sizes.value = it }
        }
    }

    fun insert(item: BottomSize) {
        viewModelScope.launch { insertUseCase(item) }
    }

    fun delete(item: BottomSize) {
        viewModelScope.launch { deleteUseCase(item) }
    }
}
