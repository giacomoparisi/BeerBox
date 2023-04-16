package com.giacomoparisi.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.giacomoparisi.core.compose.preview.ScreenPreview
import com.giacomoparisi.core.compose.theme.BeerBoxTheme
import com.giacomoparisi.home.data.HomeAction
import com.giacomoparisi.home.data.HomeState
import com.giacomoparisi.home.data.HomeViewModel
import com.giacomoparisi.home.ui.header.HeaderLogo
import com.giacomoparisi.home.ui.list.BeerList
import com.giacomoparisi.home.ui.search.SearchBar

@Composable
fun HomeScreen(viewModel: HomeViewModel) {

    val state by viewModel.stateFlow.collectAsState()

    HomeScreen(
        state = state,
        onScrollPositionChanged = { viewModel.dispatch(HomeAction.SetScrollPosition(it)) },
        onSearchTextChanged = { viewModel.dispatch(HomeAction.SetSearch(it)) },
        onSearchClicked = { viewModel.dispatch(HomeAction.Search) }
    )

}

@Composable
fun HomeScreen(
    state: HomeState,
    onScrollPositionChanged: (Int) -> Unit,
    onSearchTextChanged: (String) -> Unit,
    onSearchClicked: () -> Unit
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier =
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
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
        BeerList(beers = state.beers, onScrollPositionChanged = onScrollPositionChanged)
    }
}


@ScreenPreview
@Composable
fun HomeScreenPreview() {
    BeerBoxTheme {
        HomeScreen(
            state = HomeState.mock(),
            onScrollPositionChanged = {},
            onSearchTextChanged = {},
            onSearchClicked = {}
        )
    }
}