package com.example.codapizza.arraypizza

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.codapizza.R

enum class NavigationDrawerItems(
    val navigate: @Composable () -> Unit,
    @StringRes val nameItem: Int,
    @DrawableRes val imageItem: Int,
) {
    Telegram(
        nameItem = R.string.contact_telegram,
        imageItem = R.drawable.telegram,
        navigate = {
            val context = androidx.compose.ui.platform.LocalContext.current
            val urlTG = "https://t.me/Pankratar"
            val browserIntent = android.content.Intent(
                android.content.Intent.ACTION_VIEW,
                android.net.Uri.parse(urlTG)
            )
            androidx.core.content.ContextCompat.startActivity(context, browserIntent, null)
        }
    )

}