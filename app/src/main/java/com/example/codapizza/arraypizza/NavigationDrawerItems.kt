package com.example.codapizza.arraypizza

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.codapizza.R

enum class NavigationDrawerItems(
    @StringRes val nameItem: Int,
    @DrawableRes val imageItem: Int,
) {
    Cart(
        nameItem = R.string.cart,
        imageItem = R.drawable.cart
    ),
    Telegram(
        nameItem = R.string.contact_telegram,
        imageItem = R.drawable.telegram,
    ),
    VK(
        nameItem = R.string.contact_vk,
        imageItem = R.drawable.vk
    ),

}