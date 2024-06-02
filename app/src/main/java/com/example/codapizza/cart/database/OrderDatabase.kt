package com.example.codapizza.cart.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Orders::class], version = 3, exportSchema = false)
@TypeConverters(OrderTypeConverter::class)
abstract class OrderDatabase : RoomDatabase() {

    abstract fun orderDao(): OrderDao

}

val migration_2_3: Migration = object : Migration(2, 3){
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE orders ADD COLUMN quantity INT DEFAULT '1'")
    }
}