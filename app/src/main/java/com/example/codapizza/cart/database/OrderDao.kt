package com.example.codapizza.cart.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface OrderDao {

    @Query("Select * FROM `order`")
    fun getAllOrders(): Flow<List<Order>>

    @Query("SELECT * FROM `order` WHERE id=(:id)")
    suspend fun getOneOrderFromCart(id: UUID): Order

    @Update
    suspend fun updateOrder(order: Order)

    @Insert
    suspend fun addOrder(order: Order)

    @Delete
    suspend fun deleteOrder(order: Order)

}