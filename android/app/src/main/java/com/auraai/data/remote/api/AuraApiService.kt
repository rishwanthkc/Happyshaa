package com.auraai.data.remote.api

import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Streaming

// Authentication Sync
data class UserSyncRequest(
    val display_name: String?,
    val photo_url: String?
)

data class NetworkUserProfile(
    val uid: String,
    val email: String,
    val display_name: String?,
    val photo_url: String?
)

data class UserSyncResponse(
    val status: String,
    val user: NetworkUserProfile
)

// Mood DTOs
data class MoodAnalysisRequest(
    val text: String
)

data class NetworkMoodLog(
    val log_id: String,
    val uid: String,
    val timestamp: Double,
    val primary_emotion: String,
    val confidence_score: Float,
    val stress_level: Float,
    val anxiety_level: Float,
    val sadness_level: Float,
    val anger_level: Float,
    val happiness_level: Float,
    val confidence_level: Float,
    val suggested_activities: List<String>
)

data class NetworkMoodHistoryResponse(
    val logs: List<NetworkMoodLog>
)

// Chat DTOs
data class ChatMessageRequest(
    val message: String,
    val current_mood: String
)

data class NetworkChatMessage(
    val msg_id: String,
    val uid: String,
    val timestamp: Double,
    val sender: String,
    val content: String
)

data class NetworkChatHistoryResponse(
    val history: List<NetworkChatMessage>
)

// Music DTOs
data class NetworkMusicTrack(
    val song_id: String,
    val title: String,
    val artist: String,
    val url: String,
    val category: String,
    val mood_tag: String
)

data class FavoriteToggleRequest(
    val song_id: String
)

data class HistoryLogRequest(
    val song_id: String,
    val duration_sec: Int
)

// Contacts DTOs
data class ContactCreateRequest(
    val name: String,
    val phone: String,
    val email: String?,
    val relationship_label: String,
    val is_favorite: Boolean,
    val is_emergency: Boolean
)

data class ContactUpdateRequest(
    val name: String?,
    val phone: String?,
    val email: String?,
    val relationship_label: String?,
    val is_favorite: Boolean?,
    val is_emergency: Boolean?
)

data class NetworkContact(
    val contact_id: String,
    val uid: String,
    val name: String,
    val phone: String,
    val email: String?,
    val relationship_label: String,
    val is_favorite: Boolean,
    val is_emergency: Boolean
)

// Games DTOs
data class NetworkGame(
    val game_id: String,
    val title: String,
    val description: String,
    val category: String
)

data class GameScoreSubmitRequest(
    val game_id: String,
    val score: Int
)

data class NetworkAchievement(
    val achievement_id: String,
    val uid: String,
    val achievement_type: String,
    val timestamp: Double
)

data class NetworkGameProgressResponse(
    val score_id: String,
    val xp_earned: Int,
    val coins_earned: Int,
    val new_balance: Int,
    val unlocked_achievement: String?
)

// Journal DTOs
data class JournalCreateRequest(
    val title: String,
    val content: String,
    val audio_url: String?
)

data class NetworkJournal(
    val journal_id: String,
    val uid: String,
    val timestamp: Double,
    val title: String?,
    val content: String,
    val detected_emotion: String,
    val emotion_confidence: Float,
    val audio_url: String?,
    val reflection: String,
    val gratitude_highlights: List<String>,
    val triggers: List<String>
)

data class NetworkWeeklyReport(
    val report_id: String,
    val uid: String,
    val timestamp: Double,
    val dominant_mood: String,
    val average_stress_level: Float,
    val gratitude_summary: String,
    val identified_triggers: List<String>,
    val self_care_plan: List<String>
)

// Recommendation DTOs
data class NetworkRecommendationCard(
    val id: String,
    val title: String,
    val description: String,
    val activity_type: String,
    val target_route: String,
    val difficulty: String,
    val coins_reward: Int
)

// Notifications DTOs
data class FCMTokenRequest(
    val fcm_token: String
)

data class NetworkNotificationItem(
    val notification_id: String,
    val uid: String,
    val timestamp: Double,
    val title: String,
    val body: String,
    val is_read: Boolean
)

// Story DTOs
data class StoryGenerateRequest(
    val category: String,
    val length: String
)

data class StoryResponse(
    val story_id: String,
    val uid: String,
    val title: String,
    val content: String,
    val category: String,
    val length: String,
    val timestamp: Double,
    val is_favorite: Boolean
)

data class StoryFavoriteRequest(
    val story_id: String
)

// Meditation DTOs
data class MeditationSessionRequest(
    val breathing_type: String,
    val duration_seconds: Int,
    val coins_reward: Int,
    val xp_reward: Int
)

data class MeditationSessionResponse(
    val session_id: String,
    val uid: String,
    val breathing_type: String,
    val duration_seconds: Int,
    val timestamp: Double,
    val coins_reward: Int,
    val xp_reward: Int
)

data class MeditationHistoryResponse(
    val sessions: List<MeditationSessionResponse>,
    val streak: Int,
    val total_xp: Int,
    val total_coins: Int
)

/**
 * Retrofit interface defining Aura AI backend API services.
 */
interface AuraApiService {
    
    // Auth Sync
    @POST("api/v1/auth/sync")
    suspend fun syncUser(
        @Header("Authorization") token: String,
        @Body request: UserSyncRequest
    ): UserSyncResponse

    // Mood Log
    @POST("api/v1/mood/analyze")
    suspend fun analyzeMood(
        @Header("Authorization") token: String,
        @Body request: MoodAnalysisRequest
    ): NetworkMoodLog

