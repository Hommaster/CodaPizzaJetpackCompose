package com.example.codapizza.model

import androidx.annotation.StringRes
import com.example.codapizza.R

enum class SizePizza(
    @StringRes val sizeName: Int
) {
    Small(
        sizeName = R.string.size_small
    ),
    Average(
        sizeName = R.string.size_average
    ),
    Big(
        sizeName = R.string.size_big
    ),
    VeryBig(
        sizeName = R.string.size_very_big
    )
}
