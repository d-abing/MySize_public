package com.aube.mysize.presentation.viewmodel.clothes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aube.mysize.domain.usecases.ClothesUseCases
import com.aube.mysize.presentation.model.clothes.ClothesWithAuthor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClothesDetailViewModel @Inject constructor(
    private val clothesUseCases: ClothesUseCases,
    private val userInfoCacheManager: UserInfoCacheManager,
    private val clothesFilterManager: ClothesFilterManager
) : ViewModel() {

    private val _clothesDetail = MutableStateFlow<ClothesWithAuthor?>(null)
    val clothesDetail: StateFlow<ClothesWithAuthor?> = _clothesDetail

    fun getById(id: String) {
        viewModelScope.launch {
            val clothes = clothesUseCases.getClothesById(id) ?: return@launch
            val nickname = userInfoCacheManager.getNickname(clothes.createUserId)

            val filtered = clothesFilterManager.filterClothes(listOf(clothes))

            if (filtered.isNotEmpty()) {
                _clothesDetail.value = ClothesWithAuthor(clothes, nickname)
            } else {
                _clothesDetail.value = null
            }
        }
    }

    fun clearClothesDetailState() {
        _clothesDetail.value = null
    }
}
