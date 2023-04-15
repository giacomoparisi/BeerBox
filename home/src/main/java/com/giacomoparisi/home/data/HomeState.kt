package com.giacomoparisi.home.data

import com.giacomoparisi.domain.datatypes.LazyData
import com.giacomoparisi.domain.datatypes.PagedList
import com.giacomoparisi.domain.datatypes.toData
import com.giacomoparisi.domain.datatypes.toPagedList
import com.giacomoparisi.entities.beer.Beer

data class HomeState(
    val beers: LazyData<PagedList<Beer>> = LazyData.Empty
) {

    companion object {

        fun mock(): HomeState =
            HomeState(
                beers =
                listOf(Beer.mock(), Beer.mock(), Beer.mock())
                    .toPagedList()
                    .toData()
            )

    }

}

sealed class HomeAction {

    object GetBeers: HomeAction()

}