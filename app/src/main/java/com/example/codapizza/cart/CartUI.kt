package com.example.codapizza.cart

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
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
import androidx.navigation.compose.rememberNavController
import com.example.codapizza.R
import com.example.codapizza.arraypizza.ArrayOfPizza
import com.example.codapizza.cart.boxOfOrderUI.BoxOfOrder
import com.example.codapizza.cart.database.Orders
import com.example.codapizza.cart.topAppBarForCartUI.TopAppBarForCartUI
import com.example.codapizza.cart.viewmodel.MainActivityViewModel
import com.example.codapizza.theme.AppTheme
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


@OptIn(ExperimentalMaterial3Api::class)
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
    val dismissState = rememberSwipeToDismissBoxState()
    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromEndToStart = false,
        backgroundContent = {
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
            modifier = Modifier
                .background(colorResource(id = R.color.black))
                .fillMaxSize(),
        ){
            TopAppBarForCartUI(
                navController,
                mainActivityViewModel
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
                    fs.collection("orders").document().set(mapOf("1" to "1"))
                },
                content = {
                    val d1 = (totalCost.value*100).roundToInt() / 100.0
                    Text(
                        text = stringResource(id = R.string.send_an_order, d1),
                        textAlign = TextAlign.Center,
                        fontSize = 15.sp
                    )
                }
            )
        }
    }
}
