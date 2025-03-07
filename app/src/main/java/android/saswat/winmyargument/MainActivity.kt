package android.saswat.winmyargument

import android.os.Bundle
import android.saswat.winmyargument.navigation.Navigation
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import android.saswat.winmyargument.ui.theme.WinMyArgumentTheme
import androidx.navigation.compose.rememberNavController
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class MainActivity : ComponentActivity() {
   override fun onCreate(savedInstanceState: Bundle?) {
       installSplashScreen()
       super.onCreate(savedInstanceState)
       enableEdgeToEdge()
       
       setContent {
           WinMyArgumentTheme {
               val nav = rememberNavController()
               
               Surface(
                   modifier = Modifier.fillMaxSize(),
                   color = Color.White
               ) {
                   // Just create the Navigation with the NavController
                   Navigation(navController = nav)
               }
           }
       }
   }
}