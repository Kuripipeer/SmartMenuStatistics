package org.smartmenu.project.ui.screens.clients

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAddAlt1
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import org.smartmenu.project.ui.*
import org.smartmenu.project.ui.AccentPurpleDark
import org.smartmenu.project.ui.screens.home.components.ActionCard
import org.smartmenu.project.ui.viewmodels.AdmonViewModel

@Composable
fun ClientsHome(navController: NavController, innerPadding: PaddingValues) {

    val vm: AdmonViewModel = viewModel()
    val clients = vm.clientsList.value
    val colors = MaterialTheme.colorScheme

    LaunchedEffect(Unit) {
        vm.getClientes()
    }

    // Fondo degradado
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
            .padding(top = 50.dp)
    ) {

        // Header con flecha
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    navController.navigate(HomeScreenRoute) {
                        popUpTo(ClientsScreenRoute) { inclusive = true }
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }

            Text(
                text = "Clientes",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                modifier = Modifier.padding(start = 10.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Contenedor blanco
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
                .background(colors.background)
                .padding(horizontal = 15.dp, vertical = 15.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            // Lista de clientes
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(clients) { client ->
                    ActionCard(
                        text = "${client.nombre} — ${client.telefono}",
                        icon = Icons.Default.Person,
                        onClick = {
                            // VISTA DETALLE SI QUIERES LUEGO
                             navController.navigate(EditClientScreenRoute(client.id))
                        },
                        //extraText = client.correo  // si tu ActionCard soporta subtítulo
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón Crear Cliente
            Button(
                onClick = { navController.navigate(NewClientScreenRoute) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colors.primary)
            ) {
                Icon(
                    imageVector = Icons.Default.PersonAddAlt1,
                    contentDescription = null,
                    tint = colors.onPrimary
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Registrar cliente",
                    color = colors.onPrimary,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
