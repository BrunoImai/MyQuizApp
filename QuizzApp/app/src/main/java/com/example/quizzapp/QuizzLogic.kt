import android.R
import android.content.Context
import android.content.SharedPreferences
import android.provider.Settings.Global.getString
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.quizzapp.LeaderboardView
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.math.ceil

data class Question(
    val text: String,
    val answers: List<String>,
    val correctAnswer: String,
    val imageLink: String
)

val quizQuestions = listOf(
    Question(
        "What is the capital of France?",
        listOf("Paris", "London", "Berlin", "Rio de Janeiro"),
        "Paris",
        "https://cdn.unenvironment.org/2020-02/sustainable-cities.jpg"
    ),
    Question(
        "Which planet is closest to the Sun?",
        listOf("Venus", "Mercury", "Mars", "Pluto"),
        "Mercury",
        "https://cdn.unenvironment.org/2020-02/sustainable-cities.jpg"
    ),
    Question(
        "What is the largest ocean on Earth?",
        listOf("Atlantic Ocean", "Indian Ocean", "Pacific Ocean", "Dead Ocean"),
        "Pacific Ocean",
        "https://cdn.unenvironment.org/2020-02/sustainable-cities.jpg"
    )
)

@Composable
fun HomePage() {
    var playerName by remember { mutableStateOf("") }
    var showQuiz by remember { mutableStateOf(false) }
    var showLeaderboard by remember { mutableStateOf(false) }
    var nullUserName by remember { mutableStateOf(true) }

    if (showQuiz) {
        QuizApp(playerName) { showQuiz = false }
    } else if (showLeaderboard) {
        LeaderboardView()
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = playerName,
                onValueChange = { playerName = it },
                label = { Text("Enter your name") },
                modifier = Modifier.fillMaxWidth(),
                isError = nullUserName
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (playerName != "") {
                        showQuiz = true
                        nullUserName = false
                    } else {
                        nullUserName = true
                    }
                          },
                enabled = playerName.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Start Quiz")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { showLeaderboard = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Leaderboard")
            }
        }
    }
}


@Composable
fun QuizApp(playerName: String, onQuizEnd: (Int) -> Unit) {
    var currentQuestionIndex by remember { mutableStateOf(0) }
    var score by remember { mutableStateOf(0) }
    var isAnswered by remember { mutableStateOf(false) }
    var timeRemaining by remember { mutableStateOf(60) }

    if (currentQuestionIndex >= quizQuestions.size) {

        onQuizEnd(score) // Pass the score to onQuizEnd
        return
    }

    val currentQuestion = quizQuestions[currentQuestionIndex]
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedQuizCard(
            playerName = playerName,
            question = currentQuestion,
            isAnswered = isAnswered,
            onAnswerSelected = { answer ->
                if (!isAnswered) {
                    isAnswered = true
                    if (answer == currentQuestion.correctAnswer) {
                        score += 100 * timeRemaining
                    }
                    CoroutineScope(Dispatchers.Main).launch {
                        delay(1000) // Delay for 1 second
                        currentQuestionIndex++
                        isAnswered = false
                        timeRemaining = 60
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Score: $score", fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Time Remaining: ${ceil(timeRemaining.toFloat())} seconds")

        val timerScope = rememberCoroutineScope()
        DisposableEffect(currentQuestionIndex) {
            val timerJob = timerScope.launch {
                val timerFlow = MutableStateFlow(60)
                launch {
                    while (timerFlow.value > 0) {
                        delay(1000)
                        timerFlow.value -= 1
                    }
                }
                timerFlow.collect { remainingTime ->
                    timeRemaining = remainingTime
                    if (remainingTime == 0 && !isAnswered) {
                        currentQuestionIndex++
                        isAnswered = false
                        timeRemaining = 60
                    }
                }
            }
            onDispose {
                timerJob.cancel()
            }
        }
    }
}

@Composable
fun AnimatedQuizCard(
    playerName: String,
    question: Question,
    isAnswered: Boolean,
    onAnswerSelected: (String) -> Unit
) {
    Card(
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 10.dp
        ),
        modifier = Modifier
            .animateContentSize(
                animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
            )
            .padding(16.dp)
            .clickable { },
        border = BorderStroke(2.dp, Color.Magenta)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberAsyncImagePainter(question.imageLink),
                contentDescription = "Question Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.CenterHorizontally)
                    .border(BorderStroke(2.dp, Color.Magenta))
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = question.text,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .scale(scaleX = 1f, scaleY = 1f)
                    .alpha(if (isAnswered) 0.5f else 1f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            question.answers.forEach { answer ->
                AnswerOption(
                    text = answer,
                    isAnswered = isAnswered,
                    isCorrect = answer == question.correctAnswer,
                    onAnswerSelected = { onAnswerSelected(answer) }
                )
            }
        }
    }
}

@Composable
fun AnswerOption(
    text: String,
    isAnswered: Boolean,
    isCorrect: Boolean,
    onAnswerSelected: () -> Unit
) {
    val textColor by animateColorAsState(
        targetValue = if (isAnswered) {
            if (isCorrect) Color.Green else Color.Red
        } else {
            Color.Black
        }
    )

    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        color = textColor,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .padding(vertical = 4.dp)
            .clickable { onAnswerSelected() }
            .alpha(if (isAnswered) 0.5f else 1f)
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MaterialTheme {
        val playerName by remember { mutableStateOf("") }
        val score by remember { mutableStateOf(0) }
        val showResults by remember { mutableStateOf(false) }
        val showLeaderboard by remember { mutableStateOf(false) }

        if (showResults) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Player Name: $playerName")
                Text("Score: $score")
            }
        } else if (showLeaderboard) {
            LeaderboardView()
        } else {
            HomePage()
        }
    }
}