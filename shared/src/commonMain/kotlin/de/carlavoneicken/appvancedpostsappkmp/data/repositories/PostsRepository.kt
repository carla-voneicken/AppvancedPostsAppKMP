package de.carlavoneicken.appvancedpostsappkmp.data.repositories

import de.carlavoneicken.appvancedpostsappkmp.business.utils.NetworkError
import de.carlavoneicken.appvancedpostsappkmp.business.utils.Result
import de.carlavoneicken.appvancedpostsappkmp.data.Post

interface PostsRepository {

    suspend fun getPostsByUserId(userId: Int): Result<List<Post>, NetworkError>

    suspend fun getPostById(id: Int): Result<Post, NetworkError>

    suspend fun createPost(post: Post): Result<Post, NetworkError>

    suspend fun updatePost(post: Post): Result<Post, NetworkError>
}