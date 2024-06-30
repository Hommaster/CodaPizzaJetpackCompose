package com.example.codapizza.arraypizza.viewpager

import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.codapizza.R
import com.example.codapizza.arraypizza.BoxOfProduct
import com.example.codapizza.model.Pizzas
import com.example.codapizza.model.SizePizza
import com.example.codapizza.productInfo.drinks.DrinkInfo
import com.example.codapizza.productInfo.snack.ProductInfoData
import com.example.codapizza.productInfo.snack.SizeProductNotPizza
import com.example.codapizza.productInfo.snack.SnackInfo
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ViewPager(
    pagerState: PagerState,
    scope: CoroutineScope,
    navController: NavController,
) {
    LazyRow(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.Center
    ){
        item {
            repeat(pagerState.pageCount) {
                val color = if(pagerState.currentPage == it) colorResource(id = R.color.orange) else colorResource(
                    id = R.color.white
                )
                val colorText = if(pagerState.currentPage == it) colorResource(id = R.color.white) else colorResource(
                    id = R.color.orange
                )
                val textButton: MutableList<Int> = mutableListOf(R.string.view_pager_info_pizzas, R.string.view_pager_info_snacks, R.string.view_pager_info_drinks)


                Button(
                    modifier = Modifier
                        .padding(2.dp),
                    shape = RoundedCornerShape(15.dp),
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(it)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(color)
                ) {
                    Text(
                        modifier = Modifier
                            .background(color),
                        text = stringResource(id = textButton[it]),
                        color = colorText
                    )
                }
            }
        }

    }

    VerticalPager(
        state = pagerState
    ) {page ->
        LazyColumn {
            when(page){
                0 -> {
                    items(Pizzas.entries.toTypedArray()) { pizza ->
                        val pizzaName: String = stringResource(id = pizza.pizzaName)

                        val productFromMainScreen = ProductInfoData(
                            pizzaName = pizzaName,
                            productName = null,
                            productSize = null,
                            sizePizza = SizePizza.Small,
                            priceProduct = pizza.price.toFloat()
                        )
                        val json3 = Uri.encode(Gson().toJson(productFromMainScreen))
                        val productName: Int = -1
                        BoxOfProduct(
                            pizza = pizza,
                            snack = null,
                            drink = null,
                            onClick = {
                                navController.navigate("screen_2_v_2/$pizzaName/1/$json3/$productName/$page") {
                                    popUpTo("screen_1")
                                }
                            }
                        )
                    }
                }
                1 -> {
                    items(SnackInfo.entries.toTypedArray()) { snack ->

                        val pizzaName = "pizzaWithArrayOfPizza"

                        val snackName = snack.snackName

                        val productFromMainScreen = ProductInfoData(
                            pizzaName = null,
                            productName = snackName,
                            productSize = SizeProductNotPizza.Standard,
                            sizePizza = null,
                            priceProduct = snack.price.toFloat()
                        )

                        val json3 = Uri.encode(Gson().toJson(productFromMainScreen))

                        BoxOfProduct(
                            pizza = null,
                            snack = snack,
                            drink = null,
                            onClick = {
                                navController.navigate("screen_2_v_2/$pizzaName/1/$json3/$snackName/$page") {
                                    popUpTo("screen_1")
                                }
                            }
                        )
                    }
                }
                2 -> {
                    items(DrinkInfo.entries.toTypedArray()) { drink ->

                        val pizzaName = "pizzaWithArrayOfPizza"

                        val drinkName = drink.drinkName

                        val productFromMainScreen = ProductInfoData(
                            pizzaName = null,
                            productName = drinkName,
                            productSize = SizeProductNotPizza.Standard,
                            sizePizza = null,
                            priceProduct = drink.price.toFloat()
                        )
                        val json = Uri.encode(Gson().toJson(productFromMainScreen))

                        BoxOfProduct(
                            pizza = null,
                            snack = null,
                            drink = drink,
                            onClick = {
                                navController.navigate("screen_2_v_2/$pizzaName/1/$json/$drinkName/$page") {
                                    popUpTo("screen_1")
                                }
                            }
                        )
                    }
                }
            }
        }

    }
}