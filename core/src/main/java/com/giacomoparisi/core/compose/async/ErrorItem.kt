package com.giacomoparisi.core.compose.async

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.giacomoparisi.core.compose.theme.BeerBoxTheme
import com.giacomoparisi.domain.error.BeerBoxException

@Composable
fun ErrorItem(
    error: BeerBoxException,
    onRetryClicked: () -> Unit,
) {


    Column(
        verticalArrangement = Arrangement.Center,
        modifier =
        Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clickable { onRetryClicked() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = getErrorMessage(exception = error),
            color = MaterialTheme.colorScheme.onSecondary,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(id = R.string.COMMON_error_retry),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )

    }
}

@Preview
@Composable
private fun ErrorItemPreview() {
    BeerBoxTheme {
        ErrorItem(
            error = BeerBoxException.Unknown(),
            onRetryClicked = {}
        )
    }
}