    @GET("api/v1/mood/history")
    suspend fun getMoodHistory(
        @Header("Authorization") token: String
    ): NetworkMoodHistoryResponse

    // Chat
    @Streaming
    @POST("api/v1/chat/respond")
    suspend fun streamChatResponse(
        @Header("Authorization") token: String,
        @Body request: ChatMessageRequest
    ): ResponseBody

    @GET("api/v1/chat/history")
    suspend fun getChatHistory(
        @Header("Authorization") token: String
    ): NetworkChatHistoryResponse

    // Music
    @GET("api/v1/music/songs")
    suspend fun getSongs(
        @Header("Authorization") token: String
    ): List<NetworkMusicTrack>

    @POST("api/v1/music/favorites/toggle")
    suspend fun toggleFavoriteSong(
        @Header("Authorization") token: String,
        @Body request: FavoriteToggleRequest
    ): ResponseBody

    @GET("api/v1/music/favorites")
    suspend fun getFavoriteSongs(
        @Header("Authorization") token: String
    ): List<NetworkMusicTrack>

    @POST("api/v1/music/history")
    suspend fun logPlaybackHistory(
        @Header("Authorization") token: String,
        @Body request: HistoryLogRequest
    ): ResponseBody

    @GET("api/v1/music/recommend")
    suspend fun getMusicRecommendations(
        @Header("Authorization") token: String
    ): List<NetworkMusicTrack>

    // Contacts
    @GET("api/v1/contacts")
    suspend fun getContacts(
        @Header("Authorization") token: String
    ): List<NetworkContact>

    @POST("api/v1/contacts")
    suspend fun createContact(
        @Header("Authorization") token: String,
        @Body request: ContactCreateRequest
    ): NetworkContact

    @PUT("api/v1/contacts/{contact_id}")
    suspend fun updateContact(
        @Header("Authorization") token: String,
        @Path("contact_id") contactId: String,
        @Body request: ContactUpdateRequest
    ): ResponseBody

    @DELETE("api/v1/contacts/{contact_id}")
    suspend fun deleteContact(
        @Header("Authorization") token: String,
        @Path("contact_id") contactId: String
    ): ResponseBody

    @POST("api/v1/contacts/{contact_id}/favorite")
    suspend fun toggleFavoriteContact(
        @Header("Authorization") token: String,
        @Path("contact_id") contactId: String
    ): ResponseBody

    // Games
    @GET("api/v1/games")
    suspend fun getGames(
        @Header("Authorization") token: String
    ): List<NetworkGame>

    @POST("api/v1/games/scores")
    suspend fun submitGameScore(
        @Header("Authorization") token: String,
        @Body request: GameScoreSubmitRequest
    ): NetworkGameProgressResponse

    @GET("api/v1/games/achievements")
    suspend fun getAchievements(
        @Header("Authorization") token: String
    ): List<NetworkAchievement>

    // Journal
    @POST("api/v1/journal")
    suspend fun createJournalEntry(
        @Header("Authorization") token: String,
        @Body request: JournalCreateRequest
    ): NetworkJournal

    @GET("api/v1/journal")
    suspend fun getJournals(
        @Header("Authorization") token: String
    ): List<NetworkJournal>

    @POST("api/v1/journal/weekly/generate")
    suspend fun generateWeeklyReport(
        @Header("Authorization") token: String
    ): NetworkWeeklyReport

    @GET("api/v1/journal/weekly")
    suspend fun getWeeklyReports(
        @Header("Authorization") token: String
    ): List<NetworkWeeklyReport>

    // Recommendation Engine
    @GET("api/v1/recommend")
    suspend fun getDailyRecommendations(
        @Header("Authorization") token: String
    ): List<NetworkRecommendationCard>

    // Notifications
    @POST("api/v1/notifications/token")
    suspend fun saveFcmToken(
        @Header("Authorization") token: String,
        @Body request: FCMTokenRequest
    ): ResponseBody

    @GET("api/v1/notifications/history")
    suspend fun getNotificationHistory(
        @Header("Authorization") token: String
    ): List<NetworkNotificationItem>

    @POST("api/v1/notifications/test")
    suspend fun sendTestNotification(
        @Header("Authorization") token: String
    ): ResponseBody

    // Story Generator
    @Streaming
    @POST("api/v1/stories/generate")
    suspend fun generateStoryStream(
        @Header("Authorization") token: String,
        @Body request: StoryGenerateRequest
    ): ResponseBody

    @GET("api/v1/stories/history")
    suspend fun getStoryHistory(
        @Header("Authorization") token: String
    ): List<StoryResponse>

    @POST("api/v1/stories/favorite")
    suspend fun toggleStoryFavorite(
        @Header("Authorization") token: String,
        @Body request: StoryFavoriteRequest
    ): StoryResponse

    // Meditation
    @POST("api/v1/meditation/session")
    suspend fun submitMeditationSession(
        @Header("Authorization") token: String,
        @Body request: MeditationSessionRequest
    ): MeditationSessionResponse

    @GET("api/v1/meditation/history")
    suspend fun getMeditationHistory(
        @Header("Authorization") token: String
    ): MeditationHistoryResponse

    @POST("api/v1/journal/voice/summarize")
    suspend fun summarizeVoiceJournal(
        @Header("Authorization") token: String,
        @Body request: VoiceJournalSummarizeRequest
    ): VoiceJournalSummarizeResponse
}

data class VoiceJournalSummarizeRequest(
    val audio_url: String
)

data class VoiceJournalSummarizeResponse(
    val transcription: String,
    val summary: String,
    val detected_emotion: String,
    val reflection: String
)
