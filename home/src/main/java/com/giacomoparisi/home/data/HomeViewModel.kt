package com.giacomoparisi.home.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giacomoparisi.core.ext.isTerminated
import com.giacomoparisi.data.error.ErrorMapper
import com.giacomoparisi.data.error.toError
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

                // TODO: loading
                val beers = getBeers(page = 1)
                emit { it.copy(beers = beers.toData()) }
            },
            { throwable ->
                emit { it.copy(beers = throwable.toError(errorMapper)) }
            }
        )

    private fun getBeersNextPage(position: Int): Action =
        action(
            {
                val currentBeers = state.beers.currentOrPrevious()
                if (currentBeers != null && needNextPage(scrollPosition = position)) {
                    // TODO: loading
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
        val beers = getBeersUseCase(page, pageSize, name = state.search)
        return if (beers.page == 1) beers
        else state.beers.currentOrPrevious()?.addPage(beers) ?: PagedList.empty()
    }

    /* --- search --- */

    private suspend fun search(value: String) {
        emit { it.copy(search = value) }
    }


    /* --- actions --- */

    fun dispatch(action: HomeAction) {
        when (action) {
            HomeAction.GetBeers ->
                viewModelScope.launchAction(getBeersFirstPage())

            is HomeAction.SetScrollPosition ->
                viewModelScope.launchAction(getBeersNextPage(action.position))

            is HomeAction.SetSearch ->
                viewModelScope.launchSafe { search(action.value) }

            HomeAction.Search ->
                viewModelScope.launchAction(getBeersFirstPage())
        }
    }

}