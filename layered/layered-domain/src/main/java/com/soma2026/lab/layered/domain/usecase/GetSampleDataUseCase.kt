package com.soma2026.lab.layered.domain.usecase

import com.soma2026.lab.layered.data.repository.SampleRepositoryImpl
import javax.inject.Inject

class GetSampleDataUseCase @Inject constructor(
    private val repository: SampleRepositoryImpl
) {
    suspend operator fun invoke(): Result<String> = repository.getData()
}
