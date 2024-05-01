package com.example.codapizza.arraypizza

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.MaterialTheme
//noinspection UsingMaterialAndMaterial3Libraries
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
import androidx.compose.ui.unit.sp
import com.example.codapizza.model.Pizzas

@Composable
fun BoxOfPizza(
    pizza: Pizzas,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(4.dp, 10.dp)
            .clip(RoundedCornerShape(23.dp))
            .border(2.dp, Color.Green, RoundedCornerShape(23.dp))
            .background(Color.LightGray)
            .clickable { onClick() }
    ){
        Column{
            Image(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(0.dp, 15.dp)
                    .size(250.dp),
                painter = painterResource(id = pizza.pizzaImage),
                contentDescription = stringResource(id = pizza.pizzaName)
            )
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { onClick() }
            ){
                Column(
                    modifier = Modifier
                        .padding(20.dp, 1.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .padding(10.dp, 2.dp),
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
}