package de.carlavoneicken.appvancedpostsappkmp.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import de.carlavoneicken.appvancedpostsappkmp.data.database.AppDatabase

// Code from Android Docs: https://developer.android.com/kotlin/multiplatform/room
fun androidDatabaseBuilder(context: Context): RoomDatabase.Builder<AppDatabase> {
    val appContext = context.applicationContext
    val dbFile = appContext.getDatabasePath("app.db")
    return Room.databaseBuilder<AppDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}