package com.soma2026.lab.layered.domain.repository

import com.soma2026.lab.layered.domain.model.Standing

interface StandingsRepository {
    suspend fun getStandings(): Result<List<Standing>>
}
