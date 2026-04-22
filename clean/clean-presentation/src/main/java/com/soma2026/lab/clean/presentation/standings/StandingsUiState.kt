package com.soma2026.lab.clean.presentation.standings

import com.soma2026.lab.clean.domain.model.Standing

sealed interface StandingsUiState {
    data object Loading : StandingsUiState
    data class Success(val standings: List<Standing>) : StandingsUiState
    data class Error(val message: String) : StandingsUiState
}
