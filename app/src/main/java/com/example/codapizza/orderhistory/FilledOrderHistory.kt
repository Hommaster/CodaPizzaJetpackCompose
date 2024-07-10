package com.example.codapizza.orderhistory

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.codapizza.R
import com.example.codapizza.cart.database.Orders

@Composable
fun FilledOrderHistory(
    modifier: Modifier,
    listOrders: MutableList<MutableList<Orders>>
) {
    LazyColumn(
        modifier = modifier
    ) {
        item {
            listOrders.forEach { orderList ->
                Box(
                    modifier = Modifier
                        .padding(0.dp, 0.dp, 0.dp, 8.dp)
                        .background(
                            colorResource(id = R.color.orange),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .width(350.dp)
                ) {
                    var date = ""
                    var totalPrice = 0f
                    orderList.forEach { order ->
                        date = order.description
                        totalPrice += order.price
                    }
                    var numberOrder = 0
                    Column(
                        modifier = Modifier
                            .padding(8.dp, 4.dp)
                    ) {
                        Text(text = "Your order from $date")
                        orderList.forEach { order ->
                            Row (
                                modifier = Modifier
                                    .padding(0.dp, 4.dp, 0.dp, 2.dp)
                            ){
                                numberOrder += 1
                                Text(text = "$numberOrder. ")
                                Column {
                                    Text(text = order.title)
                                    if(order.toppings.isNotEmpty()) {
                                        Row {
                                            Text(text = "Toppings: ")
                                            Column {
                                                order.toppings.forEach { (key, value) ->
                                                    Text(text = "$key - $value")
                                                }
                                            }
                                        }
                                    }
                                    if(order.sauce.isNotEmpty()) {
                                        Row {
                                            Text(text = "Sauces: ")
                                            Column {
                                                order.sauce.forEach { (key, value) ->
                                                    Text(text = "$key - $value")
                                                }
                                            }
                                        }
                                    }
                                    Text(text = "Price: ${order.price}")
                                }
                                Image(painter = painterResource(id = order.image!!), contentDescription = order.image.toString())
                            }
                        }
                        Text(text = "Total price - $totalPrice")
                    }
                }
            }
        }
    }
}