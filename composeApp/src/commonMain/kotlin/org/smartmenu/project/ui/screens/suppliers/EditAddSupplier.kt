package org.smartmenu.project.ui.screens.suppliers

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
import org.smartmenu.project.ui.SuppliersScreenRoute
import org.smartmenu.project.ui.screens.auth.components.ActionButton
import org.smartmenu.project.ui.screens.auth.components.TextFieldPrefab
import org.smartmenu.project.ui.viewmodels.AdmonViewModel
import org.smartmenu.project.ui.viewmodels.AuthViewModel
import kotlin.reflect.KClass

@Composable
fun EditAddSupplier(
    navController: NavController,
    innerPadding: PaddingValues,
    supplierId: Int
) {
    val vm: AdmonViewModel = viewModel()
    val colors = MaterialTheme.colorScheme

    val selectedProveedor by vm.selectedSupplier
    val updateSuccess by vm.supplierUpdateSuccess
    val updateError by vm.supplierUpdateError

    val snackbar = remember { SnackbarHostState() }
    var showDeleteDialog by remember { mutableStateOf(false) }

    // ===========================================
    // Cargar datos iniciales
    // ===========================================
    LaunchedEffect(Unit) {
        vm.getProveedorById(supplierId)
    }

    // Estados del formulario
    var nombre by remember { mutableStateOf("") }
    var contacto by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }

    // Llenar campos automáticos
    LaunchedEffect(selectedProveedor) {
        selectedProveedor?.let { prov ->
            nombre = prov.nombre
            contacto = prov.contacto
            telefono = prov.telefono
            correo = prov.correo
        }
    }

    // ===========================================
    // Manejo actualización / error
    // ===========================================
    if (updateSuccess) {
        LaunchedEffect(Unit) {
            snackbar.showSnackbar("Proveedor actualizado con éxito")
            vm.resetSupplierUpdateStates()

            // Regresar a la lista
            navController.navigate(SuppliersScreenRoute) {
                popUpTo(SuppliersScreenRoute) { inclusive = true }
            }
        }
    }

    updateError?.let { error ->
        LaunchedEffect(error) {
            snackbar.showSnackbar(error)
            vm.resetSupplierUpdateStates()
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
                    vm.deleteProveedor(supplierId)
                }) {
                    Text("Eliminar", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancelar")
                }
            },
            title = { Text("¿Eliminar proveedor?") },
            text = { Text("Esta acción no se puede deshacer.") }
        )
    }

    // ===========================================
    // UI principal
    // ===========================================
    Scaffold(
        snackbarHost = { SnackbarHost(snackbar) }
    ) { scaffoldPadding ->

        if (selectedProveedor == null) {
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

            // Fondo
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

            Column(modifier = Modifier.fillMaxSize()) {

                // Flecha atrás
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            navController.navigate(SuppliersScreenRoute) {
                                popUpTo(SuppliersScreenRoute) { inclusive = true }
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
                    "Editar Proveedor",
                    fontSize = 36.sp,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(30.dp))

                // Formulario
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
                        text = "Nombre del proveedor",
                        value = nombre,
                        onValueChange = { nombre = it },
                        placeholder = "Proveedor A",
                        imeAction = ImeAction.Next
                    )

                    TextFieldPrefab(
                        text = "Nombre del contacto",
                        value = contacto,
                        onValueChange = { contacto = it },
                        placeholder = "Carlos López",
                        imeAction = ImeAction.Next
                    )

                    TextFieldPrefab(
                        text = "Teléfono",
                        value = telefono,
                        onValueChange = { telefono = it },
                        placeholder = "5551112222",
                        imeAction = ImeAction.Next
                    )

                    TextFieldPrefab(
                        text = "Correo",
                        value = correo,
                        onValueChange = { correo = it },
                        placeholder = "correo@proveedor.com",
                        imeAction = ImeAction.Send,
                        keyboardActions = KeyboardActions(
                            onSend = {
                                vm.updateProveedor(
                                    id = supplierId,
                                    nombre = nombre,
                                    contacto = contacto,
                                    telefono = telefono,
                                    correo = correo
                                )
                            }
                        )
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Botón actualizar
                    ActionButton(
                        text = "Actualizar",
                        onClick = {
                            vm.updateProveedor(
                                id = supplierId,
                                nombre = nombre,
                                contacto = contacto,
                                telefono = telefono,
                                correo = correo
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
                        Text("Eliminar proveedor", color = Color.White)
                    }

                    Spacer(modifier = Modifier.height(40.dp))
                }
            }
        }
    }
}
