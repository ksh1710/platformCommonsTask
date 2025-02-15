package com.example.platformcommonstask.presentation.navigation

sealed class Destinations(var route: String) {
    data object UserListScreen : Destinations("userList")
    data object AddUserScreen : Destinations("addUser")
    data object MovieListScreen : Destinations("movieList")
    data object MovieDetailScreen : Destinations("movieDetail/{movieId}"){
        fun createRoute(movieId: String): String {
            return "movieDetail/$movieId"
        }
    }
}