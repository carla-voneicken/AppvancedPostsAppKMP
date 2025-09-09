package de.carlavoneicken.appvancedpostsappkmp.business.usecases

import de.carlavoneicken.appvancedpostsappkmp.data.models.User
import de.carlavoneicken.appvancedpostsappkmp.data.repositories.UsersRepository
import kotlinx.coroutines.flow.Flow

class ObserveUsersUsecase(private val usersRepository: UsersRepository) {

    suspend operator fun invoke(): Flow<List<User>> {
        return usersRepository.observeUsers()
    }
}