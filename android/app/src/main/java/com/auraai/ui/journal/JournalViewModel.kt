package com.auraai.ui.journal

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auraai.domain.model.JournalEntry
import com.auraai.domain.model.WeeklyReport
import com.auraai.domain.usecase.CreateJournalEntryUseCase
import com.auraai.domain.usecase.GetJournalsUseCase
import com.auraai.domain.usecase.GenerateWeeklyReportUseCase
import com.auraai.domain.usecase.GetWeeklyReportsUseCase
import com.auraai.domain.usecase.SummarizeVoiceJournalUseCase
import com.auraai.domain.usecase.GetCurrentUserTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class JournalViewModel @Inject constructor(
    private val createJournalEntryUseCase: CreateJournalEntryUseCase,
    private val getJournalsUseCase: GetJournalsUseCase,
    private val generateWeeklyReportUseCase: GenerateWeeklyReportUseCase,
    private val getWeeklyReportsUseCase: GetWeeklyReportsUseCase,
    private val summarizeVoiceJournalUseCase: SummarizeVoiceJournalUseCase,
    private val getTokenUseCase: GetCurrentUserTokenUseCase
) : ViewModel() {

    private val _journals = MutableStateFlow<List<JournalEntry>>(emptyList())
    val journals: StateFlow<List<JournalEntry>> = _journals.asStateFlow()

    private val _weeklyReports = MutableStateFlow<List<WeeklyReport>>(emptyList())
    val weeklyReports: StateFlow<List<WeeklyReport>> = _weeklyReports.asStateFlow()

    private val _recentAnalysis = MutableStateFlow<JournalEntry?>(null)
    val recentAnalysis: StateFlow<JournalEntry?> = _recentAnalysis.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    // Voice Notes recording states
    private val _isRecording = MutableStateFlow(false)
    val isRecording: StateFlow<Boolean> = _isRecording.asStateFlow()

    private val _isPausedRecording = MutableStateFlow(false)
    val isPausedRecording: StateFlow<Boolean> = _isPausedRecording.asStateFlow()

    private val _recordingDurationSeconds = MutableStateFlow(0)
    val recordingDurationSeconds: StateFlow<Int> = _recordingDurationSeconds.asStateFlow()

    private val _amplitudeList = MutableStateFlow<List<Float>>(emptyList())
    val amplitudeList: StateFlow<List<Float>> = _amplitudeList.asStateFlow()

    private val _isPlayingPreview = MutableStateFlow(false)
    val isPlayingPreview: StateFlow<Boolean> = _isPlayingPreview.asStateFlow()

    private var recordedFile: File? = null
    private var voiceRecorder: VoiceRecorder? = null
    private var voicePlayer: VoicePlayer? = null
    private var timerJob: Job? = null
    private var waveformJob: Job? = null

    fun loadJournalData() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            getTokenUseCase().onSuccess { token ->
                val journalsRes = getJournalsUseCase(token)
                val reportsRes = getWeeklyReportsUseCase(token)

                journalsRes.onSuccess { _journals.value = it }
                reportsRes.onSuccess { _weeklyReports.value = it }
            }.onFailure {
                _errorMessage.value = "Auth token lookup failed: ${it.localizedMessage}"
            }
            _isLoading.value = false
        }
    }

    fun submitJournalEntry(title: String, content: String, audioUrl: String?) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            getTokenUseCase().onSuccess { token ->
                createJournalEntryUseCase(token, title, content, audioUrl).onSuccess { entry ->
                    _recentAnalysis.value = entry
                    loadJournalData()
                }.onFailure {
                    _errorMessage.value = "Failed to submit journal entry: ${it.localizedMessage}"
                }
            }
            _isLoading.value = false
        }
    }

    fun triggerWeeklyCompilation() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            getTokenUseCase().onSuccess { token ->
                generateWeeklyReportUseCase(token).onSuccess {
                    loadJournalData()
                }.onFailure {
                    _errorMessage.value = "Failed to compile weekly report: ${it.localizedMessage}"
                }
            }
            _isLoading.value = false
        }
    }

    // Voice note actions
    fun startRecording(context: Context) {
        if (voiceRecorder == null) {
            voiceRecorder = VoiceRecorder(context)
        }
        voiceRecorder?.startRecording(
            onSuccess = { file ->
                recordedFile = file
                _isRecording.value = true
                _isPausedRecording.value = false
                _recordingDurationSeconds.value = 0
                _amplitudeList.value = emptyList()
                startTimer()
                startWaveformPolling()
            },
            onError = {
                _errorMessage.value = "Failed to start recording: ${it.localizedMessage}"
            }
        )
    }

    fun pauseRecording() {
        voiceRecorder?.pauseRecording()
        _isPausedRecording.value = true
        timerJob?.cancel()
        waveformJob?.cancel()
    }

    fun resumeRecording() {
        voiceRecorder?.resumeRecording()
        _isPausedRecording.value = false
        startTimer()
        startWaveformPolling()
    }

    fun stopRecording() {
        val file = voiceRecorder?.stopRecording()
        if (file != null) {
            recordedFile = file
        }
        _isRecording.value = false
        _isPausedRecording.value = false
        timerJob?.cancel()
        waveformJob?.cancel()
    }

    fun playPreview() {
        val file = recordedFile ?: return
        if (voicePlayer == null) {
            voicePlayer = VoicePlayer()
        }
        _isPlayingPreview.value = true
        voicePlayer?.startPlaying(
            file = file,
            onComplete = {
                _isPlayingPreview.value = false
            },
            onError = {
                _isPlayingPreview.value = false
                _errorMessage.value = "Failed to play audio preview: ${it.localizedMessage}"
            }
        )
    }

    fun stopPreview() {
        voicePlayer?.stopPlaying()
        _isPlayingPreview.value = false
    }

    fun deleteRecording() {
        stopPreview()
        recordedFile?.delete()
        recordedFile = null
        _isRecording.value = false
        _isPausedRecording.value = false
        _recordingDurationSeconds.value = 0
        _amplitudeList.value = emptyList()
    }

    fun submitVoiceJournal(title: String) {
        val file = recordedFile ?: return
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            
            val mockAudioUrl = "https://storage.googleapis.com/auraai/voice_${System.currentTimeMillis()}.mp4"
            getTokenUseCase().onSuccess { token ->
                summarizeVoiceJournalUseCase(token, mockAudioUrl).onSuccess { response ->
                    val content = "Voice note transcription: ${response.transcription}\n\nSummary: ${response.summary}"
                    createJournalEntryUseCase(token, title, content, mockAudioUrl).onSuccess { entry ->
                        _recentAnalysis.value = entry
                        deleteRecording()
                        loadJournalData()
                    }.onFailure {
                        _errorMessage.value = "Failed to submit journal: ${it.localizedMessage}"
                    }
                }.onFailure {
                    _errorMessage.value = "Failed to summarize voice note: ${it.localizedMessage}"
                }
            }
            _isLoading.value = false
        }
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                _recordingDurationSeconds.value = _recordingDurationSeconds.value + 1
            }
        }
    }

    private fun startWaveformPolling() {
        waveformJob?.cancel()
        waveformJob = viewModelScope.launch {
            while (true) {
                delay(100)
                val amp = voiceRecorder?.getMaxAmplitude()?.toFloat() ?: 0f
                val normAmp = (amp / 32767f).coerceIn(0.05f, 1.0f)
                val currentList = _amplitudeList.value.toMutableList()
                currentList.add(normAmp)
                if (currentList.size > 30) {
                    currentList.removeAt(0)
                }
                _amplitudeList.value = currentList
            }
        }
    }

    fun clearRecentAnalysis() {
        _recentAnalysis.value = null
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
        waveformJob?.cancel()
        voicePlayer?.stopPlaying()
        voiceRecorder?.stopRecording()
    }
}
