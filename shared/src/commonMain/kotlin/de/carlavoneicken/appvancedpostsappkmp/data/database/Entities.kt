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
    @PrimaryKey val id: Int,
    val username: String
)

@OptIn(ExperimentalTime::class)
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
    @PrimaryKey(autoGenerate = true) val localId: Int = 0, // local primary key
    val remoteId: Int?, // API id (null until server assigns)
    val userRemoteId: Int,
    val title: String,
    val body: String,
    val syncState: SyncState = SyncState.SYNCED,
    val updatedAt: Long = Clock.System.now().toEpochMilliseconds()
)