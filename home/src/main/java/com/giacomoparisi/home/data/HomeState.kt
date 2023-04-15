package com.giacomoparisi.home.data

import com.giacomoparisi.domain.datatypes.LazyData
import com.giacomoparisi.domain.datatypes.PagedList
import com.giacomoparisi.entities.beer.Beer

data class HomeState(
    val beers: LazyData<PagedList<Beer>> = LazyData.Empty
)