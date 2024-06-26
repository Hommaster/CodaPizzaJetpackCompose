package com.example.codapizza.productInfo.snack

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun ProductImage(
    modifier: Modifier,
    productName: Int?
) {

    val sizeImageProduct = 370.dp

    Row(
        modifier = modifier
            .padding(32.dp)
    ) {
        val productNameString: String = stringResource(id = productName!!)
        Log.d("productNAme", "$productNameString")
        val productInfo = ProcessingOfProductInformation(productNameString, "null")

        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .align(Alignment.CenterVertically)
//                .size(sizeImagePizza)
                .aspectRatio(1f),
        ) {
            Image(
                painter = painterResource(id = productInfo.getProductImage()),
                contentDescription = "${productInfo.getProductDescription()}",
                modifier = modifier
                    .align(Alignment.Center)
                    .size(sizeImageProduct)
            )
        }
    }

}