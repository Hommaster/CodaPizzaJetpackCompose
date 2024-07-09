package com.example.codapizza.orderhistory

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.codapizza.R
import com.example.codapizza.cart.database.OrderFromFirebase
import com.example.codapizza.cart.swipetodismiss.SwipeToDismiss
import com.example.codapizza.cart.viewmodel.MainActivityViewModel
import com.example.codapizza.desygnfiles.TopAppBarForScreens


@Composable
fun OrderHistory(
    navController: NavController
) {

    val listOrderFromDatabase = remember {
        mutableListOf(listOf<OrderFromFirebase>())
    }
    val listOrders = ParsedListOfOrdersFromFirebase(listOrderFromDatabase)

    SwipeToDismiss(
        navController
    ) {
        Column(
            modifier = Modifier
                .background(colorResource(id = R.color.black))
                .fillMaxSize(),
        ) {
            TopAppBarForScreens(
                textID = R.string.order_history,
                navController = navController,
                mainActivityViewModel = MainActivityViewModel(),
                cartNotEmpty = false
            )
            if(listOrders.isEmpty()) {
                EmptyOrderHistory(
                    modifier = Modifier
                        .padding(30.dp, 250.dp)
                        .align(Alignment.CenterHorizontally),
                    navController = navController
                )
            } else {
                FilledOrderHistory(
                    modifier = Modifier
                        .padding(30.dp, 20.dp)
                        .align(Alignment.CenterHorizontally),
                    listOrders
                )
            }
        }
    }
}