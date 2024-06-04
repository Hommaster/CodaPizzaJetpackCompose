package com.example.codapizza.cart

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.codapizza.R
import com.example.codapizza.cart.boxOfOrderUI.BoxOfOrder
import com.example.codapizza.cart.database.Orders
import com.example.codapizza.cart.viewmodel.MainActivityViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import kotlinx.coroutines.launch


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

    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.black))
            .fillMaxSize(),
    ){
        Card(
            modifier = Modifier
                .padding(0.dp, 4.dp),
            shape = RoundedCornerShape(15.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
            ) {
                TopAppBar(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    IconButton(
                        modifier = Modifier
                            .align(Alignment.Bottom)
                            .padding(0.dp, 4.dp, 0.dp, 0.dp),
                        onClick = {
                            coroutineScope.launch {
                                mainActivityViewModel.deleteAll()
                            }
                            navController.navigate("cart_screen_empty")
                        },
                        content = {
                            Icon(
                                Icons.Filled.Delete, "Menu"
                            )
                        }
                    )
                    IconButton(
                        modifier = Modifier
                            .padding(200.dp, 0.dp, 0.dp, 0.dp)
                            .align(Alignment.Bottom),
                        onClick = {
                            coroutineScope.launch {
                                mainActivityViewModel.deleteAll()
                            }
                            navController.navigate("cart_screen_empty")
                        },
                        content = {
                            Icon(
                                Icons.Filled.Close, "Menu"
                            )
                        }
                    )
                }
            }
        }
        LazyColumn(
            modifier = Modifier
                .padding(20.dp, 50.dp)
                .fillMaxWidth()
                .weight(1f, fill = true),
        ) {
            coroutineScope.launch {
                mainActivityViewModel.orders.collect{
                    orderList.value = it
                }
            }
            item {
                orderList.value.forEach {
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
                .width(280.dp),
            onClick = {
                val json = Uri.encode(Gson().toJson(orderList))
                Log.d("InfoJson", json)
                fs.collection("orders").document().set(mapOf("1" to "1"))
            },
            content = {
                Text(
                    text = stringResource(id = R.string.send_an_order, totalCost.value),
                    textAlign = TextAlign.Center
                )
            }
        )
    }
}
