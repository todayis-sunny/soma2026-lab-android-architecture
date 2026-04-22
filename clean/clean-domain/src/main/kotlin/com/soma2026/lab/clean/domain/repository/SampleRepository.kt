package com.soma2026.lab.clean.domain.repository

interface SampleRepository {
    suspend fun getData(): Result<String>
}
