package com.giacomoparisi.domain.usecases.beer

import javax.inject.Inject

class GetBeersUseCase @Inject constructor(
    private val repository: BeerRepository
) {

    suspend operator fun invoke(page: Int, pageSize: Int) =
        repository.getBeers(page, pageSize)

}