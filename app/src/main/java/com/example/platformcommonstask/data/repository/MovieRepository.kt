package com.example.platformcommonstask.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.platformcommonstask.data.model.MovieResult
import com.example.platformcommonstask.data.network.MovieApiService
import com.example.platformcommonstask.paging.MoviePagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val movieApiService: MovieApiService
) {
    fun getMovies(): Flow<PagingData<MovieResult>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { MoviePagingSource(movieApiService) }
        ).flow
    }
}
