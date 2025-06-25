package com.example.tmdbapp.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tmdbapp.domain.model.Actor
import com.example.tmdbapp.presentation.theme.TMDBAppTheme
import com.example.tmdbapp.presentation.ui.screen.ActorDetailsScreen
import com.example.tmdbapp.presentation.ui.screen.FavoriteActorsScreen
import com.example.tmdbapp.presentation.ui.screen.HomeScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLDecoder

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

sealed class Screen(val route: String, val icon: ImageVector, val label: String) {
    data object Home : Screen("home", Icons.Default.Home, "Actors")
    data object Favorites : Screen("favorites", Icons.Default.Favorite, "Favorites")
}

@Composable
fun TMDBApp() {
    val navController = rememberNavController()
    val items = listOf(Screen.Home, Screen.Favorites)

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
                val currentRoute =
                    navController.currentBackStackEntryAsState().value?.destination?.route
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = screen.icon,
                                contentDescription = screen.label
                            )
                        },
                        label = { Text(text = screen.label) },
                        selected = currentRoute == screen.route,
                        onClick = {
                            if (currentRoute != screen.route) {
                                navController.navigate(screen.route) {
//                                    popUpTo(navController.graph.startDestinationId) {
//                                        saveState = true
//                                    }
//                                    launchSingleTop = true
//                                    restoreState = true
                                }
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) { HomeScreen(navController) }
            composable(
                route = "actor_detail/{actorJson}",
                arguments = listOf(navArgument("actorJson") { type = NavType.StringType })
            ) { backStackEntry ->
                val actorJson = backStackEntry.arguments?.getString("actorJson")
                if (actorJson != null) {
                    val decodedActorJson = URLDecoder.decode(actorJson, "UTF-8")
                    val actor = Json.decodeFromString<Actor>(decodedActorJson)
                    ActorDetailsScreen(
                        actor = actor,
                        onBack = { navController.popBackStack() }
                    )
                } else {
                    // Handle error: actorJson is null, perhaps navigate back or show error
                }
            }
            composable(Screen.Favorites.route) {
                FavoriteActorsScreen(
                    onBackClick = {},
                    onActorClick = { actor ->
                        val actorJson = Json.encodeToString(actor)
                        val encoded = java.net.URLEncoder.encode(actorJson, "UTF-8")
                        navController.navigate("actor_detail/$encoded")
                    }
                )
            }

        }
    }
}
