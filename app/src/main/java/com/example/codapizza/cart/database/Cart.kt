package com.example.codapizza.cart.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.util.Date
import java.util.UUID

@Entity
data class Cart(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    var title: String = "",
    var description: String = "",
    var date: Date = Date(),
    var image: Int? = null,
    var price: BigDecimal = 1.0.toBigDecimal()
)
