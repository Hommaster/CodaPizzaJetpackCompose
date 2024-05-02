package com.example.codapizza.arraypizza

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavHostController
import com.example.codapizza.R
import com.example.codapizza.model.Pizzas
import kotlinx.coroutines.launch

@Composable
fun ArrayOfPizza(navController: NavHostController) {

    val context = LocalContext.current
    val urlVK = "https://vk.com/xzycoc"
    val urlTG = "https://t.me/Pankratar"

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .padding(0.dp, 40.dp, 30.dp, 0.dp)
            ){
                NavigationDrawerItems.entries.forEach { item ->
                    NavigationDrawerItem(
                        label= {
                            Row {
                                Column(
                                    modifier = Modifier
                                        .size(250.dp, 30.dp)
                                ){
                                    Text(
                                        stringResource(id = item.nameItem),
                                        fontSize = 22.sp
                                    )
                                }
                                Image(
                                    modifier = Modifier
                                        .padding(10.dp, 0.dp)
                                        .size(35.dp),
                                    painter = painterResource(id = item.imageItem),
                                    contentDescription = stringResource(id = item.nameItem)
                                )
                            }
                        },
                        selected = false,
                        onClick = {
                            scope.launch { drawerState.close() }
                            when(item.nameItem) {
                                R.string.cart -> {
                                    navController.navigate("cart_screen")
                                }

                                R.string.contact_telegram -> {
                                    val browserIntent = Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse(urlTG)
                                    )
                                    startActivity(context, browserIntent, null)
                                }

                                R.string.contact_vk -> {
                                    val browserIntent = Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse(urlVK)
                                    )
                                    startActivity(context, browserIntent, null)
                                }
                            }
                        }
                    )
                }
            }
        },
        content={
            Column(
                modifier = Modifier
                    .background(Color.Black)
            ){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                ){
                    TopAppBar(
                        modifier = Modifier
                            .fillMaxHeight()
                    ) {
                        IconButton(
                            modifier = Modifier
                                .align(Alignment.Bottom),
                            onClick = {scope.launch {drawerState.open()}},
                            content = { Icon(Icons.Filled.Menu, "Menu") }
                        )
                    }
                }
                LazyColumn {
                    items(Pizzas.entries.toTypedArray()) { pizza ->
                        val pizzaName: String = stringResource(id = pizza.pizzaName)
                        BoxOfPizza(
                            pizza = pizza,
                            onClick = {
                                navController.navigate("screen_2/$pizzaName")
                            }
                        )
                    }
                }
            }
        }
    )
}