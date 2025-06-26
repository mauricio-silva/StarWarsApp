package com.example.tmdbapp.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.tmdbapp.presentation.theme.TMDBAppTheme
import com.example.tmdbapp.presentation.ui.components.TMDBApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TMDBAppTheme {
                TMDBApp()
            }
        }
    }
}
