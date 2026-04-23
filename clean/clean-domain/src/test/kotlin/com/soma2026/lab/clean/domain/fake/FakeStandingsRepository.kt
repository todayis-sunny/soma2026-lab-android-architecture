package com.soma2026.lab.clean.domain.fake

import com.soma2026.lab.clean.domain.model.Standing
import com.soma2026.lab.clean.domain.repository.StandingsRepository

class FakeStandingsRepository : StandingsRepository {

    var result: Result<List<Standing>> = Result.success(emptyList())

    override suspend fun getStandings(): Result<List<Standing>> = result
}
