object Google {

    object Hilt {

        private const val group = "com.google.dagger"
        const val version = "2.45"

        const val android = "$group:hilt-android:$version"

        const val compiler = "$group:hilt-android-compiler:$version"

    }

    object Accompanist {

        private const val group = "com.google.accompanist"
        private const val version = "0.28.0"

        const val navigationAnimation = "$group:accompanist-navigation-animation:$version"
    }

}