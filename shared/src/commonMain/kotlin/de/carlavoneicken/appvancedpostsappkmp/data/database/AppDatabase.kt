package de.carlavoneicken.appvancedpostsappkmp.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import de.carlavoneicken.appvancedpostsappkmp.data.utils.TimeConverters

@Database(
    entities = [UserEntity::class, PostEntity::class],
    version = 1,
    exportSchema = true
)
@ConstructedBy(AppDatabseConstructor::class)
@TypeConverters(TimeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun postDao(): PostDao
}

@Suppress("KotlinNoActualForExpect")
expect object AppDatabseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}