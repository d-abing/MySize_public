package com.aube.mysize.presentation.viewmodel.size

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aube.mysize.domain.model.size.BodySize
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
class BodySizeViewModel @Inject constructor(
    private val insertUseCase: InsertSizeUseCase<BodySize>,
    private val getAllUseCase: GetSizeListUseCase<BodySize>,
    private val deleteUseCase: DeleteSizeUseCase<BodySize>
) : ViewModel() {

    private val _sizes = MutableStateFlow<List<BodySize>>(emptyList())
    val sizes: StateFlow<List<BodySize>> = _sizes.asStateFlow()

    init {
        viewModelScope.launch {
            getAllUseCase().collect { _sizes.value = it }
        }
    }

    fun getSizeById(id: Int): BodySize? = sizes.value.find { it.id == id }

    fun insert(item: BodySize) {
        viewModelScope.launch { insertUseCase(item) }
    }

    fun delete(item: BodySize) {
        viewModelScope.launch { deleteUseCase(item) }
    }
}

