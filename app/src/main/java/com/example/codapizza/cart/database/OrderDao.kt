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

    @Query("Select * FROM `orders`")
    fun getAllOrders(): Flow<List<Orders>>

    @Query("DELETE FROM orders")
    suspend fun deleteAll()

    @Query("SELECT * FROM `orders` WHERE id=(:id)")
    suspend fun getOneOrderFromCart(id: UUID): Orders

    @Update
    suspend fun updateOrder(order: Orders)

    @Insert
    suspend fun addOrder(order: Orders)

    @Delete
    suspend fun deleteOrder(order: Orders)

}