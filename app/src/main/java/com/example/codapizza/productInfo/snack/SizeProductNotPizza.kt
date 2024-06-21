package com.example.codapizza.productInfo.snack

import androidx.annotation.StringRes
import com.example.codapizza.R

enum class SizeProductNotPizza(
    @StringRes val sizeProduct: Int
) {
    Standard(
        sizeProduct = R.string.size_small_snack
    ),
    Big(
        sizeProduct = R.string.size_big_snack
    )
}