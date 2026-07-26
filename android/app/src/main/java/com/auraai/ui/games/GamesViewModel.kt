package com.auraai.ui.games

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auraai.domain.model.Game
import com.auraai.domain.model.Achievement
import com.auraai.domain.model.GameProgress
import com.auraai.domain.usecase.GetGamesUseCase
import com.auraai.domain.usecase.SubmitGameScoreUseCase
import com.auraai.domain.usecase.GetAchievementsUseCase
import com.auraai.domain.usecase.GetCurrentUserTokenUseCase
import com.auraai.data.local.db.QuestDao
import com.auraai.data.local.db.QuestEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GamesViewModel @Inject constructor(
    private val getGamesUseCase: GetGamesUseCase,
    private val submitGameScoreUseCase: SubmitGameScoreUseCase,
    private val getAchievementsUseCase: GetAchievementsUseCase,
    private val getTokenUseCase: GetCurrentUserTokenUseCase,
    private val questDao: QuestDao
) : ViewModel() {

    private val _games = MutableStateFlow<List<Game>>(emptyList())
    val games: StateFlow<List<Game>> = _games.asStateFlow()

    private val _achievements = MutableStateFlow<List<Achievement>>(emptyList())
    val achievements: StateFlow<List<Achievement>> = _achievements.asStateFlow()

    private val _progressEvent = MutableStateFlow<GameProgress?>(null)
    val progressEvent: StateFlow<GameProgress?> = _progressEvent.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _userCoins = MutableStateFlow(0)
    val userCoins: StateFlow<Int> = _userCoins.asStateFlow()

    private val _quests = MutableStateFlow<List<QuestEntity>>(emptyList())
    val quests: StateFlow<List<QuestEntity>> = _quests.asStateFlow()

    init {
        viewModelScope.launch {
            questDao.getAllQuests().collect { questList ->
                if (questList.isEmpty()) {
                    // Populate default wellness quests
                    questDao.insertQuest(QuestEntity(text = "Complete 5 min Box Breathing", isCompleted = false))
                    questDao.insertQuest(QuestEntity(text = "Write a daily journal entry reflection", isCompleted = false))
                    questDao.insertQuest(QuestEntity(text = "Complete mood track check-in", isCompleted = false))
                } else {
                    _quests.value = questList
                }
            }
        }
    }

    fun loadGameCenter() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            getTokenUseCase().onSuccess { token ->
                val gamesRes = getGamesUseCase(token)
                val achRes = getAchievementsUseCase(token)

                gamesRes.onSuccess { _games.value = it }
                achRes.onSuccess { _achievements.value = it }
            }.onFailure {
                _errorMessage.value = "Auth token lookup failed: ${it.localizedMessage}"
            }
            _isLoading.value = false
        }
    }

    fun submitScore(gameId: String, score: Int) {
        viewModelScope.launch {
            _errorMessage.value = null
            getTokenUseCase().onSuccess { token ->
                submitGameScoreUseCase(token, gameId, score).onSuccess { progress ->
                    _progressEvent.value = progress
                    _userCoins.value = progress.newBalance
                    
                    // Refresh achievements
                    getAchievementsUseCase(token).onSuccess { _achievements.value = it }
                }.onFailure {
                    _errorMessage.value = "Failed to submit score: ${it.localizedMessage}"
                }
            }
        }
    }

    fun clearProgressEvent() {
        _progressEvent.value = null
    }

    fun addCoins(amount: Int) {
        _userCoins.value = _userCoins.value + amount
    }

    fun addQuest(text: String) {
        viewModelScope.launch {
            questDao.insertQuest(QuestEntity(text = text, isCompleted = false))
        }
    }

    fun deleteQuest(id: Int) {
        viewModelScope.launch {
            questDao.deleteQuest(id)
        }
    }

    fun updateQuestText(quest: QuestEntity, newText: String) {
        viewModelScope.launch {
            questDao.insertQuest(quest.copy(text = newText))
        }
    }

    fun toggleQuestCompleted(quest: QuestEntity) {
        viewModelScope.launch {
            val newCompletedState = !quest.isCompleted
            questDao.updateQuestCompletion(quest.id, newCompletedState)
            if (newCompletedState) {
                addCoins(10)
            } else {
                addCoins(-10)
            }
        }
    }
}
