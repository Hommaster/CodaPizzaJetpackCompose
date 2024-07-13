package com.example.codapizza.cart.internetHelper

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.ui.platform.LocalContext
import com.example.codapizza.helperInternet.ConnectionStatus
import com.example.codapizza.helperInternet.currentConnectivityStatus
import com.example.codapizza.helperInternet.observeConnectivityAsFlow

@Composable
fun connectivityStatus(): State<ConnectionStatus> {
    val mCtx = LocalContext.current

    return produceState(initialValue = mCtx.currentConnectivityStatus) {
        mCtx.observeConnectivityAsFlow().collect { value = it}
    }
}