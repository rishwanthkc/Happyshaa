package com.auraai.domain.usecase

import com.auraai.domain.model.Story
import com.auraai.domain.repository.StoriesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StoryUseCases @Inject constructor(
    val generateStory: GenerateStoryStreamUseCase,
    val getStoryHistory: GetStoryHistoryUseCase,
    val toggleStoryFavorite: ToggleStoryFavoriteUseCase,
    val syncStoriesHistory: SyncStoriesHistoryUseCase
)

class GenerateStoryStreamUseCase @Inject constructor(
    private val repository: StoriesRepository
) {
    operator fun invoke(token: String, category: String, length: String): Flow<String> {
        return repository.generateStory(token, category, length)
    }
}

class GetStoryHistoryUseCase @Inject constructor(
    private val repository: StoriesRepository
) {
    operator fun invoke(token: String, uid: String): Flow<List<Story>> {
        return repository.getStories(token, uid)
    }
}

class ToggleStoryFavoriteUseCase @Inject constructor(
    private val repository: StoriesRepository
) {
    operator fun invoke(token: String, storyId: String): Flow<Story> {
        return repository.toggleStoryFavorite(token, storyId)
    }
}

class SyncStoriesHistoryUseCase @Inject constructor(
    private val repository: StoriesRepository
) {
    suspend operator fun invoke(token: String, uid: String) {
        repository.syncStoriesHistory(token, uid)
    }
}
