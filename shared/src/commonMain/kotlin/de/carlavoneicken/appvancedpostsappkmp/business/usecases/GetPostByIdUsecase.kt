package de.carlavoneicken.appvancedpostsappkmp.business.usecases

import de.carlavoneicken.appvancedpostsappkmp.business.utils.NetworkError
import de.carlavoneicken.appvancedpostsappkmp.business.utils.NetworkResult
import de.carlavoneicken.appvancedpostsappkmp.data.models.Post
import de.carlavoneicken.appvancedpostsappkmp.data.repositories.PostsRepository

class GetPostByIdUsecase(private val postsRepository: PostsRepository) {

    suspend operator fun invoke(id: Long): NetworkResult<Post, NetworkError> {
        return postsRepository.getPostById(id)
    }

}