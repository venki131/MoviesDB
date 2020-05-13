package com.example.moviesdb.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.moviesdb.data.model.DetailsResponseModel
import com.example.moviesdb.data.model.MoviesSearchResponseModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class GetSearchResultsDataSourceImpl(
    private val omdbApi: OmdbApi
) : GetSearchResultsDataSource, PageKeyedDataSource<Int, MoviesSearchResponseModel>() {

    private val _downloadedSearchResult = MutableLiveData<MoviesSearchResponseModel>()

    private val _downloadedDetails = MutableLiveData<DetailsResponseModel>()

    override val downloadedSearchResult: LiveData<MoviesSearchResponseModel>
        get() = _downloadedSearchResult

    private val scope = CoroutineScope(Dispatchers.IO)

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

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, MoviesSearchResponseModel>
    ) {
        scope.launch {
            try {
                val response = omdbApi.getSearchResults(type = "Movie",page = params.requestedLoadSize, searchTitle = "Tangled").await()
                _downloadedSearchResult.postValue(response)
            } catch (e: NoConnectivityException) {
                Log.e("connectivity", "no internet connection", e)
            }
        }
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, MoviesSearchResponseModel>
    ) {
        scope.launch {
           // val response = omdbApi.getSearchResults()
        }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, MoviesSearchResponseModel>
    ) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}