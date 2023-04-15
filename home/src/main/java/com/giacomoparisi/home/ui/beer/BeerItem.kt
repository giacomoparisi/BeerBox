package com.giacomoparisi.home.ui.beer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.giacomoparisi.core.compose.async.Loading
import com.giacomoparisi.core.compose.theme.BeerBoxTheme
import com.giacomoparisi.entities.beer.Beer
import com.giacomoparisi.entities.mock.Mock
import com.giacomoparisi.home.R

@Composable
fun BeerItem(beer: Beer) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier =
            Modifier
                .fillMaxWidth()
                .height(120.dp)
        ) {
            BeerImage(beer)
            Spacer(modifier = Modifier.width(20.dp))
            BeerInfo(beer)
        }
        Spacer(modifier = Modifier.height(20.dp))
        Divider(
            color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.2f),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun BeerImage(beer: Beer) {
    SubcomposeAsyncImage(
        model =
        ImageRequest.Builder(LocalContext.current)
            .data(beer.imageUrl)
            .crossfade(enable = true)
            .build(),
        contentDescription = null,
        contentScale = ContentScale.FillHeight,
        error = {
            Image(
                painter = painterResource(id = R.drawable.beer_placeholder),
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier.fillMaxSize()
            )
        },
        loading = {
            Box(Modifier.fillMaxSize()) {
                Loading(
                    size = 20.dp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        },
        modifier =
        Modifier
            .fillMaxHeight()
            .width(50.dp)
    )
}

@Composable
private fun BeerInfo(beer: Beer) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = beer.name,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = beer.shortDescription,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSecondary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = beer.description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSecondary,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = stringResource(id = R.string.HOME_beer_info),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

@Composable
private fun BeerItemPreview(
    name: String,
    shortDescription: String,
    description: String
) {
    BeerBoxTheme {
        Box(Modifier.background(MaterialTheme.colorScheme.background)) {
            BeerItem(
                beer =
                Beer.mock()
                    .copy(
                        name = name,
                        shortDescription = shortDescription,
                        description = description
                    )
            )
        }
    }
}

@Preview
@Composable
private fun BeerItemShortTextsPreview() {
    BeerItemPreview(
        name = Mock.name,
        shortDescription = Mock.body,
        description = Mock.body
    )
}

@Preview
@Composable
private fun BeerItemLongTextsPreview() {
    BeerItemPreview(
        name = Mock.nameLong,
        shortDescription = Mock.bodyLong,
        description = Mock.bodyLong
    )
}