package com.example.platformcommonstask.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.platformcommonstask.presentation.ui.addUserScreen.AddUserScreen
import com.example.platformcommonstask.presentation.ui.movieDetailScreen.MovieDetailScreen
import com.example.platformcommonstask.presentation.ui.movieListScreen.MovieListScreen
import com.example.platformcommonstask.presentation.ui.userListScreen.UserListScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Destinations.UserListScreen.route) {
        composable(Destinations.UserListScreen.route) {
            UserListScreen(
                addUserClick = {
                    navController.navigate(Destinations.AddUserScreen.route)
                },
                userItemClicked = {
                    navController.navigate(Destinations.MovieListScreen.route)
                })
        }
        composable(Destinations.AddUserScreen.route) {
            AddUserScreen(navController = navController)
        }
        composable(Destinations.MovieListScreen.route) {
            MovieListScreen(
                navController = navController,
                onMovieClicked = {
                    navController.navigate(
                        Destinations.MovieDetailScreen.createRoute(it.toString())
                    )
                }
            )
        }
        composable(
            route = Destinations.MovieDetailScreen.route,
            arguments = listOf(
                navArgument("movieId") { type = NavType.StringType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId")?.toInt()
            MovieDetailScreen(movieId = movieId!!, navController = navController)
        }
    }
}
