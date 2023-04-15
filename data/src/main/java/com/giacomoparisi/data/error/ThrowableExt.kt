package com.giacomoparisi.data.error

import com.giacomoparisi.domain.datatypes.LazyData

suspend fun <T> Throwable.toError(errorMapper: ErrorMapper, value: T? = null): LazyData.Error<T> =
    LazyData.Error(errorMapper.map(this), value)