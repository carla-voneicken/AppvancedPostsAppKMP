package de.carlavoneicken.appvancedpostsappkmp.business.usecases

import de.carlavoneicken.appvancedpostsappkmp.business.utils.NetworkError
import de.carlavoneicken.appvancedpostsappkmp.business.utils.NetworkResult
import de.carlavoneicken.appvancedpostsappkmp.data.repositories.UsersRepository

class RefreshUsersUsecase(private val usersRepository: UsersRepository) {

    suspend operator fun invoke(): NetworkResult<Unit, NetworkError> {
        return usersRepository.refreshUsers()
    }
}