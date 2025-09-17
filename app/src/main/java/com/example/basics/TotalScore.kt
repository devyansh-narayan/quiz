package com.example.basics

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.basics.ui.theme.BasicsTheme

class TotalScore : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val score = this.intent.getIntExtra("SCORE_KEY", 0) // 0 = default if nothing passed
        setContent {
            BasicsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ScoreUI(
                        modifier = Modifier.padding(innerPadding),
                        score
                    )
                }
            }
        }
    }
}

@Composable
fun ScoreUI(modifier: Modifier = Modifier , score: Int = -1) {

    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xff100e09))
            .padding(20.dp)
    ) {
        Text(
            "Congrats You Scored $score / 5",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontSize = 35.sp,
            letterSpacing = 2.sp,
            textAlign = TextAlign.Center,
            lineHeight = 35.sp
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(onClick = {
                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)

        }, modifier = Modifier.size(80.dp , 80.dp)

        ) {
            Icon(Icons.Filled.Refresh, contentDescription = "Add button")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoadUIScore() {
    BasicsTheme {
        ScoreUI()
    }
}