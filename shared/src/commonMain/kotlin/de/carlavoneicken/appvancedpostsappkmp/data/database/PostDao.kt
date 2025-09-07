package de.carlavoneicken.appvancedpostsappkmp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import de.carlavoneicken.appvancedpostsappkmp.data.database.utils.SyncState
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {
    // UI-facing queries (hide locally-deleted items)
    @Query("""
        SELECT * FROM posts 
        WHERE userRemoteId = :userId AND syncState != 'DELETING' 
        ORDER BY updatedAt DESC
    """)
    fun observeByUser(userId: Long): Flow<List<PostEntity>>

    @Query("SELECT * FROM posts WHERE localId = :localId")
    fun observeByLocalId(localId: Long): Flow<PostEntity?>

    // Mutations
    @Insert
    suspend fun insert(entity: PostEntity): Long

    @Update
    suspend fun update(entity: PostEntity)

    @Query("DELETE FROM posts WHERE localId = :localId")
    suspend fun hardDelete(localId: Long)

    // Convenience “state flips”
    @Query("UPDATE posts SET syncState = :state, updatedAt = :ts WHERE localId = :id")
    suspend fun markState(id: Long, state: SyncState, ts: Long)

    // Sync queues
    @Query("SELECT * FROM posts WHERE syncState IN ('CREATING','UPDATING','DELETING') ORDER BY updatedAt ASC")
    suspend fun loadPending(): List<PostEntity>

    // Upsert by remoteId for pulls -> Room tries to insert the row, if there is no conflict it's inserted
    // if there is a conflict on the primary key, Room does an update of the existing row
    @Upsert
    suspend fun upsertAll(posts: List<PostEntity>)

    // Resolve server ids after create
    @Query("UPDATE posts SET remoteId = :remoteId, syncState = 'SYNCED', updatedAt = :ts WHERE localId = :localId")
    suspend fun bindRemoteId(localId: Long, remoteId: Long, ts: Long)
}