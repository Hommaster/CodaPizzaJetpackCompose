package com.example.codapizza.desygnfiles

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.example.codapizza.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarCustom(
    title: @Composable () -> Unit
) {
    TopAppBar(
        modifier = Modifier
            .fillMaxHeight(),
        colors = TopAppBarColors(
            containerColor = colorResource(id = R.color.orange),
            scrolledContainerColor = colorResource(id = R.color.orange),
            navigationIconContentColor = colorResource(id = R.color.black),
            titleContentColor = colorResource(id = R.color.black),
            actionIconContentColor = colorResource(id = R.color.orange)
        ),
        title = {
            title()
        }
    )
}