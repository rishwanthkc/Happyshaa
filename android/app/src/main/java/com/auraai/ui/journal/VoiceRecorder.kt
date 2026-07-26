package com.auraai.ui.journal

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import java.io.File

class VoiceRecorder(private val context: Context) {

    private var mediaRecorder: MediaRecorder? = null
    private var currentFile: File? = null

    fun startRecording(onSuccess: (File) -> Unit, onError: (Exception) -> Unit) {
        try {
            val audioDir = File(context.cacheDir, "voice_notes").apply { mkdirs() }
            val file = File(audioDir, "voice_note_${System.currentTimeMillis()}.mp4")
            currentFile = file

            mediaRecorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                MediaRecorder(context)
            } else {
                @Suppress("DEPRECATION")
                MediaRecorder()
            }.apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setOutputFile(file.absolutePath)
                prepare()
                start()
            }
            onSuccess(file)
        } catch (e: Exception) {
            onError(e)
        }
    }

    fun pauseRecording() {
        try {
            mediaRecorder?.pause()
        } catch (e: Exception) {
            // handle error
        }
    }

    fun resumeRecording() {
        try {
            mediaRecorder?.resume()
        } catch (e: Exception) {
            // handle error
        }
    }

    fun stopRecording(): File? {
        return try {
            mediaRecorder?.apply {
                stop()
                release()
            }
            mediaRecorder = null
            currentFile
        } catch (e: Exception) {
            mediaRecorder = null
            null
        }
    }

    fun getMaxAmplitude(): Int {
        return try {
            mediaRecorder?.maxAmplitude ?: 0
        } catch (e: Exception) {
            0
        }
    }
}
