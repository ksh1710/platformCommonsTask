package com.example.platformcommonstask.presentation.ui.movieDetailScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.platformcommonstask.viewmodel.MovieDetailViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.navigation.NavController
import coil3.request.ImageRequest
import com.example.platformcommonstask.presentation.ui.movieDetailScreen.components.GenreChips
import com.example.platformcommonstask.presentation.ui.movieDetailScreen.components.ProductionChips
import com.example.platformcommonstask.presentation.ui.movieDetailScreen.components.RatingsSection


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(
    movieId: Int,
    navController:NavController,
    viewModel: MovieDetailViewModel = hiltViewModel()
) {
    val movie by viewModel.movieDetail.collectAsState()

    LaunchedEffect(movieId) {
        viewModel.fetchMovieDetails(movieId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Movie Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                })
        }
    ) { paddingValues ->
        movie?.let { movie ->
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                item {
                    // Poster Image
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("https://image.tmdb.org/t/p/w500/${movie.poster_path}")
                            .build(),
                        contentDescription = movie.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(450.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Title and Tagline
                    Text(
                        text = movie.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    if (movie.tagline.isNotEmpty()) {
                        Text(
                            text = movie.tagline,
                            fontStyle = FontStyle.Italic,
                            fontSize = 16.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )

                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Release Date and Runtime
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                    {
                        Text(
                            text = "Release Date: ${movie.release_date}",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = "Runtime: ${movie.runtime} mins",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Overview
                    Text(
                        text = movie.overview,
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )


                    Spacer(modifier = Modifier.height(16.dp))

                    // Genres
                    GenreChips(movie.genres)

                    Spacer(modifier = Modifier.height(16.dp))

                    // Production Companies
                    ProductionChips(movie.production_companies)


                    Spacer(modifier = Modifier.height(16.dp))

                    // Ratings
                    RatingsSection(voteAverage = movie.vote_average, voteCount = movie.vote_count)


                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        } ?: run {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

