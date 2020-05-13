package com.example.moviesdb.data.network

import com.example.moviesdb.BuildConfig
import com.example.moviesdb.data.model.MoviesSearchResponseModel
import com.example.moviesdb.utils.UrlConstant
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbApi {
    @GET(UrlConstant.SEARCH_API)

    fun getSearchResults(
        @Query("type") type: String,
        @Query("page") page: Int,
        @Query("s") searchTitle: String
    ): Deferred<MoviesSearchResponseModel>

    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): OmdbApi {
            val requestInterceptor = Interceptor {

                val url = it.request()
                    .url
                    .newBuilder()
                    .addQueryParameter("apikey",
                        BuildConfig.API_KEY
                    )
                    .build()

                val request = it.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor it.proceed(request)
            }
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .addInterceptor(logging)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BuildConfig.BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(OmdbApi::class.java)
        }
    }
}