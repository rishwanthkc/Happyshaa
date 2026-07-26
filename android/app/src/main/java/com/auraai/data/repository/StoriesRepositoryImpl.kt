package com.auraai.data.repository

import com.auraai.data.local.dao.StoriesDao
import com.auraai.data.local.entity.CachedStoryEntity
import com.auraai.data.remote.api.AuraApiService
import com.auraai.data.remote.api.StoryFavoriteRequest
import com.auraai.data.remote.api.StoryGenerateRequest
import com.auraai.domain.model.Story
import com.auraai.domain.repository.StoriesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StoriesRepositoryImpl @Inject constructor(
    private val apiService: AuraApiService,
    private val storiesDao: StoriesDao
) : StoriesRepository {

    override fun generateStory(token: String, category: String, length: String): Flow<String> = flow {
        val responseBody = apiService.generateStoryStream(
            token = "Bearer $token",
            request = StoryGenerateRequest(category, length)
        )
        
        val reader = responseBody.charStream().buffered()
        val buffer = CharArray(256)
        var charsRead: Int
        
        while (reader.read(buffer).also { charsRead = it } != -1) {
            if (charsRead > 0) {
                emit(String(buffer, 0, charsRead))
            }
        }
        reader.close()
    }.flowOn(Dispatchers.IO)

    override fun getStories(token: String, uid: String): Flow<List<Story>> {
        return storiesDao.getStories(uid).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun toggleStoryFavorite(token: String, storyId: String): Flow<Story> = flow {
        try {
            val response = apiService.toggleStoryFavorite(
                token = "Bearer $token",
                request = StoryFavoriteRequest(storyId)
            )
            storiesDao.updateFavoriteStatus(storyId, response.is_favorite)
            
            val updatedEntity = CachedStoryEntity(
                storyId = response.story_id,
                uid = response.uid,
                title = response.title,
                content = response.content,
                category = response.category,
                length = response.length,
                timestamp = response.timestamp.toLong(),
                isFavorite = response.is_favorite
            )
            storiesDao.insertStory(updatedEntity)
            emit(updatedEntity.toDomain())
        } catch (e: Exception) {
            storiesDao.updateFavoriteStatus(storyId, true)
            emit(Story(storyId, "offline_user", "Calming Journey", "Content", "Sleep", "Short", System.currentTimeMillis() / 1000, true))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun syncStoriesHistory(token: String, uid: String) {
        try {
            val responseList = apiService.getStoryHistory("Bearer $token")
            responseList.forEach { story ->
                storiesDao.insertStory(
                    CachedStoryEntity(
                        storyId = story.story_id,
                        uid = story.uid,
                        title = story.title,
                        content = story.content,
                        category = story.category,
                        length = story.length,
                        timestamp = story.timestamp.toLong(),
                        isFavorite = story.is_favorite
                    )
                )
            }
        } catch (e: Exception) {
            // Ignore network failures for offline caching
        }
    }

    private fun CachedStoryEntity.toDomain(): Story = Story(
        storyId = storyId,
        uid = uid,
        title = title,
        content = content,
        category = category,
        length = length,
        timestamp = timestamp,
        isFavorite = isFavorite
    )
}
