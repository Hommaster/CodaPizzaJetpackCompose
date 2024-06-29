package com.example.codapizza.productInfo.snack

import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import com.example.codapizza.R

enum class SnackInfo(
    @IntegerRes val price: Int,
    @StringRes val snackName: Int,
    @DrawableRes val snackImage: Int,
    val drinkInfo: Boolean
) {
    FrenchFries(
        price = 4,
        snackName = R.string.french_fries,
        snackImage = R.drawable.french_fries_standart,
        drinkInfo = false
    ),
    Nuggets(
        price = 4,
        snackName = R.string.Nuggets,
        snackImage = R.drawable.nuggets_small,
        drinkInfo = false
    )
}