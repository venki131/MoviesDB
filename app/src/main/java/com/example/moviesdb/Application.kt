package com.example.moviesdb

import android.app.Application
import com.example.moviesdb.data.db.MoviesDatabase
import com.example.moviesdb.data.network.*
import com.example.moviesdb.data.repository.MoviesRepository
import com.example.moviesdb.data.repository.MoviesRepositoryImpl
import com.example.moviesdb.ui.MovieSearchViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

open class Application : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@Application))

        bind() from singleton { MoviesDatabase(instance()) }
        bind() from singleton { instance<MoviesDatabase>().movieSearchResultDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { OmdbApi(instance()) }
        bind<GetSearchResultsDataSource>() with singleton { GetSearchResultsDataSourceImpl(instance()) }
        bind<MoviesRepository>() with singleton {
            MoviesRepositoryImpl(
                instance(),
                instance()
            )
        }
        bind() from provider {
            MovieSearchViewModelFactory(instance())
        }

    }
}