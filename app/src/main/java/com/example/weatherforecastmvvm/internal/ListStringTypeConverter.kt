package com.example.weatherforecastmvvm.internal

import androidx.room.TypeConverter
import androidx.room.TypeConverters


class ListStringTypeConverter {
    @TypeConverter
    fun fromList(list: List<String>): String {
        // Convert the List<String> to a String
        return list.joinToString(",")
    }

    @TypeConverter
    fun toList(string: String): List<String> {
        // Convert the String back to a List<String>
        return string.split(",")
    }
}