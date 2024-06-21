package com.example.codapizza.productInfo.snack

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
data class ProductInfoData(
    val toppings: Map<Topping, ToppingPlacement> = emptyMap(),
    val productSize: SizeProductNotPizza = SizeProductNotPizza.Standard,
    val productName: Int?,
): Parcelable {

//    @IgnoredOnParcel
//    private val pizzaNamePrice: BigDecimal = when(snackName) {
//        "Carbonara" -> 11.99
//        "Margherita" -> 9.99
//        "Chicago" -> 10.99
//        else -> 9.99
//    }.toBigDecimal()

    @IgnoredOnParcel
    private val sizeSnackPrice: BigDecimal = when (productSize) {
        SizeProductNotPizza.Standard -> 4.0
        SizeProductNotPizza.Big -> 5.0
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

    fun withTopping(topping: Topping, placement: ToppingPlacement?): ProductInfoData {
        return copy(
            toppings = if(placement == null) {
                toppings - topping
            } else {
                toppings + (topping to placement)
            }
        )
    }

    fun changeSizePizza(size: SizeProductNotPizza): ProductInfoData {
        return copy(
            productSize = size
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

class ProductInfoType : NavType<ProductInfoData>(isNullableAllowed = true) {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun get(bundle: Bundle, key: String): ProductInfoData? {
        return bundle.getParcelable(key, ProductInfoData::class.java)
    }
    override fun parseValue(value: String): ProductInfoData {
        return Gson().fromJson(value, ProductInfoData::class.java)
    }
    override fun put(bundle: Bundle, key: String, value: ProductInfoData) {
        bundle.putParcelable(key, value)
    }
}