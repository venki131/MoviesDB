package com.example.moviesdb.ui

import androidx.lifecycle.ViewModel
import com.example.moviesdb.BuildConfig
import com.example.moviesdb.data.repository.MoviesRepository
import com.example.moviesdb.utils.lazyDeferred

class MainViewModel (
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    val searchResults by lazyDeferred {
        moviesRepository.getSearchResults("movie",BuildConfig.API_KEY, 1, "guardians")
    }
}