package com.giacomoparisi.home.ui.header

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giacomoparisi.core.compose.theme.BeerBoxTheme

@Composable
fun HeaderLogo() {
    Text(
        text =
        buildAnnotatedString {
            withStyle(SpanStyle(fontWeight = FontWeight.Normal)) { append("Beer") }
            append(" ")
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("Box") }
        },
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.padding(20.dp)
    )
}

@Preview
@Composable
private fun HeaderLogoPreview() {
    BeerBoxTheme {
        HeaderLogo()
    }
}