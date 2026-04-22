package com.soma2026.lab.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soma2026.lab.core.ui.theme.FormDraw
import com.soma2026.lab.core.ui.theme.FormLoss
import com.soma2026.lab.core.ui.theme.FormWin

@Composable
fun FormBadge(
    form: String,
    modifier: Modifier = Modifier
) {
    val results = remember(form) { form.split(",").takeLast(5) }
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        results.forEach { result ->
            FormDot(result.trim())
        }
    }
}

@Composable
private fun FormDot(result: String) {
    val color = when (result) {
        "W" -> FormWin
        "D" -> FormDraw
        "L" -> FormLoss
        else -> Color.Gray
    }
    Box(
        modifier = Modifier
            .size(16.dp)
            .background(color = color, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = result,
            color = Color.White,
            fontSize = 8.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
