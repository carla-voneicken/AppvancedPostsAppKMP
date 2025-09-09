package de.carlavoneicken.appvancedpostsappkmp.business.usecases

import de.carlavoneicken.appvancedpostsappkmp.data.models.Post
import de.carlavoneicken.appvancedpostsappkmp.data.repositories.PostsRepository
import kotlinx.coroutines.flow.Flow

class ObservePostsByUserIdUsecase(private val postsRepository: PostsRepository) {

    operator fun invoke(userId: Long): Flow<List<Post>> {
        return postsRepository.observePostsByUserId(userId)
    }

}