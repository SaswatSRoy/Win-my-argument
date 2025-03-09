package android.saswat.winmyargument.ui.mainScreen

import android.media.Image
import android.saswat.winmyargument.R
import android.saswat.winmyargument.viewModel.AuthViewModel
import android.service.autofill.OnClickAction
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.content.MediaType.Companion.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    navigationController: NavController,
    viewModel: AuthViewModel = viewModel()
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var text by remember { mutableStateOf("") }
    
    // Determine if drawer is open to control blur effect
    val isDrawerOpen by remember {
        derivedStateOf { drawerState.currentValue == DrawerValue.Open }
    }
    
    // Calculate blur radius based on drawer state
    val blurRadius by animateFloatAsState(
        targetValue = if (isDrawerOpen) 10f else 0f,
        animationSpec = tween(300)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFC2B280)),
        contentAlignment = Alignment.Center
    ) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    // Drawer content will be added later
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text("Navigation Drawer Content")
                    }
                }
            }
        ) {
            // Main content with blur effect applied when drawer is open
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFC2B280))
                    .blur(blurRadius.dp)  // Apply blur based on drawer state
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Menu button at top left - using original positioning
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.TopStart
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(Color(0xFFE8A87C))
                                .clickable { scope.launch { drawerState.open() } }
                                .padding(3.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.menu__1_),
                                contentDescription = "Menu Button",
                                modifier = Modifier.size(45.dp),
                            )
                        }
                    }


                    Spacer(modifier = Modifier.height(20.dp))

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Win My Argument",
                            color = Color(0xFF333333),
                            fontSize = 45.sp,
                            fontFamily = FontFamily(Font(R.font.abhayalibrebold))
                        )
                        Text(
                            text = "Debate Smarter, Win Faster",
                            color = Color(0xFF555555),
                            fontSize = 25.sp,
                            fontFamily = FontFamily(Font(R.font.abhayalibrebold))

                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))


                    OutlinedTextField(
                        value = text,
                        onValueChange = { text = it },
                        modifier = Modifier.fillMaxWidth().background(Color(0xFFE5D5A4)),
                        placeholder = {
                            Text(
                                "Enter your argument here....",
                                color = Color.Gray,
                                fontFamily = FontFamily(Font(R.font.irishgroverregular)),
                                modifier = Modifier.padding(
                                    start = 16.dp,
                                    top = 12.dp,
                                    bottom = 12.dp
                                ),
                                fontSize = 20.sp
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color.Black,
                            focusedBorderColor = Color.Black,
                            cursorColor = Color.Black,
                        ),
                        shape = RoundedCornerShape(8.dp),
                        singleLine = true,
                    )

                    Spacer(modifier = Modifier.height(16.dp))


                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {

                            ActionButton(
                                iconRes = R.drawable.magnifying_glass,
                                contentDescription = "Search",
                                onClick = { /* Handle search action */ }
                            )

                            // Microphone button with improved interaction
                            ActionButton(
                                iconRes = R.drawable.mic,
                                contentDescription = "Microphone",
                                onClick = { /* Handle microphone action */ }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))


                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(2.dp, Color.Black, RoundedCornerShape(8.dp))
                            .background(Color(0xFFE8D6B3), RoundedCornerShape(8.dp))
                            .padding(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(Color(0xFF66CCCC))
                                    .padding(8.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.idea),
                                    contentDescription = "Tip",
                                    modifier = Modifier.size(45.dp),

                                    )
                            }
                            Text(
                                text = "Tip: Be specific with your argument for better results!",
                                color = Color.Black,
                                fontSize = 18.sp,
                                fontFamily = FontFamily(Font(R.font.inriasansbold))
                            )
                        }
                    }


                    ChatButton(
                        onClick = { /* Handle chat action */ }
                    )

                    Spacer(modifier = Modifier.weight(1f))
                }
            }

        }
    }
}
    @Composable
    fun ActionButton(
        iconRes: Int,
        contentDescription: String,
        onClick: () -> Unit
    ) {
        val interactionSource = remember { MutableInteractionSource() }
        val isPressed by interactionSource.collectIsPressedAsState()
        val scale by animateFloatAsState(
            targetValue = if (isPressed) 0.9f else 1f,
            animationSpec = tween(durationMillis = 100)
        )

        Box(
            modifier = Modifier
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                }
                .clip(CircleShape)
                .border(2.dp, Color.Black, CircleShape)
                .background(Color(0xFFE8D6B3))
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = onClick
                )
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = contentDescription,
                modifier = Modifier.size(45.dp),
            )
        }
    }

    @Composable
    fun ChatButton(onClick: () -> Unit) {
        val interactionSource = remember { MutableInteractionSource() }
        val isPressed by interactionSource.collectIsPressedAsState()
        val scale by animateFloatAsState(
            targetValue = if (isPressed) 0.95f else 1f,
            animationSpec = tween(durationMillis = 100)
        )
        val backgroundColor by animateColorAsState(
            targetValue = if (isPressed) Color(0xFFE0C9A6) else Color(0xFFE8D6B3),
            animationSpec = tween(durationMillis = 100)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                }
                .border(2.dp, Color.Black, RoundedCornerShape(8.dp))
                .background(backgroundColor, RoundedCornerShape(8.dp))
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = onClick
                )
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color(0xFFFF6B6B))
                        .border(1.dp, Color.Black, CircleShape)
                        .padding(8.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.speech_bubble),
                        contentDescription = "Chat",
                        modifier = Modifier.size(45.dp)
                    )
                }
                Text(
                    text = "Chat with your friends",
                    color = Color.Black,
                    fontSize = 23.sp,
                    fontFamily = FontFamily(Font(R.font.inriasansbold)),
                    modifier = Modifier.wrapContentSize()
                )
            }
        }
    }


@Preview ()
@Composable
fun MainScreenPreview() {
    MainScreen(navigationController = rememberNavController())
}