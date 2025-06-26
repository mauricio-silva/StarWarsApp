package com.example.tmdbapp.presentation.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.tmdbapp.domain.model.Actor
import com.example.tmdbapp.presentation.ui.components.LoadingScreen
import com.example.tmdbapp.presentation.ui.components.SearchBar
import com.example.tmdbapp.presentation.ui.components.StateScreen
import com.example.tmdbapp.presentation.viewmodel.FavoriteActorsViewModel
import com.example.tmdbapp.presentation.viewmodel.FavoritesUiState
import com.mdev.tmdbapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteActorsScreen(
    viewModel: FavoriteActorsViewModel = hiltViewModel(),
    onActorClick: (Actor) -> Unit
) {
    val uiState by viewModel.favoritesUiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        SearchBar(
            query = searchQuery,
            onQueryChange = { viewModel.onSearchQueryChange(it) }
        )

        when(uiState) {
            is FavoritesUiState.Loading -> {
                LoadingScreen()
            }

            is FavoritesUiState.NoActorsSearchedFound -> {
                StateScreen(
                    title = stringResource(R.string.not_found_error_title),
                    message = stringResource(R.string.not_found_error_message),
                    imageRes = R.drawable.not_found,
                    buttonText = stringResource(R.string.clear_search_label),
                    onClick = { viewModel.onSearchQueryChange("") }
                )
            }

            is FavoritesUiState.NoFavoriteActorsFound -> {
                StateScreen(
                    title = stringResource(R.string.not_found_error_title),
                    message = stringResource(R.string.not_found_error_message),
                    imageRes = R.drawable.not_found
                )
            }

            is FavoritesUiState.Success -> {
                AnimatedVisibility(
                    visible = uiState is FavoritesUiState.Success,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        val favorites = (uiState as? FavoritesUiState.Success)?.favorites
                        favorites?.let { actors ->
                            items(actors) { actor ->
                                FavoriteActorItem(actor = actor, onClick = { onActorClick(actor) })
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FavoriteActorItem(
    actor: Actor,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = actor.imageUrl,
            contentDescription = actor.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = actor.name,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = actor.department,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

