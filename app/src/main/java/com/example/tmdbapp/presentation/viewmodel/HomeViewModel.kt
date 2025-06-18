package com.example.tmdbapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.tmdbapp.domain.usecase.GetHomeCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getCharactersUseCase: GetHomeCharactersUseCase
) : ViewModel() {

    val actors = getCharactersUseCase()
        .cachedIn(viewModelScope)
}