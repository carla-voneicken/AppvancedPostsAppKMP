package de.carlavoneicken.appvancedpostsappkmp.di

import de.carlavoneicken.appvancedpostsappkmp.business.usecases.CreatePostUsecase
import de.carlavoneicken.appvancedpostsappkmp.business.usecases.GetPostByIdUsecase
import de.carlavoneicken.appvancedpostsappkmp.business.usecases.GetPostByUserIdUsecase
import de.carlavoneicken.appvancedpostsappkmp.business.usecases.GetUserUsecase
import de.carlavoneicken.appvancedpostsappkmp.business.usecases.UpdatePostUsecase
import de.carlavoneicken.appvancedpostsappkmp.business.viewmodels.PostDetailViewModel
import de.carlavoneicken.appvancedpostsappkmp.business.viewmodels.PostsViewModel
import de.carlavoneicken.appvancedpostsappkmp.business.viewmodels.UsersViewModel
import de.carlavoneicken.appvancedpostsappkmp.data.repositories.PostsRepository
import de.carlavoneicken.appvancedpostsappkmp.data.repositories.PostsRepositoryImpl
import de.carlavoneicken.appvancedpostsappkmp.data.repositories.UsersRepository
import de.carlavoneicken.appvancedpostsappkmp.data.repositories.UsersRepositoryImpl
import de.carlavoneicken.appvancedpostsappkmp.network.httpClient
import org.koin.dsl.module

val appModule = module {
    // 1. HttpClient: make a singleton platform-specific HttpClient instance available in Koin
    single { httpClient }

    // 2. Repository: inject the httpClient automatically using get()
    single<PostsRepository> {
        PostsRepositoryImpl(get())
    }
    single<UsersRepository> {
        UsersRepositoryImpl(get())
    }

    // 3. UseCases:
    single<CreatePostUsecase> {
        CreatePostUsecase(get())
    }
    single<GetPostByIdUsecase> {
        GetPostByIdUsecase(get())
    }
    single<GetPostByUserIdUsecase> {
        GetPostByUserIdUsecase(get())
    }
    single<GetUserUsecase> {
        GetUserUsecase(get())
    }
    single<UpdatePostUsecase> {
        UpdatePostUsecase(get())
    }

    // 4. ViewModels
    factory<UsersViewModel> {
        UsersViewModel()
    }
    factory<PostsViewModel> {
        (userId: Int) -> PostsViewModel(userId)
    }
    factory<PostDetailViewModel> {
        (postId: Int?, userId: Int) -> PostDetailViewModel(postId, userId)
    }
}