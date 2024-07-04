package com.example.codapizza.cart.database

import android.os.Parcelable
import com.example.codapizza.model.Sauce
import com.example.codapizza.model.Topping
import com.example.codapizza.model.ToppingPlacement
import kotlinx.parcelize.Parcelize
import java.util.Date
import java.util.UUID

@Parcelize
data class OrderFromFirebase(
//    var title: String = "",
//    var description: String = "",
//    var date: String = "",
//    var image: Int? = null,
//    var toppings: Map<String, String> = emptyMap(),
//    var sauce: Map<String, String> = emptyMap(),
//    var price: Float = 0f,
//    var quantity: Int = 1,
//    var productID: Int = -1
    val order_list : HashMap<String, HashMap<String, Map<String, HashMap<String, String>>>> = hashMapOf()
) : Parcelable
