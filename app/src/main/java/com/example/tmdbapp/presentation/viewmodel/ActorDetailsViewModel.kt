package com.example.tmdbapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdbapp.domain.model.Actor
import com.example.tmdbapp.domain.usecase.RemoveFavoriteActorUseCase
import com.example.tmdbapp.domain.usecase.SaveFavoriteActorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActorDetailsViewModel @Inject constructor(
    private val saveFavoriteUseCase: SaveFavoriteActorUseCase,
    private val removeFavoriteActorUseCase: RemoveFavoriteActorUseCase
): ViewModel() {

    private val _isFavorite = MutableStateFlow<Boolean>(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite

    fun loadActor(actor: Actor) {
        _isFavorite.value = actor.isFavorite
    }

    fun favoriteActor(actor: Actor) {
        viewModelScope.launch {
            saveFavoriteUseCase.invoke(actor)
            _isFavorite.value = true

        }
    }

    fun unfavoriteActor(actor: Actor) {
        viewModelScope.launch {
            removeFavoriteActorUseCase.invoke(actor.id)
            _isFavorite.value = false
        }
    }
}