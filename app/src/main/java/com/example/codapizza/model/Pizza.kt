package com.example.codapizza.model

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.RequiresApi
import androidx.compose.ui.util.trace
import androidx.navigation.NavType
import com.example.codapizza.model.ToppingPlacement.*
import com.google.gson.Gson
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@Parcelize
data class Pizza(
    val toppings: Map<Topping, ToppingPlacement> = emptyMap(),
    val sauces: Map<Sauce, Int> = emptyMap(),
    val sizePizza: SizePizza? = null,
    val pizzaName: String?
): Parcelable {

    @IgnoredOnParcel
    private val pizzaNamePrice: BigDecimal = when(pizzaName) {
        "Carbonara" -> 11.99
        "Margherita" -> 9.99
        "Chicago" -> 10.99
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

    @IgnoredOnParcel
    private val saucesPrice: BigDecimal = sauces.asSequence()
        .sumOf {
            (_, quantity) ->
            when(quantity) {
                0 -> 0.0
                1 -> 1.20
                else -> quantity * 1.20
            }
        }.toBigDecimal()

    val price: BigDecimal
        get() = pizzaNamePrice + sizePizzaPrice + toppingsPrice + saucesPrice

    fun withTopping(topping: Topping, placement: ToppingPlacement?): Pizza {
        return copy(
            toppings = if(placement == null) {
                toppings - topping
            } else {
                toppings + (topping to placement)
            }
        )
    }

    fun withQuantity(sauce: Sauce, quantity: Int?): Pizza {
        return copy(
            sauces = if(quantity == null) {
                sauces - sauce
            } else {
                sauces + (sauce to quantity)
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

class PizzaType : NavType<Pizza>(isNullableAllowed = true) {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun get(bundle: Bundle, key: String): Pizza? {
        return bundle.getParcelable(key, Pizza::class.java)
    }
    override fun parseValue(value: String): Pizza {
        return Gson().fromJson(value, Pizza::class.java)
    }
    override fun put(bundle: Bundle, key: String, value: Pizza) {
        bundle.putParcelable(key, value)
    }
}
