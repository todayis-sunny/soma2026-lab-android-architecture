package com.soma2026.lab.clean.domain.usecase

import com.soma2026.lab.clean.domain.repository.SampleRepository
import javax.inject.Inject

class GetSampleDataUseCase @Inject constructor(
    private val repository: SampleRepository,
) {
    suspend operator fun invoke(): Result<String> = repository.getData()
}
