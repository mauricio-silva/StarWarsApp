package com.example.tmdbapp.presentation.ui.screen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.tmdbapp.domain.model.Actor
import com.example.tmdbapp.domain.model.ActorMovie
import com.example.tmdbapp.presentation.theme.HighlightColor2
import com.example.tmdbapp.presentation.viewmodel.ActorDetailsViewModel
import com.mdev.tmdbapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActorDetailsScreen(
    actor: Actor,
    viewModel: ActorDetailsViewModel = hiltViewModel(),
) {
    val details = actor.details
    val isFavorite by viewModel.isFavorite.collectAsState()

    val favoriteButtonAnimateColorState by animateColorAsState(
        targetValue = if (isFavorite) HighlightColor2 else Color.LightGray,
        animationSpec = tween(durationMillis = 400)
    )

    LaunchedEffect(actor.id) {
        viewModel.loadActor(actor)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 16.dp)
            .background(Color.White)
    ) {
        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                AsyncImage(
                    model = details.imageUrl,
                    contentDescription = details.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.LightGray, CircleShape)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(details.name, style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        if (isFavorite) viewModel.unfavoriteActor(actor)
                        else viewModel.favoriteActor(actor)
                    },
                    colors = ButtonDefaults.buttonColors(favoriteButtonAnimateColorState)
                ) {
                    Text(
                        stringResource(if (isFavorite) R.string.unfavorite_label else R.string.favorite_label),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    stringResource(R.string.biography_label),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                InfoRow(stringResource(R.string.name_label), details.name)
                InfoRow(stringResource(R.string.original_name_label), details.name)
                InfoRow(stringResource(R.string.gender_label), details.gender)
                InfoRow(stringResource(R.string.department_label), details.department)
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.known_for_label),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        items(details.knowFor) { actorMovie ->
            KnownForCard(actorMovie)
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = "$label: ",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )
        Text(text = value, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun KnownForCard(movie: ActorMovie) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
    ) {
        Column(Modifier.background(MaterialTheme.colorScheme.surface)) {
            movie.posterPath?.let {
                AsyncImage(
                    model = it,
                    contentDescription = movie.title,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .background(Color.Black)
                )
            }
            Column(modifier = Modifier.padding(12.dp)) {
                Text(movie.title, style = MaterialTheme.typography.titleSmall)
                Spacer(modifier = Modifier.height(4.dp))
                Text(movie.overview, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
