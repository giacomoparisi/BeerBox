package com.giacomoparisi.core.compose.async

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.giacomoparisi.core.R
import com.giacomoparisi.domain.error.BeerBoxException

fun getErrorMessage(context: Context, exception: BeerBoxException): String =
    context.getString(getErrorRes(exception))

@Composable
fun getErrorMessage(exception: BeerBoxException): String =
    stringResource(id = getErrorRes(exception))

private fun getErrorRes(exception: BeerBoxException): Int =
    when (exception) {
        is BeerBoxException.Unknown ->
            R.string.COMMON_error
        is BeerBoxException.NetworkErrorTimeOut ->
            R.string.COMMON_error_network
        is BeerBoxException.NetworkErrorUnknownHost ->
            R.string.COMMON_error_network
        is BeerBoxException.NetworkErrorHTTP ->
            R.string.COMMON_error
    }