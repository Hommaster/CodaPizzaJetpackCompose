package com.example.codapizza.cart.repository

import android.content.Context
import androidx.room.Room
import com.example.codapizza.cart.database.Order
import com.example.codapizza.cart.database.OrderDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.UUID

class OrderRepository @OptIn(DelicateCoroutinesApi::class)
private constructor(
    context: Context,
    private val coroutineScope: CoroutineScope = GlobalScope
){

    private val database : OrderDatabase = Room.databaseBuilder(
        context.applicationContext,
        OrderDatabase::class.java,
        "order-database"
    ).build()

    fun getOrders(): Flow<List<Order>> = database.orderDao().getAllOrders()
    suspend fun getOrder(id: UUID): Order = database.orderDao().getOneOrderFromCart(id)

    fun updateOrder(order: Order) {
        coroutineScope.launch {
            database.orderDao().updateOrder(order)
        }
    }

    suspend fun addOrder(order: Order) {
        database.orderDao().addOrder(order)
    }

    suspend fun deleteOrder(order: Order) {
        database.orderDao().deleteOrder(order)
    }

    companion object {
        private var INSTANCE: OrderRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = OrderRepository(context)
            }
        }

        fun get(): OrderRepository {
            return INSTANCE ?:
            throw IllegalStateException("OrderRepository must be initialized")
        }
    }

}