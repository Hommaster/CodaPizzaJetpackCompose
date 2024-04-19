package com.example.codapizza.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.codapizza.R

enum class Topping (
    @StringRes val toppingName: Int,
    @DrawableRes val toppingDrawable: Int,
    @DrawableRes val pizzaOverlayImage: Int,
) {
    Basil(
        toppingName = R.string.topping_basil,
        toppingDrawable = R.drawable.basil,
        pizzaOverlayImage = R.drawable.topping_basil
    ),
    Mushroom(
        toppingName = R.string.topping_mushroom,
        toppingDrawable = R.drawable.mushroom,
        pizzaOverlayImage = R.drawable.topping_mushroom
    ),
    Olives(
        toppingName = R.string.topping_olives,
        toppingDrawable = R.drawable.olives,
        pizzaOverlayImage = R.drawable.topping_olive
    ),
    Peppers(
        toppingName = R.string.topping_peppers,
        toppingDrawable = R.drawable.peppers,
        pizzaOverlayImage = R.drawable.topping_peppers
    ),
    Pepperoni(
        toppingName = R.string.topping_pepperoni,
        toppingDrawable = R.drawable.pepperoni,
        pizzaOverlayImage = R.drawable.topping_pepperoni
    ),
    Pineapple(
        toppingName = R.string.topping_pineapple,
        toppingDrawable = R.drawable.pineapple,
        pizzaOverlayImage = R.drawable.topping_pineapple
    ),
}