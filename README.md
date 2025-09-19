# AppvancedPostsAppKMP

A Kotlin Multiplatform (KMP) sample app that displays Users and Posts, sharing business logic, networking, and persistence across Android and iOS. 

- Android UI: Jetpack Compose
- iOS UI: SwiftUI (using the shared KMP layer via KMP-ObservableViewModel)
- Shared: Ktor HTTP client, Kotlinx Serialization, Coroutines, Koin DI, Room (Multiplatform), SQLDelight bundled SQLite

The project fetches demo data from jsonplaceholder.typicode.com and persists it locally via Room. Dependency Injection is provided by Koin. Networking uses Ktor with JSON serialization.


## Stack / Tooling
- Languages: Kotlin (KMP), Swift (iOS)
- UI: Jetpack Compose (Android), SwiftUI (iOS)
- DI: Koin
- Networking: Ktor Client (core, content-negotiation, logging, kotlinx-json)
- Persistence: Room (Multiplatform), SQLite bundled
- Serialization: kotlinx.serialization
- Concurrency: kotlinx.coroutines
- Build system: Gradle (Kotlin DSL) with Gradle Wrapper
- Kotlin: 2.2.10 (see gradle/libs.versions.toml)
- Android Gradle Plugin (AGP): 8.12.3 (see gradle/libs.versions.toml)


## Requirements
- JDK 11 (project compiles with JVM target 11)
- Android Studio (Electric Eel or newer recommended) with Android SDK:
  - compileSdk: 36
  - minSdk: 24
  - targetSdk: 36
- Xcode for iOS development
  - TODO: Confirm the exact minimum Xcode and iOS deployment target versions
- Kotlin/Gradle via the provided Gradle Wrapper (./gradlew)


## Project Structure
This is a Kotlin Multiplatform project targeting Android and iOS.

- /composeApp
  - Android application module (Jetpack Compose UI)
  - Entry point: MainActivity (composeApp/src/androidMain/.../MainActivity.kt)
  - Application class initializes Koin: App (composeApp/src/androidMain/.../App.kt)
- /shared
  - Shared KMP module (business logic, networking, data, DI)
  - Important source set: shared/src/commonMain/kotlin
  - Ktor HttpClient configuration: shared/src/commonMain/.../network/HttpClientProvider.kt
  - Repositories (e.g., PostsRepositoryImpl) use https://jsonplaceholder.typicode.com
  - Room setup, DAOs, and entities in shared/src/commonMain/.../data/database
- /iosApp
  - Native iOS app (SwiftUI)
  - Entry point: iosApp.swift (@main)
  - Initializes Koin via KoinHelperKt.doInitKoinIos()
