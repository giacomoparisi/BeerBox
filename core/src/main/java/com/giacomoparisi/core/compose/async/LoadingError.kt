package com.giacomoparisi.core.compose.async

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.giacomoparisi.domain.datatypes.LazyData
import com.giacomoparisi.domain.error.BeerBoxException

@Composable
fun <T> LoadingError(
    data: LazyData<T>,
    modifier: Modifier = Modifier,
    loading: Boolean = true,
    onRetryClicked: () -> Unit,
    composable: @Composable (T) -> Unit,
) {

    var error by remember { mutableStateOf<BeerBoxException?>(null) }
    LaunchedEffect(key1 = data) {
        when (data) {
            is LazyData.Error -> error = data.error
            else -> {}
        }
    }

    Box(modifier = modifier) {
        AnimatedVisibility(
            visible = data.currentOrPrevious() != null,
            enter = fadeIn(tween(durationMillis = 1000)),
            exit = fadeOut(tween(durationMillis = 1000))
        ) {
            val value = data.currentOrPrevious()
            if (value != null) composable(value)
        }
        AnimatedVisibility(
            visible = data.isLoading() && loading,
            enter = fadeIn(tween(durationMillis = 400)),
            exit = fadeOut(tween(durationMillis = 400))
        ) {
            Box(
                modifier =
                Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background.copy(alpha = 0.7f))
            ) {
                Loading(modifier = Modifier.align(Alignment.Center), size = 40.dp)
            }
        }
        AnimatedVisibility(
            visible = data.isError(),
            enter = fadeIn(tween(durationMillis = 400)),
            exit = fadeOut(tween(durationMillis = 400))
        ) {
            val lastError = error
            if (lastError != null)
                Error(
                    error = lastError,
                    onRetryClicked = onRetryClicked,
                    modifier = Modifier.fillMaxSize()
                )
        }
    }
}