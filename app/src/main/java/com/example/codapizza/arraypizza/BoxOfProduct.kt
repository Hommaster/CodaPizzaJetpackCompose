package com.example.codapizza.arraypizza

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.MaterialTheme
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.codapizza.R
import com.example.codapizza.model.Pizzas
import com.example.codapizza.productInfo.drinks.DrinkInfo
import com.example.codapizza.productInfo.snack.SnackInfo


@Composable
fun BoxOfProduct(
    pizza: Pizzas?,
    snack: SnackInfo?,
    drink: DrinkInfo?,
    onClick: () -> Unit
) {

    val product = ProductCheck(pizza, snack, drink)

    Box(
        modifier = Modifier
            .padding(14.dp, 6.dp)
            .clip(RoundedCornerShape(23.dp))
            .background(Color.White)
            .clickable { onClick() }
    ){
        Column{
            Image(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(0.dp, 15.dp)
                    .size(250.dp),
                painter = painterResource(id = product.getProductImageInt()),
                contentDescription = stringResource(id = product.getProductNameInt())
            )
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(id = R.color.orange))
                    .clickable { onClick() }
            ){
                Column(
                    modifier = Modifier
                        .padding(20.dp, 1.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .padding(10.dp, 2.dp),
                        text = stringResource(id = product.getProductNameInt()),
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        style = MaterialTheme.typography.body1
                    )
                    if(pizza != null && snack == null) {
                        Column {
                            Row {
                                Text(
                                    text = stringResource(id = product.getProductIngredientsInt()!!),
                                    style = MaterialTheme.typography.body2,
                                    color = Color.White
                                )
                            }
                            Text(
                                text = stringResource(id = pizza.pricePizza),
                                style = MaterialTheme.typography.body2,
                                fontSize = 20.sp,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}