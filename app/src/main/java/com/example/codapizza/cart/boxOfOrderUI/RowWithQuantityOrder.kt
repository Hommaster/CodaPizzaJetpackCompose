package com.example.codapizza.cart.boxOfOrderUI

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.codapizza.R
import com.example.codapizza.cart.database.Orders
import com.example.codapizza.cart.viewmodel.MainActivityViewModel
import kotlinx.coroutines.launch

@Composable
fun RowWithQuantityOrder(
    mainActivityViewModel: MainActivityViewModel,
    navController: NavHostController,
    onClick: () -> Unit,
    order: Orders
) {
    val coroutineScope = rememberCoroutineScope()

    val qS: MutableState<Int> = rememberSaveable {
        mutableIntStateOf(mainActivityViewModel.getQuantityOrder(order))
    }
    Card(
        modifier = Modifier
            .padding(0.dp, 0.dp, 0.dp, 0.dp)
            .size(100.dp, 30.dp),
        shape = RoundedCornerShape(23.dp),
        colors = CardColors(colorResource(id = R.color.white), Color.Black, Color.White, Color.White)
    ){
        Row(
            modifier = Modifier
                .align(Alignment.Start)
        ){
            IconButton(
                modifier = Modifier
                    .padding(5.dp, 5.dp, 2.dp, 0.dp)
                    .size(20.dp),
                colors = IconButtonColors(Color.White, colorResource(id = R.color.orange), Color.Blue, Color.Blue),
                onClick = {
                    onClick()
                    coroutineScope.launch {
                        if(order.price.toInt() == 0) {
                            onClick()
                        }
                        if(order.quantity == 1) {
                            mainActivityViewModel.deleteOrder(order)
                        }
                        else {
                            mainActivityViewModel.reductionInQuantity(order)
                            qS.value = mainActivityViewModel.getQuantityOrder(order)
                        }
                    }
                    coroutineScope.launch {
                        mainActivityViewModel.orders.collect{
                            if(it.isEmpty()) navController.navigate("cart_screen_empty")
                        }
                    }
                },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.minus),
                    contentDescription = "DeleteButton"
                )
            }
            qS.value = mainActivityViewModel.getQuantityOrder(order)
            onClick()
            Text(
                modifier = Modifier
                    .padding(12.dp, 3.dp, 3.dp, 0.dp),
                text = qS.value.toString(),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            IconButton(
                modifier = Modifier
                    .padding(12.dp, 5.dp, 2.dp, 0.dp)
                    .size(20.dp),
                colors = IconButtonColors(Color.White, colorResource(id = R.color.orange), Color.Blue, Color.Blue),
                onClick = {
                    onClick()
                    coroutineScope.launch {
                        mainActivityViewModel.addingQuantity(order)
                        qS.value = mainActivityViewModel.getQuantityOrder(order)
                    }
                    coroutineScope.launch {
                        mainActivityViewModel.orders.collect{
                            if(it.isEmpty()) navController.navigate("cart_screen_empty")
                        }
                    }
                },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.plus),
                    contentDescription = "DeleteButton"
                )
            }
        }
    }
}