package de.carlavoneicken.appvancedpostsappkmp.business.usecases

import de.carlavoneicken.appvancedpostsappkmp.data.models.Post
import de.carlavoneicken.appvancedpostsappkmp.data.repositories.PostsRepository
import kotlinx.coroutines.flow.Flow

class ObserveAllPostsUsecase(private val postsRepository: PostsRepository) {
    operator fun invoke(): Flow<List<Post>> {
        return postsRepository.observeAllPosts()
    }
}