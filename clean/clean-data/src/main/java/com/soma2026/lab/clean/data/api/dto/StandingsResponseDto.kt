package com.soma2026.lab.clean.data.api.dto

data class StandingsResponseDto(
    val standings: List<StandingGroupDto>
)

data class StandingGroupDto(
    val type: String,
    val table: List<StandingTableDto>
)

data class StandingTableDto(
    val position: Int,
    val team: TeamDto,
    val playedGames: Int,
    val form: String?,
    val won: Int,
    val draw: Int,
    val lost: Int,
    val points: Int,
    val goalsFor: Int,
    val goalsAgainst: Int,
    val goalDifference: Int
)

data class TeamDto(
    val id: Int,
    val name: String,
    val shortName: String,
    val crest: String
)
