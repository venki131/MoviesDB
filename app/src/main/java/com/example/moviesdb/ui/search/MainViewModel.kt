package com.example.moviesdb.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.moviesdb.data.model.Search
import com.example.moviesdb.data.network.OmdbApi
import com.example.moviesdb.data.repository.MoviesRepository
import com.example.moviesdb.ui.search.data.MoviesDataSource
import com.example.moviesdb.ui.search.data.MoviesDataSourceFactory
import com.example.moviesdb.utils.lazyDeferred

class MainViewModel(
    private val moviesRepository: MoviesRepository,
    private val omdbApi: OmdbApi
) : ViewModel() {
    private lateinit var searchType: String
    private lateinit var movieTitle: String
    private lateinit var plot: String

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    var moviesPagedList: LiveData<PagedList<Search>>
    private val moviesDataSourceFactory: MoviesDataSourceFactory
    private var movieLiveSource: LiveData<MoviesDataSource>
    private var liveMovieSource: LiveData<MoviesDataSource>

    init {
        _isLoading.postValue(true)
        val moviesSourceFactory = MoviesDataSourceFactory(omdbApi)
        liveMovieSource = moviesSourceFactory.movieLiveDataSource
        val config = PagedList.Config.Builder().setEnablePlaceholders(false)
            .setPageSize(MoviesDataSource.PAGE_SIZE).build()
        this.moviesDataSourceFactory = MoviesDataSourceFactory(omdbApi)
        movieLiveSource = moviesDataSourceFactory.movieLiveDataSource
        moviesPagedList = LivePagedListBuilder(moviesDataSourceFactory, config).build()
    }

    val searchResults by lazyDeferred {
        moviesRepository.getSearchResults(searchType, 1, movieTitle)
    }

    val movieDetails by lazyDeferred {
        moviesRepository.getDetails(plot, movieTitle)
    }

    fun getSearchInputs(type: String, movieTitle: String) {
        searchType = type
        this.movieTitle = movieTitle
    }

    fun getDetails(plot: String, movieTitle: String) {
        this.plot = plot
        this.movieTitle = movieTitle
    }

    fun moviesRefresh(searchType : String, movieTitle : String) {
        _isLoading.postValue(true)
        MoviesDataSource.searchTitle = movieTitle
        MoviesDataSource.searchType = searchType
        moviesDataSourceFactory.movieLiveDataSource.value?.invalidate()
    }
}