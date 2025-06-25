package com.example.tmdbapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdbapp.domain.model.Actor
import com.example.tmdbapp.domain.usecase.GetFavoriteActorsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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

    private val _favoritesUiState = MutableStateFlow<List<Actor>>(emptyList())
    val favoritesUiState: StateFlow<List<Actor>> = _favoritesUiState

    private var allFavorites: List<Actor> = emptyList()

    init {
        viewModelScope.launch {
            getFavoriteActorsUseCase().collectLatest { actors ->
                allFavorites = actors
                applyFilter()
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        applyFilter()
    }

    private fun applyFilter() {
        val query = _searchQuery.value.lowercase()
        val filtered = if (query.isBlank()) {
            allFavorites
        } else {
            allFavorites.filter { it.name.lowercase().contains(query) }
        }

        val sortedByActorsName = filtered.sortedBy { actor -> actor.name }
        _favoritesUiState.value = sortedByActorsName
    }
}