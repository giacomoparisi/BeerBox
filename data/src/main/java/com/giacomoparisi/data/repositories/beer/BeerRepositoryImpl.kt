package com.giacomoparisi.data.repositories.beer

import com.giacomoparisi.data.repositories.beer.network.BeerApi
import com.giacomoparisi.domain.datatypes.PagedList
import com.giacomoparisi.domain.datatypes.toPagedList
import com.giacomoparisi.domain.usecases.beer.BeerRepository
import com.giacomoparisi.entities.beer.Beer
import javax.inject.Inject

class BeerRepositoryImpl @Inject constructor(
    private val api: BeerApi
) : BeerRepository {

    override suspend fun getBeers(page: Int, pageSize: Int): PagedList<Beer> =
        api.getBeers(page, pageSize)
            .mapNotNull { it.toBeer() }
            .let { it.toPagedList(page, it.size < pageSize) }

}