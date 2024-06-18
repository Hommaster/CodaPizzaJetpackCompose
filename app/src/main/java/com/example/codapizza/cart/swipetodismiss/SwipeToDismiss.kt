package com.example.codapizza.cart.swipetodismiss

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController
import com.example.codapizza.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeToDismiss(
    navController: NavController,
    content: @Composable () -> Unit
) {
    val dismissState = rememberSwipeToDismissBoxState()
    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromEndToStart = false,
        backgroundContent = {
            @Suppress("UNUSED_EXPRESSION")
            when(dismissState.targetValue){
                SwipeToDismissBoxValue.StartToEnd -> {
                    Box(
                        contentAlignment = Alignment.CenterEnd,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(colorResource(id = R.color.orange))
                    ) {}
                    if(dismissState.progress.toDouble() >= 0.5) {
                        navController.popBackStack("screen_1", false)
                    }
                }
                else -> null
            }
        }
    ) {
        content()
    }
}