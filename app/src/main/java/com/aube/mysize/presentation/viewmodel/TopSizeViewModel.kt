package com.aube.mysize.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aube.mysize.domain.model.TopSize
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
class TopSizeViewModel @Inject constructor(
    private val insertUseCase: InsertSizeUseCase<TopSize>,
    private val getAllUseCase: GetSizeListUseCase<TopSize>,
    private val deleteUseCase: DeleteSizeUseCase<TopSize>
) : ViewModel() {

    private val _sizes = MutableStateFlow<List<TopSize>>(emptyList())
    val sizes: StateFlow<List<TopSize>> = _sizes.asStateFlow()

    init {
        viewModelScope.launch {
            getAllUseCase().collect { _sizes.value = it }
        }
    }

    fun insert(item: TopSize) {
        viewModelScope.launch { insertUseCase(item) }
    }

    fun delete(item: TopSize) {
        viewModelScope.launch { deleteUseCase(item) }
    }
}
