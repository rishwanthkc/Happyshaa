package com.auraai.data.repository

import com.auraai.data.local.db.CachedMoodEntity
import com.auraai.data.local.db.MoodDao
import com.auraai.data.remote.api.AuraApiService
import com.auraai.data.remote.api.MoodAnalysisRequest
import com.auraai.data.remote.api.NetworkMoodLog
import com.auraai.domain.model.MoodLog
import com.auraai.domain.repository.MoodRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoodRepositoryImpl @Inject constructor(
    private val apiService: AuraApiService,
    private val moodDao: MoodDao
) : MoodRepository {

    override suspend fun analyzeMood(token: String, text: String): Result<MoodLog> {
        return try {
            val response = apiService.analyzeMood("Bearer $token", MoodAnalysisRequest(text))
            
            // Mirror remote response into local cache database
            val localScore = when (response.primary_emotion.lowercase()) {
                "happiness", "confidence" -> 5
                "sadness", "anger" -> 2
                "stress", "anxiety" -> 1
                else -> 3
            }
            val entity = CachedMoodEntity(
                uid = response.uid,
                timestamp = (response.timestamp * 1000).toLong(),
                score = localScore,
                note = "Detected: ${response.primary_emotion}",
                isSynced = true
            )
            moodDao.insertMood(entity)
            
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getMoodHistory(uid: String): Flow<List<MoodLog>> {
        return moodDao.getCachedMoods(uid).map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun cacheMoodLocally(uid: String, score: Int, note: String) {
        val entity = CachedMoodEntity(
            uid = uid,
            timestamp = System.currentTimeMillis(),
            score = score,
            note = note,
            isSynced = false
        )
        moodDao.insertMood(entity)
    }

    private fun NetworkMoodLog.toDomain(): MoodLog = MoodLog(
        logId = log_id,
        uid = uid,
        timestamp = (timestamp * 1000).toLong(),
        primaryEmotion = primary_emotion,
        confidenceScore = confidence_score,
        stressLevel = stress_level,
        anxietyLevel = anxiety_level,
        sadnessLevel = sadness_level,
        angerLevel = anger_level,
        happinessLevel = happiness_level,
        confidenceLevel = confidence_level,
        suggestedActivities = suggested_activities
    )

    private fun CachedMoodEntity.toDomain(): MoodLog = MoodLog(
        logId = id.toString(),
        uid = uid,
        timestamp = timestamp,
        primaryEmotion = when (score) {
            5 -> "Happiness"
            4 -> "Confidence"
            2 -> "Sadness"
            1 -> "Anxiety"
            else -> "Neutral"
        },
        confidenceScore = 1.0f,
        stressLevel = if (score <= 2) 0.6f else 0.1f,
        anxietyLevel = if (score == 1) 0.8f else 0.2f,
        sadnessLevel = if (score == 2) 0.7f else 0.1f,
        angerLevel = 0.1f,
        happinessLevel = if (score == 5) 0.9f else 0.2f,
        confidenceLevel = if (score == 4) 0.8f else 0.4f,
        suggestedActivities = listOf(note)
    )
}
