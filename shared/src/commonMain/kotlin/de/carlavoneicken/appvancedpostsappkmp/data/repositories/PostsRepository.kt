package de.carlavoneicken.appvancedpostsappkmp.data.repositories

import de.carlavoneicken.appvancedpostsappkmp.business.utils.NetworkError
import de.carlavoneicken.appvancedpostsappkmp.business.utils.NetworkResult
import de.carlavoneicken.appvancedpostsappkmp.data.models.Post
import kotlinx.coroutines.flow.Flow

interface PostsRepository {
    // UI data from database
    fun observeAllPosts(): Flow<List<Post>>
    fun observePostsByUserId(userId: Long): Flow<List<Post>>
    fun observePostById(id: Long): Flow<Post?>

    // Refresh data via HttpRequest
    suspend fun refreshAllPosts(): NetworkResult<Unit, NetworkError>
    suspend fun refreshPostsByUserId(userId: Long): NetworkResult<Unit, NetworkError>
    suspend fun refreshPostById(id: Long): NetworkResult<Unit, NetworkError>

    // Write data via HttpRequest
    suspend fun createPost(post: Post): NetworkResult<Unit, NetworkError>
    suspend fun updatePost(post: Post): NetworkResult<Unit, NetworkError>
}