package com.example.codapizza.cart

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.codapizza.R

@Composable
fun EmptyCartUI(
    navController: NavController
){
    Log.d("INFOaboutScreen", "screen emptyinfo")
    Column(){
        Text(
            text = stringResource(id = R.string.cart_is_empty)
        )
        TextButton(onClick = {
            navController.navigate("screen_1")
        }) {
            Text(text = stringResource(id = R.string.back_to_menu))
        }
    }
}