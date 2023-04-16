package com.giacomoparisi.home.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giacomoparisi.core.ext.isTerminated
import com.giacomoparisi.data.error.ErrorMapper
import com.giacomoparisi.data.error.toError
import com.giacomoparisi.domain.datatypes.LazyData
import com.giacomoparisi.domain.datatypes.PagedList
import com.giacomoparisi.domain.datatypes.addPage
import com.giacomoparisi.domain.datatypes.toData
import com.giacomoparisi.domain.ext.Action
import com.giacomoparisi.domain.ext.action
import com.giacomoparisi.domain.ext.catchToNullSuspend
import com.giacomoparisi.domain.ext.launchAction
import com.giacomoparisi.domain.ext.launchSafe
import com.giacomoparisi.domain.usecases.beer.GetBeersUseCase
import com.giacomoparisi.entities.beer.Beer
import com.giacomoparisi.entities.beer.BeerFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getBeersUseCase: GetBeersUseCase,
    private val errorMapper: ErrorMapper,
    private val mutex: Mutex
) : ViewModel() {

    /* --- settings --- */

    private val pageSize = 25
    private var beersJob: Job? = null

    /* --- state --- */

    // Handle screen state with StateFlow for Jetpack Compose UI
    private val mutableStateFlow = MutableStateFlow(HomeState())
    val stateFlow = mutableStateFlow.asStateFlow()
    private val state get() = mutableStateFlow.value

    private suspend fun emit(update: suspend (HomeState) -> HomeState) {
        mutex.withLock { mutableStateFlow.emit(update(mutableStateFlow.value)) }
    }

    /* --- init --- */

    init {
        dispatch(HomeAction.GetBeers)
    }

    /* --- beers --- */

    private fun getBeersFirstPage(): Action =
        action(
            {
                // New first page requested, abort other pages fetching in progress if exist
                catchToNullSuspend { beersJob?.cancel() }
                beersJob = null

                emit { it.copy(firstPage = LazyData.Loading()) }
                val beers = getBeers(page = 1)
                emit { it.copy(beers = beers.toData(), firstPage = LazyData.unit()) }
            },
            { throwable ->
                emit { it.copy(firstPage = throwable.toError(errorMapper)) }
            }
        )

    private fun getBeersNextPage(): Action =
        action(
            {
                val currentBeers = state.beers.currentOrPrevious()
                if (currentBeers != null) {
                    emit { it.copy(beers = it.beers.toLoading()) }
                    val beers = getBeers(page = currentBeers.page + 1)
                    emit { it.copy(beers = beers.toData()) }
                }
            },
            { throwable ->
                emit { it.copy(beers = it.beers.toError(errorMapper, throwable)) }
            }
        )

    private fun needNextPage(scrollPosition: Int): Boolean {

        val beers = state.beers.dataOrNull()

        return beersJob.isTerminated &&
                beers != null &&
                beers.isCompleted.not() &&
                (scrollPosition + 1) >= beers.size
    }

    private suspend fun getBeers(page: Int): PagedList<Beer> {
        val beers =
            getBeersUseCase(
                page,
                pageSize,
                name = state.search,
                filter = state.selectedFilter
            )
        return if (beers.page == 1) beers
        else state.beers.currentOrPrevious()?.addPage(beers) ?: PagedList.empty()
    }

    /* --- search --- */

    private suspend fun search(value: String) {
        emit { it.copy(search = value) }
    }

    private suspend fun toggleFilter(filter: BeerFilter) {
        emit { it.copy(selectedFilter = if (it.selectedFilter == filter) null else filter) }
        dispatch(HomeAction.GetBeers)
    }

    private suspend fun clearFilters() {
        emit { it.copy(selectedFilter = null, search = "") }
        dispatch(HomeAction.GetBeers)
    }

    /* --- detail --- */

    private suspend fun selectBeer(beer: Beer?) {
        emit { it.copy(selectedBeer = beer) }
    }

    /* --- pagination --- */

    private fun setScrollPosition(position: Int) {
        if (needNextPage(scrollPosition = position)) dispatch(HomeAction.GetBeersNextPage)
    }


    /* --- actions --- */

    // All action available for Home screen
    fun dispatch(action: HomeAction) {
        when (action) {
            HomeAction.GetBeers ->
                viewModelScope.launchAction(getBeersFirstPage())

            HomeAction.GetBeersNextPage ->
                viewModelScope.launchAction(getBeersNextPage())

            is HomeAction.SetScrollPosition ->
                viewModelScope.launchSafe { setScrollPosition(action.position) }

            is HomeAction.SetSearch ->
                viewModelScope.launchSafe { search(action.value) }

            HomeAction.Search ->
                viewModelScope.launchAction(getBeersFirstPage())

            is HomeAction.SelectBeer ->
                viewModelScope.launchSafe { selectBeer(action.beer) }

            is HomeAction.ToggleFilter ->
                viewModelScope.launchSafe { toggleFilter(action.filter) }

            HomeAction.ClearFilters ->
                viewModelScope.launchSafe { clearFilters() }
        }
    }

}