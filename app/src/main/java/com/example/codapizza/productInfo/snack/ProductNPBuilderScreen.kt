package com.example.codapizza.productInfo.snack

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.codapizza.R
import com.example.codapizza.cart.swipetodismiss.SwipeToDismiss
import com.example.codapizza.desygnfiles.BoxWithImageScrollToDismiss

@Composable
fun ProductNPBuilderScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    orderID: String?,
    productName: Int?,
    productNPFromOrder: ProductInfoData?,
) {
    var product by rememberSaveable {
        when(productNPFromOrder!!.productName) {
            0 -> {
                mutableStateOf(ProductInfoData(productName = productName))
            }
            else -> {
                mutableStateOf(productNPFromOrder)
            }
        }
    }

    val nameOfProduct = if(productNPFromOrder!!.productName == 0) productName else productNPFromOrder.productName

    SwipeToDismiss(
        navController
    ) {
        Box {
            Column(
                modifier = modifier
                    .background(Color.Black)
                    .padding(0.dp, 30.dp)
            ) {

                ProductImage(
                    modifier = modifier,
                    productName = nameOfProduct
                )

            }
            BoxWithImageScrollToDismiss(
                modifier = Modifier
                    .padding(0.dp, 30.dp),
                colorFilter = R.color.orange
            )
        }

    }
}