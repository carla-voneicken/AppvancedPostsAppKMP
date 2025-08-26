package de.carlavoneicken.appvancedpostsappkmp

import android.app.Application
import de.carlavoneicken.appvancedpostsappkmp.di.initKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}