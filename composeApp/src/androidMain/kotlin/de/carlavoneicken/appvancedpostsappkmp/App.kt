package de.carlavoneicken.appvancedpostsappkmp

import android.app.Application
import de.carlavoneicken.appvancedpostsappkmp.di.initKoin
import org.koin.android.ext.koin.androidContext

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin(
            appDeclaration = { androidContext(this@App) }
        )
    }
}