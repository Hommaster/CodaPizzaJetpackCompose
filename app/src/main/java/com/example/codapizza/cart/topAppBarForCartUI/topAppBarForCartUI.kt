package com.example.codapizza.cart.topAppBarForCartUI

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.codapizza.R
import com.example.codapizza.cart.viewmodel.MainActivityViewModel
import kotlinx.coroutines.launch

@Composable
fun TopAppBarForCartUI(
    navController: NavHostController,
    mainActivityViewModel: MainActivityViewModel,
) {
    val coroutineScope = rememberCoroutineScope()

    Card(
        modifier = Modifier
            .padding(0.dp, 0.dp),
        shape = RoundedCornerShape(0.dp, 0.dp, 15.dp, 15.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.End)
                .height(80.dp),
        ){
            TopAppBar(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.Bottom)
                        .padding(160.dp, 0.dp, 0.dp, 10.dp),
                    text = stringResource(id = R.string.cart),
                    fontSize = 25.sp
                    )
                IconButton(
                    modifier = Modifier
                        .align(Alignment.Bottom)
                        .padding(60.dp, 4.dp, 0.dp, 0.dp),
                    onClick = {
                        coroutineScope.launch {
                            mainActivityViewModel.deleteAll()
                        }
                        navController.navigate("cart_screen_empty")
                    },
                    content = {
                        Icon(
                            Icons.Filled.Delete, "Menu"
                        )
                    }
                )
                IconButton(
                    modifier = Modifier
                        .align(Alignment.Bottom)
                        .padding(10.dp, 4.dp, 0.dp, 4.dp),
                    onClick = {
                        navController.popBackStack("screen_1", false)
                    },
                    content = {
                        Icon(
                            modifier = Modifier
                                .scale(1.7f),
                            painter = painterResource(id = R.drawable.button_close),
                            contentDescription = "return_to_menu",
                        )
                    }
                )
            }
        }
    }
}