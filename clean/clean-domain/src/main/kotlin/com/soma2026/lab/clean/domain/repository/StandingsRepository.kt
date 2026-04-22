package com.soma2026.lab.clean.domain.repository

import com.soma2026.lab.clean.domain.model.Standing

interface StandingsRepository {
    suspend fun getStandings(): Result<List<Standing>>
}
