package org.smartmenu.project.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import org.smartmenu.project.models.RolesResponse
import org.smartmenu.project.ui.AccentPurpleDark
import org.smartmenu.project.ui.HomeScreenRoute
import org.smartmenu.project.ui.RegisterScreenRoute
import org.smartmenu.project.ui.SmartMenuTheme
import org.smartmenu.project.ui.UsersScreenRoute
import org.smartmenu.project.ui.screens.auth.components.ActionButton
import org.smartmenu.project.ui.screens.auth.components.HiddenTextField
import org.smartmenu.project.ui.screens.auth.components.TextFieldPrefab
import org.smartmenu.project.ui.screens.auth.components.RoleDropdownPrefab
import org.smartmenu.project.ui.viewmodels.AdmonViewModel

@Composable
fun RegisterScreen(navController: NavController, innerPadding: PaddingValues) {

    val colors = MaterialTheme.colorScheme
    val admvm: AdmonViewModel = viewModel()

    // Estados desde VM
    val roles = admvm.rolesList.value
    val userCreatedSuccess by admvm.userCreatedSuccessfully
    val userCreateError by admvm.userCreateError

    // Snackbar
    val snackbarHostState = remember { SnackbarHostState() }

    // Estados del form
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var showConfirmPassword by remember { mutableStateOf(false) }

    var selectedRole by remember { mutableStateOf<RolesResponse?>(null) }

    // === MANEJO DE EXITO ===
    if (userCreatedSuccess) {
        LaunchedEffect(Unit) {
            snackbarHostState.showSnackbar("Usuario creado con éxito")

            // Cambia "home" por tu ruta real
            navController.navigate(UsersScreenRoute) {
                popUpTo(RegisterScreenRoute) { inclusive = true }
            }

            admvm.resetSuccessState()
        }
    }

    // === MANEJO DE ERROR ===
    userCreateError?.let { errorMsg ->
        LaunchedEffect(errorMsg) {
            snackbarHostState.showSnackbar(errorMsg)
            admvm.resetErrorState()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { scaffoldPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding)
        ) {

            // Background degradado
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

            Column(modifier = Modifier.fillMaxSize()) {

                // Encabezado
                Row(
                    modifier = Modifier
                        .padding(innerPadding)
                        .height(90.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            navController.navigate(UsersScreenRoute) {
                                popUpTo(RegisterScreenRoute) { inclusive = true }
                            }
                        },
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

                // Contenido scrollable
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(topStart = 60.dp))
                        .background(colors.background)
                        .padding(horizontal = 38.dp, vertical = 38.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    // Campo First Name
                    TextFieldPrefab(
                        text = "First Name",
                        value = firstName,
                        onValueChange = { firstName = it },
                        placeholder = "Juan Alfonso"
                    )

                    // Campo Last Name
                    TextFieldPrefab(
                        text = "Last Name",
                        value = lastName,
                        onValueChange = { lastName = it },
                        placeholder = "Pérez López"
                    )

                    // Dropdown Rol
                    RoleDropdownPrefab(
                        roles = roles,
                        selectedRole = selectedRole,
                        onRoleSelected = { selectedRole = it }
                    )

                    // Email
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

                    // Confirm Password
                    HiddenTextField(
                        text = "Confirm Password",
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        showPassword = showConfirmPassword,
                        onShowPasswordChange = { showConfirmPassword = !showConfirmPassword }
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Botón
                    ActionButton(
                        text = "Sign Up",
                        onClick = {
                            if (
                                firstName.isBlank() ||
                                lastName.isBlank() ||
                                email.isBlank() ||
                                password.isBlank() ||
                                confirmPassword.isBlank()
                            ) return@ActionButton

                            if (password != confirmPassword) return@ActionButton
                            if (selectedRole == null) return@ActionButton

                            admvm.newUser(
                                nombre = "$firstName $lastName",
                                usuario = email,
                                contraseña = password,
                                rol_id = selectedRole!!.id
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(60.dp))
                }
            }
        }
    }
}

@Preview
@Composable
fun RegisterScreenPreview() {
    SmartMenuTheme {
        RegisterScreen(
            navController = rememberNavController(),
            innerPadding = PaddingValues()
        )
    }
}
