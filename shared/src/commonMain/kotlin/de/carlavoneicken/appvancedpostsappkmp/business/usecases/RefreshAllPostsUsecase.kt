package de.carlavoneicken.appvancedpostsappkmp.business.usecases

import de.carlavoneicken.appvancedpostsappkmp.business.utils.NetworkError
import de.carlavoneicken.appvancedpostsappkmp.business.utils.NetworkResult
import de.carlavoneicken.appvancedpostsappkmp.data.repositories.PostsRepository

class RefreshAllPostsUsecase(private val postsRepository: PostsRepository) {
    suspend operator fun invoke(): NetworkResult<Unit, NetworkError> {
        return postsRepository.refreshAllPosts()
    }
}