// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id(GradlePlugin.Android.application) version GradlePlugin.Android.version apply false
    id(GradlePlugin.Android.library) version GradlePlugin.Android.version apply false
    id(GradlePlugin.Kotlin.android) version GradlePlugin.Kotlin.version apply false
    id(GradlePlugin.Hilt.android) version GradlePlugin.Hilt.version apply false
}