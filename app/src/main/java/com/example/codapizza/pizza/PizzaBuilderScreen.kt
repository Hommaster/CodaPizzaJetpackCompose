package com.example.codapizza.pizza

import android.util.Log
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.codapizza.cart.viewmodel.OrderDetailViewModel
import com.example.codapizza.model.Sauce
import com.example.codapizza.sauce.SauceCell
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PizzaBuilderScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    pizzaFromOrder: Pizza?,
    orderID: String?,
    pizzaName: String?
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

    val nameOfPizza = if(pizzaFromOrder!!.pizzaName == "pizzaWithArrayOfPizza") pizzaName else pizzaFromOrder.pizzaName

    val dismissState = rememberSwipeToDismissBoxState()
    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromEndToStart = false,
        backgroundContent = {
            @Suppress("UNUSED_EXPRESSION")
            when(dismissState.targetValue){
                SwipeToDismissBoxValue.StartToEnd -> {
                    Box(
                        contentAlignment = Alignment.CenterEnd,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(colorResource(id = R.color.orange))
                    ) {}
                    if(dismissState.progress.toDouble() >= 0.5) {
                        navController.popBackStack("screen_1", false)
                    }
                }
                else -> null
            }
        }
    ) {
        Column(
            modifier = modifier
                .background(Color.Black)
                .padding(0.dp, 30.dp)
        ) {
            PizzasImage(
                pizza = pizza,
                pizzaName = nameOfPizza,
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
                orderID = orderID,
                mainActivityViewModel = MainActivityViewModel(),
                navController = navController,
                pizzaName = nameOfPizza,
                modifier = modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(10.dp)
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

        items(Sauce.entries.toTypedArray()) {sauce ->
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
private fun OrderButton(
    mainActivityViewModel: MainActivityViewModel,
    orderID: String?,
    navController: NavHostController,
    pizza: Pizza,
    pizzaName: String?,
    modifier: Modifier = Modifier
) {

    val orderDetailViewModel: OrderDetailViewModel? = if(orderID!="1") OrderDetailViewModel(UUID.fromString(orderID)) else null

    val pizzas = when(pizzaName) {
        stringResource(R.string.margherita) -> Pizzas.Margherita
        stringResource(R.string.carbonara) -> Pizzas.Carbonara
        stringResource(R.string.chicago) -> Pizzas.Chicago
        else -> Pizzas.Margherita
    }
    val order: MutableState<Orders?> = rememberSaveable {
        mutableStateOf(null)
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

    when(orderID){
        "1" -> {
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
                            sauce = pizza.sauces,
                            price = pizza.price.toFloat()
                        )
                        mainActivityViewModel.addOrder(newOrder)
                        navController.popBackStack("screen_1", false)
                    }
                },
                shape = RoundedCornerShape(23.dp),
                colors = ButtonDefaults.buttonColors(contentColor = Color.White, backgroundColor = colorResource(
                    id = R.color.orange
                ))
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
        else -> {
            Button(
                modifier = modifier
                    .width(280.dp),
                onClick = {
                    coroutineScope.launch {
                        orderDetailViewModel!!.updateOrder { oldOrder->
                            Log.d("info_order", pizza.toppings.toString())
                            oldOrder.copy(
                                date = Date(),
                                toppings = pizza.toppings,
                                price = pizza.price.toFloat()
                            )
                        }
                    }
                    coroutineScope.launch {
                        orderDetailViewModel!!.order.collect{
                            order.value = it
                        }
                    }
                    coroutineScope.launch {
                        mainActivityViewModel.updateOrder(order.value!!)
                    }
                    navController.navigate("cart_screen") {
                        popUpTo("screen_1")
                    }
                },
                shape = RoundedCornerShape(23.dp),
                colors = ButtonDefaults.buttonColors(contentColor = Color.White, backgroundColor = colorResource(
                    id = R.color.orange
                ))
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
                    text = stringResource(R.string.change_order_button, price)
                        .toUpperCase(Locale.current),
                    textAlign = TextAlign.Center
                )
            }
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