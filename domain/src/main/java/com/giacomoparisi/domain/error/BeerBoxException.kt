package com.giacomoparisi.domain.error

sealed class BeerBoxException : Throwable() {

    /* --- common --- */

    class Unknown : BeerBoxException()
    class NetworkErrorTimeOut : BeerBoxException()
    class NetworkErrorUnknownHost : BeerBoxException()
    data class NetworkErrorHTTP(
            val code: Int,
            val endpoint: String?,
            val method: String?,
            val token: String?,
            val requestBody: String?,
            val responseBody: String?,
            val errorMessage: String?
    ) : BeerBoxException()
}