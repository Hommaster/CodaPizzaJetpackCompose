package com.example.codapizza

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.MutableState
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.codapizza.arraypizza.ArrayOfPizza
import com.example.codapizza.cart.CartUI
import com.example.codapizza.cart.EmptyCartUI
import com.example.codapizza.cart.database.Order
import com.example.codapizza.theme.AppTheme
import com.example.codapizza.pizza.PizzaBuilderScreen
import com.example.codapizza.cart.viewmodel.MainActivityViewModel

class MainActivity : AppCompatActivity() {
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
                    "screen_2/{pizza_name}",
                    arguments = listOf(
                        navArgument(name = "pizza_name"){
                            type = NavType.StringType
                        }
                    )
                ) { backstackEntry ->
                    PizzaBuilderScreen(
                        navController,
                        pizzaName = backstackEntry.arguments?.getString("pizza_name")
                    )
                }
                composable("screen_1") {
                    AppTheme {
                        ArrayOfPizza(
                            navController,
                            mainActivityViewModel = MainActivityViewModel()
                        )
                    }
                }
                composable(
                    "cart_screen",
                ) {backStackEntry ->
                    CartUI(
                        navController,
                        mainActivityViewModel = MainActivityViewModel()
                    )
                }
                composable(
                    "cart_screen_empty"
                ) { navBackStackEntry ->
                    EmptyCartUI(
                        navController
                    )
                }
            }
        }
    }
}