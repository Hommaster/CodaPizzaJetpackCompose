package com.example.codapizza.cart.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface CartDao {

    @Query("Select * FROM cart")
    fun getAllFromCart(): Flow<List<Cart>>

    @Query("SELECT * FROM cart WHERE id=(:id)")
    suspend fun getOneOrderFromCart(id: UUID): Cart

    @Query("DELETE FROM cart WHERE id=(:id)")
    suspend fun deleteAllFromCart(id: UUID): Cart

    @Update
    suspend fun updateOrderFromCart(id: UUID): Cart

    @Insert
    suspend fun addOrder(cart: Cart)

    @Delete
    suspend fun deleteOrder(cart: Cart)

}