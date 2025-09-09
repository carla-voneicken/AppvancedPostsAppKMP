package de.carlavoneicken.appvancedpostsappkmp.business.usecases

import de.carlavoneicken.appvancedpostsappkmp.business.utils.NetworkError
import de.carlavoneicken.appvancedpostsappkmp.business.utils.NetworkResult
import de.carlavoneicken.appvancedpostsappkmp.data.repositories.PostsRepository

class RefreshPostByIdUsecase(private val postsRepository: PostsRepository) {
    suspend operator fun invoke(id: Long): NetworkResult<Unit, NetworkError> {
        return postsRepository.refreshPostById(id)
    }
}