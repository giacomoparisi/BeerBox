plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.giacomoparisi.data"
    compileSdk = ProjectSettings.compileSdk

    defaultConfig {
        minSdk = ProjectSettings.minSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        buildConfig = true
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

    /*
         Retrofit
         HTTP client for Android
         Benefits:
            - Easier networking implementation for Android apps
            - Very fast
            - Reduce error-prone boilerplate code
            - Support for Kotlin Coroutines
            - Use OkHttp for low level network operations
     */
    implementation(Squareup.Retrofit2.retrofit)
    // Used for debug requests and response
    implementation(Squareup.OkHttp3.loggingInterceptor)
    // Moshi Json library for response deserialization
    implementation(Squareup.Retrofit2.Converter.moshi)
    implementation(Squareup.Moshi.kotlin)


}