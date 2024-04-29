package com.example.codapizza.arraypizza

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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

    val cartItemString = stringResource(id = R.string.cart)
    val vkSocItemString = stringResource(id = R.string.contact_vk)
    val instaSocItemString = stringResource(id = R.string.contact_insta)

    val context = LocalContext.current
    val urlVK = "https://vk.com/xzycoc"
    val urlTG = "https://t.me/Pankratar"

    val items = listOf(cartItemString, vkSocItemString, instaSocItemString)
    val selectedItem = remember { mutableStateOf(items[0]) }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        modifier = Modifier
            .padding(0.dp, 25.dp),
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet{
                items.forEach { item ->
                    NavigationDrawerItem(
                        label= { androidx.compose.material3.Text(item, fontSize = 22.sp) },
                        selected = selectedItem.value==item,
                        onClick = {
                            scope.launch { drawerState.close() }
                            when(item) {
                                cartItemString -> navController.navigate("cart_screen")
                                vkSocItemString -> {
                                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(urlVK))
                                    startActivity(context, browserIntent, null)
                                }
                                instaSocItemString -> {
                                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(urlTG))
                                    startActivity(context, browserIntent, null)
                                }
                            }
                        }
                    )
                }
            }
        },
        content={
            Column{
                Row{
                    TopAppBar {
                        IconButton(onClick = {scope.launch {drawerState.open()}},
                            content = { Icon(Icons.Filled.Menu, "Меню") }
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