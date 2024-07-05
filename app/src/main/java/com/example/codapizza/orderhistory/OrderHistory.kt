package com.example.codapizza.orderhistory

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.codapizza.R
import com.example.codapizza.cart.database.OrderFromFirebase
import com.example.codapizza.cart.database.Orders
import com.example.codapizza.cart.swipetodismiss.SwipeToDismiss
import com.example.codapizza.cart.viewmodel.MainActivityViewModel
import com.example.codapizza.desygnfiles.TopAppBarForScreens
import com.example.codapizza.model.Sauce
import com.example.codapizza.model.Topping
import com.example.codapizza.model.ToppingPlacement
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.Date


@Composable
fun OrderHistory(
    navController: NavController
) {

    val list = remember {
        mutableStateOf(emptyList<OrderFromFirebase>())
    }

    val orderList : HashMap<String, Orders> = hashMapOf()
    val globalOrderList: HashMap<String, HashMap<String, Orders>> = hashMapOf()

    val fs = Firebase.firestore

    fs.collection("orders").get().addOnCompleteListener { task ->
        if (task.isComplete) {
            list.value = task.result.toObjects(OrderFromFirebase::class.java)
        }
    }

    list.value.forEach {
        it.order_list.forEach { order ->
            order.value.forEach { key, value2 ->
                val orderOne = Orders()
                value2["sauces"]!!.forEach { k->
                    orderOne.sauce = mapOf(
                        Sauce.valueOf(k.key) to k.value.toInt()
                    )
                }
                value2["toppings"]!!.forEach { k ->
                    orderOne.toppings = mapOf(
                        Topping.valueOf(k.key) to ToppingPlacement.valueOf(k.value)
                    )
                }
                value2["product_info"]!!.forEach { valu ->
                    when(valu.key) {
                        "product_name" -> {
                            orderOne.title = valu.value
                        }
                        "product_price" -> {
                            orderOne.price = valu.value.toFloat()
                        }
                        "product_quantity" -> {
                            orderOne.quantity = valu.value.toInt()
                        }
                        "product_date" -> {
                            orderOne.description = valu.value
                        }
                    }
                }
                orderList[orderOne.description] = orderOne
            }
            Log.d("infoOrderList", "${orderList}")
        }
        globalOrderList[Date().toString()] = orderList
        Log.d("infoOrderList", "${globalOrderList}")
    }

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
                Column(
                    modifier = Modifier
                        .padding(30.dp, 250.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Card(
                        shape = RoundedCornerShape(15.dp),
                        colors = CardColors(
                            colorResource(id = R.color.orange),
                            Color.Cyan,
                            Color.Cyan,
                            Color.Cyan
                        )
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(2.dp, 2.dp),
                            color = colorResource(id = R.color.white),
                            text = stringResource(id = R.string.order_history_is_empty),
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            style = TextStyle(textIndent = TextIndent(15.sp, 30.sp))
                        )
                        Row(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                        ) {
                            TextButton(onClick = {
                                navController.popBackStack("screen_1", false)
                            }) {
                                Text(
                                    color = colorResource(id = R.color.black),
                                    text = stringResource(id = R.string.back_to_menu),
                                    fontStyle = FontStyle.Italic,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                )
                            }
                        }
                        LazyColumn {
                            item {
                                globalOrderList.forEach { orderList ->
                                    Box(
                                        modifier = Modifier
                                            .background(Color.White)
                                            .border(2.dp, Color.Red, RoundedCornerShape(13.dp))
                                    ) {
                                        Text(text = "pidor")
                                    }
                                }
//                                listOfOrderList.forEach { orderList ->
//                                    Text(text = "gavno")
//                                    Log.d("InfoWtf", "${listOfOrderList.get(0)}")
//                                    Box(
//                                        modifier = Modifier
//                                            .background(Color.White)
//                                            .border(2.dp, Color.Red, RoundedCornerShape(13.dp))
//                                    ) {
//                                        Column {
//                                            orderList.forEach { order ->
//                                                Text(text = order.title)
//                                            }
//                                        }
//                                    }
//                                }
                            }
                        }
                    }
                }
            }
        }
    }