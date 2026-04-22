package com.soma2026.lab.layered.data.repository

import com.soma2026.lab.layered.domain.repository.SampleRepository
import javax.inject.Inject

class SampleRepositoryImpl @Inject constructor() : SampleRepository {
    override suspend fun getData(): Result<String> {
        return Result.success("Hello from Layered Data Layer")
    }
}
