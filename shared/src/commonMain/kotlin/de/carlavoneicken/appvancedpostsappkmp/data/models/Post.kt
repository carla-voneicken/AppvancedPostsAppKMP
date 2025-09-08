package de.carlavoneicken.appvancedpostsappkmp.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val userId: Long,
    val id: Long,
    val title: String,
    val body: String
)
