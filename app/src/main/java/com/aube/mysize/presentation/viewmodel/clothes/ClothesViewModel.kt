package com.aube.mysize.presentation.viewmodel.clothes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aube.mysize.domain.model.clothes.Clothes
import com.aube.mysize.domain.usecase.clothes.DeleteClothesUseCase
import com.aube.mysize.domain.usecase.clothes.GetClothesByIdUseCase
import com.aube.mysize.domain.usecase.clothes.GetClothesListUseCase
import com.aube.mysize.domain.usecase.clothes.InsertClothesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClothesViewModel @Inject constructor(
    getClothesListUseCase: GetClothesListUseCase,
    private val getClothesByIdUseCase: GetClothesByIdUseCase,
    private val insertClothesUseCase: InsertClothesUseCase,
    private val deleteClothesUseCase: DeleteClothesUseCase,
) : ViewModel() {

    val clothesList: StateFlow<List<Clothes>> = getClothesListUseCase.invoke()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    fun insert(clothes: Clothes) {
        viewModelScope.launch {
            insertClothesUseCase(clothes)
        }
    }

    fun delete(clothes: Clothes) {
        viewModelScope.launch {
            deleteClothesUseCase(clothes)
        }
    }

    fun getById(id: Int, onResult: (Clothes?) -> Unit) {
        viewModelScope.launch {
            onResult(getClothesByIdUseCase(id))
        }
    }
}
