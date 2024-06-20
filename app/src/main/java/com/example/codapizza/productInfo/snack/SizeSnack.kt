package com.example.codapizza.productInfo.snack

import androidx.annotation.StringRes
import com.example.codapizza.R

enum class SizeSnack(
    @StringRes val sizeSnack: Int
) {
    Standard(
        sizeSnack = R.string.size_small_snack
    ),
    Big(
        sizeSnack = R.string.size_big_snack
    )
}