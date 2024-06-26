package com.example.codapizza.desygnfiles

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.codapizza.R

@Composable
fun InfoForTopAppBar(
    textID: Int
) {
    BoxWithImageScrollToDismiss(
        modifier = Modifier,
        colorFilter = R.color.black
    )
    Box(
        modifier = Modifier
            .padding(0.dp, 15.dp, 0.dp, 0.dp)
            .size(350.dp, 50.dp)
            .background(Color.Transparent),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(0.dp, 0.dp, 0.dp, 10.dp),
            text = stringResource(id = textID),
            fontSize = 25.sp
        )
    }
}