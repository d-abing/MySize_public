package com.aube.mysize.presentation.viewmodel.recommend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aube.mysize.domain.model.size.BodySize
import com.aube.mysize.domain.usecase.recommend.RecommendShopsUseCase
import com.aube.mysize.presentation.model.recommend.RecommendedShopResult
import com.aube.mysize.presentation.model.recommend.UserPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecommendViewModel @Inject constructor(
    private val recommendShopsUseCase: RecommendShopsUseCase
) : ViewModel() {

    private val _recommendedShops = MutableStateFlow<RecommendedShopResult>(RecommendedShopResult.Loading)
    val recommendedShops: StateFlow<RecommendedShopResult> = _recommendedShops

    fun loadRecommendations(forceRefresh: Boolean = false, bodySize: BodySize, preference: UserPreference) {
        viewModelScope.launch {
            _recommendedShops.value = recommendShopsUseCase(forceRefresh, bodySize, preference)
        }
    }
}
