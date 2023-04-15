package com.giacomoparisi.domain.usecases.beer

import com.giacomoparisi.domain.datatypes.PagedList
import com.giacomoparisi.entities.beer.Beer

interface BeerRepository {

    suspend fun getBeers(page: Int, pageSize: Int): PagedList<Beer>

}