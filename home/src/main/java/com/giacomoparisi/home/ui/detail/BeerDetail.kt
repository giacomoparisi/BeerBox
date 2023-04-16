package com.giacomoparisi.home.ui.detail

import BeerImage
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giacomoparisi.core.compose.theme.BeerBoxTheme
import com.giacomoparisi.entities.beer.Beer
import com.giacomoparisi.home.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BeerDetail(
    beer: Beer,
    sheetState: SheetState,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        containerColor = MaterialTheme.colorScheme.secondary,
        sheetState = sheetState,
        content = {
            BeerDetail(beer = beer)
        },
        onDismissRequest = { onDismiss() },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun BeerDetail(beer: Beer) {
    Row(
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp, start = 20.dp, end = 20.dp)
    ) {
        BeerImage(
            beer = beer,
            contentScale = ContentScale.Fit,
            modifier =
            Modifier
                .height(200.dp)
                .width(80.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        BeerInfo(beer)
        Spacer(modifier = Modifier.width(5.dp))
    }
}

@Composable
private fun RowScope.BeerInfo(beer: Beer) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.weight(1f)) {
                // Name
                Text(
                    text = beer.name,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onBackground,
                )
                Spacer(modifier = Modifier.height(5.dp))
                // Short Description
                Text(
                    text = beer.shortDescription,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSecondary,
                )
            }
            // Badge Icon
            Image(
                painter = painterResource(id = R.drawable.detail_badge),
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                modifier = Modifier.size(30.dp)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        // Full Description
        // Added max height (50% of the screen) with scroll
        // enabled for very long description text
        val screenHeight = LocalConfiguration.current.screenHeightDp
        Text(
            text = beer.description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSecondary,
            modifier =
            Modifier
                .heightIn(max = (screenHeight * 0.5f).dp)
                .verticalScroll(rememberScrollState())
        )
    }
}

@Preview
@Composable
private fun BeerDetailPreview() {
    BeerBoxTheme {
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier.height(300.dp)
        ) {
            BeerDetail(beer = Beer.mock())
        }
    }
}