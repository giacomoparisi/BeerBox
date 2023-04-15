plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
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

}