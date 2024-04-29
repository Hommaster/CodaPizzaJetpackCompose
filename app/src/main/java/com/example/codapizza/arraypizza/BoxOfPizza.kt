package com.example.codapizza.arraypizza

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.example.codapizza.model.Pizzas

@Composable
fun BoxOfPizza(
    pizza: Pizzas,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(4.dp, 10.dp)
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