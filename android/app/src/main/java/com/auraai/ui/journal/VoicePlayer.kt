package com.auraai.ui.journal

import android.media.MediaPlayer
import java.io.File

class VoicePlayer {

    private var mediaPlayer: MediaPlayer? = null

    fun startPlaying(file: File, onComplete: () -> Unit, onError: (Exception) -> Unit) {
        try {
            stopPlaying()
            mediaPlayer = MediaPlayer().apply {
                setDataSource(file.absolutePath)
                prepare()
                start()
                setOnCompletionListener {
                    stopPlaying()
                    onComplete()
                }
            }
        } catch (e: Exception) {
            onError(e)
        }
    }

    fun stopPlaying() {
        try {
            mediaPlayer?.release()
            mediaPlayer = null
        } catch (e: Exception) {
            mediaPlayer = null
        }
    }

    fun isPlaying(): Boolean {
        return mediaPlayer?.isPlaying ?: false
    }
}
