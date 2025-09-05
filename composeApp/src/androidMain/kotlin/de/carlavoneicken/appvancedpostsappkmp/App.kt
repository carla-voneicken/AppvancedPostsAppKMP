package de.carlavoneicken.appvancedpostsappkmp

import android.app.Application
import de.carlavoneicken.appvancedpostsappkmp.database.getDatabaseBuilder
import de.carlavoneicken.appvancedpostsappkmp.di.appModule
import de.carlavoneicken.appvancedpostsappkmp.data.database.dbModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        val dbBuilder = getDatabaseBuilder(this)

        startKoin {
            androidContext(this@App)
            modules(
                appModule,
                dbModule(dbBuilder)
            )
        }
    }
}