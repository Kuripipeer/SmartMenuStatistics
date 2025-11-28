package org.smartmenu.project.ui.screens.clients

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import org.smartmenu.project.ui.AccentPurpleDark
import org.smartmenu.project.ui.ClientsScreenRoute
import org.smartmenu.project.ui.screens.auth.components.ActionButton
import org.smartmenu.project.ui.screens.auth.components.TextFieldPrefab
import org.smartmenu.project.ui.viewmodels.AdmonViewModel
import org.smartmenu.project.ui.viewmodels.AuthViewModel
import kotlin.reflect.KClass

@Composable
fun EditClient(
    navController: NavController,
    innerPadding: PaddingValues,
    clientId: Int
) {
    val vm: AdmonViewModel = viewModel()
    val colors = MaterialTheme.colorScheme

    val selectedClient by vm.selectedClient
    val updateSuccess by vm.clientUpdateSuccess
    val updateError by vm.clientUpdateError

    val snackbar = remember { SnackbarHostState() }

    var showDeleteDialog by remember { mutableStateOf(false) }

    // =============================
    // CARGAR CLIENTE DESDE API
    // =============================
    LaunchedEffect(Unit) {
        vm.getClientById(clientId)
    }

    // CAMPOS DEL FORMULARIO
    var nombre by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }

    // CUANDO CARGA EL CLIENTE, LLENAR CAMPOS
    LaunchedEffect(selectedClient) {
        selectedClient?.let { cli ->
            nombre = cli.nombre
            telefono = cli.telefono
            correo = cli.correo
        }
    }

    // =============================
    // MANEJO DE ÉXITO
    // =============================
    if (updateSuccess) {
        LaunchedEffect(Unit) {
            snackbar.showSnackbar("Cliente actualizado con éxito")
            vm.resetClientUpdateStates()

            navController.navigate(ClientsScreenRoute) {
                popUpTo(ClientsScreenRoute) { inclusive = true }
            }
        }
    }

    // =============================
    // MANEJO DE ERROR
    // =============================
    updateError?.let { err ->
        LaunchedEffect(err) {
            snackbar.showSnackbar(err)
            vm.resetClientUpdateStates()
        }
    }

    // =============================
    // DIÁLOGO ELIMINAR
    // =============================
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    showDeleteDialog = false
                    vm.deleteClient(clientId)
                }) { Text("Eliminar", color = Color.Red) }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancelar")
                }
            },
            title = { Text("¿Eliminar cliente?") },
            text = { Text("Esta acción no se puede deshacer.") }
        )
    }

    // =============================
    // UI
    // =============================
    Scaffold(
        snackbarHost = { SnackbarHost(snackbar) }
    ) { scaffoldPadding ->

        if (selectedClient == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(scaffoldPadding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
            return@Scaffold
        }

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
                                colors.primary.copy(alpha = 0.9f),
                                AccentPurpleDark.copy(alpha = 0.95f)
                            ),
                            start = Offset(0f, 0f),
                            end = Offset(1000f, 0f)
                        )
                    )
            )

            Column(modifier = Modifier.fillMaxSize()) {

                // Flecha de regreso
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            navController.navigate(ClientsScreenRoute) {
                                popUpTo(ClientsScreenRoute) { inclusive = true }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                }

                // Título
                Text(
                    text = "Editar Cliente",
                    fontSize = 36.sp,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(25.dp))

                // TARJETA DEL FORM
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(topStart = 50.dp))
                        .background(colors.background)
                        .padding(28.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    TextFieldPrefab(
                        text = "Nombre",
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
                        text = "Correo",
                        value = correo,
                        onValueChange = { correo = it },
                        placeholder = "cliente@mail.com",
                        imeAction = ImeAction.Send,
                        keyboardActions = KeyboardActions(
                            onSend = {
                                vm.updateClient(
                                    id = clientId,
                                    nombre = nombre,
                                    telefono = telefono,
                                    correo = correo
                                )
                            }
                        )
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // BOTÓN ACTUALIZAR
                    ActionButton(
                        text = "Actualizar cliente",
                        onClick = {
                            vm.updateClient(
                                id = clientId,
                                nombre = nombre,
                                telefono = telefono,
                                correo = correo
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // BOTÓN ELIMINAR
                    Button(
                        onClick = { showDeleteDialog = true },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red.copy(alpha = 0.85f)
                        )
                    ) {
                        Text("Eliminar cliente", color = Color.White)
                    }

                    Spacer(modifier = Modifier.height(40.dp))
                }
            }
        }
    }
}
