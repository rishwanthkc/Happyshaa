package com.auraai.domain.repository

import com.auraai.domain.model.MoodLog
import kotlinx.coroutines.flow.Flow

interface MoodRepository {
    
    suspend fun analyzeMood(token: String, text: String): Result<MoodLog>
    
    fun getMoodHistory(uid: String): Flow<List<MoodLog>>
    
    suspend fun cacheMoodLocally(uid: String, score: Int, note: String)
}
