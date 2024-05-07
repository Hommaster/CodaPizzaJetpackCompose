package com.example.codapizza.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.codapizza.cart.database.Order
import com.example.codapizza.viewmodel.MainActivityViewModel
import kotlinx.coroutines.launch

@Composable
fun CartUI(
    navController: NavHostController,
    mainActivityViewModel: MainActivityViewModel,
) {

    val coroutineScope = rememberCoroutineScope()

    val orderList : MutableState<List<Order>> = remember {
        mutableStateOf(emptyList())
    }

    Column(
        modifier = Modifier
            .padding(20.dp, 100.dp)
    ) {
        Text(
            text = "Корзина пуста"
        )
        Text(
            text = "И находится в технической доработке"
        )
        TextButton(onClick = { 
            navController.navigate("screen_1")
        }) {
            Text(text = "Вернуться в меню")
        }
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
                    BoxOfOrder(order = it)
                }
            }
        }
    }
}

@Composable
fun BoxOfOrder(
    order: Order
) {
    Row{
        Image(
            painter = painterResource(id = order.image!!), 
            contentDescription = order.title
        )
        Text(
            text = order.title
        )
        Text(
            text = order.price.toString()
        )
    }
}
