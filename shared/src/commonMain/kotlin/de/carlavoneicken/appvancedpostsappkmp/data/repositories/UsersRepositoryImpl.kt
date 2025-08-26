package de.carlavoneicken.appvancedpostsappkmp.data.repositories

import de.carlavoneicken.appvancedpostsappkmp.business.utils.NetworkError
import de.carlavoneicken.appvancedpostsappkmp.business.utils.NetworkResult
import de.carlavoneicken.appvancedpostsappkmp.business.utils.safeCall
import de.carlavoneicken.appvancedpostsappkmp.data.User
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class UsersRepositoryImpl(private val httpClient: HttpClient): UsersRepository {
    private val baseUrl = "https://jsonplaceholder.typicode.com"

    override suspend fun getUsers(): NetworkResult<List<User>, NetworkError> = safeCall(
        block = {
            httpClient.get("$baseUrl/users")
        },
        mapper = { it.body() }
    )
}