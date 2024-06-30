package com.example.codapizza.cart.boxOfOrderUI

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.codapizza.R
import com.example.codapizza.cart.database.Orders
import com.example.codapizza.cart.viewmodel.MainActivityViewModel
import com.example.codapizza.model.SizePizza
import com.example.codapizza.productInfo.snack.ProcessingOfProductInformation
import com.example.codapizza.productInfo.snack.ProductInfoData
import com.example.codapizza.productInfo.snack.SizeProductNotPizza
import com.google.gson.Gson

@Composable
fun BoxOfOrder(
    mainActivityViewModel: MainActivityViewModel,
    navController: NavHostController,
    onClick: () -> Unit,
    order: Orders
) {

    val orderIdToString = order.id.toString()

    val productInfo = ProcessingOfProductInformation(order.title, order.title, order.productID)

    val title = stringResource(id = productInfo.getProductName())

    val titleAnother = productInfo.getNotPizzaName()

    val priceProduct = productInfo.getPriceProduct().toFloat()

    val productFromMainScreen = ProductInfoData(
        pizzaName = title,
        sauces = order.sauce,
        toppings = order.toppings,
        productName = titleAnother,
        sizePizza = SizePizza.Small,
        productSize = SizeProductNotPizza.Standard,
        priceProduct = priceProduct
    )
    val json3 = Uri.encode(Gson().toJson(productFromMainScreen))
    val productName: Int = productInfo.getProductIDFromOrder()

    Card(
        modifier = Modifier
            .padding(0.dp, 4.dp),
        shape = RoundedCornerShape(23.dp),
        colors = CardColors(colorResource(id = R.color.orange), Color.Black, Color.White, Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(4.dp, 6.dp)
                .fillMaxSize(),
        ){
            Image(
                painter = painterResource(id = order.image!!),
                contentDescription = order.title
            )
            Column(
                modifier = Modifier
                    .padding(10.dp, 0.dp)
                    .weight(1f, fill = true)
            ){
                Text(
                    modifier = Modifier
                        .padding(20.dp, 0.dp, 0.dp, 0.dp),
                    text = order.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    fontStyle = FontStyle.Italic
                )
                order.toppings.forEach{
                    Text(
                        text = "${it.key}: ${it.value}, ",
                        fontSize = 15.sp
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .padding(0.dp, 4.dp)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ){
            Text(
                modifier = Modifier
                    .padding(10.dp, 0.dp, 0.dp, 0.dp),
                text = order.price.toString(),
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier
                    .clickable {
                        navController.navigate("screen_2_v_2/${productFromMainScreen.pizzaName}/$orderIdToString/$json3/$productName/${order.productID}")
                    },
                text = stringResource(id = R.string.change_pizza),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            RowWithQuantityOrder(
                mainActivityViewModel,
                navController,
                onClick = onClick,
                order
            )
        }
    }

}