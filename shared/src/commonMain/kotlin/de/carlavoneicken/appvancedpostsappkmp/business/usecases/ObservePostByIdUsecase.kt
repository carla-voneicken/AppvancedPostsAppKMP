package de.carlavoneicken.appvancedpostsappkmp.business.usecases

import de.carlavoneicken.appvancedpostsappkmp.data.models.Post
import de.carlavoneicken.appvancedpostsappkmp.data.repositories.PostsRepository
import kotlinx.coroutines.flow.Flow

class ObservePostByIdUsecase(private val postsRepository: PostsRepository) {
    suspend operator fun invoke(id: Long): Flow<Post?> {
        return postsRepository.observePostById(id)
    }
}