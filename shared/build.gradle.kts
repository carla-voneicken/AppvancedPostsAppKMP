import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    // Kotlin Serialization: https://github.com/Kotlin/kotlinx.serialization?tab=readme-ov-file#gradle
    kotlin("plugin.serialization") version "2.2.0"
    // KMP NativeCoroutines
    //id("com.google.devtools.ksp") version "2.2.10-2.0.2"
    id("com.rickclephas.kmp.nativecoroutines") version "1.0.0-ALPHA-46"
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
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
        all {
            // KMP ObservableViewModel
            languageSettings.optIn("kotlinx.cinterop.ExperimentalForeignApi")
            // KMP NativeCoroutines
            languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
        }
        commonMain.dependencies {
            // Koin: https://insert-koin.io/docs/setup/koin/#kotlin-multiplatform
            implementation(libs.koin.core)

            // Ktor: https://ktor.io/docs/client-create-multiplatform-application.html#ktor-dependencies
            // Ktor Content Negotiation: https://ktor.io/docs/client-serialization.html#add_dependencies
            // Ktor JSON Plugins: https://ktor.io/docs/client-serialization.html#add_dependencies
            // bundles -> installs ktor core, content negotiation, logging and serialization
            implementation(libs.bundles.ktor)

            // Kotlin Coroutines: https://github.com/Kotlin/kotlinx.coroutines?tab=readme-ov-file#gradle
            implementation(libs.kotlinx.coroutines.core)
            // Kotlin Serialization: https://github.com/Kotlin/kotlinx.serialization?tab=readme-ov-file#gradle
            implementation(libs.kotlinx.serialization.json)

            // KMP ObservableViewModel: https://github.com/rickclephas/KMP-ObservableViewModel
            api("com.rickclephas.kmp:kmp-observableviewmodel-core:1.0.0-BETA-13")

            implementation(libs.room.runtime)
            implementation(libs.sqlite.bundled)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
            implementation(libs.koin.android)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
            implementation(libs.koin.core)
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

room {
    schemaDirectory("$projectDir/schemas")
}

dependencies {
    add("kspAndroid", libs.room.compiler)
    add("kspIosSimulatorArm64", libs.room.compiler)
    add("kspIosX64", libs.room.compiler)
    add("kspIosArm64", libs.room.compiler)
}
