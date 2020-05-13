package com.example.moviesdb.data.network

import androidx.lifecycle.LiveData
import com.example.moviesdb.data.model.DetailsResponseModel
import com.example.moviesdb.data.model.MoviesSearchResponseModel
import retrofit2.http.Query

interface GetSearchResultsDataSource {

    suspend fun getSearchResult(
        @Query("type") type: String,
        @Query("page") page: Int,
        @Query("s") searchTitle: String
    )
    val downloadedSearchResult: LiveData<MoviesSearchResponseModel>

    suspend fun getDetails (
        @Query("plot") plot : String,
        @Query("t") movieTitle : String
    )

    val downloadedDetails : LiveData<DetailsResponseModel>
}