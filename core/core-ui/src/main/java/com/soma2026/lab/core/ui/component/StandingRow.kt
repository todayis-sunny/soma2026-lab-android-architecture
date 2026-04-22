package com.soma2026.lab.core.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StandingRow(
    position: Int,
    teamName: String,
    teamLogoUrl: String,
    playedGames: Int,
    won: Int,
    drawn: Int,
    lost: Int,
    points: Int,
    goalDifference: Int,
    form: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = position.toString(),
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.width(20.dp)
        )
        TeamLogo(
            url = teamLogoUrl,
            contentDescription = teamName,
            size = 24.dp
        )
        Text(
            text = teamName,
            fontSize = 13.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )
        StatText(playedGames.toString())
        StatText(won.toString())
        StatText(drawn.toString())
        StatText(lost.toString())
        StatText(
            text = if (goalDifference > 0) "+$goalDifference" else goalDifference.toString()
        )
        StatText(
            text = points.toString(),
            fontWeight = FontWeight.Bold
        )
        FormBadge(form = form)
    }
}

@Composable
private fun StatText(
    text: String,
    fontWeight: FontWeight = FontWeight.Normal
) {
    Text(
        text = text,
        fontSize = 12.sp,
        fontWeight = fontWeight,
        textAlign = TextAlign.Center,
        modifier = Modifier.width(24.dp)
    )
}
