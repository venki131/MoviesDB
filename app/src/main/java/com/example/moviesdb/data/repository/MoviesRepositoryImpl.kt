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

    override suspend fun getDetails(): LiveData<DetailsResponseModel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun persistFetchedResult(fetchedResultDao: MoviesSearchResponseModel) {
        GlobalScope.launch(Dispatchers.IO) {
            moviesSearchResultDao.upsert(fetchedResultDao)
        }
    }
}