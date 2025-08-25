package de.carlavoneicken.appvancedpostsappkmp.di

import org.koin.core.context.startKoin

// initKoin: top-level function that starts the Koin dependency injection container
fun initKoin() {
    startKoin {
        modules(appModule)
    }
}