package com.soma2026.lab.layered.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soma2026.lab.layered.domain.repository.SampleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SampleViewModel @Inject constructor(
    private val sampleRepository: SampleRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<SampleUiState>(SampleUiState.Idle)
    val uiState: StateFlow<SampleUiState> = _uiState

    fun loadData() {
        viewModelScope.launch {
            _uiState.value = SampleUiState.Loading
            sampleRepository.getData()
                .onSuccess { data -> _uiState.value = SampleUiState.Success(data) }
                .onFailure { error -> _uiState.value = SampleUiState.Error(error.message ?: "Unknown error") }
        }
    }
}

sealed interface SampleUiState {
    data object Idle : SampleUiState
    data object Loading : SampleUiState
    data class Success(val data: String) : SampleUiState
    data class Error(val message: String) : SampleUiState
}
