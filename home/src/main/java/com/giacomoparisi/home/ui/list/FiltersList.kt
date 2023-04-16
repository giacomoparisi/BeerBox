package com.giacomoparisi.home.ui.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giacomoparisi.core.compose.theme.BeerBoxTheme
import com.giacomoparisi.entities.beer.BeerFilter
import com.giacomoparisi.home.data.HomeState
import com.giacomoparisi.home.ui.filters.FilterItem

@Composable
fun FiltersList(selectedFilter: BeerFilter?, onFilterSelected: (BeerFilter) -> Unit) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(HomeState.filters) {
            FilterItem(
                filter = it,
                isSelected = it == selectedFilter,
                onClick = onFilterSelected
            )
        }
    }
}

@Composable
private fun FilterListPreview(selectedFilter: BeerFilter?) {
    BeerBoxTheme {
        FiltersList(selectedFilter = selectedFilter, onFilterSelected = {})
    }
}

@Preview
@Composable
private fun FilterListPreview() {
    FilterListPreview(selectedFilter = null)
}

@Preview
@Composable
private fun FilterListSelectedPreview() {
    FilterListPreview(selectedFilter = BeerFilter.Golden)
}