package de.carlavoneicken.appvancedpostsappkmp.di

import de.carlavoneicken.appvancedpostsappkmp.data.database.AppDatabase
import de.carlavoneicken.appvancedpostsappkmp.data.database.getAppDatabase
import de.carlavoneicken.appvancedpostsappkmp.data.database.getDatabaseBuilder
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single<AppDatabase> {
        val builder = getDatabaseBuilder(context = get())
        getAppDatabase(builder)
    }
}