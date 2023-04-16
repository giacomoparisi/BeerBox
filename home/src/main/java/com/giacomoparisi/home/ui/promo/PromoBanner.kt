package com.giacomoparisi.home.ui.promo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giacomoparisi.core.compose.theme.BeerBoxTheme
import com.giacomoparisi.home.R

@Composable
fun PromoBanner() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier =
        Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary, MaterialTheme.shapes.medium)
            .padding(horizontal = 20.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = stringResource(id = R.string.HOME_promo_title),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary,
            )
            Text(
                text = stringResource(id = R.string.HOME_promo_description),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Image(
            painter = painterResource(id = R.drawable.promo),
            contentDescription = null,
            modifier = Modifier.size(70.dp)
        )
    }
}

@Preview
@Composable
private fun PromoBannerPreview() {
    BeerBoxTheme {
        PromoBanner()
    }
}