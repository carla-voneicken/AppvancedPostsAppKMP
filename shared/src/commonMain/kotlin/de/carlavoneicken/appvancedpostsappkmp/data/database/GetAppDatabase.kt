package de.carlavoneicken.appvancedpostsappkmp.data.database

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

fun getAppDatabase(builder: RoomDatabase.Builder<AppDatabase>): AppDatabase {
    return builder
        .setDriver(BundledSQLiteDriver())
        // database should use Dispatchers.IO for executing asynchronous queries
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}