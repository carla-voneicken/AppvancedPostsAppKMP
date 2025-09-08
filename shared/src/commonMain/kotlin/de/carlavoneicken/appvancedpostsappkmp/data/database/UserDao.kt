package de.carlavoneicken.appvancedpostsappkmp.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    // Observe all users
    @Query("SELECT * FROM users ORDER BY username")
    fun observeAll(): Flow<List<UserEntity>>

    // Observe a certain user by userId
    // returns a Flow that emits updates automatically -> "keep me posted whenever it changes"
    @Query("SELECT * FROM users WHERE id = :id")
    fun observeById(id: Long): Flow<UserEntity?>

    // Get a certain user
    // one-time query -> "give me the value now, once"
    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getById(id: Long): UserEntity?

    // Update or insert a list of users
    @Upsert
    suspend fun upsertAll(users: List<UserEntity>)

    // Update or insert a single user
    @Upsert
    suspend fun upsert(user: UserEntity)

    // Delete all users
    @Query("DELETE FROM users")
    suspend fun clear()
}