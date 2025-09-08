package de.carlavoneicken.appvancedpostsappkmp.data.repositories

import de.carlavoneicken.appvancedpostsappkmp.business.utils.NetworkError
import de.carlavoneicken.appvancedpostsappkmp.business.utils.NetworkResult
import de.carlavoneicken.appvancedpostsappkmp.data.models.User

interface UsersRepository {
    suspend fun getUsers(): NetworkResult<List<User>, NetworkError>
}