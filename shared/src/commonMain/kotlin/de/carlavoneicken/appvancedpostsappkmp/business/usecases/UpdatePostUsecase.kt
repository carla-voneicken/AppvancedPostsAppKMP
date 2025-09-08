package de.carlavoneicken.appvancedpostsappkmp.business.usecases

import de.carlavoneicken.appvancedpostsappkmp.business.utils.NetworkError
import de.carlavoneicken.appvancedpostsappkmp.business.utils.NetworkResult
import de.carlavoneicken.appvancedpostsappkmp.data.models.Post
import de.carlavoneicken.appvancedpostsappkmp.data.repositories.PostsRepository

class UpdatePostUsecase(private val postsRepository: PostsRepository) {

    suspend operator fun invoke(post: Post): NetworkResult<Post, NetworkError> {
        return postsRepository.updatePost(post)
    }
}