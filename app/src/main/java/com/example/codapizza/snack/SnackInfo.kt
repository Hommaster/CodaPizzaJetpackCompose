package com.example.codapizza.snack

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.codapizza.R

enum class SnackInfo(
    @StringRes val snackName: Int,
    @DrawableRes val snackImage: Int
) {
    FrenchFries(
        snackName = R.string.french_fries,
        snackImage = R.drawable.french_fries_standart
    ),
    Nuggets(
        snackName = R.string.Nuggets,
        snackImage = R.drawable.nuggets_small
    )
}