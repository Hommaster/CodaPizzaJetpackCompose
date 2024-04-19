package com.example.codapizza.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.codapizza.R
import com.example.codapizza.model.Pizza
import com.example.codapizza.model.SizePizza

@Composable
fun PizzaMargaritaImage(
    pizza: Pizza,
    modifier: Modifier
) {

    val sizeImagePizza = when (pizza.sizePizza) {
        SizePizza.Small -> 250.dp
        SizePizza.Average -> 300.dp
        SizePizza.Big -> 350.dp
        SizePizza.VeryBig -> 400.dp
        else -> 350.dp
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Image(
            modifier = modifier
                .align(Alignment.Center)
                .size(sizeImagePizza),
            painter = painterResource(id = R.drawable.pizza_margarrita),
            contentDescription = "pizza_margarita",
        )
        pizza.toppings.forEach { (topping, placement) -> 
            Image(

                painter = painterResource(id = topping.pizzaOverlayImage),
                contentDescription = stringResource(id = topping.toppingName),
                modifier = Modifier
                    .focusable(false)
                    .align(Alignment.Center)
                    .size(sizeImagePizza),
            )
        }
    }
}