package com.example.tmdbapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdbapp.domain.model.Actor
import com.example.tmdbapp.domain.usecase.RemoveFavoriteActorUseCase
import com.example.tmdbapp.domain.usecase.SaveFavoriteActorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActorDetailsViewModel @Inject constructor(
    private val saveFavoriteUseCase: SaveFavoriteActorUseCase,
    private val removeFavoriteActorUseCase: RemoveFavoriteActorUseCase
): ViewModel() {

    fun favoriteActor(actor: Actor) {
        viewModelScope.launch {
            saveFavoriteUseCase.invoke(actor)
        }
    }

    fun unfavoriteActor(actor: Actor) {
        viewModelScope.launch {
            removeFavoriteActorUseCase.invoke(actor.id)
        }
    }
}