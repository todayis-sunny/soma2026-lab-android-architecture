package com.soma2026.lab.layered.domain.usecase

import com.soma2026.lab.layered.data.api.dto.StandingTableDto
import com.soma2026.lab.layered.data.repository.StandingsRepositoryImpl
import com.soma2026.lab.layered.domain.model.Standing
import javax.inject.Inject

class GetStandingsUseCase @Inject constructor(
    private val repository: StandingsRepositoryImpl
) {
    suspend operator fun invoke(): Result<List<Standing>> = runCatching {
        val response = repository.getStandings()
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