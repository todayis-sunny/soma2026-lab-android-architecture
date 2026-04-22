package com.soma2026.lab.layered.domain.repository

interface SampleRepository {
    suspend fun getData(): Result<String>
}
