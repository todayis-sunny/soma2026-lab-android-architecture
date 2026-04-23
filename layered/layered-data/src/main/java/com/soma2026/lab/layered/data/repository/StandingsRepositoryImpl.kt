package com.soma2026.lab.layered.data.repository

import com.soma2026.lab.layered.data.api.PremierLeagueApiService
import com.soma2026.lab.layered.data.api.dto.StandingsResponseDto
import javax.inject.Inject

class StandingsRepositoryImpl @Inject constructor(
    private val apiService: PremierLeagueApiService
) {
    suspend fun getStandings(): StandingsResponseDto = apiService.getStandings()
}
