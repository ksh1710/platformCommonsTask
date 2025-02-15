package com.example.platformcommonstask.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.platformcommonstask.presentation.navigation.NavGraph
import com.example.platformcommonstask.presentation.theme.PlatformCommonsTaskTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlatformCommonsTaskTheme {
                NavGraph()
            }
        }
    }
}
