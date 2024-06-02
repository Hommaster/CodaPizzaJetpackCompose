package com.example.codapizza.cart.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.ViewModelFactoryDsl
import com.example.codapizza.cart.database.Orders
import com.example.codapizza.cart.repository.OrderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class OrderDetailViewModel(orderID: UUID): ViewModel() {

    private val ordersRepository = OrderRepository.get()

    private val _order: MutableStateFlow<Orders?> = MutableStateFlow(null)

    val order: StateFlow<Orders?> = _order.asStateFlow()

    init{
        viewModelScope.launch {
            _order.value = ordersRepository.getOrder(orderID)
        }
    }

    fun updateOrder(onUpdate: (Orders) -> Orders){
        _order.update { oldOrder ->
            oldOrder?.let { onUpdate(it) }
        }
    }

}

@Suppress("UNCHECKED_CAST")
class OrderDetailViewModelFactory(
    private val orderID: UUID
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return OrderDetailViewModel(orderID) as T
    }
}