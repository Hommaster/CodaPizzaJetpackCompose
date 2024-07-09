package com.example.codapizza.orderhistory

import com.example.codapizza.cart.database.OrderFromFirebase
import com.example.codapizza.cart.database.Orders
import com.example.codapizza.model.Sauce
import com.example.codapizza.model.Topping
import com.example.codapizza.model.ToppingPlacement
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

fun parsedListOfOrdersFromFirebase(
    listOrderFromDatabase: MutableList<List<OrderFromFirebase>>
): MutableList<MutableList<Orders>> {

    val listOrders: MutableList<MutableList<Orders>> = mutableListOf()

    val fs = Firebase.firestore

    fs.collection("orders").get().addOnCompleteListener { task ->
        if (task.isComplete) {
            task.result.documents.forEach { documentSnapshot ->
                listOrderFromDatabase.add(listOf(documentSnapshot.toObject(OrderFromFirebase::class.java)!!))
            }
        }
    }

    listOrderFromDatabase.forEach { listOrders1 ->
        if (listOrders1.isNotEmpty()) {
            listOrders1.forEach { it ->
                it.order_list.forEach { order ->
                    val ordersList: MutableList<Orders> = mutableListOf()
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
                        ordersList.add(orderOne)
                    }
                    listOrders.add(ordersList)
                }
            }
        }
    }
    val middle = listOrders.size/2

    return listOrders.subList(0, middle)
}