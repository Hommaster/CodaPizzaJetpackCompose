package com.example.codapizza.productInfo.pizza

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.ExposedDropdownMenuBox
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.ExposedDropdownMenuDefaults
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.codapizza.R
import com.example.codapizza.model.SizePizza
import com.example.codapizza.productInfo.snack.ProductInfoData
import com.example.codapizza.productInfo.snack.SizeProductNotPizza

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ToppingCellDropdownMenu(
    product: ProductInfoData,
    setSizePizza: (sizePizza: SizePizza) -> Unit,
    setSizeProduct: (sizeProduct: SizeProductNotPizza) -> Unit
) {
    val context = LocalContext.current

    val smallString = stringResource(id = SizePizza.Small.sizeName)
    val averageString = stringResource(id = SizePizza.Average.sizeName)
    val bigString = stringResource(id = SizePizza.Big.sizeName)
    val veryBigString = stringResource(id = SizePizza.VeryBig.sizeName)

    val standardSizeProduct = stringResource(id = SizeProductNotPizza.Standard.sizeProduct)
    val bigSizeProduct = stringResource(id = SizeProductNotPizza.Big.sizeProduct)

    val pizzaSizeChane = if(product.productName == null) {
        arrayOf(smallString, averageString, bigString, veryBigString)
    } else {
        arrayOf(standardSizeProduct, bigSizeProduct)
    }
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(pizzaSizeChane[0]) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        ExposedDropdownMenuBox(
            modifier = Modifier
                .width(240.dp)
                .clip(RoundedCornerShape(26.dp))
                .background(colorResource(id = R.color.orange))
                .align(Alignment.Center),
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            },
        ) {
            TextField(
                modifier = Modifier,
                value = selectedText,
                textStyle = TextStyle(
                    color = colorResource(id = R.color.white),
                    fontSize = 18.sp
                ),
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .clip(RoundedCornerShape(2.dp))
                    .border(0.dp, Color.White, RoundedCornerShape(2.dp))
                    .background(Color.Transparent),
            ) {
                pizzaSizeChane.forEach { item ->
                    DropdownMenuItem(
                        content = { Text(text = item) },
                        onClick = {
                            selectedText = item
                            expanded = false

                            when (selectedText) {
                                smallString -> setSizePizza(SizePizza.Small)
                                averageString -> setSizePizza(SizePizza.Average)
                                bigString -> {
                                    if(product.productName == null){
                                        setSizePizza(SizePizza.Big)
                                    } else {
                                        setSizeProduct(SizeProductNotPizza.Big)
                                    }
                                }
                                veryBigString -> setSizePizza(SizePizza.VeryBig)
                                standardSizeProduct -> setSizeProduct(SizeProductNotPizza.Standard)
                            }
                            Log.d("InfoSize", selectedText)
                            Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                        },
                    )
                }
            }
        }
    }
}