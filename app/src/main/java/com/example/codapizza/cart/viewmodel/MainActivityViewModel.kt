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

    fun getQuantityOrder(order: Orders): Int {
        return order.quantity
    }

    private fun getTotalPrice(order: Orders): Float {
        val price = order.price * order.quantity
        return price
    }

    fun getOverrideTotalPrice(): Float {
        var totalPrice = 0f
        orders.value.forEach {
            totalPrice += getTotalPrice(it)
        }
        return totalPrice
    }

    suspend fun addOrder(order: Orders) {
        orderRepository.addOrder(order)
    }

    suspend fun deleteOrder(order: Orders) {
        orderRepository.deleteOrder(order)
    }

    suspend fun updateOrder(order: Orders){
        orderRepository.updateOrder(order)
    }

    suspend fun reductionInQuantity(order: Orders) {
        order.quantity -= 1
        orderRepository.updateOrder(order)
    }

    suspend fun addingQuantity(order: Orders) {
        order.quantity += 1
        orderRepository.updateOrder(order)
    }

    suspend fun deleteAll() {
        orderRepository.deleteAll()
    }
}
