package com.example.codapizza.orderhistory.helper

import android.widget.Toast
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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.codapizza.R
import com.example.codapizza.cart.database.Orders
import com.example.codapizza.cart.viewmodel.MainActivityViewModel
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID

@Composable
fun FilledOrderHistory(
    modifier: Modifier,
    listOrders: MutableList<MutableList<Orders>>
) {

    val context = LocalContext.current
    val toastText = stringResource(id = R.string.order_from_history_added_to_cart)

    val coroutineScope = rememberCoroutineScope()
    
    val mainActivityViewModel = MainActivityViewModel()
    
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
                        Text(text = stringResource(id = R.string.order_date, date))
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
                                            Text(text = stringResource(id = R.string.order_history_toppings))
                                            Column {
                                                order.toppings.forEach { (key, value) ->
                                                    Text(text = "$key - $value")
                                                }
                                            }
                                        }
                                    }
                                    if(order.sauce.isNotEmpty()) {
                                        Row {
                                            Text(text = stringResource(id = R.string.order_history_sauces))
                                            Column {
                                                order.sauce.forEach { (key, value) ->
                                                    Text(text = "$key - $value")
                                                }
                                            }
                                        }
                                    }
                                    Text(text = stringResource(id = R.string.order_history_price, order.price))
                                }
                                Image(painter = painterResource(id = order.image!!), contentDescription = order.image.toString())
                            }
                        }
                        Text(text = stringResource(id = R.string.order_history_total_price, totalPrice))
                        TextButton(
                            onClick = {
                                coroutineScope.launch {
                                    orderList.forEach { order ->
                                        val newOrder = Orders(
                                            id = UUID.randomUUID(),
                                            title = order.title,
                                            description = order.description,
                                            date = Date(),
                                            image = order.image,
                                            toppings = order.toppings,
                                            sauce = order.sauce,
                                            productID = order.productID,
                                            price = order.price
                                        )
                                        mainActivityViewModel.addOrder(newOrder)
                                    }
                                    Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
                                }
                            }
                        ) {
                            Text(text = "Repeat this order")
                        }
                    }
                }
            }
        }
    }
}