package com.soma2026.lab.clean.domain.usecase

import com.soma2026.lab.clean.domain.fake.FakeStandingsRepository
import com.soma2026.lab.clean.domain.model.Standing
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetStandingsUseCaseTest {

    private lateinit var fakeRepository: FakeStandingsRepository
    private lateinit var useCase: GetStandingsUseCase

    @Before
    fun setUp() {
        fakeRepository = FakeStandingsRepository()
        useCase = GetStandingsUseCase(fakeRepository)
    }

    @Test
    fun `성공 응답을 받으면 Result_success를 반환한다`() = runTest {
        // Clean 아키텍처 특징: domain은 DTO를 모른다.
        // Repository가 이미 도메인 모델로 변환해서 반환하므로 UseCase는 순수하게 전달만 한다.
        // DTO 매핑 검증은 clean-data 레이어의 Mapper 테스트에서 담당한다.
        val standings = listOf(
            Standing(
                position = 1,
                teamName = "Arsenal",
                teamLogoUrl = "https://example.com/arsenal.png",
                playedGames = 30,
                won = 20,
                drawn = 5,
                lost = 5,
                points = 65,
                goalDifference = 30,
                form = "WWWDW"
            )
        )
        fakeRepository.result = Result.success(standings)

        val result = useCase()

        assertTrue(result.isSuccess)
        assertEquals(standings, result.getOrNull())
    }

    @Test
    fun `빈 리스트를 받으면 빈 Result_success를 반환한다`() = runTest {
        fakeRepository.result = Result.success(emptyList())

        val result = useCase()

        assertTrue(result.isSuccess)
        assertEquals(emptyList<Standing>(), result.getOrNull())
    }

    @Test
    fun `실패 응답을 받으면 Result_failure를 반환한다`() = runTest {
        val exception = RuntimeException("Network error")
        fakeRepository.result = Result.failure(exception)

        val result = useCase()

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}