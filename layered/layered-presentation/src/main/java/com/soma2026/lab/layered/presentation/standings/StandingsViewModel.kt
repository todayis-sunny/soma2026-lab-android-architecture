package com.soma2026.lab.layered.presentation.standings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soma2026.lab.layered.domain.usecase.GetStandingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StandingsViewModel @Inject constructor(
    private val getStandingsUseCase: GetStandingsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<StandingsUiState>(StandingsUiState.Loading)
    val uiState: StateFlow<StandingsUiState> = _uiState

    init {
        loadStandings()
    }

    fun loadStandings() {
        viewModelScope.launch {
            _uiState.value = StandingsUiState.Loading
            getStandingsUseCase()
                .onSuccess { standings -> _uiState.value = StandingsUiState.Success(standings) }
                .onFailure { error -> _uiState.value = StandingsUiState.Error(error.message ?: "Unknown error") }
        }
    }
}
