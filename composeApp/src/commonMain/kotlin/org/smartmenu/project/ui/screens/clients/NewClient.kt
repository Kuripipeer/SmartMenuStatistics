package org.smartmenu.project.ui.screens.clients

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import org.smartmenu.project.ui.AccentPurpleDark
import org.smartmenu.project.ui.ClientsScreenRoute
import org.smartmenu.project.ui.screens.auth.components.ActionButton
import org.smartmenu.project.ui.screens.auth.components.TextFieldPrefab
import org.smartmenu.project.ui.viewmodels.AdmonViewModel
import org.smartmenu.project.ui.viewmodels.AuthViewModel
import kotlin.reflect.KClass

@Composable
fun NewClient(navController: NavController, innerPadding: PaddingValues) {

    val colors = MaterialTheme.colorScheme
    val vm: AdmonViewModel = viewModel()

    // Estados del ViewModel
    val createSuccess by vm.clientCreateSuccess
    val createError by vm.clientCreateError

    // Snackbar
    val snackbarHostState = remember { SnackbarHostState() }

    // Campos del formulario
    var nombre by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }

    // ===============================
    // ÉXITO
    // ===============================
    if (createSuccess) {
        LaunchedEffect(Unit) {
            snackbarHostState.showSnackbar("Cliente creado con éxito")
            vm.resetClientCreateStates()

            navController.navigate(ClientsScreenRoute) {
                popUpTo(ClientsScreenRoute) { inclusive = true }
            }
        }
    }

    // ===============================
    // ERROR
    // ===============================
    createError?.let { err ->
        LaunchedEffect(err) {
            snackbarHostState.showSnackbar(err)
            vm.resetClientCreateStates()
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

            // Fondo degradado
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.linearGradient(
                            listOf(
                                colors.primary.copy(alpha = 0.95f),
                                AccentPurpleDark.copy(alpha = 0.95f)
                            ),
                            start = Offset.Zero,
                            end = Offset(1000f, 0f)
                        )
                    )
            )

            Column(modifier = Modifier.fillMaxSize()) {

                // Header
                Row(
                    modifier = Modifier
                        .padding(innerPadding)
                        .height(90.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            navController.navigate(ClientsScreenRoute) {
                                popUpTo(ClientsScreenRoute) { inclusive = true }
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
                        text = "Nuevo Cliente",
                        fontSize = 40.sp,
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 40.dp),
                        textAlign = TextAlign.Center
                    )
                }

                // Contenido
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(topStart = 60.dp))
                        .background(colors.background)
                        .padding(horizontal = 38.dp, vertical = 38.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    TextFieldPrefab(
                        text = "Nombre del cliente",
                        value = nombre,
                        onValueChange = { nombre = it },
                        placeholder = "Carlos Ramírez",
                        imeAction = ImeAction.Next
                    )

                    TextFieldPrefab(
                        text = "Teléfono",
                        value = telefono,
                        onValueChange = { telefono = it },
                        placeholder = "5511223344",
                        imeAction = ImeAction.Next
                    )

                    TextFieldPrefab(
                        text = "Correo electrónico",
                        value = correo,
                        onValueChange = { correo = it },
                        placeholder = "cliente@mail.com",
                        imeAction = ImeAction.Send,
                        keyboardActions = KeyboardActions(
                            onSend = {
                                if (
                                    nombre.isBlank() ||
                                    telefono.isBlank() ||
                                    correo.isBlank()
                                ) return@KeyboardActions

                                vm.newClient(
                                    nombre = nombre,
                                    telefono = telefono,
                                    correo = correo
                                )
                            }
                        )
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Botón crear cliente
                    ActionButton(
                        text = "Registrar cliente",
                        onClick = {
                            if (
                                nombre.isBlank() ||
                                telefono.isBlank() ||
                                correo.isBlank()
                            ) return@ActionButton

                            vm.newClient(
                                nombre = nombre,
                                telefono = telefono,
                                correo = correo
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(60.dp))
                }
            }
        }
    }
}
