package de.carlavoneicken.appvancedpostsappkmp.data.database

import androidx.room.RoomDatabase
import org.koin.dsl.module

fun dbModule(builder: RoomDatabase.Builder<AppDatabase>) = module {
    single { buildDatabase(builder) }
    single { get<AppDatabase>().userDao() }
    single { get<AppDatabase>().postDao() }
}