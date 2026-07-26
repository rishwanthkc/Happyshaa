package com.auraai.domain.repository

import com.auraai.domain.model.Story
import kotlinx.coroutines.flow.Flow

interface StoriesRepository {
    fun generateStory(token: String, category: String, length: String): Flow<String>
    fun getStories(token: String, uid: String): Flow<List<Story>>
    fun toggleStoryFavorite(token: String, storyId: String): Flow<Story>
    suspend fun syncStoriesHistory(token: String, uid: String)
}
