package com.example.codapizza.model

import android.os.Parcelable
import android.util.Log
import com.example.codapizza.model.ToppingPlacement.*
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal
import kotlin.math.roundToInt

@Parcelize
data class Pizza(
    val toppings: Map<Topping, ToppingPlacement> = emptyMap(),
    val sizePizza: SizePizza? = null,
    val pizzaName: String?
): Parcelable {

    @IgnoredOnParcel
    private val pizzaNamePrice: BigDecimal = when(pizzaName) {
        "Carbonara" -> 11.99
        "Margherita" -> 9.99
        else -> 9.99
    }.toBigDecimal()

    @IgnoredOnParcel
    private val sizePizzaPrice: BigDecimal = when (sizePizza) {
        SizePizza.Small -> 5.0
        SizePizza.Average -> 6.0
        SizePizza.Big -> 7.0
        SizePizza.VeryBig -> 8.0
        else -> 7.0
    }.toBigDecimal()

    @IgnoredOnParcel
    private val toppingsPrice: BigDecimal = toppings.asSequence()
        .sumOf { (_, toppingPlacement) ->
            when (toppingPlacement) {
                Left, Right -> 0.5
                All -> 1.0
            }
        }.toBigDecimal()

    val price: BigDecimal
        get() = pizzaNamePrice + sizePizzaPrice + toppingsPrice

    fun withTopping(topping: Topping, placement: ToppingPlacement?): Pizza {
        return copy(
            toppings = if(placement == null) {
                toppings - topping
            } else {
                toppings + (topping to placement)
            }
        )
    }

    fun changeSizePizza(size: SizePizza): Pizza {
        return copy(
            sizePizza = size
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
