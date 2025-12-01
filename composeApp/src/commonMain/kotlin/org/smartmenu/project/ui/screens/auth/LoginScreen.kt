package org.smartmenu.project.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.foundation.Image
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.ImeAction
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import org.jetbrains.compose.resources.painterResource
import org.smartmenu.project.ui.HomeScreenRoute
import org.smartmenu.project.ui.LoginScreenRoute
import org.smartmenu.project.ui.viewmodels.rememberAuthViewModel
import smartmenustatistics.composeapp.generated.resources.*
import kotlin.reflect.KClass

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController, innerPadding: PaddingValues){
    val colors = MaterialTheme.colorScheme
    val authViewModel : AuthViewModel = rememberAuthViewModel()
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var showPassword by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(authViewModel.isLogged){
        if (authViewModel.isLogged){
            navController.navigate(HomeScreenRoute){
                popUpTo(LoginScreenRoute){
                    inclusive = true
                }
            }
        }
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
                            colors.secondary.copy(alpha = 0.90f),
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
            // Logo
            Column (
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                Image(
                    painter = painterResource(Res.drawable.logo),
                    contentDescription = "Logo",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(120.dp)
                )
            }

            // Parte del login
            Column (
                modifier = Modifier
                    .weight(2f)
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
                    Text(
                        text = "Login",
                        fontSize = 32.sp,
                        color = colors.onBackground,
                        modifier = Modifier
                            .padding(bottom = 28.dp)
                    )

                    // Email
                    TextFieldPrefab(
                        text = "Email",
                        value = email,
                        onValueChange = { email = it },
                        placeholder = "ejemplo@gmail.com",
                        imeAction = ImeAction.Next
                    )

                    // Password
                    HiddenTextField(
                        text = "Password",
                        value = password,
                        onValueChange = { password = it },
                        showPassword = showPassword,
                        onShowPasswordChange = { showPassword = !showPassword },
                        imeAction = ImeAction.Send,
                        keyboardActions = KeyboardActions(
                            onSend = {
                                if ( email.isBlank() || password.isBlank()) return@KeyboardActions

                                authViewModel.login(
                                    user = email,
                                    password = password
                                )
                            }
                        )
                    )

                    // Login Button
                    ActionButton(
                        text = "Login",
                        onClick = {
                            if ( email.isBlank() || password.isBlank()) return@ActionButton

                            authViewModel.login(
                                user = email,
                                password = password
                            )
                        }
                    )

                }
            }
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview(){
    SmartMenuTheme {
        LoginScreen(
            navController = rememberNavController(),
            innerPadding = PaddingValues(0.dp)
        )
    }
}