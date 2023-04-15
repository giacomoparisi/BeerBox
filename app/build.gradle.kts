plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.giacomoparisi.beerbox"
    compileSdk = ProjectSettings.compileSdk

    defaultConfig {
        applicationId = "com.giacomoparisi.beerbox"
        minSdk = ProjectSettings.minSdk
        targetSdk = ProjectSettings.targetSdk
        versionCode = ProjectSettings.versionCode
        versionName = ProjectSettings.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = AndroidX.Compose.version
    }
}

dependencies {

    /*
        Clean Architecture Modules
        Benefits:
            - Code is further decoupled so is even more easily testable
            - The package structure is even easier to navigate
            - Team can add new features even more quickly
            - Project is even easier to maintain
     */
    implementation(project(":entities"))
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":core"))
    implementation(project(":home"))

    /*
        View Model
        Official Android library for ViewModel, a class for business logic or
        screen level state holder
        Benefits:
            - Better integration with the android framework
            - Caches state and persists it through configuration changes
              (no need to fetch data again when navigating between screens)
     */
    implementation(AndroidX.Lifecycle.viewModelKtx)

    /*
        Kotlin Coroutines
        Official Kotlin Coroutines libraries
     */
    implementation(KotlinX.Coroutines.core)
    implementation(KotlinX.Coroutines.android)

    /*
        Dagger Hilt for dependency injection:
        Benefits:
            - Reduce error-prone boilerplate code
            - Builds and validates dependency graphs, ensuring that every object's dependencies
              can be satisfied and no dependency cycles exist
            - Better dependencies management (dependencies scope, modules, singleton ecc.)
            - Easier unit and integration testing
     */
    implementation(Google.Hilt.android)
    kapt(Google.Hilt.compiler)
    kapt(AndroidX.Hilt.compiler)

    /*
        Jetpack Compose
        Official new Android’s recommended modern toolkit for building native UI.
        Benefits:
         - Simplifies and accelerates UI development on Android
         - Less code
         - Powerful preview and debug tools
         - No more xml files, only Kotlin for the entire project
         - Better and easier UI testing
     */
    implementation(AndroidX.Compose.ui)
    implementation(AndroidX.Compose.uiText)
    implementation(AndroidX.Compose.uiTooling)
    implementation(AndroidX.Compose.uiPreview)
    implementation(AndroidX.Compose.foundation)
    implementation(AndroidX.Compose.material3)
    implementation(AndroidX.Compose.animation)
    implementation(AndroidX.Lifecycle.viewModelCompose)
    implementation(AndroidX.Activity.compose)

}