package android.saswat.winmyargument.ui.about

import android.content.Context
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import android.util.Log
import android.saswat.winmyargument.ui.Screens
import android.saswat.winmyargument.MainActivity

@Composable
fun HowScreen(
    navController: NavHostController,
    onNavigateTo: () -> Unit = {},
) {
    // Get the context outside the clickable lambda
    val context = LocalContext.current
    
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
            AnimatedText("How It Works ?")

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "We analyze scientific papers, expert opinions, and peer-reviewed studies to craft fact-based, balanced arguments. The app presents both sides, helping you make your case with confidence.",
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
                    .clickable { 
                        // Log that we're navigating to SignIn
                        Log.d("Navigation", "Navigating to SignIn from How screen")
                       
                        // Use the provided navigation callback
                        onNavigateTo()
                    }
            )

            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@Preview
@Composable
fun HowScreenPreview() {
    HowScreen(navController = rememberNavController(), onNavigateTo = {})
}