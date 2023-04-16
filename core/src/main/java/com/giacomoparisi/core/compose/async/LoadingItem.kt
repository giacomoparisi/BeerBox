package com.giacomoparisi.core.compose.async

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giacomoparisi.core.compose.theme.BeerBoxTheme

@Composable
fun LoadingItem() {
    Box(
        modifier =
        Modifier
            .fillMaxWidth()
            .height(120.dp)
    ) {
        Loading(size = 30.dp, modifier = Modifier.align(Alignment.Center))
    }
}

@Preview
@Composable
private fun LoadingItemPreview() {
    BeerBoxTheme {
        LoadingItem()
    }
}