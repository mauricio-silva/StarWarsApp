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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.mdev.tmdbapp.R
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteActorsScreen(
    viewModel: FavoriteActorsViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onActorClick: (Actor) -> Unit
) {
    val actors by viewModel.favoritesUiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    val isLoading = remember { mutableStateOf(false) }
    val isError = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isLoading.value = true
        delay(400) // Simula carregamento, substitua por estado real se houver
        isLoading.value = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopAppBar(
            title = {
                Text(
                    stringResource(R.string.favorites_label),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        )

        SearchBar(
            query = searchQuery,
            onQueryChange = { viewModel.onSearchQueryChange(it) }
        )

        when {
            isLoading.value -> {
                LoadingScreen()
            }

            isError.value -> {
                StateScreen(
                    title = stringResource(R.string.not_found_error_title),
                    message = stringResource(R.string.not_found_error_message),
                    imageRes = R.drawable.not_found,
                    buttonText = stringResource(R.string.clear_search_label),
                    onClick = { viewModel.onSearchQueryChange("") }
                )
            }

            actors.isEmpty() -> {
                StateScreen(
                    title = stringResource(R.string.not_found_error_title),
                    message = stringResource(R.string.not_found_error_message),
                    imageRes = R.drawable.not_found,
                    buttonText = stringResource(R.string.clear_search_label),
                    onClick = { viewModel.onSearchQueryChange("") }
                )
            }

            else -> {
                AnimatedVisibility(
                    visible = actors.isNotEmpty(),
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(actors) { actor ->
                            FavoriteActorItem(actor = actor, onClick = { onActorClick(actor) })
                        }
//                    items(actors.size, key = { it }) { index ->
//                        val currentActor = actors[index]
//                        FavoriteActorItem(actor = currentActor, onClick = { onActorClick(currentActor) })
//                    }
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

