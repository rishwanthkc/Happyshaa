package com.auraai.ui.mood

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auraai.domain.model.MoodLog
import com.auraai.domain.usecase.AnalyzeMoodUseCase
import com.auraai.domain.usecase.CacheMoodLocallyUseCase
import com.auraai.domain.usecase.GetMoodHistoryUseCase
import com.auraai.domain.usecase.GetCurrentUserTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoodViewModel @Inject constructor(
    private val analyzeMoodUseCase: AnalyzeMoodUseCase,
    private val getMoodHistoryUseCase: GetMoodHistoryUseCase,
    private val cacheMoodLocallyUseCase: CacheMoodLocallyUseCase,
    private val getTokenUseCase: GetCurrentUserTokenUseCase
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _lastAnalysisResult = MutableStateFlow<MoodLog?>(null)
    val lastAnalysisResult: StateFlow<MoodLog?> = _lastAnalysisResult.asStateFlow()

    fun getMoodHistory(uid: String): StateFlow<List<MoodLog>> {
        return getMoodHistoryUseCase(uid).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    }

    fun analyzeAndLogMood(text: String, fallbackUid: String) {
        if (text.isBlank()) {
            _errorMessage.value = "Please write a sentence about how you feel."
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            
            getTokenUseCase().onSuccess { token ->
                analyzeMoodUseCase(token, text)
                    .onSuccess { moodLog ->
                        _lastAnalysisResult.value = moodLog
                    }
                    .onFailure { exception ->
                        // Cache locally as fallback in offline mode
                        cacheMoodLocallyUseCase(fallbackUid, 3, text)
                        _errorMessage.value = "Offline mode: Saved mood locally. (${exception.localizedMessage})"
                    }
            }.onFailure { exception ->
                // Token failed, fallback locally
                cacheMoodLocallyUseCase(fallbackUid, 3, text)
                _errorMessage.value = "Auth token expired. Saved mood locally."
            }
            
            _isLoading.value = false
        }
    }

    fun clearState() {
        _errorMessage.value = null
        _lastAnalysisResult.value = null
    }
}
