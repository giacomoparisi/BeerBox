package com.giacomoparisi.home.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giacomoparisi.core.compose.theme.BeerBoxTheme
import com.giacomoparisi.domain.ext.launchSafe
import com.giacomoparisi.entities.mock.Mock
import com.giacomoparisi.home.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    onSearchClicked: () -> Unit
) {

    val softKeyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val scope = rememberCoroutineScope()

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier =
        Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(MaterialTheme.colorScheme.secondary, RoundedCornerShape(10.dp))
            .clickable { scope.launchSafe { focusRequester.requestFocus() } }
            .padding(10.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.search),
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSecondary),
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            maxLines = 1,
            singleLine = true,
            keyboardOptions =
            KeyboardOptions(
                autoCorrect = false,
                imeAction = ImeAction.Search
            ),
            keyboardActions =
            KeyboardActions(
                onSearch = {
                    softKeyboardController?.hide()
                    onSearchClicked()
                }
            ),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.onSecondary),
            decorationBox = {
                if (value.isEmpty())
                    Text(
                        text = stringResource(id = R.string.HOME_search_hint),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondary,
                        modifier = Modifier.fillMaxWidth()
                    )
                it()
            },
            textStyle =
            MaterialTheme
                .typography
                .bodyMedium
                .copy(color = MaterialTheme.colorScheme.onSecondary),
            modifier =
            Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
        )
    }
}

@Composable
fun SearchBarPreview(text: String) {
    BeerBoxTheme {
        SearchBar(value = text, onValueChange = {}, onSearchClicked = {})
    }
}

@Preview
@Composable
fun SearchBarTextPreview() {
    SearchBarPreview(Mock.name)
}

@Preview
@Composable
fun SearchBarPreview() {
    SearchBarPreview(text = "")
}