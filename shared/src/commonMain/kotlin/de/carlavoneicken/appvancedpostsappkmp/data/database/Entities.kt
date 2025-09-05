package de.carlavoneicken.appvancedpostsappkmp.data.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

enum class SyncState { SYNCED, CREATING, UPDATING, DELETING, FAILED }

class Converters {
    @TypeConverter fun toState(name: String?): SyncState? = name?.let { SyncState.valueOf(it) }
    @TypeConverter fun fromState(state: SyncState?): String? = state?.name
}

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: Long,
    val username: String
)

@Entity(
    tableName = "posts",
    indices = [
        // unique id required so that we can do an upsert on the existing database using the data from
        // the API (which has the remoteId as primary key)
        Index("remoteId", unique = true),
        Index("userRemoteId")
    ]
)
data class PostEntity(
    @PrimaryKey(autoGenerate = true) val localId: Long = 0, // local primary key
    val remoteId: Long?, // API id (null until server assigns)
    val userRemoteId: Long,
    val title: String,
    val body: String,
    val syncState: SyncState = SyncState.SYNCED,
    val updatedAt: Long
)

// set updatedAt when constructing the PostEntity:     updatedAt = Clock.System.now().toEpochMilliseconds()
// do the opt in like this at the very top of the file: @file:OptIn(ExperimentalTime::class)