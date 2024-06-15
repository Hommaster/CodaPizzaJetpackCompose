package com.example.codapizza.arraypizza

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavHostController
import com.example.codapizza.R
import com.example.codapizza.cart.viewmodel.MainActivityViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ArrayOfProducts(
    navController: NavHostController,
    mainActivityViewModel: MainActivityViewModel,
) {

    val context = LocalContext.current
    val urlVK = "https://vk.com/xzycoc"
    val urlTG = "https://t.me/Pankratar"

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val haveOrder: MutableState<Int> = rememberSaveable {
        mutableStateOf(0)
    }

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet(
                    modifier = Modifier
                        .padding(0.dp, 40.dp, 67.dp, 0.dp),
                    drawerContainerColor = colorResource(id = R.color.orange)
                ) {
                    NavigationDrawerItems.entries.forEach { item ->
                        NavigationDrawerItem(
                            colors = NavigationDrawerItemDefaults.colors(
                                unselectedContainerColor = colorResource(R.color.orange)
                            ),
                            label = {
                                Row {
                                    Image(
                                        modifier = Modifier
                                            .size(35.dp),
                                        painter = painterResource(id = item.imageItem),
                                        contentDescription = stringResource(id = item.nameItem)
                                    )
                                    Column(
                                        modifier = Modifier
                                            .padding(20.dp, 0.dp)
                                            .size(250.dp, 30.dp)
                                    ) {
                                        Text(
                                            stringResource(id = item.nameItem),
                                            fontSize = 22.sp
                                        )
                                    }
                                }
                            },
                            selected = false,
                            onClick = {
                                scope.launch {
                                    drawerState.close()
                                }
                                when (item.nameItem) {
                                    R.string.cart -> {
                                        scope.launch {
                                            mainActivityViewModel.orders.collect {
                                                if (it.isEmpty()) {
                                                    navController.navigate("cart_screen_empty") {
                                                        popUpTo("screen_1")
                                                    }
                                                } else {
                                                    navController.navigate("cart_screen") {
                                                        popUpTo("screen_1")
                                                    }
                                                }
                                            }
                                        }
                                    }

                                    R.string.order_history -> {
                                        navController.navigate("order_history") {
                                            popUpTo("screen_1")
                                        }
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
            content = {
                Box {
                    LaunchedEffect(key1 = Unit) {
                        scope.launch {
                            mainActivityViewModel.orders.collect{
                                it.size
                                haveOrder.value = it.size
                            }
                        }
                    }
                    Column(
                        modifier = Modifier
                            .background(colorResource(id = R.color.black))
                    ) {
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
                                TopAppBar(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                ) {
                                    IconButton(
                                        modifier = Modifier
                                            .align(Alignment.Bottom),
                                        onClick = { scope.launch { drawerState.open() } },
                                        content = { Icon(Icons.Filled.Menu, "Menu") }
                                    )
                                }
                            }
                        }

                        val pagerState = rememberPagerState(pageCount = {
                            2
                        })
                        ViewPager(
                            pagerState = pagerState,
                            scope = scope,
                            navController = navController
                        )
                    }
                    if(haveOrder.value > 0) {
                        Box(
                            modifier = Modifier
                                .background(Color.Transparent)
                                .padding(270.dp, 720.dp, 0.dp, 0.dp)
                                .size(120.dp, 60.dp),
                        ) {
                            Button(
                                modifier = Modifier
                                    .padding(2.dp),
                                shape = RoundedCornerShape(15.dp),
                                onClick = {
                                    navController.navigate("cart_screen") {
                                        popUpTo("screen_1")
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.black))
                            ) {
                                Text(
                                    modifier = Modifier
                                        .background(colorResource(id = R.color.black)),
                                    text = stringResource(id = R.string.cart),
                                    color = colorResource(id = R.color.orange)
                                )
                            }
                        }
                    }
                }
            }
        )
    }