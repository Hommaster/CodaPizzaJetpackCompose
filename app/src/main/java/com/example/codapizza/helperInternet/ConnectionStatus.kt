package com.example.codapizza.helperInternet

sealed class ConnectionStatus{
    object Available: ConnectionStatus()
    object Unavailable: ConnectionStatus()
}