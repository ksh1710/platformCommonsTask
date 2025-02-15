package com.example.platformcommonstask.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.platformcommonstask.data.network.UserApiService
import com.example.platformcommonstask.data.model.UserResponseData
import javax.inject.Inject


class UserPagingSource @Inject constructor(
    private val apiService: UserApiService
) : PagingSource<Int, UserResponseData>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserResponseData> {
        return try {
            val page = params.key ?: 1
            Log.d("idk", "Fetching users from page: $page")
            val response = apiService.getUsers(page)
            Log.d("idk", "Received: ${response.data}")

            LoadResult.Page(
                data = response.data,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (page < response.total_pages) page + 1 else null
            )
        } catch (e: Exception) {
            Log.e("idk", "Error fetching users", e)
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UserResponseData>): Int? {
        return state.anchorPosition
    }
}
