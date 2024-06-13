package com.example.codapizza.cart.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.codapizza.model.Sauce
import com.example.codapizza.model.Topping
import com.example.codapizza.model.ToppingPlacement
import kotlinx.parcelize.Parcelize
import java.util.Date
import java.util.UUID

@Parcelize
@Entity
data class Orders(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    var title: String = "",
    var description: String = "",
    var date: Date = Date(),
    var image: Int? = null,
    var toppings: Map<Topping, ToppingPlacement>,
    var sauce: Map<Sauce, Int>,
    var price: Float = 0f,
    var quantity: Int = 1
): Parcelable
