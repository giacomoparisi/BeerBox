package com.giacomoparisi.entities.beer

sealed class BeerFilter(val ebcMin: Int, val ebcMax: Int?) {

    object Golden : BeerFilter(ebcMin = 0, ebcMax = 12)

    object Amber : BeerFilter(ebcMin = 13, ebcMax = 30)

    object Brown : BeerFilter(ebcMin = 31, ebcMax = 59)

    object Black : BeerFilter(ebcMin = 60, ebcMax = null)

}