package com.example.codapizza.arraypizza.helper

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.codapizza.R
import kotlin.math.roundToInt

@Composable
fun HaveOrderNumber(navController: NavHostController, totalCost: MutableState<Float>) {
    Box(
        modifier = Modifier
            .background(Color.Transparent)
            .padding(250.dp, 720.dp, 0.dp, 0.dp)
            .size(140.dp, 100.dp),
    ) {
        Button(
            modifier = Modifier
                .size(150.dp, 60.dp)
                .align(Alignment.TopStart)
                .padding(0.dp),
            shape = RoundedCornerShape(15.dp),
            onClick = {
                navController.navigate("cart_screen") {
                    popUpTo("screen_1")
                }
            },
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.black))
        ) {
            Image(
                modifier = Modifier
                    .padding(0.dp, 0.dp, 4.dp, 0.dp)
                    .size(30.dp),
                colorFilter = ColorFilter.tint(color = colorResource(id = R.color.orange)),
                painter = painterResource(id = R.drawable.cart),
                contentDescription = stringResource(id = R.string.cart)
            )
            val totalCostAfterRounded = (totalCost.value*100).roundToInt() / 100.0
            Text(
                modifier = Modifier
                    .padding(0.dp)
                    .background(colorResource(id = R.color.black)),
                text = totalCostAfterRounded.toString(),
                fontSize = 15.sp,
                color = colorResource(id = R.color.orange)
            )
        }
    }
}