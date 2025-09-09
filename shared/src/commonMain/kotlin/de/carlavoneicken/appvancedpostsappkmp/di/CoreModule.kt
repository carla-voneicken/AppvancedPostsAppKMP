package de.carlavoneicken.appvancedpostsappkmp.di

import de.carlavoneicken.appvancedpostsappkmp.business.usecases.CreatePostUsecase
import de.carlavoneicken.appvancedpostsappkmp.business.usecases.ObserveAllPostsUsecase
import de.carlavoneicken.appvancedpostsappkmp.business.usecases.ObservePostByIdUsecase
import de.carlavoneicken.appvancedpostsappkmp.business.usecases.ObservePostsByUserIdUsecase
import de.carlavoneicken.appvancedpostsappkmp.business.usecases.ObserveUsersUsecase
import de.carlavoneicken.appvancedpostsappkmp.business.usecases.RefreshAllPostsUsecase
import de.carlavoneicken.appvancedpostsappkmp.business.usecases.RefreshPostByIdUsecase
import de.carlavoneicken.appvancedpostsappkmp.business.usecases.RefreshPostsByUserIdUsecase
import de.carlavoneicken.appvancedpostsappkmp.business.usecases.RefreshUsersUsecase
import de.carlavoneicken.appvancedpostsappkmp.business.usecases.UpdatePostUsecase
import de.carlavoneicken.appvancedpostsappkmp.data.database.AppDatabase
import de.carlavoneicken.appvancedpostsappkmp.data.database.PostDao
import de.carlavoneicken.appvancedpostsappkmp.data.database.UserDao
import de.carlavoneicken.appvancedpostsappkmp.data.repositories.PostsRepository
import de.carlavoneicken.appvancedpostsappkmp.data.repositories.PostsRepositoryImpl
import de.carlavoneicken.appvancedpostsappkmp.data.repositories.UsersRepository
import de.carlavoneicken.appvancedpostsappkmp.data.repositories.UsersRepositoryImpl
import de.carlavoneicken.appvancedpostsappkmp.network.httpClient
import org.koin.core.module.Module
import org.koin.dsl.module

val coreModule: Module = module {
    // 1. HttpClient: make a singleton platform-specific HttpClient instance available in Koin
    single { httpClient }
    single<UserDao> { get<AppDatabase>().getUserDao() }
    single<PostDao> { get<AppDatabase>().getPostDao() }

    // 2. Repository: inject the httpClient automatically using get()
    single<PostsRepository> {
        PostsRepositoryImpl(get(), get())
    }
    single<UsersRepository> {
        UsersRepositoryImpl(get(), get())
    }

    // 3. UseCases:
    single<ObserveAllPostsUsecase> {
        ObserveAllPostsUsecase(get())
    }
    single<ObservePostsByUserIdUsecase> {
        ObservePostsByUserIdUsecase(get())
    }
    single<ObservePostByIdUsecase> {
        ObservePostByIdUsecase(get())
    }

    single<RefreshAllPostsUsecase> {
        RefreshAllPostsUsecase(get())
    }
    single<RefreshPostsByUserIdUsecase> {
        RefreshPostsByUserIdUsecase(get())
    }
    single<RefreshPostByIdUsecase> {
        RefreshPostByIdUsecase(get())
    }

    single<CreatePostUsecase> {
        CreatePostUsecase(get())
    }
    single<UpdatePostUsecase> {
        UpdatePostUsecase(get())
    }

    single<ObserveUsersUsecase> {
        ObserveUsersUsecase(get())
    }
    single<RefreshUsersUsecase> {
        RefreshUsersUsecase(get())
    }

    // 4. ViewModels: not needed because we don't inject the ViewModels, we just create them in the AppNavigation (Android) / Views (iOS)
//    factory<UsersViewModel> {
//        UsersViewModel()
//    }
//    factory<PostsViewModel> {
//        (userId: Int) -> PostsViewModel(userId)
//    }
//    factory<PostDetailViewModel> {
//        (postId: Int?, userId: Int) -> PostDetailViewModel(postId, userId)
//    }
}