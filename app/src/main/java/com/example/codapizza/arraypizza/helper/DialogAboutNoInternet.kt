package com.example.codapizza.arraypizza.helper

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.unit.sp

@Composable
fun DialogAboutNoInternet(
    openDialog: MutableState<Boolean>,
    onClick: () -> Unit
) {

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { onClick() },
            title = { Text(text = "Подтверждение действия") },
            text = { Text("Вы действительно хотите удалить выбранный элемент?") },
            confirmButton = {
                Button({ onClick() }) {
                    Text("OK", fontSize = 22.sp)
                }
            }
        )
    }

}