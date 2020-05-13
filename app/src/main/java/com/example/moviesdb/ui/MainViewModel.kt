package com.example.moviesdb.ui

import androidx.lifecycle.ViewModel
import com.example.moviesdb.data.repository.MoviesRepository
import com.example.moviesdb.utils.lazyDeferred

class MainViewModel(
    private val moviesRepository: MoviesRepository
) : ViewModel() {
    private lateinit var searchType: String
    private lateinit var movieTitle: String
    val searchResults  by lazyDeferred {
        moviesRepository.getSearchResults(searchType, 1, movieTitle)
    }

    fun getSearchInputs(type : String, movieTitle : String) {
        searchType = type
        this.movieTitle = movieTitle
    }
}