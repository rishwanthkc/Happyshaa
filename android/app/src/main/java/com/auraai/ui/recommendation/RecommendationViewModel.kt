package com.auraai.ui.recommendation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auraai.domain.model.RecommendationCard
import com.auraai.domain.usecase.GetDailyRecommendationsUseCase
import com.auraai.domain.usecase.GetCurrentUserTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecommendationViewModel @Inject constructor(
    private val getRecommendationsUseCase: GetDailyRecommendationsUseCase,
    private val getTokenUseCase: GetCurrentUserTokenUseCase
) : ViewModel() {

    private val _recommendations = MutableStateFlow<List<RecommendationCard>>(emptyList())
    val recommendations: StateFlow<List<RecommendationCard>> = _recommendations.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun loadRecommendations() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            getTokenUseCase().onSuccess { token ->
                getRecommendationsUseCase(token).onSuccess {
                    _recommendations.value = it
                }.onFailure {
                    _errorMessage.value = "Failed to load daily recommendations: ${it.localizedMessage}"
                }
            }.onFailure {
                _errorMessage.value = "Auth token lookup failed: ${it.localizedMessage}"
            }
            _isLoading.value = false
        }
    }
}
