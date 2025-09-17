package com.example.basics

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.basics.ui.theme.BasicsTheme

data class Questions(
    val question: String,
    val options: List<String>,
    val correctAnswer: String
)

enum class AnswerState {
    Neutral, Correct, Wrong
}


fun checkAns(question: Questions, selectedOption: String): Boolean {
    return question.correctAnswer == selectedOption
}

val QuestionsList = mutableListOf(
    Questions(
        question = "Who won 'Most Handsome Man 2025'?",
        options = listOf("The Rock", "Ryan Gosling", "Your Gym Trainer", "Developer"),
        correctAnswer = "Developer"
    ),
    Questions(
        question = "What does a programmer do on a date?",
        options = listOf(
            "Talk about pointers",
            "Debug emotions",
            "Google pickup lines",
            "Update relationship status on GitHub"
        ),
        correctAnswer = "Debug emotions"
    ),
    Questions(
        question = "Why did the Android developer break up?",
        options = listOf(
            "Too many fragments",
            "Activities were boring",
            "No context",
            "Found a better layout"
        ),
        correctAnswer = "No context"
    ),
    Questions(
        question = "Best way to survive a zombie apocalypse?",
        options = listOf(
            "Run fast",
            "Use a shotgun",
            "Offer them free Wi-Fi",
            "Throw Windows updates at them"
        ),
        correctAnswer = "Offer them free Wi-Fi"
    ),
    Questions(
        question = "What’s the strongest data type?",
        options = listOf("int", "long", "char", "Maa ka ashirwad"),
        correctAnswer = "Maa ka ashirwad"
    ),
    Questions(
        question = "Why don’t programmers play hide and seek?",
        options = listOf(
            "Too busy coding",
            "They never hide bugs",
            "Because good luck hiding from StackOverflow",
            "Memory leaks give them away"
        ),
        correctAnswer = "They never hide bugs"
    ),
    Questions(
        question = "If your crush texts 'k', what should you reply?",
        options = listOf("Ok", "kk", "null", "404 feelings not found"),
        correctAnswer = "404 feelings not found"
    ),
    Questions(
        question = "Biggest lie ever told in coding?",
        options = listOf(
            "I’ll fix it later",
            "It works on my machine",
            "Just 5 more minutes",
            "I know what I’m doing"
        ),
        correctAnswer = "It works on my machine"
    )
)


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BasicsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainUI(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }


    }
}


fun clickAns(question: List<Questions>, currentIndex: Int, answer: String): Boolean {
    if (checkAns(question[currentIndex], answer)) {
        AnswerState.Correct
        return true
    } else {
        AnswerState.Wrong
        return false
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainUI(modifier: Modifier = Modifier) {

    val context = LocalContext.current
    val currentList = remember { QuestionsList.shuffled().take(5) }
    var currentIndex by remember { mutableIntStateOf(0) }
    var controlOpacity by remember { mutableStateOf(false) }
    var reset by remember { mutableStateOf("") }
    var score by remember { mutableIntStateOf(0) }


    // Animate size based on expanded state


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xff100e09))
            .padding(20.dp)
    ) {
        Text(
            currentList[currentIndex].question.uppercase(),
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontSize = 35.sp,
            letterSpacing = 2.sp,
            textAlign = TextAlign.Center,
            lineHeight = 35.sp
        )

        Spacer(modifier = Modifier.height(100.dp))

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(currentList[currentIndex].options) { option ->
                var answerState by remember { mutableStateOf(AnswerState.Neutral) }
                var expanded by remember { mutableStateOf(false) }

                if (reset != option) {
                    expanded = false
                    answerState = AnswerState.Neutral
                }

                val scaleX by animateDpAsState(
                    targetValue = if (expanded) 375.dp else 350.dp,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioHighBouncy, // Controls the bounciness
                        stiffness = Spring.StiffnessMedium
                    )
                )

                val scaleY by animateDpAsState(
                    targetValue = if ((controlOpacity && !clickAns(
                            currentList,
                            currentIndex,
                            option
                        ) && reset != option

                    )) {
                        0.dp
                    } else{
                        answerState = if (clickAns(
                                currentList,
                                currentIndex,
                                option
                            ) && controlOpacity) {AnswerState.Correct} else {
                                answerState
                        }
                        60.dp
                          },
                    animationSpec = tween(
                        durationMillis = 225,
                        easing = LinearEasing // The linear ease function
                    )
                )

                Box(
                    modifier = Modifier
                        .size(scaleX, scaleY)
                        .clip(RoundedCornerShape(24.dp))
                        .border(2.dp, Color.White.copy(alpha = 0.4f), RoundedCornerShape(24.dp))
                        .clickable {
                            // Only allow first click
                            val isCorrect = clickAns(currentList, currentIndex, option)
                            expanded = !expanded
                            controlOpacity = !controlOpacity

                            reset = option

                            // Store the text of THIS clicked option

                            answerState = if (isCorrect) {
                                score++
                                AnswerState.Correct} else AnswerState.Wrong
                        }
                ) {
                    // Blurred background
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .blur(20.dp) // 👈 this blurs the background behind
                            .background(
                                when (answerState) {
                                    AnswerState.Correct -> Color(0x8028A745) // green tint
                                    AnswerState.Wrong -> Color(0x80DC3545)   // red tint
                                    else -> Color.White.copy(alpha = 0.2f)   // neutral
                                }
                            ) // transparent white tint
                            .border(1.dp, Color.White.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
                    )

                    // Button text
                    Box(
                        modifier = Modifier
                            .matchParentSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            option,
                            color = Color.White,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center
                        )
                    }

                }

                Spacer(modifier = Modifier.height(15.dp))

            }
        }
        Spacer(modifier = Modifier.height(40.dp))
        Button(onClick = {
            if (currentIndex < currentList.size - 1) {
                currentIndex++
            }
            else{
                val intent = Intent(context, TotalScore::class.java)
                intent.putExtra("SCORE_KEY", score)
                context.startActivity(intent)
            }
            controlOpacity = false
        }, modifier = Modifier.size(80.dp , 80.dp).alpha(
            if(controlOpacity) 1f else 0f
        )

        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Add button")
        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun LoadUI() {
    BasicsTheme {
        MainUI()
    }
}