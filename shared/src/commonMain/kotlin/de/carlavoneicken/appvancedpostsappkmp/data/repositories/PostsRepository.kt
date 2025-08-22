package de.carlavoneicken.appvancedpostsappkmp.data.repositories

import de.carlavoneicken.appvancedpostsappkmp.business.utils.NetworkError
import de.carlavoneicken.appvancedpostsappkmp.business.utils.Result
import de.carlavoneicken.appvancedpostsappkmp.business.utils.safeCall
import de.carlavoneicken.appvancedpostsappkmp.network.httpClient
import de.carlavoneicken.appvancedpostsappkmp.data.Post
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.util.network.NetworkAddress
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException

object PostsRepository {
    private const val BASE_URL = "https://jsonplaceholder.typicode.com"

    suspend fun getPostsByUserId(userId: Int): Result<List<Post>, NetworkError> = safeCall(
        // block: the actual HTTP request or operation that could fail
        block = { httpClient.get("$BASE_URL/posts") { parameter("userId", userId) }},
        // mapper: transforms the raw result of the block into the desired output type (here: List<Post>)
        mapper = { it.body() }
    )
}