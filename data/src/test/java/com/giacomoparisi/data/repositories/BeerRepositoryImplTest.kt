package com.giacomoparisi.data.repositories

import com.giacomoparisi.data.repositories.beer.BeerRepositoryImpl
import com.giacomoparisi.data.repositories.beer.network.BeerApi
import com.giacomoparisi.data.repositories.beer.network.request.BeerResponse
import com.giacomoparisi.entities.beer.BeerFilter
import io.kotest.matchers.equals.shouldBeEqual
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class BeerRepositoryImplTest {

    lateinit var api: BeerApi
    lateinit var repository: BeerRepositoryImpl

    private val beerResponse =
        BeerResponse(
            id = 1,
            name = "beer_1",
            tagline = "",
            description = "",
            imageUrl = ""
        )

    @Before
    fun before() {

        api = mockk()
        repository = BeerRepositoryImpl(api)

    }

    @Test
    fun testGetParameterName() {

        val searchInputs = listOf("name", "name name   ", "    ", "")

        val names = searchInputs.map { repository.getNameParameter(it) }

        names.shouldBeEqual(listOf("name", "name name", null, null))

    }

    @Test
    fun testGetBeersPageNotCompleted() {
        testGetBeers(pageSize = 10, responseBeersCount = 10)
    }

    @Test
    fun testGetBeersPageCompleted() {
        testGetBeers(pageSize = 20, responseBeersCount = 5)
    }

    private fun testGetBeers(pageSize: Int, responseBeersCount: Int) {

        coEvery { api.getBeers(any(), any(), any(), any(), any()) } returns
                (1 .. responseBeersCount).map { beerResponse }

        val beers =
            runBlocking {
                repository.getBeers(
                    page = 1,
                    pageSize = pageSize,
                    name = "name",
                    filter = BeerFilter.Golden
                )
            }

        beers.isCompleted.shouldBeEqual(expected = pageSize  > responseBeersCount)
        beers.size.shouldBeEqual(expected = responseBeersCount)
        coVerify(exactly = 1) {
            api.getBeers(
                page = 1,
                pageSize = pageSize,
                name = "name",
                ebcMin = BeerFilter.Golden.ebcMin,
                ebcMax = BeerFilter.Golden.ebcMax
            )
        }

    }

}