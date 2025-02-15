package com.example.platformcommonstask.data.repository

import com.example.platformcommonstask.data.model.MovieDetail
import com.example.platformcommonstask.data.network.MovieApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieDetailRepository @Inject constructor(
    private val apiService: MovieApiService
) {
    suspend fun getMovieDetail(movieId: Int): MovieDetail {
        return apiService.getMovieDetails(movieId)
    }
}
