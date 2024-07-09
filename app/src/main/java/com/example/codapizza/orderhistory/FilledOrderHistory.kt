package com.example.codapizza.orderhistory

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
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
                        .background(colorResource(id = R.color.orange), shape = RoundedCornerShape(10.dp))
                ) {
                    Column {
                        orderList.forEach { order ->
                            Text(text = order.title)
                        }
                    }
                }
            }
        }
    }
}