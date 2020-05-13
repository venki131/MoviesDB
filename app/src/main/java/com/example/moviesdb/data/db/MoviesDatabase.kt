package com.example.moviesdb.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.moviesdb.data.model.DetailsResponseModel
import com.example.moviesdb.data.model.MoviesSearchResponseModel
import com.example.moviesdb.utils.Converters

@Database(
    entities = [MoviesSearchResponseModel::class/*, DetailsResponseModel::class*/],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun movieSearchResultDao(): MoviesSearchResultDao

    companion object {
        @Volatile
        private var instance: MoviesDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                MoviesDatabase::class.java,
                "movies.db"
            ).build()
    }
}