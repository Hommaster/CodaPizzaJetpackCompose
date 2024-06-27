package com.example.codapizza.productInfo.pizza

import android.util.Log
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.navigation.NavController
import com.example.codapizza.R
import com.example.codapizza.cart.database.Orders
import com.example.codapizza.cart.viewmodel.MainActivityViewModel
import com.example.codapizza.cart.viewmodel.OrderDetailViewModel
import com.example.codapizza.productInfo.snack.ProcessingOfProductInformation
import com.example.codapizza.productInfo.snack.ProductInfoData
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID

@Composable
fun OrderButton(
    mainActivityViewModel: MainActivityViewModel,
    orderID: String?,
    navController: NavController,
    product: ProductInfoData,
    productInfo: ProcessingOfProductInformation,
    modifier: Modifier = Modifier
) {

    val orderDetailViewModel: OrderDetailViewModel? = if(orderID!="1") OrderDetailViewModel(UUID.fromString(orderID)) else null

    val order: MutableState<Orders?> = rememberSaveable {
        mutableStateOf(null)
    }

    val ingredients = productInfo.getProductIngredients()

    val descriptionPizza = if(ingredients != 0) stringResource(id = productInfo.getProductIngredients()) else ""
    val titleProduct = stringResource(id = productInfo.getProductDescription())
    val imageProduct = productInfo.getProductImage()

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
                            title = titleProduct,
                            description = descriptionPizza,
                            date = Date(),
                            image = imageProduct,
                            toppings = product.toppings,
                            sauce = product.sauces,
                            price = product.price.toFloat()
                        )
                        mainActivityViewModel.addOrder(newOrder)
                        navController.popBackStack("screen_1", false)
                    }
                },
                shape = RoundedCornerShape(23.dp),
                colors = ButtonDefaults.buttonColors(contentColor = Color.White, containerColor = colorResource(
                    id = R.color.orange
                )
                )
            ) {

                val price = product.price
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
                            Log.d("info_order", product.toppings.toString())
                            oldOrder.copy(
                                date = Date(),
                                toppings = product.toppings,
                                sauce = product.sauces,
                                price = product.price.toFloat()
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
                colors = ButtonDefaults.buttonColors(contentColor = Color.White, containerColor = colorResource(
                    id = R.color.orange
                )
                )
            ) {
                val price = product.price
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