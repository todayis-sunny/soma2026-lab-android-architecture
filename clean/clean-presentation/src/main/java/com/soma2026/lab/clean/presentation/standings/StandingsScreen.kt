package com.soma2026.lab.clean.presentation.standings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.soma2026.lab.core.ui.component.StandingRow

@Composable
fun StandingsScreen(
    modifier: Modifier = Modifier,
    viewModel: StandingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(modifier = modifier.fillMaxSize()) {
        Text(
            text = "프리미어리그 순위표",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )
        StandingsHeader()
        HorizontalDivider()
        when (val state = uiState) {
            is StandingsUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is StandingsUiState.Success -> {
                LazyColumn {
                    items(state.standings) { standing ->
                        StandingRow(
                            position = standing.position,
                            teamName = standing.teamName,
                            teamLogoUrl = standing.teamLogoUrl,
                            playedGames = standing.playedGames,
                            won = standing.won,
                            drawn = standing.drawn,
                            lost = standing.lost,
                            points = standing.points,
                            goalDifference = standing.goalDifference,
                            form = standing.form
                        )
                        HorizontalDivider()
                    }
                }
            }
            is StandingsUiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = state.message)
                }
            }
        }
    }
}

@Composable
private fun StandingsHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "#",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.width(20.dp)
        )
        Spacer(modifier = Modifier.size(24.dp))
        Text(
            text = "팀",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
        listOf("P", "W", "D", "L", "GD", "Pts").forEach { label ->
            Text(
                text = label,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.width(24.dp)
            )
        }
        Text(
            text = "최근",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.width(88.dp)
        )
    }
}
