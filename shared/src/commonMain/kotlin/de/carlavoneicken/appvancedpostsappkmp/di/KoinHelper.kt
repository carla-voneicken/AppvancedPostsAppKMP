package de.carlavoneicken.appvancedpostsappkmp.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

// initKoin: top-level function that starts the Koin dependency injection container
fun initKoin(appDeclaration: KoinAppDeclaration = {}) {
    startKoin {
        appDeclaration()
        modules(coreModule, platformModule())
    }
}

fun initKoinIos() = initKoin(appDeclaration = {})