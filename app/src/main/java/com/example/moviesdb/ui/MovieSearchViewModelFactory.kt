package com.example.moviesdb.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviesdb.data.repository.MoviesRepository

class MovieSearchViewModelFactory(
    private val moviesRepository: MoviesRepository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(moviesRepository) as T
    }
}