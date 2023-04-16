package com.giacomoparisi.domain.usecases.beer

import com.giacomoparisi.domain.datatypes.PagedList
import com.giacomoparisi.entities.beer.Beer
import com.giacomoparisi.entities.beer.BeerFilter
import javax.inject.Inject

class GetBeersUseCase @Inject constructor(
    private val repository: BeerRepository
) {

    suspend operator fun invoke(
        page: Int,
        pageSize: Int,
        name: String,
        filter: BeerFilter?
    ): PagedList<Beer> =
        repository.getBeers(page, pageSize, name, filter)

}