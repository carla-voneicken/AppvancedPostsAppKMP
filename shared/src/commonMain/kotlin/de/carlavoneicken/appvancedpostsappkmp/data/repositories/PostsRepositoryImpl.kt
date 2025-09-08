package de.carlavoneicken.appvancedpostsappkmp.data.repositories

import de.carlavoneicken.appvancedpostsappkmp.business.utils.NetworkError
import de.carlavoneicken.appvancedpostsappkmp.business.utils.NetworkResult
import de.carlavoneicken.appvancedpostsappkmp.business.utils.safeCall
import de.carlavoneicken.appvancedpostsappkmp.data.database.PostDao
import de.carlavoneicken.appvancedpostsappkmp.data.models.Post
import de.carlavoneicken.appvancedpostsappkmp.data.remote.PostDto
import de.carlavoneicken.appvancedpostsappkmp.data.remote.toDomain
import de.carlavoneicken.appvancedpostsappkmp.data.remote.toEntity
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.collections.map

class PostsRepositoryImpl(
    private val httpClient: HttpClient,
    private val postDao: PostDao
): PostsRepository {
    private val baseUrl = "https://jsonplaceholder.typicode.com"

    override fun observeAllPosts(): Flow<List<Post>> =
        postDao.observeAll()
            .map { entities -> entities.map { it.toDomain() } }

    override fun observePostsByUserId(userId: Long): Flow<List<Post>> =
        postDao.observeByUserId(userId)
            .map { entities -> entities.map { it.toDomain() } }

    override fun observePost(id: Long): Flow<Post?> =
        postDao.observeById(id)
            .map { entity -> entity?.toDomain()  }


    override suspend fun refreshAllPosts(): NetworkResult<Unit, NetworkError> =
        safeCall(
            // block: the actual HTTP request or operation that could fail
            block = {
                httpClient.get("$baseUrl/posts")
            },
            // mapper: transforms the raw result of the block into the desired output type (here: List<Post>)
            mapper = { response ->
                // deserialize the body of the response as a list of PostDto
                val dtos: List<PostDto> = response.body()
                // map the list of PostDto to a list of PostEntity and insert/update them into the database
                postDao.upsertAll(dtos.map { it.toEntity() } )
            }
        )

    override suspend fun refreshPostsByUserId(userId: Long): NetworkResult<Unit, NetworkError> =
        safeCall(
            block = {
                httpClient.get("$baseUrl/posts") {
                    parameter("userId", userId)
                }
            },
            mapper = { response ->
                val dtos: List<PostDto> = response.body()
                postDao.upsertAll(dtos.map { it.toEntity() } )
            }
        )

    override suspend fun refreshPost(id: Long): NetworkResult<Unit, NetworkError> =
        safeCall(
            block = {
                httpClient.get("$baseUrl/posts/$id")
            },
            mapper = { response ->
                val dto: PostDto = response.body()
                postDao.upsert(dto.toEntity())
            }
        )

    override suspend fun createPost(post: Post): NetworkResult<Unit, NetworkError> = safeCall(
        block = {
            httpClient.post("$baseUrl/posts") {
                contentType(ContentType.Application.Json)
                setBody(post)
            }
        },
        mapper = { response ->
            val created: PostDto = response.body()
            postDao.upsert(created.toEntity())
        }
    )

    override suspend fun updatePost(post: Post): NetworkResult<Unit, NetworkError> = safeCall(
        block = {
            httpClient.put("$baseUrl/posts/${post.id}") {
                contentType(ContentType.Application.Json)
                setBody(post)
            }
        },
        mapper = { response ->
            val updated: PostDto = response.body()
            postDao.upsert(updated.toEntity())
        }
    )
}