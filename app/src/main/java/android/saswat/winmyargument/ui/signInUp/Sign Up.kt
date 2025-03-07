package android.saswat.winmyargument.ui.signInUp

import android.saswat.winmyargument.viewModel.AuthViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.saswat.winmyargument.R
import android.widget.Toast
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

@Composable
fun SignInScreen(
    onNavigateToMain: () -> Unit = {},
    navController: NavController,
    onSignUpClick: () -> Unit = {},
    authViewModel: AuthViewModel = viewModel()
) {

    val backgroundColor = Color(0xFFC2B280)
    val textColor = Color(0xFF000000)
    val buttonColor = Color(0xFF02A3AF)

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    // Add these for handling authentication state
    val snackbarHostState = remember { SnackbarHostState() }
    val authState = authViewModel.authState.collectAsState()

    // Add LaunchedEffect to react to auth state changes
    LaunchedEffect(authState.value) {
        when (val state = authState.value) {
            is AuthViewModel.AuthState.SignedIn -> {
                authViewModel.checkUserExists { userExists ->
                    if (userExists) {
                        Toast.makeText(
                            navController.context,
                            "Successfully signed in!",
                            Toast.LENGTH_SHORT
                        ).show()
                        onNavigateToMain()
                    } else {
                        Toast.makeText(
                            navController.context,
                            "Account created successfully!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    
                }
            }
            is AuthViewModel.AuthState.Error -> {
                // Show error in toast instead of snackbar for better visibility
                Toast.makeText(
                    navController.context,
                    state.message,
                    Toast.LENGTH_LONG
                ).show()
            }
            is AuthViewModel.AuthState.Initial,
            is AuthViewModel.AuthState.SignedOut,
            is AuthViewModel.AuthState.Loading -> {
                // Don't navigate automatically for these states
            }
            is AuthViewModel.AuthState.PasswordResetSent -> {
                // Show confirmation toast for password reset
                Toast.makeText(
                    navController.context,
                    "Password reset email sent!",
                    Toast.LENGTH_SHORT
                ).show()
               // Don't navigate automatically for these states
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        // Low opacity badge in background
        Image(
            painter = painterResource(id = R.drawable.badge),
            contentDescription = "Badge",
            modifier = Modifier
                .align(Alignment.Center)
                .size(500.dp)
                .alpha(0.4f),
            contentScale = ContentScale.Fit
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            // Logo row at the top
            Box(
                modifier = Modifier
                    .fillMaxWidth()

            ){
                Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Welcome Back",
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth(),
                    fontFamily = FontFamily(Font(R.font.josefinsansregular))
                )

                Image(
                    painter = painterResource(id = R.drawable.trophy),
                    contentDescription = "Trophy Icon",
                    modifier = Modifier
                        .size(10.dp)
                        .padding(8.dp)
                )
            }

            }

            // Heading
            Spacer(modifier = Modifier.height(18.dp))

            // Email field
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text(text="Enter email") },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedIndicatorColor = textColor,
                    focusedIndicatorColor = textColor
                ),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp)
            )

            // Password field
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("Enter the password") },
                singleLine = true,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val iconSize = 24.dp
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            painter = painterResource(
                                id = if (passwordVisible) R.drawable.open else R.drawable.eye
                            ),
                            contentDescription = if (passwordVisible) "Hide password" else "Show password",
                            modifier = Modifier.size(iconSize)
                        )
                    }
                },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedIndicatorColor = textColor,
                    focusedIndicatorColor = textColor
                ),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp)
            )

            // Update the Sign In Button with authentication logic
            Button(
                onClick = { authViewModel.signIn(email, password) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(top = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                shape = RoundedCornerShape(28.dp)
            ) {
                if (authState.value is AuthViewModel.AuthState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White
                    )
                } else {
                    Text(
                        text = "Sign In",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }

            // Add "Forgot Password?" link
            TextButton(
                onClick = {
                    if (email.isNotBlank()) {
                        authViewModel.resetPassword(email)
                    } else {
                        MainScope().launch {
                            snackbarHostState.showSnackbar("Please enter your email address")
                        }
                    }
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(
                    text = "Forgot Password?",
                    color = buttonColor
                )
            }

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                color = textColor.copy(alpha = 0.5f)
            )

            // Or continue with text
            Text(
                text = "Or continue with",
                color = textColor.copy(alpha = 0.7f),
                fontSize = 17.sp,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // Social sign-in options with badge background
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Google icon
                    IconButton(onClick = { /* Google sign-in logic */ }) {
                        Image(
                            painter = painterResource(id = R.drawable.google),
                            contentDescription = "Sign in with Google",
                            modifier = Modifier.size(36.dp)
                        )
                    }

                    // GitHub icon
                    IconButton(onClick = { /* GitHub sign-in logic */ }) {
                        Image(
                            painter = painterResource(id = R.drawable.github),
                            contentDescription = "Sign in with GitHub",
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }

                // Badge in background with low opacity
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Already have an account
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Already have an account? ",
                    color = textColor
                )
                TextButton(onClick = onSignUpClick) {
                    Text(
                        text = "Sign Up",
                        color = buttonColor  // Teal color for the link
                    )
                }
            }
        }

        // Add SnackbarHost for error messages
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )
    }
}
@Preview
@Composable
fun PreviewSignUp() {
    SignInScreen(navController = rememberNavController())


}