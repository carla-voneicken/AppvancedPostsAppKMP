package de.carlavoneicken.appvancedpostsappkmp.di

import de.carlavoneicken.appvancedpostsappkmp.business.usecases.CreatePostUsecase
import de.carlavoneicken.appvancedpostsappkmp.data.repositories.PostsRepository
import de.carlavoneicken.appvancedpostsappkmp.data.repositories.PostsRepositoryImpl
import de.carlavoneicken.appvancedpostsappkmp.network.httpClient
import org.koin.dsl.module

val appModule = module {
    // 1. HttpClient: make a singleton platform-specific HttpClient instance available in Koin
    single { httpClient }

    // 2. Repository: inject the httpClient automatically using get()
    single<PostsRepository> {
        PostsRepositoryImpl(get())
    }
}