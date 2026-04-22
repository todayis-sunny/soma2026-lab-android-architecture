package com.soma2026.lab.layered.data.api

import com.soma2026.lab.layered.data.api.dto.StandingsResponseDto
import retrofit2.http.GET

interface PremierLeagueApiService {
    @GET("competitions/PL/standings")
    suspend fun getStandings(): StandingsResponseDto
}
