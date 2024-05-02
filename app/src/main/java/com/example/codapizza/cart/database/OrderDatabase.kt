package com.example.codapizza.cart.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Order::class], version = 1, exportSchema = false)
@TypeConverters(OrderTypeConverter::class)
abstract class OrderDatabase : RoomDatabase() {

    abstract fun orderDao(): OrderDao

}