package com.example.codapizza.productInfo.snack

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.navigation.NavType
import com.example.codapizza.model.Pizzas
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
    val productSize: SizeProductNotPizza = SizeProductNotPizza.Standard,
    val productName: Int?,
    val sizePizza: SizePizza? = null,
    val pizzaName: String?
): Parcelable {

    init {
        Log.d("infoPizzaName", "$pizzaName")
    }

    @IgnoredOnParcel
    private val productPrice: BigDecimal? = if(pizzaName != null && pizzaName != "pizzaWithArrayOfPizza") {
        Pizzas.valueOf(pizzaName).price.toBigDecimal()
    } else if (pizzaName == null && productName != null && productName != 0) {
        when (productSize) {
            SizeProductNotPizza.Standard -> 4.0
            SizeProductNotPizza.Big -> 5.0
        }.toBigDecimal()
    } else null

    @IgnoredOnParcel
    private val toppingsPrice: BigDecimal = toppings.asSequence()
        .sumOf { (_, toppingPlacement) ->
            when (toppingPlacement) {
                Left, Right -> 0.5
                All -> 1.0
                else -> 0.0
            }
        }.toBigDecimal()

    @IgnoredOnParcel
    private val sizePizzaPrice: BigDecimal = when (sizePizza) {
        SizePizza.Small -> 0.99
        SizePizza.Average -> 6.0
        SizePizza.Big -> 7.0
        SizePizza.VeryBig -> 8.0
        else -> 0.0
    }.toBigDecimal()

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
        get() = productPrice!! + toppingsPrice + saucesPrice + sizePizzaPrice + sizeProductPrice

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