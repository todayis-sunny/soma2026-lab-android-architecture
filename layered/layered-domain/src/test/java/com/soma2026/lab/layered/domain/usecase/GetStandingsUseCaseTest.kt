package com.soma2026.lab.layered.domain.usecase

import com.soma2026.lab.layered.data.api.dto.StandingGroupDto
import com.soma2026.lab.layered.data.api.dto.StandingTableDto
import com.soma2026.lab.layered.data.api.dto.StandingsResponseDto
import com.soma2026.lab.layered.data.api.dto.TeamDto
import com.soma2026.lab.layered.data.repository.StandingsRepositoryImpl
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetStandingsUseCaseTest {

    private lateinit var repository: StandingsRepositoryImpl
    private lateinit var useCase: GetStandingsUseCase

    @Before
    fun setUp() {
        repository = mockk()
        useCase = GetStandingsUseCase(repository)
    }

    @Test
    fun `TOTAL 타입 standings가 있으면 도메인 모델로 변환해 반환한다`() = runTest {
        val tableDto = StandingTableDto(
            position = 1,
            team = TeamDto(id = 57, name = "Arsenal", shortName = "ARS", crest = "https://example.com/arsenal.png"),
            playedGames = 30,
            form = "WWWDW",
            won = 20,
            draw = 5,
            lost = 5,
            points = 65,
            goalsFor = 70,
            goalsAgainst = 30,
            goalDifference = 40
        )
        val response = StandingsResponseDto(
            standings = listOf(StandingGroupDto(type = "TOTAL", table = listOf(tableDto)))
        )
        coEvery { repository.getStandings() } returns response

        val result = useCase()

        assertTrue(result.isSuccess)
        val standings = result.getOrThrow()
        assertEquals(1, standings.size)
        assertEquals("Arsenal", standings[0].teamName)
        assertEquals(65, standings[0].points)
    }

    @Test
    fun `TOTAL 타입 standings가 없으면 IllegalStateException을 반환한다`() = runTest {
        val response = StandingsResponseDto(
            standings = listOf(StandingGroupDto(type = "HOME", table = emptyList()))
        )
        coEvery { repository.getStandings() } returns response

        val result = useCase()

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is IllegalStateException)
    }

    @Test
    fun `Repository가 예외를 던지면 Result_failure를 반환한다`() = runTest {
        coEvery { repository.getStandings() } throws RuntimeException("Network error")

        val result = useCase()

        assertTrue(result.isFailure)
        assertEquals("Network error", result.exceptionOrNull()?.message)
    }
}