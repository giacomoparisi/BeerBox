package com.giacomoparisi.domain.ext

import com.giacomoparisi.domain.error.WeatherComposeException
import kotlinx.coroutines.delay

fun <T> catchToNull(block: () -> T): T? =
    try {
        block()
    } catch (error: Throwable) {
        if (error.isCoroutineException()) throw error
        null
    }

suspend fun <T> catchToNullSuspend(block: suspend () -> T): T? =
    try {
        block()
    } catch (error: Throwable) {
        if (error.isCoroutineException()) throw error
        null
    }

suspend fun <T> catchSuspend(block: suspend () -> T, onError: suspend (Throwable) -> T): T =
    try {
        block()
    } catch (error: Throwable) {
        if (error.isCoroutineException()) throw error
        onError(error)
    }

fun <T> catch(block: () -> T, onError: (Throwable) -> T): T =
    try {
        block()
    } catch (error: Throwable) {
        if (error.isCoroutineException()) throw error
        onError(error)
    }

suspend fun <T> retryExponentialBackoff(
    times: Int = 3,
    initialDelayMillis: Long = 100, // 0.1 second
    maxDelayMillis: Long = 1000,    // 1 second
    factor: Double = 2.0,
    block: suspend () -> T
): T {
    var currentDelay = initialDelayMillis
    repeat(times - 1) {

        try {
            return block()
        } catch (error: Throwable) {
            if (error.isCoroutineException()) throw error
        }
        delay(currentDelay)
        currentDelay = (currentDelay * factor).toLong().coerceAtMost(maxDelayMillis)
    }
    return block() // last attempt
}

fun <T> T?.nullToError(error: WeatherComposeException? = null): T =
    this ?: throw error ?: WeatherComposeException.Unknown()