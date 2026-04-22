package com.soma2026.lab.layered.domain.model

data class Standing(
    val position: Int,
    val teamName: String,
    val teamLogoUrl: String,
    val playedGames: Int,
    val won: Int,
    val drawn: Int,
    val lost: Int,
    val points: Int,
    val goalDifference: Int,
    val form: String
)
