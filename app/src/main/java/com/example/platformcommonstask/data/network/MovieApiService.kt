package com.example.platformcommonstask.data.network

import com.example.platformcommonstask.BuildConfig
import com.example.platformcommonstask.data.model.MovieDetail
import com.example.platformcommonstask.data.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MovieApiService {
    @GET("trending/movie/day")
    suspend fun getTrendingMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
    ): MovieResponse


    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
    ): MovieDetail
}


