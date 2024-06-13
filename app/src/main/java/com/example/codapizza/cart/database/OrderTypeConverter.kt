package com.example.codapizza.cart.database

import androidx.room.TypeConverter
import com.example.codapizza.model.Sauce
import com.example.codapizza.model.Topping
import com.example.codapizza.model.ToppingPlacement
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson
import java.util.Date
import java.util.UUID

class OrderTypeConverter {

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toDate(date: Long?): Date? {
        return date?.let {
            Date(it)
        }
    }

    @TypeConverter
    fun fromUUID(uuid: UUID?): String? {
        return uuid?.toString()
    }

    @TypeConverter
    fun toUUID(uuid: String?): UUID? {
        return UUID.fromString(uuid)
    }

    @TypeConverter
    fun toHashMap(value: String): Map<Topping, ToppingPlacement> =
        Gson().fromJson(value, object : TypeToken<Map<Topping, ToppingPlacement>>() {}.type)

    @TypeConverter
    fun fromHashMap(value: Map<Topping, ToppingPlacement>): String =
        Gson().toJson(value)

    @TypeConverter
    fun toHashMapSauce(value: String): Map<Sauce, Int> =
        Gson().fromJson(value, object : TypeToken<Map<Sauce, Int>>() {}.type)

    @TypeConverter
    fun fromHashMapSauce(value: Map<Sauce, Int>): String =
        Gson().toJson(value)
}