package com.auraai.ui.meditation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auraai.domain.model.MeditationSession
import com.auraai.domain.usecase.MeditationUseCases
import com.auraai.domain.usecase.GetCurrentUserTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MeditationViewModel @Inject constructor(
    private val useCases: MeditationUseCases,
    private val getTokenUseCase: GetCurrentUserTokenUseCase
) : ViewModel() {

    private val _sessions = MutableStateFlow<List<MeditationSession>>(emptyList())
    val sessions: StateFlow<List<MeditationSession>> = _sessions.asStateFlow()

    private val _streak = MutableStateFlow(0)
    val streak: StateFlow<Int> = _streak.asStateFlow()

    private val _totalXp = MutableStateFlow(0)
    val totalXp: StateFlow<Int> = _totalXp.asStateFlow()

    private val _totalCoins = MutableStateFlow(0)
    val totalCoins: StateFlow<Int> = _totalCoins.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    // Timer States
    private val _timerSecondsRemaining = MutableStateFlow(0)
    val timerSecondsRemaining: StateFlow<Int> = _timerSecondsRemaining.asStateFlow()

    private val _totalDurationSeconds = MutableStateFlow(300) // Default 5 mins
    val totalDurationSeconds: StateFlow<Int> = _totalDurationSeconds.asStateFlow()

    private val _isActive = MutableStateFlow(false)
    val isActive: StateFlow<Boolean> = _isActive.asStateFlow()

    private val _isPaused = MutableStateFlow(false)
    val isPaused: StateFlow<Boolean> = _isPaused.asStateFlow()

    private val _breathingPhase = MutableStateFlow("Rest")
    val breathingPhase: StateFlow<String> = _breathingPhase.asStateFlow()

    private val _selectedPattern = MutableStateFlow("Box Breathing")
    val selectedPattern: StateFlow<String> = _selectedPattern.asStateFlow()

    private var timerJob: Job? = null

    fun loadHistory() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            getTokenUseCase().onSuccess { token ->
                useCases.getHistory(token).onSuccess { history ->
                    _sessions.value = history.sessions
                    _streak.value = history.streak
                    _totalXp.value = history.totalXp
                    _totalCoins.value = history.totalCoins
                }.onFailure {
                    _errorMessage.value = "Failed to load history: ${it.localizedMessage}"
                }
            }.onFailure {
                _errorMessage.value = "Auth token lookup failed: ${it.localizedMessage}"
            }
            _isLoading.value = false
        }
    }

    fun selectPattern(pattern: String) {
        _selectedPattern.value = pattern
        resetTimer()
    }

    fun selectDuration(seconds: Int) {
        _totalDurationSeconds.value = seconds
        _timerSecondsRemaining.value = seconds
    }

    fun startTimer() {
        if (_isActive.value && _isPaused.value) {
            _isPaused.value = false
            resumeTimerLoop()
            return
        }

        _isActive.value = true
        _isPaused.value = false
        _timerSecondsRemaining.value = _totalDurationSeconds.value
        resumeTimerLoop()
    }

    fun pauseTimer() {
        _isPaused.value = true
        timerJob?.cancel()
    }

    fun stopTimer() {
        _isActive.value = false
        _isPaused.value = false
        _timerSecondsRemaining.value = _totalDurationSeconds.value
        _breathingPhase.value = "Rest"
        timerJob?.cancel()
    }

    fun resetTimer() {
        stopTimer()
    }

    private fun resumeTimerLoop() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            var elapsedSeconds = 0
            while (_timerSecondsRemaining.value > 0) {
                delay(1000)
                if (!_isPaused.value) {
                    _timerSecondsRemaining.value = _timerSecondsRemaining.value - 1
                    elapsedSeconds++
                    updateBreathingPhase(elapsedSeconds)
                }
            }
            completeSession()
        }
    }

    private fun updateBreathingPhase(elapsed: Int) {
        val pattern = _selectedPattern.value
        when (pattern) {
            "Box Breathing" -> {
                // Inhale 4s, Hold 4s, Exhale 4s, Hold 4s (16s cycle)
                val cyclePos = elapsed % 16
                _breathingPhase.value = when {
                    cyclePos < 4 -> "Inhale"
                    cyclePos < 8 -> "Hold"
                    cyclePos < 12 -> "Exhale"
                    else -> "Hold"
                }
            }
            "4-7-8 Breathing" -> {
                // Inhale 4s, Hold 7s, Exhale 8s (19s cycle)
                val cyclePos = elapsed % 19
                _breathingPhase.value = when {
                    cyclePos < 4 -> "Inhale"
                    cyclePos < 11 -> "Hold"
                    else -> "Exhale"
                }
            }
            "Calm Breathing" -> {
                // Inhale 4s, Hold 2s, Exhale 4s (10s cycle)
                val cyclePos = elapsed % 10
                _breathingPhase.value = when {
                    cyclePos < 4 -> "Inhale"
                    cyclePos < 6 -> "Hold"
                    else -> "Exhale"
                }
            }
            "Deep Breathing" -> {
                // Inhale 5s, Hold 2s, Exhale 5s (12s cycle)
                val cyclePos = elapsed % 12
                _breathingPhase.value = when {
                    cyclePos < 5 -> "Inhale"
                    cyclePos < 7 -> "Hold"
                    else -> "Exhale"
                }
            }
            else -> {
                // Mindfulness Timer: just rest
                _breathingPhase.value = "Rest/Focus"
            }
        }
    }

    private fun completeSession() {
        _isActive.value = false
        _breathingPhase.value = "Rest"
        
        viewModelScope.launch {
            _isLoading.value = true
            getTokenUseCase().onSuccess { token ->
                // Award 15 Coins and 20 XP flat
                useCases.submitSession(
                    token = token,
                    breathingType = _selectedPattern.value,
                    durationSeconds = _totalDurationSeconds.value,
                    coinsReward = 15,
                    xpReward = 20
                ).onSuccess {
                    loadHistory()
                }
            }
            _isLoading.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}
