package com.giacomoparisi.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.giacomoparisi.core.compose.preview.ScreenPreview
import com.giacomoparisi.core.compose.theme.BeerBoxTheme
import com.giacomoparisi.home.data.HomeAction
import com.giacomoparisi.home.data.HomeState
import com.giacomoparisi.home.data.HomeViewModel
import com.giacomoparisi.home.ui.beer.BeerItem

@Composable
fun HomeScreen(viewModel: HomeViewModel) {

    val state by viewModel.stateFlow.collectAsState()

    HomeScreen(
        state = state,
        onScrollPositionChanged = { viewModel.dispatch(HomeAction.SetScrollPosition(it)) }
    )

}

@Composable
fun HomeScreen(
    state: HomeState,
    onScrollPositionChanged: (Int) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier =
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        HeaderLogo()
        // Beer List
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            contentPadding = PaddingValues(20.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(state.beers.dataOrNull()?.data ?: emptyList()) { index, item ->
                onScrollPositionChanged(index)
                // Beer Item
                BeerItem(beer = item)
            }
        }
    }
}

@Composable
private fun HeaderLogo() {
    Text(
        text =
        buildAnnotatedString {
            withStyle(SpanStyle(fontWeight = FontWeight.Normal)) { append("Beer") }
            append(" ")
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("Box") }
        },
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.padding(20.dp)
    )
}

@ScreenPreview
@Composable
fun HomeScreenPreview() {
    BeerBoxTheme {
        HomeScreen(state = HomeState.mock(), onScrollPositionChanged = {})
    }
}