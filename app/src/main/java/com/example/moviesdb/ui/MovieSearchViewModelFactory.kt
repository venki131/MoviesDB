package com.example.moviesdb.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviesdb.data.network.OmdbApi
import com.example.moviesdb.data.repository.MoviesRepository
import com.example.moviesdb.ui.search.MainViewModel

class MovieSearchViewModelFactory(
    private val moviesRepository: MoviesRepository,
    private val omdbApi: OmdbApi
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(moviesRepository, omdbApi) as T
    }
}