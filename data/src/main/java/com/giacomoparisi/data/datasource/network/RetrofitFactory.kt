package com.giacomoparisi.data.datasource.network

import com.giacomoparisi.data.utils.isDebuggable
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

inline fun <reified T> getApiService(
    baseUrl: String,
    moshi: Moshi
): T {

    val httpClient = OkHttpClient.Builder()

    val client =
        httpClient
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .let {
                if (isDebuggable()) {
                    val logger = HttpLoggingInterceptor()
                    logger.level = HttpLoggingInterceptor.Level.BODY
                    it.addInterceptor(logger)
                } else it
            }
            .build()

    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(client)
        .build()
        .create(T::class.java)
}