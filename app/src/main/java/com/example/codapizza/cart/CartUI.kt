package com.example.codapizza.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.codapizza.R
import com.example.codapizza.cart.boxOfOrderUI.BoxOfOrder
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
            .background(colorResource(id = R.color.limegreen))
            .fillMaxSize(),
    ){
        TopAppBar(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            IconButton(
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
            onClick = { /*TODO*/ },
        ) {
            Text(
                text = stringResource(id = R.string.send_an_order),
                textAlign = TextAlign.Center
            )
        }
    }
}
