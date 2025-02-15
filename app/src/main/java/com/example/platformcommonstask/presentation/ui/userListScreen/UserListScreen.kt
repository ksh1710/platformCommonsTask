package com.example.platformcommonstask.presentation.ui.userListScreen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.platformcommonstask.presentation.common.ErrorMessage
import com.example.platformcommonstask.presentation.common.LoadingIndicator
import com.example.platformcommonstask.presentation.ui.userListScreen.components.UserItem
import com.example.platformcommonstask.viewmodel.UserViewModel
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen(
    viewModel: UserViewModel = hiltViewModel(),
    addUserClick: () -> Unit,
    userItemClicked: () -> Unit
) {
    val users = viewModel.users.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Platform Commons Task") },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { addUserClick() }
            ) {
                Text(text = "Add User + ", modifier = Modifier.padding(horizontal = 6.dp))
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (users.loadState.refresh) {
                is LoadState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                is LoadState.Error -> {
                    val error = (users.loadState.refresh as LoadState.Error).error
                    Log.d("UserListScreen", "Error: ${error.localizedMessage}")
                    ErrorMessage(
                        message = "Something went wrong!",
                        onRetry = { users.retry() }
                    )
                }

                else -> {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(users.itemCount) { index ->
                            users[index]?.let { user ->
                                UserItem(user, userItemClicked = {
                                    userItemClicked()
                                })
                            }
                        }

                        when (users.loadState.append) {
                            is LoadState.Loading -> {
                                item { LoadingIndicator() }
                            }

                            is LoadState.Error -> {
                                item {
                                    ErrorMessage(
                                        message = "Error loading more users!",
                                        onRetry = { users.retry() }
                                    )
                                }
                            }

                            else -> {}
                        }
                    }
                }
            }
        }
    }
}