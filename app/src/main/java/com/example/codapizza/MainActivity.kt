package com.example.codapizza

import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.codapizza.arraypizza.ArrayOfProducts
import com.example.codapizza.cart.CartUI
import com.example.codapizza.cart.EmptyCartUI
import com.example.codapizza.theme.AppTheme
import com.example.codapizza.pizza.PizzaBuilderScreen
import com.example.codapizza.cart.viewmodel.MainActivityViewModel
import com.example.codapizza.model.Pizza
import com.example.codapizza.model.PizzaType
import com.example.codapizza.orderhistory.OrderHistory

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        val insetsController = WindowCompat.getInsetsController(window, window.decorView)
        insetsController.apply {
            hide(WindowInsetsCompat.Type.navigationBars())
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        setContent {

            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "screen_1") {
                composable(
                    "screen_2/{pizza_name}/{pizza_from_order}/{order_id}",
                    arguments = listOf(
                        navArgument(name = "pizza_name"){
                            type = NavType.StringType
                        },
                        navArgument(name = "pizza_from_order"){
                            type = PizzaType()
                        },
                        navArgument(name = "order_id"){
                            type = NavType.StringType
                        }
                    )
                ) { backstackEntry ->
                    PizzaBuilderScreen(
                        navController,
                        pizzaFromOrder = backstackEntry.arguments?.getParcelable("pizza_from_order", Pizza::class.java),
                        pizzaName = backstackEntry.arguments?.getString("pizza_name"),
                        orderID = backstackEntry.arguments?.getString("order_id")
                    )
                }
                composable("screen_1") {
                    AppTheme {
                        ArrayOfProducts(
                            navController,
                            mainActivityViewModel = MainActivityViewModel()
                        )
                    }
                }
                composable(
                    "cart_screen",
                ) {backStackEntry ->
                    AppTheme {
                        CartUI(
                            navController,
                            mainActivityViewModel = MainActivityViewModel()
                        )
                    }
                }
                composable(
                    "cart_screen_empty"
                ) { navBackStackEntry ->
                    EmptyCartUI(
                        navController
                    )
                }
                composable(
                    "order_history"
                ) {
                    OrderHistory()
                }
            }
        }
    }
}