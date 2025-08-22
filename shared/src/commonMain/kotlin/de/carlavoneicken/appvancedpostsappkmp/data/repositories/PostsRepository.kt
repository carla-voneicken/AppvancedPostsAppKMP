package de.carlavoneicken.appvancedpostsappkmp.data.repositories

import de.carlavoneicken.appvancedpostsappkmp.business.utils.NetworkError
import de.carlavoneicken.appvancedpostsappkmp.business.utils.Result
import de.carlavoneicken.appvancedpostsappkmp.business.utils.safeCall
import de.carlavoneicken.appvancedpostsappkmp.data.Post
import de.carlavoneicken.appvancedpostsappkmp.network.httpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

object PostsRepository {
    private const val BASE_URL = "https://jsonplaceholder.typicode.com"

    suspend fun getPostsByUserId(userId: Int): Result<List<Post>, NetworkError> = safeCall(
        // block: the actual HTTP request or operation that could fail
        block = {
            httpClient.get("$BASE_URL/posts") {
                parameter("userId", userId)
            }
        },
        // mapper: transforms the raw result of the block into the desired output type (here: List<Post>)
        mapper = { it.body() }
    )

    suspend fun getPostById(id: Int): Result<Post, NetworkError> = safeCall(
        block = {
            httpClient.get("$BASE_URL/posts/$id")
        },
        mapper = { it.body() }
    )

    suspend fun createPost(post: Post): Result<Post, NetworkError> = safeCall(
        block = {
            httpClient.post("$BASE_URL/posts") {
                contentType(ContentType.Application.Json)
                setBody(post)
            }
        },
        mapper = { it.body() }
    )

    suspend fun updatePost(post: Post): Result<Post, NetworkError> = safeCall(
        block = {
            httpClient.put("$BASE_URL/posts/${post.id}") {
                contentType(ContentType.Application.Json)
                setBody(post)
            }
        },
        mapper = { it.body() }
    )
}