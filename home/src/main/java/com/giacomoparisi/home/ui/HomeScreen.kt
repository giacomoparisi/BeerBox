package com.giacomoparisi.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.giacomoparisi.core.compose.async.LoadingError
import com.giacomoparisi.core.compose.preview.ScreenPreview
import com.giacomoparisi.core.compose.theme.BeerBoxTheme
import com.giacomoparisi.domain.datatypes.unwrapEvent
import com.giacomoparisi.domain.ext.launchSafe
import com.giacomoparisi.entities.beer.Beer
import com.giacomoparisi.home.data.HomeAction
import com.giacomoparisi.home.data.HomeEvent
import com.giacomoparisi.home.data.HomeState
import com.giacomoparisi.home.data.HomeViewModel
import com.giacomoparisi.home.ui.detail.BeerDetail
import com.giacomoparisi.home.ui.header.HeaderLogo
import com.giacomoparisi.home.ui.list.BeerList
import com.giacomoparisi.home.ui.search.SearchBar
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel) {

    val state by viewModel.stateFlow.collectAsState()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    // Collect on-time UI events emitted from viewModel
    LaunchedEffect(key1 = viewModel) {
        viewModel.eventsFlow
            .unwrapEvent(handlerId = "home")
            .onEach {
                when (it) {
                    HomeEvent.OpenDetailSheet ->
                        // call modal sheet show method on a new coroutine to
                        // avoid interrupting events collecting
                        scope.launchSafe { sheetState.show() }
                }
            }
            .collect()
    }

    HomeScreen(
        state = state,
        sheetState = sheetState,
        onScrollPositionChanged = { viewModel.dispatch(HomeAction.SetScrollPosition(it)) },
        onSearchTextChanged = { viewModel.dispatch(HomeAction.SetSearch(it)) },
        onSearchClicked = { viewModel.dispatch(HomeAction.Search) },
        onBeerClicked = { viewModel.dispatch(HomeAction.SelectBeer(it)) },
        onDetailClosed = { viewModel.dispatch(HomeAction.SelectBeer(beer = null)) },
        onBeersFirstPageRetry = { viewModel.dispatch(HomeAction.GetBeers) }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: HomeState,
    sheetState: SheetState,
    onScrollPositionChanged: (Int) -> Unit,
    onSearchTextChanged: (String) -> Unit,
    onSearchClicked: () -> Unit,
    onBeerClicked: (Beer) -> Unit,
    onDetailClosed: () -> Unit,
    onBeersFirstPageRetry: () -> Unit
) {
    Box(
        modifier =
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            HeaderLogo()
            // SearchBar
            Box(modifier = Modifier.padding(20.dp)) {
                SearchBar(
                    value = state.search,
                    onValueChange = onSearchTextChanged,
                    onSearchClicked = onSearchClicked
                )
            }
            // Beer List
            LoadingError(
                data = state.firstPage,
                onRetryClicked = onBeersFirstPageRetry
            ) {
                BeerList(
                    beers = state.beers,
                    onScrollPositionChanged = onScrollPositionChanged,
                    onItemClicked = onBeerClicked
                )
            }
        }

        if (state.selectedBeer != null)
            BeerDetail(
                beer = state.selectedBeer,
                sheetState = sheetState,
                onDismiss = onDetailClosed
            )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@ScreenPreview
@Composable
fun HomeScreenPreview() {
    BeerBoxTheme {
        HomeScreen(
            state = HomeState.mock(),
            sheetState = rememberModalBottomSheetState(),
            onScrollPositionChanged = {},
            onSearchTextChanged = {},
            onSearchClicked = {},
            onBeerClicked = {},
            onDetailClosed = {},
            onBeersFirstPageRetry = {}
        )
    }
}