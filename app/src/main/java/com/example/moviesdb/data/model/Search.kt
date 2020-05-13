package com.example.moviesdb.data.model


import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
data class Search(
    @SerializedName("imdbID")
    val imdbID: String,
    @SerializedName("Poster")
    val poster: String,
    @SerializedName("Title")
    val title: String,
    @SerializedName("Type")
    val type: String,
    @SerializedName("Year")
    val year: String
)