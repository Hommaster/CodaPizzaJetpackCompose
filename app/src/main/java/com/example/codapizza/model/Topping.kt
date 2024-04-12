package com.example.codapizza.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.codapizza.R

enum class Topping (
    @StringRes val toppingName: Int,
    @DrawableRes val toppingDrawable: Int
) {
    Basil(
        toppingName = R.string.topping_basil,
        toppingDrawable = R.drawable.basil
    ),
    Mushroom(
        toppingName = R.string.topping_mushroom,
        toppingDrawable = R.drawable.mushroom
    ),
    Olives(
        toppingName = R.string.topping_olives,
        toppingDrawable = R.drawable.olives
    ),
    Peppers(
        toppingName = R.string.topping_peppers,
        toppingDrawable = R.drawable.peppers
    ),
    Pepperoni(
        toppingName = R.string.topping_pepperoni,
        toppingDrawable = R.drawable.pepperoni
    ),
    Pineapple(
        toppingName = R.string.topping_pineapple,
        toppingDrawable = R.drawable.pineapple
    ),
}