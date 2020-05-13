package com.example.moviesdb.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviesdb.data.model.DetailsResponseModel
import com.example.moviesdb.data.model.MoviesSearchResponseModel

class GetSearchResultsDataSourceImpl(
    private val omdbApi: OmdbApi
) : GetSearchResultsDataSource {

    private val _downloadedSearchResult = MutableLiveData<MoviesSearchResponseModel>()

    private val _downloadedDetails = MutableLiveData<DetailsResponseModel>()

    override val downloadedSearchResult: LiveData<MoviesSearchResponseModel>
        get() = _downloadedSearchResult

    override suspend fun getDetails(plot: String, movieTitle: String) {
        try {
            val fetchSearchResults = omdbApi.getDetails(
                plot, movieTitle
            ).await()

            _downloadedDetails.postValue(fetchSearchResults)
        } catch (e: NoConnectivityException) {
            Log.e("connectivity", "no internet connection", e)
        }
    }

    override val downloadedDetails: LiveData<DetailsResponseModel>
        get() = _downloadedDetails

    override suspend fun getSearchResult(
        type: String,
        page: Int,
        searchTitle: String
    ) {
        try {
            val fetchSearchResults = omdbApi.getSearchResults(
                type, page, searchTitle
            ).await()

            _downloadedSearchResult.postValue(fetchSearchResults)
        } catch (e: NoConnectivityException) {
            Log.e("connectivity", "no internet connection", e)
        }
    }
}