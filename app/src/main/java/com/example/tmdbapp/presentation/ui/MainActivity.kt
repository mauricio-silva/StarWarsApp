package com.example.tmdbapp.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tmdbapp.domain.model.Actor
import com.example.tmdbapp.presentation.theme.TMDBAppTheme
import com.example.tmdbapp.presentation.ui.components.noRippleClickable
import com.example.tmdbapp.presentation.ui.screen.ActorDetailsScreen
import com.example.tmdbapp.presentation.ui.screen.FavoriteActorsScreen
import com.example.tmdbapp.presentation.ui.screen.HomeScreen
import com.mdev.tmdbapp.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import java.net.URLEncoder

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TMDBApp() {
    val navController = rememberNavController()
    val items = listOf(Screen.Home, Screen.Favorites)

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    val topBarTitle = when {
        currentRoute == Screen.Home.route -> stringResource(R.string.screen_home_title)
        currentRoute == Screen.Favorites.route -> stringResource(R.string.screen_favorites_title)
        currentRoute?.startsWith("actor_detail") == true -> stringResource(R.string.screen_actor_detail_title)
        else -> stringResource(R.string.app_name)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = topBarTitle,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                navigationIcon = {
                    // Show back button if not on top-level destination
                    if (currentRoute != Screen.Home.route && currentRoute != Screen.Favorites.route) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    }
                }
            )
        },
        bottomBar = {
            if (currentRoute in listOf(Screen.Home.route, Screen.Favorites.route)) {
                NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
                    items.forEach { screen ->
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    imageVector = if (currentRoute == screen.route) screen.selectedIcon else screen.unselectedIcon,
                                    contentDescription = screen.label
                                )
                            },
                            label = { Text(text = screen.label) },
                            selected = currentRoute == screen.route,
                            onClick = {
                                if (currentRoute != screen.route) {
                                    navController.navigate(screen.route)
                                }
                            }
                        )
                    }
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
                        actor = actor
                    )
                }
            }
            composable(Screen.Favorites.route) {
                FavoriteActorsScreen(
                    onActorClick = { actor ->
                        val actorJson = Json.encodeToString(actor)
                        val encoded = URLEncoder.encode(actorJson, "UTF-8")
                        navController.navigate("actor_detail/$encoded")
                    }
                )
            }
        }
    }
}
