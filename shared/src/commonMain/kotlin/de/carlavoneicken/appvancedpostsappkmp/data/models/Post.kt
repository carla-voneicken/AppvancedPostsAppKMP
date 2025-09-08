package de.carlavoneicken.appvancedpostsappkmp.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)
