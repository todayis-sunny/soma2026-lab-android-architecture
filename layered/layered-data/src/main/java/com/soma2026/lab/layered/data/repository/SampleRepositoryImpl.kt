package com.soma2026.lab.layered.data.repository

import javax.inject.Inject

class SampleRepositoryImpl @Inject constructor() {
    suspend fun getData(): Result<String> {
        return Result.success("Hello from Layered Data Layer")
    }
}
