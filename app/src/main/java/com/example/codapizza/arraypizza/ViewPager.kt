package com.example.codapizza.arraypizza

import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.codapizza.R
import com.example.codapizza.model.Pizza
import com.example.codapizza.model.Pizzas
import com.example.codapizza.snack.SnackInfo
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
    Row(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.Center
    ){
        repeat(pagerState.pageCount) {
            val color = if(pagerState.currentPage == it) colorResource(id = R.color.orange) else colorResource(
                id = R.color.white
            )
            var textButton: Int = R.string.view_pager_info_pizzas
            when(it) {
                0 -> textButton = R.string.view_pager_info_pizzas
                1 -> textButton = R.string.view_pager_info_snacks
            }

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
                    text = stringResource(id = textButton)
                )
            }
        }
    }

    VerticalPager(
        state = pagerState
    ) {page ->
        when(page){
            0 -> {
                LazyColumn {
                    items(Pizzas.entries.toTypedArray()) { pizza ->
                        val pizzaName: String = stringResource(id = pizza.pizzaName)
                        val pizzaWithArrayOfPizza = Pizza(
                            pizzaName = "pizzaWithArrayOfPizza"
                        )
                        val json = Uri.encode(Gson().toJson(pizzaWithArrayOfPizza))
                        BoxOfProduct(
                            pizza = pizza,
                            snack = null,
                            onClick = {
                                navController.navigate("screen_2/$pizzaName/$json/1") {
                                    popUpTo("screen_1")
                                }
                            }
                        )
                    }
                }
            }
            1 -> {
                LazyColumn {
                    items(SnackInfo.entries.toTypedArray()) { snack ->
//                                    val pizzaName: String = stringResource(id = pizza.pizzaName)
//                                    val pizzaWithArrayOfPizza = Pizza(
//                                        pizzaName = "pizzaWithArrayOfPizza"
//                                    )
//                                    val json = Uri.encode(Gson().toJson(pizzaWithArrayOfPizza))
                        BoxOfProduct(
                            pizza = null,
                            snack = snack,
                            onClick = {

                            }
                        )
                    }
                }
            }
        }
    }
}