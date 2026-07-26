package com.auraai.ui.story

import android.content.Context
import android.speech.tts.TextToSpeech
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auraai.domain.model.Story
import com.auraai.domain.usecase.StoryUseCases
import com.auraai.domain.usecase.GetCurrentUserTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class StoryGeneratorViewModel @Inject constructor(
    private val storyUseCases: StoryUseCases,
    private val getTokenUseCase: GetCurrentUserTokenUseCase,
    @ApplicationContext private val context: Context
) : ViewModel(), TextToSpeech.OnInitListener {

    private val _storiesHistory = MutableStateFlow<List<Story>>(emptyList())
    val storiesHistory: StateFlow<List<Story>> = _storiesHistory.asStateFlow()

    private val _storyText = MutableStateFlow("")
    val storyText: StateFlow<String> = _storyText.asStateFlow()

    private val _isGenerating = MutableStateFlow(false)
    val isGenerating: StateFlow<Boolean> = _isGenerating.asStateFlow()

    private val _isLoadingHistory = MutableStateFlow(false)
    val isLoadingHistory: StateFlow<Boolean> = _isLoadingHistory.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private var tts: TextToSpeech? = null
    private val _isSpeaking = MutableStateFlow(false)
    val isSpeaking: StateFlow<Boolean> = _isSpeaking.asStateFlow()

    init {
        tts = TextToSpeech(context, this)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts?.language = Locale.US
        }
    }

    fun loadHistory(uid: String) {
        viewModelScope.launch {
            _isLoadingHistory.value = true
            _errorMessage.value = null
            getTokenUseCase().onSuccess { token ->
                storyUseCases.syncStoriesHistory(token, uid)
                storyUseCases.getStoryHistory(token, uid).collect { list ->
                    _storiesHistory.value = list
                }
            }.onFailure {
                _errorMessage.value = "Failed to sync story history: ${it.localizedMessage}"
            }
            _isLoadingHistory.value = false
        }
    }

    fun generateStory(category: String, length: String) {
        viewModelScope.launch {
            _isGenerating.value = true
            _storyText.value = ""
            _errorMessage.value = null
            stopSpeaking()

            getTokenUseCase().onSuccess { token ->
                storyUseCases.generateStory(token, category, length).collect { chunk ->
                    _storyText.value = _storyText.value + chunk
                }
            }.onFailure {
                _errorMessage.value = "Failed to start story generation: ${it.localizedMessage}"
            }
            _isGenerating.value = false
        }
    }

    fun toggleFavorite(story: Story) {
        viewModelScope.launch {
            getTokenUseCase().onSuccess { token ->
                storyUseCases.toggleStoryFavorite(token, story.storyId).collect { updated ->
                    _storiesHistory.value = _storiesHistory.value.map {
                        if (it.storyId == updated.storyId) updated else it
                    }
                }
            }
        }
    }

    fun speakStory(text: String) {
        if (tts != null && text.isNotBlank()) {
            if (_isSpeaking.value) {
                stopSpeaking()
            } else {
                tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "story_tts")
                _isSpeaking.value = true
            }
        }
    }

    fun stopSpeaking() {
        tts?.stop()
        _isSpeaking.value = false
    }

    override fun onCleared() {
        super.onCleared()
        tts?.shutdown()
    }
}
