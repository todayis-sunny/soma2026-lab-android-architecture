package com.soma2026.lab.layered.domain.usecase

import com.soma2026.lab.layered.data.repository.SampleRepositoryImpl
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetSampleDataUseCaseTest {

    private lateinit var repository: SampleRepositoryImpl
    private lateinit var useCase: GetSampleDataUseCase

    @Before
    fun setUp() {
        repository = mockk()
        useCase = GetSampleDataUseCase(repository)
    }

    @Test
    fun `성공 응답을 받으면 Result_success를 반환한다`() = runTest {
        coEvery { repository.getData() } returns Result.success("Hello from Layered Data Layer")

        val result = useCase()

        assertTrue(result.isSuccess)
        assertEquals("Hello from Layered Data Layer", result.getOrNull())
    }

    @Test
    fun `실패 응답을 받으면 Result_failure를 반환한다`() = runTest {
        val exception = RuntimeException("Data error")
        coEvery { repository.getData() } returns Result.failure(exception)

        val result = useCase()

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}