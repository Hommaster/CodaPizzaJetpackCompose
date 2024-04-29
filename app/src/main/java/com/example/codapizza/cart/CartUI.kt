package com.example.codapizza.cart

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun CartUI(navController: NavHostController) {
    Column(
        modifier = Modifier
            .padding(20.dp, 100.dp)
    ) {
        Text(
            text = "Корзина пуста"
        )
        Text(
            text = "И находится в технической доработке"
        )
        TextButton(onClick = { 
            navController.navigate("screen_1")
        }) {
            Text(text = "Вернуться в меню")
        }
    }
}