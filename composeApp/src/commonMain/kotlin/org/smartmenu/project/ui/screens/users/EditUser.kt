package org.smartmenu.project.ui.screens.users

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
import org.smartmenu.project.ui.UsersScreenRoute
import org.smartmenu.project.ui.screens.auth.components.ActionButton
import org.smartmenu.project.ui.screens.auth.components.TextFieldPrefab
import org.smartmenu.project.ui.screens.auth.components.RoleDropdownPrefab
import org.smartmenu.project.ui.viewmodels.AdmonViewModel
import org.smartmenu.project.ui.viewmodels.AuthViewModel
import kotlin.reflect.KClass

@Composable
fun EditUser(
    navController: NavController,
    innerPadding: PaddingValues,
    userId: Int
) {
    val vm: AdmonViewModel = viewModel()
    val colors = MaterialTheme.colorScheme

    val selectedUser by vm.selectedUser
    val roles = vm.rolesList.value

    val updateSuccess by vm.userUpdateSuccess
    val updateError by vm.userUpdateError

    val snackbar = remember { SnackbarHostState() }

    var showDeleteDialog by remember { mutableStateOf(false) }

    // ===========================================
    // Cargar datos iniciales
    // ===========================================
    LaunchedEffect(Unit) {
        vm.getRoles()
        vm.getUserById(userId)
    }

    // Estados del formulario
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var selectedRole by remember { mutableStateOf<Int?>(null) }

    LaunchedEffect(selectedUser) {
        selectedUser?.let { user ->
            val nameParts = user.nombre.split(" ")
            firstName = nameParts.firstOrNull() ?: ""
            lastName = nameParts.drop(1).joinToString(" ")

            email = user.usuario
            selectedRole = user.rol_id
        }
    }

    // ===========================================
    // Manejo éxito / error
    // ===========================================
    if (updateSuccess) {
        LaunchedEffect(Unit) {
            snackbar.showSnackbar("Operación completada con éxito")
            vm.resetUpdateStates()
            navController.popBackStack()
        }
    }

    updateError?.let { error ->
        LaunchedEffect(error) {
            snackbar.showSnackbar(error)
            vm.resetUpdateStates()
        }
    }

    // ===========================================
    // Dialog eliminar
    // ===========================================
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    showDeleteDialog = false
                    vm.deleteUser(userId)
                }) {
                    Text("Eliminar", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancelar")
                }
            },
            title = { Text("¿Eliminar usuario?") },
            text = { Text("Esta acción no se puede deshacer.") }
        )
    }

    // ===========================================
    // UI principal
    // ===========================================
    Scaffold(
        snackbarHost = { SnackbarHost(snackbar) }
    ) { scaffoldPadding ->

        if (selectedUser == null) {
            Box(
                Modifier
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
                        Brush.verticalGradient(
                            listOf(
                                colors.primary.copy(alpha = 0.9f),
                                AccentPurpleDark.copy(alpha = 0.95f)
                            )
                        )
                    )
            )

            Column(
                modifier = Modifier.fillMaxSize()
            ) {

                // ===========================================
                // Flecha regresar
                // ===========================================
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            navController.navigate(UsersScreenRoute) {
                                popUpTo(UsersScreenRoute) { inclusive = true }
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
                    "Editar Usuario",
                    fontSize = 36.sp,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(30.dp))

                // ===========================================
                // Formulario
                // ===========================================
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
                        text = "First Name",
                        value = firstName,
                        onValueChange = { firstName = it },
                        placeholder = "Juan",
                        imeAction = ImeAction.Next
                    )

                    TextFieldPrefab(
                        text = "Last Name",
                        value = lastName,
                        onValueChange = { lastName = it },
                        placeholder = "Pérez",
                        imeAction = ImeAction.Next
                    )

                    RoleDropdownPrefab(
                        roles = roles,
                        selectedRole = roles.find { it.id == selectedRole },
                        onRoleSelected = { selectedRole = it.id }
                    )

                    TextFieldPrefab(
                        text = "Email",
                        value = email,
                        onValueChange = { email = it },
                        placeholder = "correo@ejemplo.com",
                        imeAction = ImeAction.Send,
                        keyboardActions = KeyboardActions(
                            onSend = {
                                if (selectedRole == null) return@KeyboardActions

                                vm.updateUser(
                                    id = userId,
                                    nombre = "$firstName $lastName",
                                    usuario = email,
                                    contraseña = null,
                                    rol_id = selectedRole!!
                                )
                            }
                        )
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Botón actualizar
                    ActionButton(
                        text = "Actualizar",
                        onClick = {
                            if (selectedRole == null) return@ActionButton

                            vm.updateUser(
                                id = userId,
                                nombre = "$firstName $lastName",
                                usuario = email,
                                contraseña = null,
                                rol_id = selectedRole!!
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Botón eliminar
                    Button(
                        onClick = { showDeleteDialog = true },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red.copy(alpha = 0.85f)
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Eliminar usuario", color = Color.White)
                    }

                    Spacer(modifier = Modifier.height(40.dp))
                }
            }
        }
    }
}
