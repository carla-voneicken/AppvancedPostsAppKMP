package de.carlavoneicken.appvancedpostsappkmp.data.repositories

import de.carlavoneicken.appvancedpostsappkmp.business.utils.NetworkError
import de.carlavoneicken.appvancedpostsappkmp.business.utils.NetworkResult
import de.carlavoneicken.appvancedpostsappkmp.business.utils.safeCall
import de.carlavoneicken.appvancedpostsappkmp.data.models.Post
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class PostsRepositoryImpl(private val httpClient: HttpClient): PostsRepository {
    private val baseUrl = "https://jsonplaceholder.typicode.com"

    override suspend fun getPostsByUserId(userId: Int): NetworkResult<List<Post>, NetworkError> = safeCall(
        // block: the actual HTTP request or operation that could fail
        block = {
            httpClient.get("$baseUrl/posts") {
                parameter("userId", userId)
            }
        },
        // mapper: transforms the raw result of the block into the desired output type (here: List<Post>)
        mapper = { it.body() }
    )

    override suspend fun getPostById(id: Int): NetworkResult<Post, NetworkError> = safeCall(
        block = {
            httpClient.get("$baseUrl/posts/$id")
        },
        mapper = { it.body() }
    )

    override suspend fun createPost(post: Post): NetworkResult<Post, NetworkError> = safeCall(
        block = {
            httpClient.post("$baseUrl/posts") {
                contentType(ContentType.Application.Json)
                setBody(post)
            }
        },
        mapper = { it.body() }
    )

    override suspend fun updatePost(post: Post): NetworkResult<Post, NetworkError> = safeCall(
        block = {
            httpClient.put("$baseUrl/posts/${post.id}") {
                contentType(ContentType.Application.Json)
                setBody(post)
            }
        },
        mapper = { it.body() }
    )
}