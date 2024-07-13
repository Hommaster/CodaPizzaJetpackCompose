package com.example.codapizza.orderhistory.helper

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.codapizza.R

@Composable
fun NoInternetOrderHistory(
    modifier: Modifier,
){
    Column(
        modifier = modifier
    ) {
        Card(
            shape = RoundedCornerShape(15.dp),
            colors = CardColors(
                colorResource(id = R.color.orange),
                Color.Cyan,
                Color.Cyan,
                Color.Cyan
            )
        ) {
            Column {
                Text(
                    modifier = Modifier
                        .padding(2.dp, 2.dp),
                    color = colorResource(id = R.color.white),
                    text = stringResource(id = R.string.do_not_internet),
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                )
                Text(
                    modifier = Modifier
                        .padding(2.dp, 2.dp),
                    color = colorResource(id = R.color.white),
                    text = stringResource(id = R.string.do_not_internet_order_history),
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                )
            }
        }

    }
}