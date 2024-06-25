package com.example.codapizza.model

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.navigation.NavType
import com.example.codapizza.R
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
enum class Pizzas(
    @IntegerRes val price: Int,
    @StringRes val pizzaName: Int,
    @StringRes val pizzaIngredients: Int,
    @DrawableRes val pizzaImage: Int,
    @StringRes val pricePizza: Int
): Parcelable {
    Margherita(
        price = 14,
        pizzaName = R.string.margherita,
        pizzaIngredients = R.string.margherita_ingredients,
        pizzaImage = R.drawable.margherita,
        pricePizza = R.string.price_margherita
    ),
    Carbonara(
        price = 16,
        pizzaName = R.string.carbonara,
        pizzaIngredients = R.string.carbonara_ingredients,
        pizzaImage = R.drawable.carbonara,
        pricePizza = R.string.price_carbonara
    ),
    Chicago(
        price = 15,
        pizzaName = R.string.chicago,
        pizzaIngredients = R.string.chicago_ingredients,
        pizzaImage = R.drawable.chicago,
        pricePizza = R.string.price_chicago
    )
}

class PizzaInfoType : NavType<Pizzas>(isNullableAllowed = true) {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun get(bundle: Bundle, key: String): Pizzas? {
        return bundle.getParcelable(key, Pizzas::class.java)
    }

    override fun parseValue(value: String): Pizzas {
        return Gson().fromJson(value, Pizzas::class.java)
    }

    override fun put(bundle: Bundle, key: String, value: Pizzas) {
        bundle.putParcelable(key, value)
    }
}
