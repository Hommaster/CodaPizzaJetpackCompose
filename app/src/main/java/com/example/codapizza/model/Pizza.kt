package com.example.codapizza.model

import android.os.Parcelable
import com.example.codapizza.model.ToppingPlacement.*
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pizza(
    val toppings: Map<Topping, ToppingPlacement> = emptyMap()
): Parcelable {

    val price: Double
        get() = 9.99 + toppings.asSequence()
            .sumOf { (_, toppingPlacement) ->
                when (toppingPlacement) {
                    Left, Right -> 0.5
                    All -> 1.0
                    None -> 0.0
                }
            }

    fun withTopping(topping: Topping, placement: ToppingPlacement?): Pizza {
        return copy(
            toppings = if(placement == null) {
                toppings - topping
            } else {
                toppings + (topping to placement)
            }
        )
    }

    fun isPlacement(topping: Topping): ToppingPlacement? {
        return if (toppings[topping] != null) {
            toppings[topping]
        } else {
            null
        }
    }

}
