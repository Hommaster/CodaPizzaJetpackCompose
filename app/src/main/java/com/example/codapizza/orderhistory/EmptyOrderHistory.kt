package com.example.codapizza.orderhistory

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.codapizza.R

@Composable
fun EmptyOrderHistory(
    modifier: Modifier,
    navController: NavController
) {
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
            Text(
                modifier = Modifier
                    .padding(2.dp, 2.dp),
                color = colorResource(id = R.color.white),
                text = stringResource(id = R.string.order_history_is_empty),
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                style = TextStyle(textIndent = TextIndent(15.sp, 30.sp))
            )
            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            ) {
                TextButton(onClick = {
                    navController.popBackStack("screen_1", false)
                }) {
                    Text(
                        color = colorResource(id = R.color.black),
                        text = stringResource(id = R.string.back_to_menu),
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                    )
                }
            }
        }
    }
}