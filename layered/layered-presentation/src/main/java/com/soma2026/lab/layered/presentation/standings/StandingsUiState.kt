package com.soma2026.lab.layered.presentation.standings

import com.soma2026.lab.layered.domain.model.Standing

sealed interface StandingsUiState {
    data object Loading : StandingsUiState
    data class Success(val standings: List<Standing>) : StandingsUiState
    data class Error(val message: String) : StandingsUiState
}
