package com.giacomoparisi.data.error

import com.giacomoparisi.domain.datatypes.LazyData
import com.giacomoparisi.domain.error.BeerBoxException
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException
import javax.inject.Inject

class ErrorMapper @Inject constructor() {

    suspend fun map(throwable: Throwable): BeerBoxException =
        when (throwable) {
            is BeerBoxException ->
                throwable

            is UnknownHostException ->
                BeerBoxException.NetworkErrorUnknownHost()

            is TimeoutException,
            is SocketTimeoutException ->
                BeerBoxException.NetworkErrorTimeOut()

            is HttpException ->
                throwable.mapToFourBooksException()

            else ->
                BeerBoxException.Unknown()
        }

}

suspend fun <T> LazyData<T>.toError(
    errorMapper: ErrorMapper,
    error: Throwable
): LazyData.Error<T> =
    when (this) {
        is LazyData.Data -> LazyData.Error(error = errorMapper.map(error), value = value)
        LazyData.Empty -> LazyData.Error(error = errorMapper.map(error), value = null)
        is LazyData.Error -> LazyData.Error(error = errorMapper.map(error), value = value)
        is LazyData.Loading -> LazyData.Error(error = errorMapper.map(error), value = value)
    }
