package com.soma2026.lab.clean.domain.usecase

import com.soma2026.lab.clean.domain.fake.FakeSampleRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetSampleDataUseCaseTest {

    private lateinit var fakeRepository: FakeSampleRepository
    private lateinit var useCase: GetSampleDataUseCase

    @Before
    fun setUp() {
        fakeRepository = FakeSampleRepository()
        useCase = GetSampleDataUseCase(fakeRepository)
    }

    @Test
    fun `성공 응답을 받으면 Result_success를 반환한다`() = runTest {
        fakeRepository.result = Result.success("Hello from Fake")

        val result = useCase()

        assertTrue(result.isSuccess)
        assertEquals("Hello from Fake", result.getOrNull())
    }

    @Test
    fun `실패 응답을 받으면 Result_failure를 반환한다`() = runTest {
        val exception = RuntimeException("Data error")
        fakeRepository.result = Result.failure(exception)

        val result = useCase()

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}