package com.example.moviesdb.ui.search.data

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.example.moviesdb.data.model.Search
import com.example.moviesdb.data.network.NoConnectivityException
import com.example.moviesdb.data.network.OmdbApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MoviesDataSource(private val omdbApi: OmdbApi) : PageKeyedDataSource<Int, Search>() {

    private val scope = CoroutineScope(Dispatchers.IO)

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Search>
    ) {
        println("page size = ${params.requestedLoadSize}")
        scope.launch {
            try {
                val response = omdbApi.getSearchResults(
                    type = searchType,
                    page = FIRST_PAGE,
                    searchTitle = searchTitle
                ).await()
                response.search.let {
                    callback.onResult(response.search.toMutableList(), null, FIRST_PAGE + 1)
                }
            } catch (e: NoConnectivityException) {
                Log.e("connectivity", "no internet connection", e)
            }
        }
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, Search>
    ) {
        scope.launch {
            try {
                val key = params.key + 1
                val response = omdbApi.getSearchResults(
                    type = searchType,
                    page = params.key,
                    searchTitle = searchTitle
                ).await()
                response.let {
                    totalSize = it.totalResults.toInt()
                    response.search.let {
                        callback.onResult(it.toMutableList(), key)
                    }
                }
            } catch (e: NoConnectivityException) {
                Log.e("connectivity", "no internet connection", e)
            }
        }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, Search>
    ) {
        scope.launch {
            try {
                val key = params.key + 1
                val response = omdbApi.getSearchResults(
                    type = searchType,
                    page = params.key,
                    searchTitle = searchTitle
                ).await()
                response.let {
                    totalSize = it.totalResults.toInt()
                    response.search.let {
                        callback.onResult(it.toMutableList(), key)
                    }
                }
            } catch (e: NoConnectivityException) {
                Log.e("connectivity", "no internet connection", e)
            }
        }
    }

    companion object {
        const val FIRST_PAGE = 1
        const val PAGE_SIZE = 4
        var totalSize = 0
        var searchType = "Movie"
        var searchTitle = ""
    }
}