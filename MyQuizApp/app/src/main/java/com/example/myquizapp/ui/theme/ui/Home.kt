import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.R
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myquizapp.ui.theme.Background
import com.example.myquizapp.ui.theme.ui.LeaderboardView
import com.example.myquizapp.ui.theme.ui.QuizView


@Composable
fun HomeView() {
    var navigateToQuiz by remember { mutableStateOf(false) }
    var navigateToLeaderboard by remember { mutableStateOf(false) }

    if (navigateToQuiz) {
        QuizView { navigateToQuiz = false }
    }
    else if (navigateToLeaderboard) {
        LeaderboardView {navigateToLeaderboard = false}
    }
    else {
        Surface(
            color = Background,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val logo = com.example.myquizapp.R.drawable.logo
                Image(
                    painter = painterResource(id = logo ),
                    contentDescription = "Pokeball",
                    modifier = Modifier.size(200.dp),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = { navigateToQuiz = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Start Quiz")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { navigateToLeaderboard = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Leaderboard")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeView() {
    HomeView()
}
