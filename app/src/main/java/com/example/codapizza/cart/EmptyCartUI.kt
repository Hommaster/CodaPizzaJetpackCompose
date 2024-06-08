package com.example.codapizza.cart

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberSwipeToDismissBoxState
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.codapizza.R
import com.example.codapizza.arraypizza.ArrayOfPizza
import com.example.codapizza.cart.viewmodel.MainActivityViewModel
import com.example.codapizza.theme.AppTheme

@Preview
@Composable
fun PreviewEmptyCartUI(
    
){
    EmptyCartUI(navController = rememberNavController())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmptyCartUI(
    navController: NavController
){
    val dismissState = rememberSwipeToDismissBoxState()
    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = {
            when(dismissState.targetValue){
                SwipeToDismissBoxValue.EndToStart -> {
                    AppTheme {
                        ArrayOfPizza(
                            rememberNavController(),
                            MainActivityViewModel()
                        )
                    }
                    if(dismissState.progress.toDouble() == 1.0) {
                        navController.popBackStack("screen_1", false)
                    }
                }
                SwipeToDismissBoxValue.StartToEnd -> {
                    AppTheme {
                        ArrayOfPizza(
                            rememberNavController(),
                            MainActivityViewModel()
                        )
                    }
                    if(dismissState.progress.toDouble() == 1.0) {
                        navController.popBackStack("screen_1", false)
                    }
                }
                else -> null
            }
            Log.d("INFOprogress", dismissState.progress.toString())
        }
    ) {
        Column(
            modifier = Modifier
                .background(colorResource(id = R.color.black))
                .fillMaxSize(),
        ) {
            Card(
                modifier = Modifier
                    .padding(0.dp, 0.dp),
                shape = RoundedCornerShape(0.dp, 0.dp, 15.dp, 15.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(colorResource(id = R.color.orange))
                        .height(80.dp)
                ) {

                }
            }

            Column(
                modifier = Modifier
                    .padding(30.dp, 250.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Card(
                    shape = RoundedCornerShape(15.dp),
                    colors = CardColors(colorResource(id = R.color.springgreen), Color.Cyan, Color.Cyan, Color.Cyan)
                ) {
                    Text(
                        color = colorResource(id = R.color.orange),
                        text = stringResource(id = R.string.cart_is_empty),
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        style = TextStyle(textIndent = TextIndent(15.sp, 30.sp))
                    )
                    Row(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                    ){
                        TextButton(onClick = {
                            navController.popBackStack("screen_1", false)
                        }) {
                            Text(
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
    }
}