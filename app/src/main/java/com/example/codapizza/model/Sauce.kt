package com.example.codapizza.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.codapizza.R

enum class Sauce (
    @StringRes val sauceName: Int,
    @DrawableRes val sauceDrawable: Int
) {
    Barbecue(
        sauceName = R.string.sauce_barbecue,
        sauceDrawable = R.drawable.barbecue_sauce
    ),
    Cheese(
        sauceName = R.string.sauce_cheese,
        sauceDrawable = R.drawable.cheese_sauce
    ),
    Garlic(
        sauceName = R.string.sauce_garlic,
        sauceDrawable = R.drawable.garlic_sauce
    ),
    Ketchup(
        sauceName = R.string.sauce_ketchup,
        sauceDrawable = R.drawable.ketchup_sauce
    ),
    SweetChili(
        sauceName = R.string.sauce_sweet_chili,
        sauceDrawable = R.drawable.sweet_chili_sauce
    )


}