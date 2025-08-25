package de.carlavoneicken.appvancedpostsappkmp.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

fun initKoinAndroid(application: Application) {
    startKoin {
        androidContext(application)
        modules(appModule)
    }
}