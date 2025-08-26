package de.carlavoneicken.appvancedpostsappkmp.business.utils

import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException

/* catch exceptions thrown by Ktor/network/JSON using a try-catch-block
-> these happen BEFORE we even get a response from the server, e.g. the request fails to reach the
server or the JSON can't be deserialized */
suspend fun <T> safeCall(
    // block: the actual HTTP request or operation that could fail
    block: suspend () -> HttpResponse,
    // mapper: transforms the raw result of the block into the desired output type (e.g. List<Post>)
    // it's generic so safeCall doesn't need to know in advance what the final type is, we supply it with the mapper
    mapper: suspend (HttpResponse) -> T
): NetworkResult<T, NetworkError> {
    return try {
        val response = block()  // run the HTTP request

        /* check HTTP status codes returned by the server -> the request might succeed at the network
        level, but the server responds with 401, ,500 etc. */
        when (response.status.value) {
            in 200..299 -> {
                val body: T = mapper(response)
                NetworkResult.Success(body)
            }
            401 -> NetworkResult.Failure(NetworkError.UNAUTHORIZED)
            408 -> NetworkResult.Failure(NetworkError.REQUEST_TIMEOUT)
            409 -> NetworkResult.Failure(NetworkError.CONFLICT)
            413 -> NetworkResult.Failure(NetworkError.PAYLOAD_TOO_LARGE)
            in 500..599 -> NetworkResult.Failure(NetworkError.SERVER_ERROR)
            else -> NetworkResult.Failure(NetworkError.UNKNOWN)
        }
    } catch (e: UnresolvedAddressException) {
        NetworkResult.Failure(NetworkError.NO_INTERNET)
    } catch (e: SerializationException) {
        NetworkResult.Failure(NetworkError.SERIALIZATION)
    } catch (e: Exception) {
        NetworkResult.Failure(NetworkError.UNKNOWN)
    }
}