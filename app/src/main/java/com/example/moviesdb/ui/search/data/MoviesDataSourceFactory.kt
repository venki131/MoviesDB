package com.example.moviesdb.ui.search.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.moviesdb.data.model.Search
import com.example.moviesdb.data.network.OmdbApi

class MoviesDataSourceFactory(
    private val omdbApi: OmdbApi
) :
    DataSource.Factory<Int, Search>() {
    val movieLiveDataSource = MutableLiveData<MoviesDataSource>()
    override fun create(): DataSource<Int, Search> {
        val moviesDataSource = MoviesDataSource(omdbApi)
        movieLiveDataSource.postValue(moviesDataSource)
        return moviesDataSource
    }
}