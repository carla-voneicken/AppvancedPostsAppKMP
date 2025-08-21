import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    // Kotlin Serialization: https://github.com/Kotlin/kotlinx.serialization?tab=readme-ov-file#gradle
    kotlin("plugin.serialization") version "2.2.0"
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
        }
    }
    
    sourceSets {
        commonMain.dependencies {
            // Koin: https://insert-koin.io/docs/setup/koin/#kotlin-multiplatform
            implementation(libs.koin.core)
            // Ktor: https://ktor.io/docs/client-create-multiplatform-application.html#ktor-dependencies
            implementation(libs.ktor.client.core)
            // Kotlin Coroutines: https://github.com/Kotlin/kotlinx.coroutines?tab=readme-ov-file#gradle
            implementation(libs.kotlinx.coroutines.core)
            // Kotlin Serialization: https://github.com/Kotlin/kotlinx.serialization?tab=readme-ov-file#gradle
            implementation(libs.kotlinx.serialization.json)
            // Ktor Content Negotiation: https://ktor.io/docs/client-serialization.html#add_dependencies
            implementation(libs.ktor.client.content.negotiation)
            // Ktor JSON Plugins: https://ktor.io/docs/client-serialization.html#add_dependencies
            implementation(libs.ktor.serialization.kotlinx.json)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}

android {
    namespace = "de.carlavoneicken.appvancedpostsappkmp.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
