package com.example.moviesdb.utils

import androidx.room.TypeConverter
import com.example.moviesdb.data.model.Search
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SearchConverter {
    private val gson = Gson()

    @TypeConverter
    fun stringToList(data: String): List<Search> {
        val type = object : TypeToken<List<Search>>() {}.type
        return gson.fromJson(data, type)
    }

    @TypeConverter
    fun listToString(someObjects: List<Search>): String {
        val type = object : TypeToken<List<Search>>() {}.type
        return gson.toJson(someObjects, type)
    }
}