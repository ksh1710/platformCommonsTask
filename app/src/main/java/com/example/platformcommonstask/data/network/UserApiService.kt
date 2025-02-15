package com.example.platformcommonstask.data.network

import com.example.platformcommonstask.data.model.AddUserRequest
import com.example.platformcommonstask.data.model.AddUserResponse
import com.example.platformcommonstask.data.model.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApiService {
    @GET("users")
    suspend fun getUsers(@Query("page") page: Int): UserResponse

    @POST("users")
    suspend fun createUser(@Body userRequest: AddUserRequest): Response<AddUserResponse>
}


