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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.codapizza.R
import com.example.codapizza.cart.swipetodismiss.SwipeToDismiss
import com.example.codapizza.cart.viewmodel.MainActivityViewModel
import com.example.codapizza.desygnfiles.BoxWithImageScrollToDismiss
import com.example.codapizza.model.Sauce
import com.example.codapizza.model.Topping
import com.example.codapizza.productInfo.snack.ProcessingOfProductInformation
import com.example.codapizza.productInfo.snack.ProductInfoData
import com.example.codapizza.sauce.SauceCell

@Composable
fun PizzaBuilderScreen(
    navController: NavController,
    pizzaName: String?,
    orderID: String?,
    productInfoData: ProductInfoData?,
    productName: Int?,
) {

    val productNameString: String = if(productName != -1 && productName != null) stringResource(id = productName) else "null"
    val pizzaNameString: String = if(productInfoData!!.pizzaName != null && productInfoData.pizzaName != "None") productInfoData.pizzaName!! else "null"

    Log.d("info_order", "$productName , ${productInfoData.pizzaName}")
    Log.d("info_gr2", "$productName , $pizzaName")

    val productInfo = ProcessingOfProductInformation(productNameString, pizzaNameString)


    var product by rememberSaveable {
        if(productName == -1) {
            when(productInfoData.pizzaName) {
                "pizzaWithArrayOfPizza" -> {
                    Log.d("info444", "$pizzaName")
                    mutableStateOf(ProductInfoData(
                        pizzaName = pizzaName,
                        productName = null))
                }
                else -> {
                    mutableStateOf(productInfoData)
                }
            }
        } else {
            Log.d("info2", "$pizzaName")
            when(productInfoData.productName) {
                0 -> {
                    mutableStateOf(ProductInfoData(
                        pizzaName = null,
                        productName = productName
                    ))
                }
                else -> {
                    mutableStateOf(productInfoData)
                }
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
                    product = product,
                    productInfo = productInfo
                )
                SizeDialog(
                    product = product,
                    onEditSize = { product = it }
                )
                ToppingList(
                    product = product,
                    onEditTopping = { product = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f, fill = true)
                )
                OrderButton(
                    product = product,
                    orderID = orderID,
                    mainActivityViewModel = MainActivityViewModel(),
                    navController = navController,
                    productInfo = productInfo,
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
    product: ProductInfoData,
    onEditTopping: (ProductInfoData) -> Unit,
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
            placementIsChoose = product.isPlacement(it),
            onSetToppingPlacement = {placement ->
                onEditTopping(product.withTopping(it, placement))
            },
            onDismissRequest = {
                toppingBeingAdd = null
            }
        )
    }

    sauceBeingAdd.let{
        for((key, value) in it) {
            onEditTopping(product.withQuantity(key!!, value))
        }
    }


    LazyColumn(
        modifier = modifier
    ) {
        if(product.productName == null) {
            items(Topping.entries.toTypedArray()) { topping ->
                ToppingCell(
                    topping = topping,
                    placement = product.toppings[topping],
                    isChecked = product.toppings[topping] != null,
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

        items(Sauce.entries.toTypedArray()) { sauce ->
            SauceCell(
                sauce = sauce,
                quantity = product.sauces[sauce],
                isChecked = product.sauces[sauce] != null,
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
    product: ProductInfoData,
    onEditSize: (ProductInfoData) -> Unit
) {
    ToppingCellDropdownMenu(
        product = product,
        setSizePizza = {sizePizza ->
            onEditSize(product.changeSizePizza(sizePizza))
        },
        setSizeProduct = {sizeProduct->
            onEditSize(product.changeSizeProduct(sizeProduct))
        }
    )
}