package com.example.codapizza.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.codapizza.cart.database.Orders
import com.example.codapizza.cart.viewmodel.MainActivityViewModel
import kotlinx.coroutines.launch

@Composable
fun CartUI(
    navController: NavHostController,
    mainActivityViewModel: MainActivityViewModel,
) {

    val coroutineScope = rememberCoroutineScope()

    val orderList : MutableState<List<Orders>> = rememberSaveable {
        mutableStateOf(emptyList())
    }

    Column(
        modifier = Modifier
            .padding(20.dp, 100.dp)
    ) {

        LazyColumn(
            modifier = Modifier
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
                        order = it
                    )
                }
            }
        }
    }
}

@Composable
fun BoxOfOrder(
    mainActivityViewModel: MainActivityViewModel,
    navController: NavHostController,
    order: Orders
) {
    val coroutineScope = rememberCoroutineScope()

    Row{
        Image(
            painter = painterResource(id = order.image!!),
            contentDescription = order.title
        )
        Column{
            Text(
                text = order.title
            )
            Text(
                text = order.price.toString()
            )
            order.toppings.forEach{
                Text(text = it.key.toString())
                Text(text = it.value.toString())
            }
        }
        IconButton(
            modifier = Modifier
                .align(Alignment.Bottom),
            onClick = {
                 coroutineScope.launch {
                     mainActivityViewModel.deleteOrder(order)
                 }
                coroutineScope.launch {
                    mainActivityViewModel.orders.collect{
                        if(it.isEmpty()) navController.navigate("cart_screen_empty")
                    }
                }
            },
        ) {
            Icon(Icons.Filled.Delete, "DeleteButton")
        }
    }
}
