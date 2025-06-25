package com.example.tmdbapp.presentation.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.tmdbapp.domain.model.Actor
import com.example.tmdbapp.presentation.ui.components.CharacterAvatar
import com.example.tmdbapp.presentation.ui.components.LoadingScreen
import com.example.tmdbapp.presentation.ui.components.StateScreen
import com.example.tmdbapp.presentation.viewmodel.HomeViewModel
import com.mdev.tmdbapp.R
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLEncoder

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val actors = viewModel.actors.collectAsLazyPagingItems()

    when (actors.loadState.refresh) {
        is LoadState.Loading -> { LoadingScreen() }
        is LoadState.Error -> {
            StateScreen(
                title = stringResource(R.string.no_internet_connection_title),
                message = stringResource(R.string.no_internet_connection_message),
                imageRes = R.drawable.no_signal,
                buttonText = stringResource(R.string.retry),
                onClick = { actors.retry() }
            )
        }
        is LoadState.NotLoading -> {
            if (actors.itemCount == 0) {
                StateScreen(
                    title = stringResource(R.string.not_found_error_title),
                    message = stringResource(R.string.not_found_error_message),
                    imageRes = R.drawable.not_found,
                    buttonText = stringResource(R.string.retry),
                    onClick = { actors.retry() }
                )
            } else {
                LazyColumn {
                    items(actors.itemCount) { index ->
                        val actor = actors[index]
                        actor?.let { currentActor ->
                            ActorItem(actor = currentActor, onClick = {
                                val actorJsonString = Json.encodeToString(currentActor)
                                val encodedActorJson = URLEncoder.encode(actorJsonString, "UTF-8")
                                navController.navigate("actor_detail/$encodedActorJson")
                            })
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ActorItem(actor: Actor, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CharacterAvatar(actor.imageUrl)
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(actor.name, fontWeight = FontWeight.Bold)
            Text(
                text = "${actor.details.gender}, ${actor.details.department}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
