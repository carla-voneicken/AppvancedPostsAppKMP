package de.carlavoneicken.appvancedpostsappkmp.Data

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val username: String
)