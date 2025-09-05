package de.carlavoneicken.appvancedpostsappkmp.database

import androidx.room.Room
import androidx.room.RoomDatabase
import de.carlavoneicken.appvancedpostsappkmp.data.database.AppDatabase
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

// Code from Android Docs: https://developer.android.com/kotlin/multiplatform/room
fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val dbFilePath = documentDirectory() + "/app.db"
    return Room.databaseBuilder<AppDatabase>(
        name = dbFilePath
    )
}

// get the document directory
private fun documentDirectory(): String {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )
    return requireNotNull(documentDirectory?.path)
}