package com.giacomoparisi.domain.usecases.beer

import com.giacomoparisi.domain.datatypes.PagedList
import com.giacomoparisi.entities.beer.Beer
import com.giacomoparisi.entities.beer.BeerFilter

interface BeerRepository {

    suspend fun getBeers(
        page: Int,
        pageSize: Int,
        name: String,
        filter: BeerFilter?
    ): PagedList<Beer>

}