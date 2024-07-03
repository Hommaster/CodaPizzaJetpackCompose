package com.example.codapizza.cart.database

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderFromFirebase(
    val orderList : HashMap<String, HashMap<String, Map<String, HashMap<String, String>>>> = hashMapOf()
) : Parcelable
