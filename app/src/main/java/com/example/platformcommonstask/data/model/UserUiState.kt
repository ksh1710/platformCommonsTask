package com.example.platformcommonstask.data.model


data class UserUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)