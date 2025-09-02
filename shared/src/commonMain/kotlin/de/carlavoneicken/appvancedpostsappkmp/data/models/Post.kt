package de.carlavoneicken.appvancedpostsappkmp.data

import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
data class Post(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String
)
