object AndroidX {

    object Activity {

        const val compose = "androidx.activity:activity-compose:1.6.1"

    }

    object Core {

        const val ktx = "androidx.core:core-ktx:1.9.0"

    }

    object Lifecycle {

        private const val group = "androidx.lifecycle"
        private const val version = "2.5.1"

        const val viewModelKtx = "$group:lifecycle-viewmodel-ktx:$version"

        const val viewModelCompose = "$group:lifecycle-viewmodel-compose:$version"

    }

    object Compose {

        const val version = "1.4.3"

        const val ui = "androidx.compose.ui:ui:1.3.3"

        const val uiText = "androidx.compose.ui:ui-text:1.3.3"

        const val uiTooling = "androidx.compose.ui:ui-tooling:1.3.3"

        const val uiPreview = "androidx.compose.ui:ui-tooling-preview:1.3.3"

        const val foundation = "androidx.compose.foundation:foundation:1.3.1"

        const val material3 = "androidx.compose.material3:material3-android:1.1.0-beta02"

        const val animation = "androidx.compose.animation:animation:1.3.3"

    }

    object Hilt {

        private const val group = "androidx.hilt"

        const val navigationCompose = "$group:hilt-navigation-compose:1.0.0"

        const val compiler = "$group:hilt-compiler:1.0.0"

    }

    object Room {

        private const val group = "androidx.room"
        private const val version = "2.5.0"

        const val runtime = "$group:room-runtime:$version"

        const val ktx = "$group:room-ktx:$version"

        const val compiler = "$group:room-compiler:$version"

        const val testing = "$group:room-testing:$version"

    }

}