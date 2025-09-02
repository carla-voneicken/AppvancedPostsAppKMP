package de.carlavoneicken.appvancedpostsappkmp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Dao
interface PostDao {
    // UI-facing queries (hide locally-deleted items)
    @Query("""
        SELECT * FROM posts 
        WHERE userRemoteId = :userId AND syncState != 'DELETING' 
        ORDER BY updatedAt DESC
    """)
    fun observeByUser(userId: Int): Flow<List<PostEntity>>

    @Query("SELECT * FROM posts WHERE localId = :localId")
    fun observeByLocalId(localId: Int): Flow<PostEntity?>

    // Mutations
    @Insert
    suspend fun insert(entity: PostEntity): Int

    @Update
    suspend fun update(entity: PostEntity)

    @Query("DELETE FROM posts WHERE localId = :localId")
    suspend fun hardDelete(localId: Int)

    // Convenience “state flips”
    @Query("UPDATE posts SET syncState = :state, updatedAt = :ts WHERE localId = :id")
    suspend fun markState(id: Int, state: SyncState, ts: Long = Clock.System.now().toEpochMilliseconds())

    // Sync queues
    @Query("SELECT * FROM posts WHERE syncState IN ('CREATING','UPDATING','DELETING') ORDER BY updatedAt ASC")
    suspend fun loadPending(): List<PostEntity>

    // Upsert by remoteId for pulls -> Room tries to insert the row, if there is no conflict it's inserted
    // if there is a conflict on the primary key, Room does an update of the existing row
    @Upsert
    suspend fun upsertAll(posts: List<PostEntity>)

    // Resolve server ids after create
    @Query("UPDATE posts SET remoteId = :remoteId, syncState = 'SYNCED', updatedAt = :ts WHERE localId = :localId")
    suspend fun bindRemoteId(localId: Int, remoteId: Int, ts: Long = Clock.System.now().toEpochMilliseconds())
}