package de.carlavoneicken.appvancedpostsappkmp.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {
    // update or insert a list of posts
    @Upsert
    suspend fun upsertAll(posts: List<PostEntity>)

    // Upsert a post: updates, if the primary key already exists, or creates a new row, if it doesn't
    @Upsert
    suspend fun upsert(post: PostEntity)

    // Update a post: updates, if the primary key already exists, but doesn't do anything, if it doesn't
    @Update
    suspend fun update(post: PostEntity)

    // Get a post with a certain id
    // one-time query -> "give me the value now, once"
    @Query("SELECT * FROM posts WHERE id = :id")
    suspend fun getById(id: Long): PostEntity?

    // Observe a post with a certain id
    // returns a Flow that emits updates automatically -> "keep me posted whenever it changes"
    @Query("SELECT * FROM posts WHERE id = :id")
    fun observeById(id: Long): Flow<PostEntity?>

    // Observe posts by a certain user
    @Query("SELECT * FROM posts WHERE userId = :userId ORDER BY id DESC")
    fun observeByUserId(userId: Long): Flow<List<PostEntity>>

    // Observe all posts
    @Query("SELECT * FROM posts ORDER BY id")
    fun observeAll(): Flow<List<PostEntity>>

    // Delete all posts
    @Query("DELETE FROM posts")
    suspend fun clear()
}