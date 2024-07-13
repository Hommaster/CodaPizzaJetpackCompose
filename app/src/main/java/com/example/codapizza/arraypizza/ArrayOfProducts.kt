package com.example.codapizza.arraypizza

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
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
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.codapizza.R
import com.example.codapizza.arraypizza.helper.HaveOrderNumber
import com.example.codapizza.arraypizza.helper.checkNameItemForNavigate
import com.example.codapizza.arraypizza.viewpager.ViewPager
import com.example.codapizza.cart.viewmodel.MainActivityViewModel
import com.example.codapizza.desygnfiles.TopAppBarCustom
import com.example.codapizza.model.NavigationDrawerItems
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ArrayOfProducts(
    navController: NavHostController,
    mainActivityViewModel: MainActivityViewModel,
) {
    val context = LocalContext.current

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val haveOrder: MutableState<Int> = rememberSaveable {
        mutableIntStateOf(0)
    }

    val totalCost : MutableState<Float> = rememberSaveable {
        mutableFloatStateOf(0f)
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
                                LaunchedEffect(key1 = Unit) {
                                    scope.launch {
                                        mainActivityViewModel.orders.collect{
                                            totalCost.value = mainActivityViewModel.getOverrideTotalPrice()
                                        }
                                    }
                                }
                                Row {
                                    Box {
                                        Image(
                                            modifier = Modifier
                                                .padding(0.dp, 0.dp, 20.dp, 0.dp)
                                                .size(35.dp),
                                            painter = painterResource(id = item.imageItem),
                                            contentDescription = stringResource(id = item.nameItem)
                                        )
                                        if(item.nameItem == R.string.cart && haveOrder.value > 0) {
                                            Box(
                                                modifier = Modifier
                                                    .padding(25.dp, 0.dp, 0.dp, 0.dp)
                                                    .size(20.dp)
                                                    .border(
                                                        width = 0.dp,
                                                        color = colorResource(id = R.color.white),
                                                        shape = RoundedCornerShape(15.dp)
                                                    )
                                                    .clip(RoundedCornerShape(15.dp))
                                                    .background(Color.White),
                                                contentAlignment = Alignment.Center,
                                            ) {
                                                Text(
                                                    textAlign = TextAlign.Center,
                                                    text = haveOrder.value.toString()
                                                )
                                            }
                                        }
                                    }
                                    Column(
                                        modifier = Modifier
                                            .padding(0.dp, 0.dp)
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
                                checkNameItemForNavigate(
                                    item.nameItem,
                                    scope,
                                    context,
                                    navController
                                )
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
                                TopAppBarCustom(
                                    title = {
                                        IconButton(
                                            modifier = Modifier
                                                .align(Alignment.Bottom),
                                            onClick = { scope.launch { drawerState.open() } },
                                            content = { Icon(Icons.Filled.Menu, "Menu") }
                                        )
                                    }
                                )
                            }
                        }

                        val pagerState = rememberPagerState(pageCount = {
                            3
                        })
                        ViewPager(
                            pagerState = pagerState,
                            scope = scope,
                            navController = navController
                        )
                    }
                    if(haveOrder.value > 0) {
                        HaveOrderNumber(
                            navController,
                            totalCost
                        )
                    }
                }
            }
        )
}