package com.giacomoparisi.data.error

import com.giacomoparisi.domain.error.BeerBoxException
import com.giacomoparisi.domain.ext.catchToNullSuspend
import okhttp3.RequestBody
import okio.Buffer
import retrofit2.HttpException


suspend fun HttpException.mapToFourBooksException(): BeerBoxException {

    val raw = response()?.raw()
    val request = raw?.request
    val response = response()

    val requestJson =
        request?.body.bodyToString()

    val responseJson =
        response?.errorBody()?.string()

    return BeerBoxException.NetworkErrorHTTP(
        code(),
        request?.url.toString(),
        request?.method,
        request?.headers?.get("Authorization"),
        requestJson,
        responseJson,
        errorMessage = null
    )

}

private suspend fun RequestBody?.bodyToString(): String? =
    catchToNullSuspend {

        this?.let {
            val buffer = Buffer()
            it.writeTo(buffer)
            buffer.readUtf8()
        }

    }
