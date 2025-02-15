package com.example.platformcommonstask.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.platformcommonstask.data.model.MovieResult
import com.example.platformcommonstask.data.network.MovieApiService
import javax.inject.Inject

class MoviePagingSource @Inject constructor(
    private val apiService: MovieApiService,
) : PagingSource<Int, MovieResult>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResult> {
        return try {
            val page = params.key ?: 1
            Log.d("idk", "Fetching movies for page: $page")

            val response = apiService.getTrendingMovies(page = page)

            Log.d("idk", "Fetched ${response.results.size} movies")

            LoadResult.Page(
                data = response.results,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.results.isNotEmpty()) page + 1 else null
            )
        } catch (e: Exception) {
            Log.d("idk", "error in movies: $e ")
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieResult>): Int? {
        return state.anchorPosition
    }
}
