package com.example.codapizza.desygnfiles

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
                .height(80.dp),
        ) {
            TopAppBarCustom(
                title = {
                    InfoForTopAppBar(
                        textID = R.string.cart
                    )
                    IconButton(
                        modifier = Modifier
                            .padding(300.dp, 20.dp, 0.dp, 0.dp),
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
                }
            )
        }
    }
}