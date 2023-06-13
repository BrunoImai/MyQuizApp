package com.example.myquizapp.ui.theme.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.rememberAsyncImagePainter
import com.example.myquizapp.GameManager
import com.example.myquizapp.ui.theme.Background
import com.example.myquizapp.ui.theme.Indigo
import com.example.myquizapp.ui.theme.Orange
import com.example.myquizapp.ui.theme.Violet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.math.ceil

data class Question(
    val text: String,
    val answers: List<String>,
    val correctAnswer: String,
    val imageLink: String
)


@Composable
fun QuizView(onQuizEnd: () -> Unit) {
    var playerName by remember { mutableStateOf("") }
    var currentQuestionIndex by remember { mutableStateOf(0) }
    var score by remember { mutableStateOf(0) }
    var isAnswered by remember { mutableStateOf(false) }
    var startQuiz by remember { mutableStateOf(false) }
    var timeRemaining by remember { mutableStateOf(60) }
    var nullUserName by remember { mutableStateOf(true) }
    val randomQuestions = GameManager.questionList.asSequence().shuffled().take(5).toList()



    if (currentQuestionIndex >= randomQuestions.size) {
        GameManager.leaderboard.add(PlayerScore(playerName, score))
        endQuizPopUp(onQuizEnd = onQuizEnd, score = score)
        return
    }
    Surface(
        color = Background,
        modifier = Modifier.fillMaxSize()
    ) {
        if (startQuiz) {
            val currentQuestion = randomQuestions[currentQuestionIndex]
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
                            startQuiz = true
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
            val rainbowColorsBrush = Brush.horizontalGradient(listOf(Color.Red, Orange, Color.Yellow, Color.Green, Color.Blue, Indigo, Violet))
            val infiniteTransition = rememberInfiniteTransition()
            val rotationAnimation = infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 360f,
                animationSpec = infiniteRepeatable(tween(1000, easing = LinearEasing))
            )
            Image(
                painter = rememberAsyncImagePainter(question.imageLink),
                contentDescription = "Question Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.CenterHorizontally)
                    .drawBehind {
                        rotate(rotationAnimation.value) {
                            drawCircle(rainbowColorsBrush, style = Stroke(40F))

                        }

                    }
                    .clip(CircleShape)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun endQuizPopUp(onQuizEnd: () -> Unit, score: Int) {
    AlertDialog(
        onDismissRequest = { onQuizEnd},
        title = { Text("Your Score!") },
        text = { Text(text = "$score", style = MaterialTheme.typography.headlineMedium,) },
        confirmButton = {
            TextButton(onClick = onQuizEnd) {
                Text("finish".uppercase())
            }
        },
    )
}