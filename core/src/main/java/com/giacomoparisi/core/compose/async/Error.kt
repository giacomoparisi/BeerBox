package com.giacomoparisi.core.compose.async

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giacomoparisi.core.R
import com.giacomoparisi.core.compose.ext.noRippleClickable
import com.giacomoparisi.core.compose.theme.BeerBoxTheme
import com.giacomoparisi.domain.error.BeerBoxException

@Composable
fun Error(
    error: BeerBoxException,
    onRetryClicked: () -> Unit,
    modifier: Modifier
) {

    Box(
        modifier =
        modifier
            .noRippleClickable { }
            .padding(20.dp)
    ) {
        Column(
            modifier =
            Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .background(
                    MaterialTheme.colorScheme.secondary,
                    MaterialTheme.shapes.medium
                )
                .padding(horizontal = 20.dp, vertical = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.COMMON_error_title),
                color = MaterialTheme.colorScheme.onSecondary,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = getErrorMessage(exception = error),
                color = MaterialTheme.colorScheme.onSecondary,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = onRetryClicked,
                modifier = Modifier.width(200.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.COMMON_error_retry),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Preview
@Composable
private fun ErrorPreview() {
    BeerBoxTheme {
        Error(
            error = BeerBoxException.Unknown(),
            modifier = Modifier.fillMaxSize(),
            onRetryClicked = {}
        )
    }
}