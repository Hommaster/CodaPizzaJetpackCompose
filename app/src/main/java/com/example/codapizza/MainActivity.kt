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
import com.example.codapizza.cart.viewmodel.MainActivityViewModel
import com.example.codapizza.model.Pizza
import com.example.codapizza.model.PizzaInfoType
import com.example.codapizza.model.PizzaType
import com.example.codapizza.model.Pizzas
import com.example.codapizza.orderhistory.OrderHistory
import com.example.codapizza.productInfo.pizza.PizzaBuilderScreen
import com.example.codapizza.productInfo.snack.ProductInfoData
import com.example.codapizza.productInfo.snack.ProductNPBuilderScreen
import com.example.codapizza.productInfo.snack.ProductInfoType

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
                    "screen_2_v_2/{pizza_info}/{pizza_from_order}/{pizza_name}/{order_id}/{product_np_from_order}/{product_name}",
                    arguments = listOf(
                        navArgument(name = "pizza_info"){
                            type = PizzaInfoType()
                        },
                        navArgument(name = "pizza_from_order"){
                            type = PizzaType()
                        },
                        navArgument(name = "pizza_name"){
                            type = NavType.StringType
                        },
                        navArgument(name = "order_id"){
                            type = NavType.StringType
                        },
                        navArgument(name = "product_name"){
                            type = NavType.IntType
                        },
                        navArgument(name = "product_np_from_order"){
                            type = ProductInfoType()
                        },
                    )
                ) {navBackStackEntry ->
                    PizzaBuilderScreen(
                        navController,
                        pizzaInfo = navBackStackEntry.arguments?.getParcelable("pizza_info", Pizzas::class.java),
                        pizzaFromOrder = navBackStackEntry.arguments?.getParcelable("pizza_from_order", Pizza::class.java),
                        pizzaName = navBackStackEntry.arguments?.getString("pizza_name"),
                        orderID = navBackStackEntry.arguments?.getString("order_id"),
                        productInfoData = navBackStackEntry.arguments?.getParcelable("product_np_from_order", ProductInfoData::class.java),
                        productName = navBackStackEntry.arguments?.getInt("product_name"),
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
                ) {
                    AppTheme {
                        CartUI(
                            navController,
                            mainActivityViewModel = MainActivityViewModel()
                        )
                    }
                }
                composable(
                    "cart_screen_empty"
                ) {
                    EmptyCartUI(
                        navController
                    )
                }
                composable(
                    "order_history"
                ) {
                    OrderHistory(
                        navController
                    )
                }
                composable(
                    "product_np_builder_screen/{product_name}/{order_ID}/{product_np_from_order}",
                    arguments = listOf(
                        navArgument(name = "product_name"){
                            type = NavType.IntType
                        },
                        navArgument(name = "product_np_from_order"){
                            type = ProductInfoType()
                        },
                        navArgument(name = "order_ID"){
                            type = NavType.StringType
                        }
                    )

                ) { backStackEntry ->

                    ProductNPBuilderScreen(
                        navController,
                        productName = backStackEntry.arguments?.getInt("product_name"),
                        orderID = backStackEntry.arguments?.getString("order_ID"),
                        productNPFromOrder = backStackEntry.arguments?.getParcelable("product_np_from_order", ProductInfoData::class.java)
                    )
                }
            }
        }
    }
}