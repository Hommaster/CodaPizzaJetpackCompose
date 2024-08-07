package com.example.codapizza.productInfo.snack

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.RequiresApi
import androidx.navigation.NavType
import com.example.codapizza.model.Sauce
import com.example.codapizza.model.SizePizza
import com.example.codapizza.model.Topping
import com.example.codapizza.model.ToppingPlacement
import com.example.codapizza.model.ToppingPlacement.All
import com.example.codapizza.model.ToppingPlacement.Left
import com.example.codapizza.model.ToppingPlacement.Right
import com.google.gson.Gson
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@Parcelize
data class ProductInfoData(
    val toppings: Map<Topping, ToppingPlacement> = emptyMap(),
    val sauces: Map<Sauce, Int> = emptyMap(),
    val productSize: SizeProductNotPizza? = null,
    val productName: Int?,
    val sizePizza: SizePizza? = null,
    val pizzaName: String?,
    val priceProduct: Float?
): Parcelable {

    @IgnoredOnParcel
    private val toppingsPrice: BigDecimal = toppings.asSequence()
        .sumOf { (_, toppingPlacement) ->
            when (toppingPlacement) {
                Left, Right -> 0.5
                All -> 1.0
            }
        }.toBigDecimal()

    @IgnoredOnParcel
    private val sizePizzaPrice: BigDecimal = if(pizzaName != null && pizzaName != "pizzaWithArrayOfPizza" && pizzaName != "None" && pizzaName != "null") {
        when (sizePizza) {
            SizePizza.Small -> 0.98
            SizePizza.Average -> 2.99
            SizePizza.Big -> 4.99
            SizePizza.VeryBig -> 6.99
            else -> 0.0
        }.toBigDecimal()
    } else 0.toBigDecimal()

    @IgnoredOnParcel
    private val sizeProductPrice: BigDecimal = when(productSize) {
        SizeProductNotPizza.Standard -> 0.0
        SizeProductNotPizza.Big -> 1.0
        else -> 0.0
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
        get() = priceProduct!!.toBigDecimal() + sizeProductPrice + toppingsPrice + saucesPrice + sizePizzaPrice

    fun withTopping(topping: Topping, placement: ToppingPlacement?): ProductInfoData {
        return copy(
            toppings = if(placement == null) {
                toppings - topping
            } else {
                toppings + (topping to placement)
            }
        )
    }

    fun withQuantity(sauce: Sauce, quantity: Int?): ProductInfoData {
        return copy(
            sauces = if(quantity == null) {
                sauces - sauce
            } else {
                sauces + (sauce to quantity)
            }
        )
    }

    fun changeSizeProduct(size: SizeProductNotPizza): ProductInfoData {
        return copy(
            productSize = size
        )
    }

    fun changeSizePizza(size: SizePizza): ProductInfoData {
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