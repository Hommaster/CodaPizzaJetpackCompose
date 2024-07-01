package com.example.codapizza.cart

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.codapizza.R
import com.example.codapizza.cart.boxOfOrderUI.BoxOfOrder
import com.example.codapizza.cart.database.Orders
import com.example.codapizza.cart.swipetodismiss.SwipeToDismiss
import com.example.codapizza.cart.viewmodel.MainActivityViewModel
import com.example.codapizza.desygnfiles.TopAppBarForScreens
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


@Composable
fun CartUI(
    navController: NavHostController,
    mainActivityViewModel: MainActivityViewModel,
) {

    //connect to firestore
    val fs = Firebase.firestore

    val coroutineScope = rememberCoroutineScope()

    val orderList : MutableState<List<Orders>> = rememberSaveable {
        mutableStateOf(emptyList())
    }

    val totalCost : MutableState<Float> = rememberSaveable {
        mutableFloatStateOf(0f)
    }
    var productInfo: HashMap<String, String> = hashMapOf()
    val product: HashMap<String, Map<String, HashMap<String, String>>> = hashMapOf()


    SwipeToDismiss(
        navController
    ) {
        Column(
            modifier = Modifier
                .background(colorResource(id = R.color.black))
                .fillMaxSize(),
        ){
            TopAppBarForScreens(
                R.string.cart,
                navController,
                mainActivityViewModel,
                true
            )
            LazyColumn(
                modifier = Modifier
                    .padding(20.dp, 50.dp)
                    .fillMaxWidth()
                    .weight(1f, fill = true),
            ) {
                coroutineScope.launch {
                    mainActivityViewModel.orders.collect{
                        orderList.value = it
                        totalCost.value = mainActivityViewModel.getOverrideTotalPrice()
                    }
                }
                item {

                    orderList.value.forEach {
                        val toppingProduct: HashMap<String, String> = hashMapOf()
                        val sauceProduct: HashMap<String, String> = hashMapOf()
                        productInfo = hashMapOf(
                            "product_name" to it.title,
                            "product_date" to it.date.toString(),
                            "product_quantity" to it.quantity.toString(),
                            "product_price" to it.price.toString(),
                        )

                        it.toppings.forEach { toppings ->
                            toppingProduct[toppings.key.toString()] = toppings.value.toString()
                        }
                        it.sauce.forEach { sauce ->
                            sauceProduct[sauce.key.toString()] = sauce.value.toString()

                        }
                        product.set(
                            key = it.id.toString(),
                            value = mapOf(
                                "product_${it.title}" to productInfo,
                                "toppings" to toppingProduct,
                                "sauces" to sauceProduct
                            )
                        )
                        BoxOfOrder(
                            mainActivityViewModel,
                            navController,
                            onClick = {
                                coroutineScope.launch {
                                    totalCost.value = mainActivityViewModel.getOverrideTotalPrice()
                                }
                            },
                            order = it
                        )
                    }
                }
            }
            Button(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(0.dp, 40.dp)
                    .width(300.dp),
                colors = ButtonColors(colorResource(id = R.color.orange), Color.White, Color.Yellow, Color.White),
                onClick = {
                    val json = Uri.encode(Gson().toJson(orderList))
                    Log.d("InfoJson", json)
                    fs.collection("drink_info").document().set(product)
                },
                content = {
                    val totalCostAfterRounded = (totalCost.value*100).roundToInt() / 100.0
                    Text(
                        text = stringResource(id = R.string.send_an_order, totalCostAfterRounded),
                        textAlign = TextAlign.Center,
                        fontSize = 15.sp
                    )
                }
            )
        }
    }
}
