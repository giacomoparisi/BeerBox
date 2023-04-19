package com.giacomoparisi.home.data

import com.giacomoparisi.data.error.ErrorMapper
import com.giacomoparisi.domain.datatypes.LazyData
import com.giacomoparisi.domain.datatypes.addPage
import com.giacomoparisi.domain.datatypes.toData
import com.giacomoparisi.domain.datatypes.toError
import com.giacomoparisi.domain.datatypes.toPagedList
import com.giacomoparisi.domain.error.BeerBoxException
import com.giacomoparisi.domain.usecases.beer.GetBeersUseCase
import com.giacomoparisi.entities.beer.Beer
import com.giacomoparisi.entities.beer.BeerFilter
import com.giacomoparisi.home.MainCoroutineRule
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.test.TestResult
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {


    private lateinit var getBeersUseCase: GetBeersUseCase
    private lateinit var errorMapper: ErrorMapper
    private lateinit var mutex: Mutex

    private lateinit var viewModel: HomeViewModel

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun before() {

        mutex = Mutex()
        errorMapper = spyk()
        getBeersUseCase = mockk()

        viewModel = spyk(HomeViewModel(getBeersUseCase, errorMapper, mutex))

    }

    @Test
    fun testSelectBeer(): TestResult = runTest {

        val beer = Beer.mock()
        viewModel.dispatch(HomeAction.SelectBeer(beer))
        advanceUntilIdle()
        viewModel.stateFlow.value.selectedBeer.shouldBe(beer)

    }

    @Test
    fun testSetScrollPosition_ignored(): TestResult = runTest {

        viewModel.dispatch(HomeAction.SetScrollPosition(position = 1))
        advanceUntilIdle()
        verify(exactly = 0) { viewModel.dispatch(HomeAction.GetBeersNextPage) }

    }

    @Test
    fun testSetScrollPosition_nextPage(): TestResult = runTest {
        coEvery {
            getBeersUseCase(
                any(),
                any(),
                any(),
                any()
            )
        } returns listOf(Beer.mock()).toPagedList(page = 1, isCompleted = false)
        viewModel.dispatch(HomeAction.GetBeers)
        advanceUntilIdle()
        viewModel.dispatch(HomeAction.SetScrollPosition(position = 1))
        advanceUntilIdle()
        verify(exactly = 1) { viewModel.dispatch(HomeAction.GetBeersNextPage) }

    }

    @Test
    fun testGetBeers_success(): TestResult = runTest {
        val beers = listOf(Beer.mock()).toPagedList(page = 1, isCompleted = false)
        coEvery {
            getBeersUseCase(
                any(),
                any(),
                any(),
                any()
            )
        } returns beers
        viewModel.dispatch(HomeAction.GetBeers)
        advanceUntilIdle()
        viewModel.state.beers.shouldBeEqual(beers.toData())
    }

    @Test
    fun testGetBeers_failed(): TestResult = runTest {
        val error = BeerBoxException.Unknown()
        coEvery {
            getBeersUseCase(
                any(),
                any(),
                any(),
                any()
            )
        } throws error
        viewModel.dispatch(HomeAction.GetBeers)
        advanceUntilIdle()
        viewModel.state.beers.shouldBeEqual(LazyData.Empty)
        viewModel.state.firstPage.shouldBeEqual(error.toError())
    }

    @Test
    fun testGetBeersNextPage_success(): TestResult = runTest {

        val beersFirstPage = listOf(Beer.mock()).toPagedList(page = 1, isCompleted = false)
        val beersNextPage = listOf(Beer.mock()).toPagedList(page = 2, isCompleted = false)

        // Mock First Page
        coEvery {
            getBeersUseCase(
                any(),
                any(),
                any(),
                any()
            )
        } returns beersFirstPage
        viewModel.dispatch(HomeAction.GetBeers)
        advanceUntilIdle()

        // Mock Second Page
        coEvery {
            getBeersUseCase(
                any(),
                any(),
                any(),
                any()
            )
        } returns beersNextPage
        viewModel.dispatch(HomeAction.GetBeersNextPage)
        advanceUntilIdle()

        val expected = beersFirstPage.addPage(beersNextPage)
        viewModel.state.beers.dataOrNull()?.page?.shouldBeEqual(expected.page)
        viewModel.state.beers.dataOrNull()?.isCompleted?.shouldBeEqual(expected.isCompleted)
        viewModel.state.beers.dataOrNull()?.data?.shouldBeEqual(expected.data)
    }

    @Test
    fun testGetBeersNextPage_failed(): TestResult = runTest {

        val beersFirstPage = listOf(Beer.mock()).toPagedList(page = 1, isCompleted = false)
        val error = BeerBoxException.Unknown()

        // Mock First Page
        coEvery {
            getBeersUseCase(
                any(),
                any(),
                any(),
                any()
            )
        } returns beersFirstPage
        viewModel.dispatch(HomeAction.GetBeers)
        advanceUntilIdle()

        // Mock Second Page
        coEvery {
            getBeersUseCase(
                any(),
                any(),
                any(),
                any()
            )
        } throws error
        viewModel.dispatch(HomeAction.GetBeersNextPage)
        advanceUntilIdle()

        viewModel.state.beers.shouldBeEqual(error.toError(beersFirstPage))
        viewModel.state.firstPage.shouldBeEqual(LazyData.unit())
    }

    @Test
    fun testSetSearch(): TestResult = runTest {
        val search = "search"
        viewModel.dispatch(HomeAction.SetSearch(search))
        advanceUntilIdle()
        viewModel.state.search.shouldBeEqual(search)
    }

    @Test
    fun testToggleFilter(): TestResult = runTest {

        val filter = BeerFilter.Golden

        // Select
        viewModel.dispatch(HomeAction.ToggleFilter(filter))
        advanceUntilIdle()
        viewModel.state.selectedFilter.shouldBe(filter)

        // Unselect
        viewModel.dispatch(HomeAction.ToggleFilter(filter))
        advanceUntilIdle()
        viewModel.state.selectedFilter.shouldBe(expected = null)

    }

    @Test
    fun testClearFilters(): TestResult = runTest {

        viewModel.dispatch(HomeAction.SetSearch(value = "search"))
        advanceUntilIdle()
        viewModel.dispatch(HomeAction.ToggleFilter(BeerFilter.Black))
        advanceUntilIdle()
        viewModel.dispatch(HomeAction.ClearFilters)
        advanceUntilIdle()

        viewModel.state.search.shouldBeEqual(expected = "")
        viewModel.state.selectedFilter.shouldBe(expected = null)

    }

}