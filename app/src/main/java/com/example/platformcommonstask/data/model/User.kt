package com.example.platformcommonstask.data.model

data class User(
    val id: Int = 0,
    val name: String,
    val job: String,
    val synced: Boolean = false
)