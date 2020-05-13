package com.example.moviesdb.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviesdb.data.model.CURRENT_DETAILS_ID
import com.example.moviesdb.data.model.CURRENT_ID
import com.example.moviesdb.data.model.DetailsResponseModel
import com.example.moviesdb.data.model.MoviesSearchResponseModel

@Dao
interface MoviesSearchResultDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(searchResult: MoviesSearchResponseModel)

    @Query("SELECT * FROM movie_search WHERE id = $CURRENT_ID")
    fun getMovieResult(): LiveData<MoviesSearchResponseModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdateDetails(details : DetailsResponseModel)

    @Query("SELECT * FROM movie_details WHERE id = $CURRENT_DETAILS_ID")
    fun getMovieDetails() : LiveData<DetailsResponseModel>
}