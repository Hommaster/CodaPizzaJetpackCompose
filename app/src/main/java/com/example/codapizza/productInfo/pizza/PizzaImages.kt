package com.example.codapizza.productInfo.pizza

import androidx.compose.foundation.Image
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.codapizza.model.Pizza
import com.example.codapizza.model.Pizzas
import com.example.codapizza.model.ToppingPlacement

@Composable
fun PizzaImages(
    pizza: Pizza,
    pizzaInfo: Pizzas,
) {
    Row(
        modifier = Modifier
            .padding(32.dp, 0.5.dp)
    ){

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .aspectRatio(1f),
        ) {
            Image(
                painter = painterResource(id = pizzaInfo.pizzaImage),
                contentDescription = "${pizzaInfo.pizzaName}",
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(370.dp)
            )
            pizza.toppings.forEach { (topping, placement) ->
                Image(
                    painter = painterResource(id = topping.pizzaOverlayImage),
                    contentDescription = stringResource(id = topping.toppingName),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .align(
                            when (placement) {
                                ToppingPlacement.Left -> Alignment.CenterStart
                                ToppingPlacement.Right -> Alignment.CenterEnd
                                ToppingPlacement.All -> Alignment.Center
                            }
                        )
                        .focusable(false)
                        .aspectRatio(
                            when (placement) {
                                ToppingPlacement.Left, ToppingPlacement.Right -> 0.5f
                                ToppingPlacement.All -> 1f
                            }
                        ),
                    alignment = when(placement) {
                        ToppingPlacement.Left -> Alignment.TopStart
                        ToppingPlacement.Right -> Alignment.TopEnd
                        ToppingPlacement.All -> Alignment.Center
                    },
                )
            }
        }
    }
}