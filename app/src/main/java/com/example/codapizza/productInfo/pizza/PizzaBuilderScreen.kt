package com.example.codapizza.productInfo.pizza

import android.util.Log
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.codapizza.R
import com.example.codapizza.cart.swipetodismiss.SwipeToDismiss
import com.example.codapizza.cart.viewmodel.MainActivityViewModel
import com.example.codapizza.desygnfiles.BoxWithImageScrollToDismiss
import com.example.codapizza.model.Pizza
import com.example.codapizza.model.Pizzas
import com.example.codapizza.model.Sauce
import com.example.codapizza.model.Topping
import com.example.codapizza.sauce.SauceCell

@Composable
fun PizzaBuilderScreen(
    navController: NavController,
    pizzaInfo: Pizzas?,
    pizzaFromOrder: Pizza?,
    pizzaName: String?,
    orderID: String?
) {

    var pizza by rememberSaveable {
        when(pizzaFromOrder!!.pizzaName) {
            "pizzaWithArrayOfPizza" -> {
                mutableStateOf(Pizza(pizzaName = pizzaName))
            }
            else -> {
                mutableStateOf(pizzaFromOrder)
            }
        }
    }

    SwipeToDismiss(
        navController
    ) {
        Box {
            Column(
                modifier = Modifier
                    .background(Color.Black)
                    .padding(0.dp, 30.dp)
            ) {
                PizzaImages(
                    pizza = pizza,
                    pizzaInfo = pizzaInfo!!
                )
                SizeDialog(
                    pizza = pizza,
                    onEditSize = { pizza = it }
                )
                ToppingList(
                    pizza = pizza,
                    onEditTopping = { pizza = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f, fill = true)
                )
                OrderButton(
                    pizza = pizza,
                    orderID = orderID,
                    mainActivityViewModel = MainActivityViewModel(),
                    navController = navController,
                    pizzaInfo = pizzaInfo,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(10.dp)
                )
            }
            BoxWithImageScrollToDismiss(
                modifier = Modifier
                    .padding(0.dp, 30.dp),
                colorFilter = R.color.orange
            )
        }
    }
}

@Composable
private fun ToppingList(
    pizza: Pizza,
    onEditTopping: (Pizza) -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "infinite transition")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.02f,
        animationSpec = infiniteRepeatable(tween(15000), RepeatMode.Reverse),
        label = "scale"
    )


    var toppingBeingAdd by rememberSaveable {
        mutableStateOf<Topping?>(null)
    }

    var sauceBeingAdd by rememberSaveable {
        mutableStateOf<Map<Sauce?, Int?>>(emptyMap())
    }

    toppingBeingAdd?.let {
        ToppingCellDialog(
            topping = it,
            placementIsChoose = pizza.isPlacement(it),
            onSetToppingPlacement = {placement ->
                onEditTopping(pizza.withTopping(it, placement))
            },
            onDismissRequest = {
                toppingBeingAdd = null
            }
        )
    }

    sauceBeingAdd.let{
        for((key, value) in it) {
            onEditTopping(pizza.withQuantity(key!!, value))
        }
    }


    LazyColumn(
        modifier = modifier
    ) {
        items(Topping.entries.toTypedArray()) { topping ->
            ToppingCell(
                topping = topping,
                placement = pizza.toppings[topping],
                isChecked = pizza.toppings[topping] != null,
                onClickTopping = {
                    toppingBeingAdd = topping
                },
                modifier = modifier
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        transformOrigin = TransformOrigin.Center
                    }
            )
        }

        items(Sauce.entries.toTypedArray()) { sauce ->
            SauceCell(
                sauce = sauce,
                quantity = pizza.sauces[sauce],
                isChecked = pizza.sauces[sauce] != null,
                onClickSauce = {
                    sauceBeingAdd = mapOf(sauce to it)
                },
                modifier = modifier
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        transformOrigin = TransformOrigin.Center
                    }
            )
        }
    }
}

@Composable
private fun SizeDialog(
    pizza: Pizza,
    onEditSize: (Pizza) -> Unit
) {
    ToppingCellDropdownMenu(
        setSizePizza = {sizePizza ->
            onEditSize(pizza.changeSizePizza(sizePizza))
        }
    )
}