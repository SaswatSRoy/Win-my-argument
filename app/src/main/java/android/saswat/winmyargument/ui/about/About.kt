package android.saswat.winmyargument.ui.about

import android.saswat.winmyargument.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay

@Composable
fun AnimatedText(
    fullText: String
) {
    var displayedText by remember { mutableStateOf("") }
    var cursorVisible by remember { mutableStateOf(true) }

    LaunchedEffect(fullText) {
        for (char in fullText) {
            displayedText += char
            delay(80) // Typing speed
        }
        while (true) {
            cursorVisible = !cursorVisible
            delay(500) // Cursor blink speed
        }
    }

    Text(
        text = displayedText + if (cursorVisible) "_" else "",
        fontSize = 35.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        fontFamily = FontFamily(Font(R.font.josefinsansregular))
    )
}

@Composable
fun AboutScreen(
    navController: NavHostController,
    onNavigateTo: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFC2B280)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 48.dp)
        ) {
            AnimatedText("Win Every Debate")

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "with Science!",
                fontSize = 29.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(R.font.josefinsansregular))
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Win My Argument is your AI-powered debate assistant, delivering concise, research-backed arguments in a structured debate format. No more endless searches—get quick, winnable responses based on credible studies.",
                fontSize = 26.sp,
                textAlign = TextAlign.Center,
                lineHeight = 32.sp,
                modifier = Modifier.padding(horizontal = 16.dp),
                fontFamily = FontFamily(Font(R.font.josefinslabregular))
            )

            Spacer(modifier = Modifier.height(40.dp))

            Image(
                painter = painterResource(id = R.drawable.next),
                contentDescription = "Start",
                modifier = Modifier
                    .size(70.dp)
                    .clickable { onNavigateTo() }
            )

            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AboutScreenPreview() {
    AboutScreen(
        navController = rememberNavController(),
        onNavigateTo = {}
    )
}