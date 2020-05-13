package com.example.moviesdb.data.repository

import androidx.lifecycle.LiveData
import com.example.moviesdb.data.model.DetailsResponseModel
import com.example.moviesdb.data.model.MoviesSearchResponseModel

interface MoviesRepository {
    suspend fun getSearchResults(
        type: String,
        page: Int,
        searchTitle: String
    ): LiveData<MoviesSearchResponseModel>

    suspend fun getDetails(): LiveData<DetailsResponseModel>
}