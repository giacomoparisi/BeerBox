package com.giacomoparisi.home.ui.filters

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giacomoparisi.core.compose.theme.BeerBoxTheme
import com.giacomoparisi.entities.beer.BeerFilter
import com.giacomoparisi.home.R

@Composable
fun FilterItem(filter: BeerFilter, isSelected: Boolean, onClick: (BeerFilter) -> Unit) {
    Text(
        text =
        stringResource(
            id =
            when (filter) {
                BeerFilter.Amber -> R.string.HOME_filter_amber
                BeerFilter.Black -> R.string.HOME_filter_black
                BeerFilter.Brown -> R.string.HOME_filter_brown
                BeerFilter.Golden -> R.string.HOME_filter_golden
            }
        ),
        maxLines = 1,
        style = MaterialTheme.typography.titleMedium,
        color =
        if (isSelected) MaterialTheme.colorScheme.onPrimary
        else MaterialTheme.colorScheme.onSecondary,
        modifier =
        Modifier
            .background(
                if (isSelected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.secondary,
                MaterialTheme.shapes.medium
            )
            .clickable { onClick(filter) }
            .padding(horizontal = 20.dp, vertical = 5.dp)
    )
}

@Composable
private fun FilterItemPreview(isSelected: Boolean) {
    BeerBoxTheme {
        FilterItem(
            filter = BeerFilter.Golden,
            isSelected = isSelected,
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun FilterItemPreview() {
    FilterItemPreview(isSelected = false)
}

@Preview
@Composable
private fun FilterItemSelectedPreview() {
    FilterItemPreview(isSelected = true)
}