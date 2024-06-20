package com.example.codapizza.productInfo.drinks

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.codapizza.R

enum class DrinkInfo(
    @StringRes val drinkName: Int,
    @DrawableRes val drinkImage: Int
) {

    CocaCola(
        drinkName = R.string.coca_cola,
        drinkImage = R.drawable.cola_05
    ),
    Sprite(
        drinkName = R.string.sprite,
        drinkImage = R.drawable.sprite_05
    ),
    Fanta(
        drinkName = R.string.fanta,
        drinkImage = R.drawable.fanta_05
    ),

}