package com.example.moviesdb.data.repository

import androidx.lifecycle.LiveData
import com.example.moviesdb.data.db.MoviesSearchResultDao
import com.example.moviesdb.data.model.DetailsResponseModel
import com.example.moviesdb.data.model.MoviesSearchResponseModel
import com.example.moviesdb.data.network.GetSearchResultsDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MoviesRepositoryImpl(
    private val moviesSearchResultDao: MoviesSearchResultDao,
    private val getSearchResultsDataSource: GetSearchResultsDataSource
) : MoviesRepository {

    init {
        getSearchResultsDataSource.downloadedSearchResult.observeForever {
            persistFetchedResult(it)
        }

        getSearchResultsDataSource.downloadedDetails.observeForever {
            persistDetails(it)
        }
    }

    override suspend fun getSearchResults(
        type: String,
        page: Int,
        searchTitle: String
    ): LiveData<MoviesSearchResponseModel> {
        getSearchResultsDataSource.getSearchResult(type, page, searchTitle)
        return withContext(Dispatchers.IO) {
            return@withContext moviesSearchResultDao.getMovieResult()
        }
    }

    override suspend fun getDetails(
        plot: String,
        movieTitle: String
    ): LiveData<DetailsResponseModel> {
        getSearchResultsDataSource.getDetails(plot, movieTitle)
        return withContext(Dispatchers.IO) {
            return@withContext moviesSearchResultDao.getMovieDetails()
        }
    }

    private fun persistFetchedResult(fetchedResultDao: MoviesSearchResponseModel) {
        GlobalScope.launch(Dispatchers.IO) {
            moviesSearchResultDao.upsert(fetchedResultDao)
        }
    }

    private fun persistDetails(fetchedDetails: DetailsResponseModel) {
        GlobalScope.launch(Dispatchers.IO) {
            moviesSearchResultDao.insertOrUpdateDetails(fetchedDetails)
        }
    }
}