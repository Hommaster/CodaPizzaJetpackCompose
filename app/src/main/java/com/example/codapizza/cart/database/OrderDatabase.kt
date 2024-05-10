package com.example.codapizza.cart.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Orders::class], version = 2, exportSchema = false)
@TypeConverters(OrderTypeConverter::class)
abstract class OrderDatabase : RoomDatabase() {

    abstract fun orderDao(): OrderDao

}