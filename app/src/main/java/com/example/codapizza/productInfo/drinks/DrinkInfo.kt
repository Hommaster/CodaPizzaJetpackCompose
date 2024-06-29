package com.example.codapizza.productInfo.drinks

import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import com.example.codapizza.R

enum class DrinkInfo(
    @IntegerRes val price: Int,
    @StringRes val drinkName: Int,
    @DrawableRes val drinkImage: Int,
    val drinkInfo: Boolean
) {

    CocaCola(
        price = 2,
        drinkName = R.string.coca_cola,
        drinkImage = R.drawable.cola_05,
        drinkInfo = true
    ),
    Sprite(
        price = 2,
        drinkName = R.string.sprite,
        drinkImage = R.drawable.sprite_05,
        drinkInfo = true
    ),
    Fanta(
        price = 2,
        drinkName = R.string.fanta,
        drinkImage = R.drawable.fanta_05,
        drinkInfo = true
    ),

}