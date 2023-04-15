package com.giacomoparisi.core.compose.preview

import androidx.compose.ui.tooling.preview.Preview
import com.giacomoparisi.core.compose.preview.PreviewDevice

@Preview(
    name = "1-Nexus One",
    device = PreviewDevice.NEXUS_ONE,
    showBackground = true,
    backgroundColor = 0xff243598
)
@Preview(
    name = "2-Nexus 5",
    device = PreviewDevice.NEXUS_5,
    showBackground = true,
    backgroundColor = 0xff243598
)
@Preview(
    name = "3-Pixel 4",
    device = PreviewDevice.PIXEL_4,
    showBackground = true,
    backgroundColor = 0xff243598
)
@Preview(
    name = "4-Pixel 6 PRO",
    device = PreviewDevice.PIXEL_6_PRO,
    showBackground = true,
    backgroundColor = 0xff243598
)
annotation class ScreenPreview