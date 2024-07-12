package com.example.codapizza.arraypizza.helper

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavHostController
import com.example.codapizza.R
import com.example.codapizza.cart.viewmodel.MainActivityViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun checkNameItemForNavigate(
    nameItem: Int,
    scope: CoroutineScope,
    context: Context,
    navController: NavHostController
) {

    val mainActivityViewModel = MainActivityViewModel()

    val urlVK = "https://vk.com/xzycoc"
    val urlTG = "https://t.me/Pankratar"

    when (nameItem) {
        R.string.cart -> {
            scope.launch {
                mainActivityViewModel.orders.collect {
                    if (it.isEmpty()) {
                        navController.navigate("cart_screen_empty") {
                            popUpTo("screen_1")
                        }
                    } else {
                        navController.navigate("cart_screen") {
                            popUpTo("screen_1")
                        }
                    }
                }
            }
        }

        R.string.order_history -> {
            navController.navigate("order_history") {
                popUpTo("screen_1")
            }
        }

        R.string.contact_telegram -> {
            val browserIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(urlTG)
            )
            startActivity(context, browserIntent, null)
        }

        R.string.contact_vk -> {
            val browserIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(urlVK)
            )
            startActivity(context, browserIntent, null)
        }
    }
}