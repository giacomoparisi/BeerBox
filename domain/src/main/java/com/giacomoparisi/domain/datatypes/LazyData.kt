package com.giacomoparisi.domain.datatypes

import com.giacomoparisi.domain.error.BeerBoxException

sealed class LazyData<out T> {

    object Empty : LazyData<Nothing>()

    data class Data<out T>(val value: T) : LazyData<T>()

    data class Loading<out T>(
        val value: T? = null,
        val extra: Map<String, String> = emptyMap()
    ) : LazyData<T>()

    data class Error<out T>(val error: BeerBoxException, val value: T? = null) : LazyData<T>()

    fun <A> map(mapper: (T) -> A): LazyData<A> =
        when (this) {
            is Data -> mapper(value).toData()
            Empty -> Empty
            is Error -> Error(error, value?.let { mapper(it) })
            is Loading -> Loading(value?.let { mapper(it) })
        }

    fun dataOrNull(): T? =
        when (this) {
            is Data -> value
            else -> null
        }

    fun currentOrPrevious(): T? =
        when (this) {
            is Data -> value
            Empty -> null
            is Error -> value
            is Loading -> value
        }

    fun errorOrNull(): BeerBoxException? =
        when (this) {
            is Data -> null
            Empty -> null
            is Error -> error
            is Loading -> null
        }

    fun toLoading(): Loading<T> =
        when (this) {
            is Data -> Loading(value = value)
            Empty -> Loading(value = null)
            is Error -> Loading(value = value)
            is Loading -> Loading(value = value)
        }

    fun toDataOrError(): LazyData<T> =
        when (this) {
            is Data -> Data(value = value)
            Empty -> Error(error = BeerBoxException.Unknown(), value = null)
            is Error -> if (value != null) Data(value = value) else this
            is Loading ->
                if (value != null) Data(value = value)
                else Error(error = BeerBoxException.Unknown(), value = null)
        }

    fun isLoading(): Boolean = this is Loading

    fun isData(): Boolean = this is Data

    fun isError(): Boolean = this is Error

    companion object {

        fun unit(): Data<Unit> = Unit.toData()

    }

}

fun <T> T.toData(): LazyData.Data<T> = LazyData.Data(this)

fun <T> BeerBoxException.toError(value: T? = null): LazyData.Error<T> =
    LazyData.Error(this, value)