package com.example.codapizza.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.codapizza.R

enum class Pizzas(
    @StringRes val pizzaName: Int,
    @StringRes val pizzaIngredients: Int,
    @DrawableRes val pizzaImage: Int
) {
    Margherita(
        pizzaName = R.string.margherita,
        pizzaIngredients = R.string.margherita_ingredients,
        pizzaImage = R.drawable.margherita
    ),
    Carbonara(
        pizzaName = R.string.carbonara,
        pizzaIngredients = R.string.carbonara_ingredients,
        pizzaImage = R.drawable.carbonara
    )
}
