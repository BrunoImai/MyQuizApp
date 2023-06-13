package com.example.quizzapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

data class PlayerScore(val playerName: String, val score: Int)

val leaderboard = mutableListOf<PlayerScore>()

// Add some sample scores for demonstration

@Composable
fun LeaderboardView() {
    leaderboard.add(PlayerScore("Player 1", 500))
    leaderboard.add(PlayerScore("Player 2", 700))
    leaderboard.add(PlayerScore("Player 3", 900))
    leaderboard.add(PlayerScore("Player 4", 600))
    leaderboard.add(PlayerScore("Player 5", 800))
    leaderboard.add(PlayerScore("Player 6", 1000))
    leaderboard.add(PlayerScore("Player 7", 1200))
    leaderboard.add(PlayerScore("Player 8", 400))
    leaderboard.add(PlayerScore("Player 9", 1100))
    leaderboard.add(PlayerScore("Player 10", 950))


    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Leaderboard",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        LazyColumn {
            items(items = leaderboard.take(10)) { playerScore ->
                LeaderboardItem(playerScore)
            }
        }
    }
}

@Composable
fun LeaderboardItem(playerScore: PlayerScore) {
    Row(
        modifier = Modifier.padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = playerScore.playerName,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = playerScore.score.toString(),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}