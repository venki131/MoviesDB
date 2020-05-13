package com.example.moviesdb.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviesdb.data.model.MoviesSearchResponseModel

class GetSearchResultsImpl(
    private val omdbApi: OmdbApi
) : GetSearchResults {

    private val _downloadedSearchResult = MutableLiveData<MoviesSearchResponseModel>()

    override val downloadedSearchResult: LiveData<MoviesSearchResponseModel>
        get() = _downloadedSearchResult

    override suspend fun getSearchResult(
        type: String,
        apiKey: String,
        page: Int,
        searchTitle: String
    ) {
        try {
            val fetchSearchResults = omdbApi.getSearchResults(
                type, apiKey, page, searchTitle
            ).await()

            _downloadedSearchResult.postValue(fetchSearchResults)
        } catch (e: NoConnectivityException) {
            Log.e("connectivity", "no internet connection", e)
        }
    }
}