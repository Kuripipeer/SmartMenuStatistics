package org.smartmenu.project.ui.screens.metrics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import org.smartmenu.project.ui.AccentPurpleDark
import org.smartmenu.project.ui.HomeScreenRoute
import org.smartmenu.project.ui.UsersScreenRoute
import org.smartmenu.project.ui.screens.home.components.GraficaCard
import org.smartmenu.project.ui.viewmodels.AuthViewModel
import org.smartmenu.project.ui.viewmodels.MetricsViewModel
import kotlin.reflect.KClass

@Composable
fun MetricsView(navController: NavController, innerPadding: PaddingValues) {
    val mVm: MetricsViewModel = viewModel()
    val colors = MaterialTheme.colorScheme

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
                        popUpTo(UsersScreenRoute) { inclusive = true }
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
                "Métricas",
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
                .padding(horizontal = 15.dp, vertical = 15.dp)
        ) {

            when {
                mVm.isLoading -> {
                    // Loader centrado
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                mVm.errorMessage != null -> {
                    // Mensaje de error
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Error al cargar métricas:\n${mVm.errorMessage}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                !mVm.response.ok || mVm.response.data.graficas.isEmpty() -> {
                    // La API respondió ok = false o sin datos
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No hay datos de métricas disponibles.",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                else -> {
                    // Lista de gráficas
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(mVm.response.data.graficas) { grafica ->
                            GraficaCard(
                                grafica = grafica,
                                modifier = Modifier.padding(bottom = 15.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
