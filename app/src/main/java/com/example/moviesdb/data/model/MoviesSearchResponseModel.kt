package com.example.moviesdb.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.moviesdb.utils.SearchConverter
import com.google.gson.annotations.SerializedName

const val CURRENT_ID = 0

@Entity(tableName = "movie_search")
class MoviesSearchResponseModel(
    @SerializedName("Response")
    val response: String,
    @TypeConverters(SearchConverter::class)
    @SerializedName("Search")
    val search: List<Search> = listOf(),
    @SerializedName("totalResults")
    val totalResults: String,
    @SerializedName("Response")
    val failResponse: String,
    @SerializedName("Error")
    val error : String
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = CURRENT_ID
}