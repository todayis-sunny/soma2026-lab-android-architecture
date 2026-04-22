package com.soma2026.lab.clean.data.mapper

import com.soma2026.lab.clean.data.api.dto.StandingTableDto
import com.soma2026.lab.clean.domain.model.Standing

object StandingMapper {
    fun toDomain(dto: StandingTableDto): Standing = Standing(
        position = dto.position,
        teamName = dto.team.name,
        teamLogoUrl = dto.team.crest,
        playedGames = dto.playedGames,
        won = dto.won,
        drawn = dto.draw,
        lost = dto.lost,
        points = dto.points,
        goalDifference = dto.goalDifference,
        form = dto.form.orEmpty()
    )
}
