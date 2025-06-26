package com.example.tmdbapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdbapp.domain.model.Actor
import com.example.tmdbapp.domain.usecase.GetFavoriteActorsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteActorsViewModel @Inject constructor(
    private val getFavoriteActorsUseCase: GetFavoriteActorsUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _favoritesUiState = MutableStateFlow<FavoritesUiState>(FavoritesUiState.Loading)
    val favoritesUiState: StateFlow<FavoritesUiState> = _favoritesUiState

    private var allFavorites = emptyList<Actor>()

    init {
        viewModelScope.launch {
            delay(300) // Fake Loading
            getFavoriteActorsUseCase().collectLatest { actors ->
                if (actors.isEmpty()) {
                    _favoritesUiState.value = FavoritesUiState.NoFavoriteActorsFound
                } else {
                    allFavorites = actors.sortedBy { actor -> actor.name }
                    _favoritesUiState.value = FavoritesUiState.Success(allFavorites)
                }
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        applyFilter()
    }

    private fun applyFilter() {
        val query = _searchQuery.value.lowercase()
        val filteredActors = allFavorites.filter { it.name.lowercase().contains(query) }
        when {
            query.isEmpty() -> {
                _favoritesUiState.value = FavoritesUiState.Success(allFavorites)
            }
            filteredActors.isEmpty() -> {
                _favoritesUiState.value = FavoritesUiState.NoActorsSearchedFound
            }
            else -> {
                _favoritesUiState.value = FavoritesUiState.Success(filteredActors)
            }
        }
    }
}

sealed class FavoritesUiState {
    data object Loading : FavoritesUiState()
    data object NoFavoriteActorsFound : FavoritesUiState()
    data object NoActorsSearchedFound: FavoritesUiState()
    data class Success(val favorites: List<Actor>) : FavoritesUiState()
}