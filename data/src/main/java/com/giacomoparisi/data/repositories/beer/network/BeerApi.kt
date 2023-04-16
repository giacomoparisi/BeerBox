package com.giacomoparisi.data.repositories.beer.network

import com.giacomoparisi.data.repositories.beer.network.request.BeerResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface BeerApi {

    @GET("v2/beers")
    suspend fun getBeers(
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int,
        @Query("beer_name") name: String?,
        @Query("ebc_gt") ebcMin: Int?,
        @Query("ebc_lt") ebcMax: Int?
    ): List<BeerResponse>

}