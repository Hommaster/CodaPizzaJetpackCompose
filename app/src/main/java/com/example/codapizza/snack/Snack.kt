package com.example.codapizza.snack

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.RequiresApi
import androidx.navigation.NavType
import com.example.codapizza.model.Topping
import com.example.codapizza.model.ToppingPlacement
import com.google.gson.Gson
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@Parcelize
data class Snack(
    val toppings: Map<Topping, ToppingPlacement> = emptyMap(),
    val sizeSnack: SizeSnack = SizeSnack.Standard,
    val snackName: String?,
): Parcelable {

//    @IgnoredOnParcel
//    private val pizzaNamePrice: BigDecimal = when(snackName) {
//        "Carbonara" -> 11.99
//        "Margherita" -> 9.99
//        "Chicago" -> 10.99
//        else -> 9.99
//    }.toBigDecimal()

    @IgnoredOnParcel
    private val sizeSnackPrice: BigDecimal = when (sizeSnack) {
        SizeSnack.Standard -> 4.0
        SizeSnack.Big -> 5.0
    }.toBigDecimal()

    @IgnoredOnParcel
    private val toppingsPrice: BigDecimal = toppings.asSequence()
        .sumOf { (_, toppingPlacement) ->
            when (toppingPlacement) {
                ToppingPlacement.Left, ToppingPlacement.Right -> 0.0
                ToppingPlacement.All -> 1.20
            }
        }.toBigDecimal()

    val price: BigDecimal
        get() = sizeSnackPrice + toppingsPrice

    fun withTopping(topping: Topping, placement: ToppingPlacement?): Snack {
        return copy(
            toppings = if(placement == null) {
                toppings - topping
            } else {
                toppings + (topping to placement)
            }
        )
    }

    fun changeSizePizza(size: SizeSnack): Snack {
        return copy(
            sizeSnack = size
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

class SnackType : NavType<Snack>(isNullableAllowed = true) {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun get(bundle: Bundle, key: String): Snack? {
        return bundle.getParcelable(key, Snack::class.java)
    }
    override fun parseValue(value: String): Snack {
        return Gson().fromJson(value, Snack::class.java)
    }
    override fun put(bundle: Bundle, key: String, value: Snack) {
        bundle.putParcelable(key, value)
    }
}