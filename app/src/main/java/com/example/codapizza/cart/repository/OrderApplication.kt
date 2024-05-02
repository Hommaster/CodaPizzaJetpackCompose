package com.example.codapizza.cart.repository

import android.app.Application

class OrderApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        OrderRepository.initialize(this)
    }

}