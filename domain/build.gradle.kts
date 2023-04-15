plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.giacomoparisi.domain"
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

}