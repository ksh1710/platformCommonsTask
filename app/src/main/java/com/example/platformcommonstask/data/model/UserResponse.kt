package com.example.platformcommonstask.data.model



data class UserResponse(
    val data: List<UserResponseData>,
    val page: Int,
    val per_page: Int,
    val support: Support,
    val total: Int,
    val total_pages: Int
)