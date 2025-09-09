package de.carlavoneicken.appvancedpostsappkmp.data.repositories

import de.carlavoneicken.appvancedpostsappkmp.business.utils.NetworkError
import de.carlavoneicken.appvancedpostsappkmp.business.utils.NetworkResult
import de.carlavoneicken.appvancedpostsappkmp.business.utils.safeCall
import de.carlavoneicken.appvancedpostsappkmp.data.database.UserDao
import de.carlavoneicken.appvancedpostsappkmp.data.models.User
import de.carlavoneicken.appvancedpostsappkmp.data.remote.UserDto
import de.carlavoneicken.appvancedpostsappkmp.data.remote.toDomain
import de.carlavoneicken.appvancedpostsappkmp.data.remote.toEntity
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UsersRepositoryImpl(
    private val httpClient: HttpClient,
    private val userDao: UserDao
): UsersRepository {
    private val baseUrl = "https://jsonplaceholder.typicode.com"

    override fun observeUsers(): Flow<List<User>> =
        userDao.observeAll()
            .map { entities -> entities.map { it.toDomain() } }


    override suspend fun refreshUsers(): NetworkResult<Unit, NetworkError> =
        safeCall(
            block = {
                httpClient.get("$baseUrl/users")
            },
            mapper = { response ->
                // deserialize the body of the response as a list of UserDto
                val dtos: List<UserDto> = response.body()
                // map the list of UserDto to a list of UserEntity and insert/update them into the database
                userDao.upsertAll(dtos.map { it.toEntity() } )
            }
        )
}