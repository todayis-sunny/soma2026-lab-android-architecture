package com.soma2026.lab.clean.domain.usecase

import com.soma2026.lab.clean.domain.model.Standing
import com.soma2026.lab.clean.domain.repository.StandingsRepository
import javax.inject.Inject

class GetStandingsUseCase @Inject constructor(
    private val repository: StandingsRepository
) {
    suspend operator fun invoke(): Result<List<Standing>> = repository.getStandings()
}
