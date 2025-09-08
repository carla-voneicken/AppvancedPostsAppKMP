package de.carlavoneicken.appvancedpostsappkmp.data.repositories

import de.carlavoneicken.appvancedpostsappkmp.business.utils.NetworkError
import de.carlavoneicken.appvancedpostsappkmp.business.utils.NetworkResult
import de.carlavoneicken.appvancedpostsappkmp.data.models.Post

interface PostsRepository {

    suspend fun getPostsByUserId(userId: Int): NetworkResult<List<Post>, NetworkError>

    suspend fun getPostById(id: Int): NetworkResult<Post, NetworkError>

    suspend fun createPost(post: Post): NetworkResult<Post, NetworkError>

    suspend fun updatePost(post: Post): NetworkResult<Post, NetworkError>
}