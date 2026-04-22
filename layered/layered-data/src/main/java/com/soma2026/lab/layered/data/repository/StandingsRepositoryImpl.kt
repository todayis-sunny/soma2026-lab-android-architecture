package com.soma2026.lab.layered.data.repository

import com.soma2026.lab.layered.data.api.PremierLeagueApiService
import com.soma2026.lab.layered.data.api.dto.StandingTableDto
import com.soma2026.lab.layered.domain.model.Standing
import com.soma2026.lab.layered.domain.repository.StandingsRepository
import javax.inject.Inject

class StandingsRepositoryImpl @Inject constructor(
    private val apiService: PremierLeagueApiService
) : StandingsRepository {

    override suspend fun getStandings(): Result<List<Standing>> = runCatching {
        val response = apiService.getStandings()
        val totalStandings = response.standings.find { it.type == "TOTAL" }
            ?: throw IllegalStateException("Standings response does not contain TOTAL type standings.")
        totalStandings.table.map { it.toDomain() }
    }
}

private fun StandingTableDto.toDomain() = Standing(
    position = position,
    teamName = team.name,
    teamLogoUrl = team.crest,
    playedGames = playedGames,
    won = won,
    drawn = draw,
    lost = lost,
    points = points,
    goalDifference = goalDifference,
    form = form.orEmpty()
)
