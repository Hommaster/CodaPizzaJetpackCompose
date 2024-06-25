package com.example.codapizza.productInfo.snack

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.codapizza.R
import com.example.codapizza.cart.swipetodismiss.SwipeToDismiss
import com.example.codapizza.desygnfiles.BoxWithImageScrollToDismiss
import com.example.codapizza.model.Sauce
import com.example.codapizza.sauce.SauceCell

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
                mutableStateOf(ProductInfoData(
                    productName = productName,
                    pizzaName = null
                ))
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
                ToppingNPList(
                    productInfoData = product,
                    onEditTopping = { product = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f, fill = true)
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

@Composable
private fun ToppingNPList(
    productInfoData: ProductInfoData,
    onEditTopping: (ProductInfoData) -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "infinite transition")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.02f,
        animationSpec = infiniteRepeatable(tween(15000), RepeatMode.Reverse),
        label = "scale"
    )


    var sauceBeingAdd by rememberSaveable {
        mutableStateOf<Map<Sauce?, Int?>>(emptyMap())
    }

    sauceBeingAdd.let{
        for((key, value) in it) {
            onEditTopping(productInfoData.withQuantity(key!!, value))
        }
    }


    LazyColumn(
        modifier = modifier
    ) {
        items(Sauce.entries.toTypedArray()) { sauce ->
            SauceCell(
                sauce = sauce,
                quantity = productInfoData.sauces[sauce],
                isChecked = productInfoData.sauces[sauce] != null,
                onClickSauce = {
                    sauceBeingAdd = mapOf(sauce to it)
                },
                modifier = modifier
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        transformOrigin = TransformOrigin.Center
                    }
            )
        }
    }
}