package com.giacomoparisi.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.giacomoparisi.core.compose.async.LoadingError
import com.giacomoparisi.core.compose.preview.ScreenPreview
import com.giacomoparisi.core.compose.theme.BeerBoxTheme
import com.giacomoparisi.entities.beer.Beer
import com.giacomoparisi.entities.beer.BeerFilter
import com.giacomoparisi.home.data.HomeAction
import com.giacomoparisi.home.data.HomeState
import com.giacomoparisi.home.data.HomeViewModel
import com.giacomoparisi.home.ui.detail.BeerDetail
import com.giacomoparisi.home.ui.header.HeaderLogo
import com.giacomoparisi.home.ui.list.BeerList
import com.giacomoparisi.home.ui.list.FiltersList
import com.giacomoparisi.home.ui.promo.PromoBanner
import com.giacomoparisi.home.ui.search.SearchBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel) {

    val state by viewModel.stateFlow.collectAsState()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    HomeScreen(
        state = state,
        sheetState = sheetState,
        onScrollPositionChanged = { viewModel.dispatch(HomeAction.SetScrollPosition(it)) },
        onSearchTextChanged = { viewModel.dispatch(HomeAction.SetSearch(it)) },
        onSearchClicked = { viewModel.dispatch(HomeAction.Search) },
        onBeerClicked = { viewModel.dispatch(HomeAction.SelectBeer(it)) },
        onDetailClosed = { viewModel.dispatch(HomeAction.SelectBeer(beer = null)) },
        onBeersFirstPageRetry = { viewModel.dispatch(HomeAction.GetBeers) },
        onBeersPageRetry = { viewModel.dispatch(HomeAction.GetBeersNextPage) },
        onFilterSelected = { viewModel.dispatch(HomeAction.ToggleFilter(it)) }
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
    onBeersFirstPageRetry: () -> Unit,
    onBeersPageRetry: () -> Unit,
    onFilterSelected: (BeerFilter) -> Unit
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
            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                // SearchBar
                SearchBar(
                    value = state.search,
                    onValueChange = onSearchTextChanged,
                    onSearchClicked = onSearchClicked
                )
                Spacer(modifier = Modifier.height(20.dp))
                // Promo
                PromoBanner()
                Spacer(modifier = Modifier.height(20.dp))
                FiltersList(
                    selectedFilter = state.selectedFilter,
                    onFilterSelected = onFilterSelected
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
            Divider(
                color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.2f),
                modifier = Modifier.fillMaxWidth()
            )
            // Beer List
            LoadingError(
                data = state.firstPage,
                onRetryClicked = onBeersFirstPageRetry
            ) {
                BeerList(
                    beers = state.beers,
                    onScrollPositionChanged = onScrollPositionChanged,
                    onItemClicked = onBeerClicked,
                    onPageRetry = onBeersPageRetry
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
            onBeersFirstPageRetry = {},
            onBeersPageRetry = {},
            onFilterSelected = {}
        )
    }
}