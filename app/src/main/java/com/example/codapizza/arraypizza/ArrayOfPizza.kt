package com.example.codapizza.arraypizza

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.codapizza.model.Pizzas

@Composable
fun ArrayOfPizza(navController: NavHostController) {
    Column {
        TopAppBar(
            title = {
                Text(text = "Cool Pizza with fucking LOVE",
                    color = Color.Blue)
            },
            windowInsets = WindowInsets(0, 60, 0 ,0),
        )
        LazyColumn {
            items(Pizzas.entries.toTypedArray()) { pizza ->
                val pizzaName: String = stringResource(id = pizza.pizzaName)
                BoxOfPizza(
                    pizza = pizza,
                    onClick = {
                        navController.navigate("screen_2/$pizzaName")
                    }
                )
            }
        }
    }
}

@Composable
fun BoxOfPizza(
    pizza: Pizzas,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(Color.LightGray)
            .clip(RoundedCornerShape(13.dp))
            .border(2.dp, Color.Green, RoundedCornerShape(13.dp))
            .clickable { onClick() }
    ){
        Image(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(250.dp),
            painter = painterResource(id = pizza.pizzaImage),
            contentDescription = stringResource(id = pizza.pizzaName)
        )
        Row(
            modifier = Modifier
                .fillMaxSize()
                .clickable { onClick() }
        ){
            Column {
                Text(
                    text = stringResource(id = pizza.pizzaName),
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.body1
                )
                Text(
                    text = stringResource(id = pizza.pizzaIngredients),
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
}