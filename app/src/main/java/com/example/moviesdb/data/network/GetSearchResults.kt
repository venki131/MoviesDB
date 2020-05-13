package com.example.moviesdb.data.network

import androidx.lifecycle.LiveData
import com.example.moviesdb.data.model.MoviesSearchResponseModel
import retrofit2.http.Query

interface GetSearchResults {

    suspend fun getSearchResult(
        @Query("type") type: String,
        @Query("apikey") apiKey: String,
        @Query("page") page: Int,
        @Query("s") searchTitle: String
    )
    val downloadedSearchResult: LiveData<MoviesSearchResponseModel>
}