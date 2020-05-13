package com.example.moviesdb.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviesdb.data.model.CURRENT_ID
import com.example.moviesdb.data.model.MoviesSearchResponseModel

@Dao
interface MoviesSearchResultDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(searchResult: MoviesSearchResponseModel)

    @Query("SELECT * FROM movie_result WHERE id = $CURRENT_ID")
    fun getMovieResult(): LiveData<MoviesSearchResponseModel>
}