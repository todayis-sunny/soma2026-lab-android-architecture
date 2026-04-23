package com.soma2026.lab.clean.domain.fake

import com.soma2026.lab.clean.domain.repository.SampleRepository

class FakeSampleRepository : SampleRepository {

    var result: Result<String> = Result.success("Hello from Fake")

    override suspend fun getData(): Result<String> = result
}