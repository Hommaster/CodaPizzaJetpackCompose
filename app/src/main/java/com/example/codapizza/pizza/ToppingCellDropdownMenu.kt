package com.example.codapizza.pizza

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
import androidx.compose.material.MaterialTheme
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.codapizza.model.SizePizza

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ToppingCellDropdownMenu(
    setSizePizza: (sizePizza: SizePizza) -> Unit
) {
    val context = LocalContext.current

    val smallString = stringResource(id = SizePizza.Small.sizeName)
    val averageString = stringResource(id = SizePizza.Average.sizeName)
    val bigString = stringResource(id = SizePizza.Big.sizeName)
    val veryBigString = stringResource(id = SizePizza.VeryBig.sizeName)

    val coffeeDrinks = arrayOf(smallString, averageString, bigString, veryBigString)
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(coffeeDrinks[2]) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        ExposedDropdownMenuBox(
            modifier = Modifier
                .width(140.dp)
                .clip(RoundedCornerShape(26.dp))
                .border(3.dp, Color.Yellow, RoundedCornerShape(26.dp))
                .background(Color.Cyan)
                .align(Alignment.Center),
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                modifier = Modifier,
                value = selectedText,
                textStyle = MaterialTheme.typography.body1,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .border(1.5.dp, Color.Yellow)
                    .background(Color.Transparent),
            ) {
                coffeeDrinks.forEach { item ->
                    DropdownMenuItem(
                        content = { Text(text = item) },
                        onClick = {
                            selectedText = item
                            expanded = false

                            when (selectedText) {
                                smallString -> setSizePizza(SizePizza.Small)
                                averageString -> setSizePizza(SizePizza.Average)
                                bigString -> setSizePizza(SizePizza.Big)
                                veryBigString -> setSizePizza(SizePizza.VeryBig)
                            }
                            Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                        },
                    )
                }
            }
        }
    }
}