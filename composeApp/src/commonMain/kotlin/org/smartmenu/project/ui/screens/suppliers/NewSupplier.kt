package org.smartmenu.project.ui.screens.suppliers

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
import org.smartmenu.project.ui.AccentPurpleDark
import org.smartmenu.project.ui.SuppliersScreenRoute
import org.smartmenu.project.ui.screens.auth.components.ActionButton
import org.smartmenu.project.ui.screens.auth.components.TextFieldPrefab
import org.smartmenu.project.ui.viewmodels.AdmonViewModel

@Composable
fun NewSupplier(navController: NavController, innerPadding: PaddingValues) {

    val colors = MaterialTheme.colorScheme
    val vm: AdmonViewModel = viewModel()

    // ESTADOS DESDE VM
    val createSuccess by vm.supplierCreateSuccess
    val createError by vm.supplierCreateError

    val snackbarHostState = remember { SnackbarHostState() }

    // CAMPOS DEL FORMULARIO
    var nombre by remember { mutableStateOf("") }
    var contacto by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }

    // ===============================
    // ÉXITO
    // ===============================
    if (createSuccess) {
        LaunchedEffect(Unit) {
            snackbarHostState.showSnackbar("Proveedor creado con éxito")
            vm.resetSupplierCreateStates()

            navController.navigate(SuppliersScreenRoute) {
                popUpTo(SuppliersScreenRoute) { inclusive = true }
            }
        }
    }

    // ===============================
    // ERROR
    // ===============================
    createError?.let { err ->
        LaunchedEffect(err) {
            snackbarHostState.showSnackbar(err)
            vm.resetSupplierCreateStates()
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

                // Encabezado con flecha
                Row(
                    modifier = Modifier
                        .padding(innerPadding)
                        .height(90.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            navController.navigate(SuppliersScreenRoute) {
                                popUpTo(SuppliersScreenRoute) { inclusive = true }
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
                        text = "Nuevo Proveedor",
                        fontSize = 40.sp,
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 40.dp),
                        textAlign = TextAlign.Center
                    )
                }

                // Formulario
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
                        text = "Nombre del proveedor",
                        value = nombre,
                        onValueChange = { nombre = it },
                        placeholder = "Proveedor A"
                    )

                    TextFieldPrefab(
                        text = "Nombre del contacto",
                        value = contacto,
                        onValueChange = { contacto = it },
                        placeholder = "Carlos López"
                    )

                    TextFieldPrefab(
                        text = "Teléfono",
                        value = telefono,
                        onValueChange = { telefono = it },
                        placeholder = "5551112222"
                    )

                    TextFieldPrefab(
                        text = "Correo electrónico",
                        value = correo,
                        onValueChange = { correo = it },
                        placeholder = "correo@proveedor.com"
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // BOTÓN CREAR PROVEEDOR
                    ActionButton(
                        text = "Registrar proveedor",
                        onClick = {
                            if (
                                nombre.isBlank() ||
                                contacto.isBlank() ||
                                telefono.isBlank() ||
                                correo.isBlank()
                            ) return@ActionButton

                            vm.newSupplier(
                                nombre = nombre,
                                contacto = contacto,
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
