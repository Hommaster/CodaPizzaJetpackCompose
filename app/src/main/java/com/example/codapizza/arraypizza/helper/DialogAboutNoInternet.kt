package com.example.codapizza.arraypizza.helper

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.example.codapizza.R

@Composable
fun DialogAboutNoInternet(
    openDialog: MutableState<Boolean>,
    onClick: () -> Unit
) {

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { onClick() },
            title = { Text(text = stringResource(id = R.string.do_not_internet)) },
            text = { Text(stringResource(id = R.string.do_not_internet_cart_ui)) },
            confirmButton = {
                Button({ onClick() }) {
                    Text("OK", fontSize = 22.sp)
                }
            }
        )
    }

}