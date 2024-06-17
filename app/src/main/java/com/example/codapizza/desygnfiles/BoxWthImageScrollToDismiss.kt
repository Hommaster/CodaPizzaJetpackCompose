package com.example.codapizza.desygnfiles

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.codapizza.R

@Composable
fun BoxWithImageScrollToDismiss(
    colorFilter: Int
) {
    Box(
        modifier = Modifier
            .padding(10.dp, 25.dp, 0.dp, 0.dp)
            .background(Color.Transparent)
    ) {
        Image(
            modifier = Modifier
                .size(55.dp),
            colorFilter = ColorFilter.tint(color = colorResource(id = colorFilter)),
            painter = painterResource(id = R.drawable.image_scroll_to_dismiss),
            contentDescription = "image_scroll_to_dismiss"
        )
    }
}