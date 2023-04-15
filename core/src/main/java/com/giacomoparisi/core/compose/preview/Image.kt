package com.giacomoparisi.core.compose.preview

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource

@Composable
fun previewOnlyPainter(@DrawableRes res: Int): Painter? =
    if (LocalInspectionMode.current) {
        painterResource(id = res)
    } else {
        null
    }