package android.saswat.winmyargument.ui.about

import androidx.compose.foundation.background
import android.saswat.winmyargument.R
import androidx.compose.foundation.Image
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun WhyScreen(
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
            AnimatedText("Why choose us?")

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Tired of endless searches and weak arguments? Win My Argument gives you structured, science-backed, and debate-ready responses in seconds. No fluff, no bias—just facts that help you win.",
                fontSize = 27.sp,
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

@Preview
@Composable
fun WhyScreenPreview() {
    WhyScreen(navController = rememberNavController(), onNavigateTo = {})
}