object Squareup {

    object Moshi {

        private const val group = "com.squareup.moshi"
        private const val version = "1.14.0"

        const val kotlin = "$group:moshi-kotlin:$version"

        const val adapters = "$group:moshi-adapters:$version"

    }

    object Retrofit2 {

        private const val group = "com.squareup.retrofit2"
        private const val version = "2.9.0"

        const val retrofit = "$group:retrofit:$version"

        object Converter {

            const val moshi = "$group:converter-moshi:$version"

        }

    }

    object OkHttp3 {

        const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:4.9.3"

    }

}