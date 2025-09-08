package de.carlavoneicken.appvancedpostsappkmp.data.remote

import de.carlavoneicken.appvancedpostsappkmp.data.database.UserEntity
import de.carlavoneicken.appvancedpostsappkmp.data.models.User
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: Long,
    val username: String,
    val email: String
)

// maps UserDto to UserEntity
fun UserDto.toEntity() = UserEntity(id = id, username = username)
// maps UserEntity to User (domain model)
fun UserEntity.toDomain() = User(id = id, username = username)