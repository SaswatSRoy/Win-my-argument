package android.saswat.winmyargument.ui.signInUp

import android.saswat.winmyargument.viewModel.AuthViewModel
import androidx.compose.foundation.Image
import android.widget.Toast
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
import android.saswat.winmyargument.ui.Screens
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(
    onSignInClick: () -> Unit = {},
    navController: NavController,
    authViewModel: AuthViewModel = viewModel(),
    onNavigateToMain: (Boolean) -> Unit,
) {

    val backgroundColor = Color(0xFFC2B280)
    val textColor = Color(0xFF000000)
    val buttonColor = Color(0xFF02A3AF)

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }
    val authState = authViewModel.authState.collectAsState()

    // Track if the user has attempted to sign up
    var hasAttemptedSignUp by remember { mutableStateOf(false) }

    LaunchedEffect(authState.value) {
        // Only process auth states if user has attempted sign up
        if (hasAttemptedSignUp) {
            when (val state = authState.value) {
                is AuthViewModel.AuthState.SignedIn -> {
                    Toast.makeText(
                        navController.context,
                        "Account created successfully!",
                        Toast.LENGTH_SHORT
                    ).show()
                    onNavigateToMain(true)
                }
                is AuthViewModel.AuthState.UserNotFound -> {
                    Toast.makeText(
                        navController.context,
                        "Account verification failed",
                        Toast.LENGTH_SHORT
                    ).show()
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
                is AuthViewModel.AuthState.Loading,
                is AuthViewModel.AuthState.PasswordResetSent -> {
                    // Don't navigate automatically for these states
                }
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
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Let's Start your Win\nStreak ",
                    fontSize = 30.sp,
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
            // Heading
            Spacer(modifier = Modifier.height(18.dp))

            // Name field
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                placeholder = { Text(text = "Enter your name") },
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

            // Email field
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text(text = "Enter email") },
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

            // Confirm Password field
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                placeholder = { Text("Confirm password") },
                singleLine = true,
                visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                        val iconSize = 24.dp
                        Icon(
                            painter = painterResource(
                                id = if (confirmPasswordVisible) R.drawable.open else R.drawable.eye
                            ),
                            contentDescription = if (confirmPasswordVisible) "Hide password" else "Show password",
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

            // Sign In Button - Added
            Button(
                onClick = {
                    if (password == confirmPassword) {
                        hasAttemptedSignUp = true
                        authViewModel.signUp(email, password, name)
                    } else {
                        MainScope().launch {
                            snackbarHostState.showSnackbar("Passwords do not match")
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(top = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                shape = RoundedCornerShape(28.dp)
            ) {
                if (hasAttemptedSignUp && authState.value is AuthViewModel.AuthState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White
                    )
                } else {
                    Text(
                        text = "Sign Up",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
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

            Spacer(modifier = Modifier.height(2.dp))
            
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
                TextButton(onClick = onSignInClick) {
                    Text(
                        text = "Sign In",
                        color = buttonColor  // Teal color for the link
                    )
                }
            }
        }
    }
}
@Preview
@Composable
fun PreviewSignIn() {
    SignUpScreen(
        navController = rememberNavController(),
        onNavigateToMain = { _ -> }
    )
}