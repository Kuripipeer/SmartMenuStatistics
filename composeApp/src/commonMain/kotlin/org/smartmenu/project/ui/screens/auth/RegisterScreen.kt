package org.smartmenu.project.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.smartmenu.project.ui.AccentPurpleDark
import org.smartmenu.project.ui.SmartMenuTheme
import org.smartmenu.project.ui.screens.auth.components.ActionButton
import org.smartmenu.project.ui.screens.auth.components.HiddenTextField
import org.smartmenu.project.ui.screens.auth.components.TextFieldPrefab
import org.smartmenu.project.ui.viewmodels.AuthViewModel

@Composable
fun RegisterScreen(navController: NavController, innerPadding: PaddingValues){
    val colors = MaterialTheme.colorScheme
    val authViewModel : AuthViewModel = viewModel()
    var firstName by remember {
        mutableStateOf("")
    }
    var lastName by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var confirmPassword by remember {
        mutableStateOf("")
    }
    var showPassword by remember {
        mutableStateOf(false)
    }
    var showConfirmPassword by remember {
        mutableStateOf(false)
    }

    Box (
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Background
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            colors.primary.copy(alpha = 0.95f),
                            AccentPurpleDark.copy(alpha = 0.95f)
                        ),
                        start = Offset(0f, 0f),
                        end = Offset(1000f, 0f)
                    )
                )
        )
        Column(
            modifier = Modifier
                .fillMaxSize()

        ) {
            Row (
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(innerPadding),
                verticalAlignment = Alignment.CenterVertically
            ){
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Volver",
                        tint = Color.White
                    )
                }
                Text(
                    text = "Sign Up",
                    fontSize = 40.sp,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 40.dp),
                    textAlign = TextAlign.Center
                )
            }

            // Parte del login
            Column (
                modifier = Modifier
                    .weight(6f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 60.dp))
                    .background(colors.background)
                    .padding(horizontal = 38.dp, vertical = 38.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Column (
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    // Email
                    TextFieldPrefab(
                        text = "First Name",
                        value = firstName,
                        onValueChange = { firstName = it },
                        placeholder = "Juan Alfonso"
                    )

                    TextFieldPrefab(
                        text = "Last Name",
                        value = lastName,
                        onValueChange = { lastName = it },
                        placeholder = "Perez Lopez"
                    )

                    TextFieldPrefab(
                        text = "Email",
                        value = email,
                        onValueChange = { email = it },
                        placeholder = "ejemplo@gmail.com"
                    )

                    // Password
                    HiddenTextField(
                        text = "Password",
                        value = password,
                        onValueChange = { password = it },
                        showPassword = showPassword,
                        onShowPasswordChange = { showPassword = !showPassword }
                    )

                    //Confirm Password
                    HiddenTextField(
                        text = "Confirm Password",
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        showPassword = showConfirmPassword,
                        onShowPasswordChange = { showConfirmPassword = !showConfirmPassword }
                    )

                    // Login Button
                    ActionButton("Sign Up")
                }

                // Sing Up
                Column (
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text(
                        text = "Already have any account? Sign In",
                        color = colors.primary,
                        modifier = Modifier
                            .clickable {
                                navController.popBackStack()
                            }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun RegisterScreenPreview(){
    SmartMenuTheme {
        RegisterScreen(
            navController = rememberNavController(),
            innerPadding = PaddingValues()
        )
    }
}