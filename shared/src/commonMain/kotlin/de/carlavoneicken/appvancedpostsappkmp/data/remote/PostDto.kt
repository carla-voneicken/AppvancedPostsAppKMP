package de.carlavoneicken.appvancedpostsappkmp.data.remote

import de.carlavoneicken.appvancedpostsappkmp.data.database.PostEntity
import de.carlavoneicken.appvancedpostsappkmp.data.models.Post
import kotlinx.serialization.Serializable

@Serializable
data class PostDto(
    val userId: Long,
    val id: Long,
    val title: String,
    val body: String
)

fun PostDto.toEntity() = PostEntity(id = id, userId = userId, title = title, body = body)
fun PostEntity.toDomain() = Post(id = id, userId = userId, title = title, body = body)