package com.giacomoparisi.home.data

import com.giacomoparisi.domain.datatypes.LazyData
import com.giacomoparisi.domain.datatypes.PagedList
import com.giacomoparisi.domain.datatypes.toData
import com.giacomoparisi.domain.datatypes.toPagedList
import com.giacomoparisi.entities.beer.Beer
import com.giacomoparisi.entities.beer.BeerFilter

// Use of LazyData datatype for handling error and loading state for async data
data class HomeState(
    val beers: LazyData<PagedList<Beer>> = LazyData.Empty,
    val firstPage: LazyData<Unit> = LazyData.Empty,
    val search: String = "",
    val selectedBeer: Beer? = null,
    val selectedFilter: BeerFilter? = null
) {

    companion object {

        val filters =
            listOf(
                BeerFilter.Golden,
                BeerFilter.Amber,
                BeerFilter.Brown,
                BeerFilter.Black
            )

        fun mock(): HomeState =
            HomeState(
                beers =
                listOf(Beer.mock(), Beer.mock(), Beer.mock())
                    .toPagedList()
                    .toData(),
                firstPage = LazyData.unit()
            )

    }

}

sealed class HomeAction {

    object GetBeers : HomeAction()
    object GetBeersNextPage : HomeAction()
    data class SetScrollPosition(val position: Int) : HomeAction()
    data class SetSearch(val value: String) : HomeAction()
    object Search : HomeAction()
    data class SelectBeer(var beer: Beer?) : HomeAction()
    data class ToggleFilter(val filter: BeerFilter) : HomeAction()
    object ClearFilters : HomeAction()

}