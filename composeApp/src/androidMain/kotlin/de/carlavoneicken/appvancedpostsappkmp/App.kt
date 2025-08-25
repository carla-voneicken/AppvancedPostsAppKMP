package de.carlavoneicken.appvancedpostsappkmp

import android.app.Application
import de.carlavoneicken.appvancedpostsappkmp.di.initKoinAndroid

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoinAndroid(this)
    }
}