package de.carlavoneicken.appvancedpostsappkmp.data.database

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import de.carlavoneicken.appvancedpostsappkmp.data.database.utils.SyncState

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