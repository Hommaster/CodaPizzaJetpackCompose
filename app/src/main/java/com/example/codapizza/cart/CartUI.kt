package com.example.codapizza.cart

import android.annotation.SuppressLint
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.codapizza.arraypizza.helper.DialogAboutNoInternet
import com.example.codapizza.cart.boxOfOrderUI.BoxOfOrder
import com.example.codapizza.cart.database.OrderFromFirebase
import com.example.codapizza.cart.database.Orders
import com.example.codapizza.cart.internetHelper.connectivityStatus
import com.example.codapizza.cart.swipetodismiss.SwipeToDismiss
import com.example.codapizza.cart.viewmodel.MainActivityViewModel
import com.example.codapizza.desygnfiles.TopAppBarForScreens
import com.example.codapizza.helperInternet.ConnectionStatus
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


@SuppressLint("MutableCollectionMutableState")
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
    val orderInfo: MutableState<HashMap<String, HashMap<String, Map<String, HashMap<String, String>>>>> = rememberSaveable {
        mutableStateOf(hashMapOf())
    }
    val productInfo: MutableState<HashMap<String, String>> = rememberSaveable {
        mutableStateOf(hashMapOf())
    }
    val product: MutableState<HashMap<String, Map<String, HashMap<String, String>>>> = rememberSaveable {
        mutableStateOf(hashMapOf())
    }

    val openDialog = remember { mutableStateOf(false) }

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
                        it.toppings.forEach { toppings ->
                            toppingProduct[toppings.key.toString()] = toppings.value.toString()
                        }

                        val sauceProduct: HashMap<String, String> = hashMapOf()
                        it.sauce.forEach { sauce ->
                            sauceProduct[sauce.key.toString()] = sauce.value.toString()
                        }
                        productInfo.value = hashMapOf(
                            "product_name" to it.title,
                            "product_date" to it.date.toString(),
                            "product_quantity" to it.quantity.toString(),
                            "product_price" to it.price.toString(),
                            "product_image" to it.image.toString(),
                            "product_ID" to it.productID.toString()
                        )
                        product.value = hashMapOf(
                            "${it.title}_${it.id}" to mapOf(
                                "product_info" to productInfo.value,
                                "toppings" to toppingProduct,
                                "sauces" to sauceProduct,
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
                    orderInfo.value = hashMapOf(
                        "order_list" to product.value
                    )
                }
            }
            val connection by connectivityStatus()
            val isConnected = connection === ConnectionStatus.Available
            Button(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(0.dp, 40.dp)
                    .width(300.dp),
                colors = ButtonColors(colorResource(id = R.color.orange), Color.White, Color.Yellow, Color.White),
                onClick = {
                    if(isConnected) {
                        fs.collection("orders").document().set(OrderFromFirebase(
                            orderInfo.value
                        ))
                        coroutineScope.launch {
                            mainActivityViewModel.deleteAll()
                        }
                        navController.navigate("cart_screen_empty")
                    } else {
                        openDialog.value = true
                    }

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
            DialogAboutNoInternet(
                openDialog,
                onClick = {
                    openDialog.value = false
                }
            )
        }
    }
}
