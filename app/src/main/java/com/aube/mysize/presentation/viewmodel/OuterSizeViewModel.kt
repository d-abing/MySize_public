package com.aube.mysize.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aube.mysize.domain.model.OuterSize
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
class OuterSizeViewModel @Inject constructor(
    private val insertUseCase: InsertSizeUseCase<OuterSize>,
    private val getAllUseCase: GetSizeListUseCase<OuterSize>,
    private val deleteUseCase: DeleteSizeUseCase<OuterSize>
) : ViewModel() {

    private val _sizes = MutableStateFlow<List<OuterSize>>(emptyList())
    val sizes: StateFlow<List<OuterSize>> = _sizes.asStateFlow()

    init {
        viewModelScope.launch {
            getAllUseCase().collect { _sizes.value = it }
        }
    }

    fun insert(item: OuterSize) {
        viewModelScope.launch { insertUseCase(item) }
    }

    fun delete(item: OuterSize) {
        viewModelScope.launch { deleteUseCase(item) }
    }
}
