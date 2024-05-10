package com.example.codapizza.pizza

import android.util.Log
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Button
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.ButtonDefaults
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.LocalTextStyle
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextMotion
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.codapizza.R
import com.example.codapizza.cart.database.Orders
import com.example.codapizza.model.Pizza
import com.example.codapizza.model.Pizzas
import com.example.codapizza.model.Topping
import com.example.codapizza.cart.viewmodel.MainActivityViewModel
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID


@Composable
fun PizzaBuilderScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    pizzaName: String?
) {
    var pizza by rememberSaveable {
        mutableStateOf(Pizza(pizzaName = pizzaName))
    }

    Column(
        modifier = modifier
            .background(colorResource(id = R.color.limegreen))
            .padding(0.dp, 30.dp)
    ) {
        PizzasImage(
            pizza = pizza,
            pizzaName = pizzaName,
            modifier = modifier
        )
        SizeDialog(
            pizza = pizza,
            onEditSize = { pizza = it }
        )
        ToppingList(
            pizza = pizza,
            onEditTopping = { pizza = it },
            modifier = modifier
                .fillMaxWidth()
                .weight(1f, fill = true)
        )
        OrderButton(
            pizza = pizza,
            mainActivityViewModel = MainActivityViewModel(),
            navController = navController,
            pizzaName = pizzaName,
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .padding(10.dp)
        )
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
    }
}

@Composable
private fun OrderButton(
    mainActivityViewModel: MainActivityViewModel,
    navController: NavHostController,
    pizza: Pizza,
    pizzaName: String?,
    modifier: Modifier = Modifier
) {

    val pizzas = when(pizzaName) {
        stringResource(R.string.margherita) -> Pizzas.Margherita
        stringResource(R.string.carbonara) -> Pizzas.Carbonara
        stringResource(R.string.chicago) -> Pizzas.Chicago
        else -> Pizzas.Margherita
    }

    val descriptionPizza = stringResource(id = pizzas.pizzaIngredients)

    val coroutineScope = rememberCoroutineScope()

    val infiniteTransition = rememberInfiniteTransition(label = "infinite transition")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(tween(15000), RepeatMode.Reverse),
        label = "scale"
    )

    Button(
        modifier = modifier
            .width(280.dp),
        onClick = {
                  coroutineScope.launch {
                      val newOrder = Orders(
                          id = UUID.randomUUID(),
                          title = pizza.pizzaName!!,
                          description = descriptionPizza,
                          date = Date(),
                          image = pizzas.pizzaImage,
                          toppings = pizza.toppings,
                          price = pizza.price.toFloat()
                      )
                      mainActivityViewModel.addOrder(newOrder)
                      navController.navigate("screen_1")
                  }
        },
        shape = RoundedCornerShape(23.dp),
        colors = ButtonDefaults.buttonColors(contentColor = Color.White, backgroundColor = Color.Blue)
    ) {

        val price = pizza.price
        Log.d("changeSize", "$price")

        Text(
            modifier = modifier
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    transformOrigin = TransformOrigin.Center
                },
            style = LocalTextStyle.current.copy(textMotion = TextMotion.Animated),
            text = stringResource(R.string.place_order_button, price)
            .toUpperCase(Locale.current),
            textAlign = TextAlign.Center
        )
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