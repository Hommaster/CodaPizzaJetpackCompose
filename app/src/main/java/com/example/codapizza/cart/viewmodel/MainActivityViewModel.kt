package com.example.codapizza.cart.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codapizza.cart.database.Orders
import com.example.codapizza.cart.repository.OrderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {

    private val orderRepository = OrderRepository.get()

    private val _orders: MutableStateFlow<List<Orders>> = MutableStateFlow(emptyList())

    val orders: StateFlow<List<Orders>>
        get() = _orders.asStateFlow()

    init {
        viewModelScope.launch {
            orderRepository.getOrders().collect{
                _orders.value = it
            }
        }
    }

    fun getOrders() {
        orderRepository.getOrders()
    }

    suspend fun addOrder(order: Orders) {
        orderRepository.addOrder(order)
    }

    suspend fun deleteOrder(order: Orders) {
        orderRepository.deleteOrder(order)
    }



}
