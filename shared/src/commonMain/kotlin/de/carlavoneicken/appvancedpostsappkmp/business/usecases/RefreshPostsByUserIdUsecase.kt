package de.carlavoneicken.appvancedpostsappkmp.business.usecases

import de.carlavoneicken.appvancedpostsappkmp.business.utils.NetworkError
import de.carlavoneicken.appvancedpostsappkmp.business.utils.NetworkResult
import de.carlavoneicken.appvancedpostsappkmp.data.repositories.PostsRepository

class RefreshPostsByUserIdUsecase(private val postsRepository: PostsRepository) {
    suspend operator fun invoke(userId: Long): NetworkResult<Unit, NetworkError> {
        return postsRepository.refreshPostsByUserId(userId)
    }
}