package com.example.codapizza.pizza

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
import com.example.codapizza.R
import com.example.codapizza.model.Pizza
import com.example.codapizza.model.Pizzas
import com.example.codapizza.model.SizePizza
import com.example.codapizza.model.ToppingPlacement



@Composable
fun PizzasImage(
    pizza: Pizza,
    modifier: Modifier,
    pizzaName: String?
) {

    val sizeImagePizza = when (pizza.sizePizza) {
        SizePizza.Small -> 270.dp
        SizePizza.Average -> 320.dp
        SizePizza.Big -> 370.dp
        SizePizza.VeryBig -> 420.dp
        else -> 370.dp
    }
    Row(
        modifier = modifier
            .padding(when(sizeImagePizza) {
                270.dp -> 100.dp
                320.dp -> 64.dp
                370.dp -> 32.dp
                420.dp -> 10.dp
                else -> 32.dp
            }, 0.5.dp)
    ){

        val pizzas = when(pizzaName) {
            stringResource(R.string.margherita) -> Pizzas.Margherita
            stringResource(R.string.carbonara) -> Pizzas.Carbonara
            stringResource(R.string.chicago) -> Pizzas.Chicago
            else -> Pizzas.Margherita
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .align(Alignment.CenterVertically)
//                .size(sizeImagePizza)
                .aspectRatio(1f),
        ) {
            Image(
                painter = painterResource(id = pizzas.pizzaImage),
                contentDescription = "${pizzas.pizzaName}",
                modifier = modifier
                    .align(Alignment.Center)
                    .size(sizeImagePizza)
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