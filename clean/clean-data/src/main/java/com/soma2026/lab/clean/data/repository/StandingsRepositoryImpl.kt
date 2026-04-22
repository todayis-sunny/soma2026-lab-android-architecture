package com.soma2026.lab.clean.data.repository

import com.soma2026.lab.clean.data.api.PremierLeagueApiService
import com.soma2026.lab.clean.data.mapper.StandingMapper
import com.soma2026.lab.clean.domain.model.Standing
import com.soma2026.lab.clean.domain.repository.StandingsRepository
import javax.inject.Inject

class StandingsRepositoryImpl @Inject constructor(
    private val apiService: PremierLeagueApiService
) : StandingsRepository {

    override suspend fun getStandings(): Result<List<Standing>> = runCatching {
        val response = apiService.getStandings()
        val totalStandings = response.standings.find { it.type == "TOTAL" }
            ?: throw IllegalStateException("Standings response does not contain TOTAL type standings.")
        totalStandings.table.map { StandingMapper.toDomain(it) }
    }
}
