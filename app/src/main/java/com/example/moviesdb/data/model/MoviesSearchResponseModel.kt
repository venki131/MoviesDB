package com.example.moviesdb.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

const val CURRENT_ID = 0

@Entity(tableName = "movie_result")
data class MoviesSearchResponseModel(
    @SerializedName("Title")
    @Expose
    val title: String,
    @SerializedName("Year")
    @Expose
    val year: String,
    @SerializedName("imdbID")
    @Expose
    val imdbId: String,
    @SerializedName("Type")
    @Expose
    val type: String,
    @SerializedName("Poster")
    @Expose
    val poster: String
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = CURRENT_ID
}