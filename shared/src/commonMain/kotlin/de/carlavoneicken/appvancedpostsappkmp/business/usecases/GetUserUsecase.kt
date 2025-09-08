package de.carlavoneicken.appvancedpostsappkmp.business.usecases

import de.carlavoneicken.appvancedpostsappkmp.business.utils.NetworkError
import de.carlavoneicken.appvancedpostsappkmp.business.utils.NetworkResult
import de.carlavoneicken.appvancedpostsappkmp.data.models.User
import de.carlavoneicken.appvancedpostsappkmp.data.repositories.UsersRepository

class GetUserUsecase(private val usersRepository: UsersRepository) {

    suspend operator fun invoke(): NetworkResult<List<User>, NetworkError> {
        return usersRepository.getUsers()
    }
}