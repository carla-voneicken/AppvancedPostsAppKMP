package de.carlavoneicken.appvancedpostsappkmp.di

import androidx.room.RoomDatabase
import de.carlavoneicken.appvancedpostsappkmp.data.database.AppDatabase
import de.carlavoneicken.appvancedpostsappkmp.data.database.dbModule
import org.koin.core.context.startKoin


// initKoin: top-level function that starts the Koin dependency injection container
fun initKoin(builder: RoomDatabase.Builder<AppDatabase>) {
    startKoin {
        modules(
            appModule,
            dbModule(builder)
        )
    }
}