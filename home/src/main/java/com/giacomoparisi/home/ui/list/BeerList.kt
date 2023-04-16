package com.giacomoparisi.home.ui.list

import BeerItem
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giacomoparisi.core.compose.async.ErrorItem
import com.giacomoparisi.core.compose.async.LoadingItem
import com.giacomoparisi.core.compose.theme.BeerBoxTheme
import com.giacomoparisi.domain.datatypes.LazyData
import com.giacomoparisi.domain.datatypes.PagedList
import com.giacomoparisi.entities.beer.Beer
import com.giacomoparisi.home.data.HomeState

@Composable
fun BeerList(
    beers: LazyData<PagedList<Beer>>,
    onScrollPositionChanged: (Int) -> Unit,
    onItemClicked: (Beer) -> Unit,
    onPageRetry: () -> Unit
) {

    val items = beers.currentOrPrevious()
    val page = items?.page
    val listState = rememberLazyListState()

    // Scroll up to the beginning of the list when the paginated list was
    // refreshed by a new search or filters
    LaunchedEffect(key1 = page) {
        if (page == 1) listState.scrollToItem(index = 0)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedVisibility(
            visible = items != null && items.data.isNotEmpty(),
            enter = fadeIn(tween(durationMillis = 1000)),
            exit = fadeOut(tween(durationMillis = 1000))
        ) {
            LazyColumn(
                state = listState,
                verticalArrangement = Arrangement.spacedBy(20.dp),
                contentPadding = PaddingValues(20.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                if (items != null)
                    itemsIndexed(items.data) { index, item ->
                        onScrollPositionChanged(index)
                        // Beer Item
                        BeerItem(beer = item, onItemClicked = onItemClicked)
                    }

                when (beers) {
                    is LazyData.Loading -> item { LoadingItem() }
                    is LazyData.Error -> item {
                        ErrorItem(
                            error = beers.error,
                            onRetryClicked = onPageRetry
                        )
                    }

                    else -> Unit
                }
            }
        }

        // Show empty list UI
        val loadedItems = beers.dataOrNull()
        AnimatedVisibility(
            visible = loadedItems != null && loadedItems.data.isEmpty(),
            enter = fadeIn(tween(durationMillis = 1000)),
            exit = fadeOut(tween(durationMillis = 1000))
        ) {
            BeerEmptyList()
        }

    }
}

@Preview
@Composable
private fun BeerListPreview() {
    BeerBoxTheme {
        BeerList(
            beers = HomeState.mock().beers,
            onScrollPositionChanged = {},
            onItemClicked = {},
            onPageRetry = {}
        )
    }
}