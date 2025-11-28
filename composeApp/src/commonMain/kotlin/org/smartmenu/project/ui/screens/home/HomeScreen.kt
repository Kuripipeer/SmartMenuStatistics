package org.smartmenu.project.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.AddBusiness
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.PeopleAlt
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Person2
import androidx.compose.material.icons.filled.Person4
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.PersonAddAlt1
import androidx.compose.material.icons.filled.PersonPinCircle
import androidx.compose.material.icons.filled.PersonSearch
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.smartmenu.project.data.services.Preferences
import org.smartmenu.project.models.Grafica
import org.smartmenu.project.ui.AccentPurpleDark
import org.smartmenu.project.ui.ClientsScreenRoute
import org.smartmenu.project.ui.HomeScreenRoute
import org.smartmenu.project.ui.LoginScreenRoute
import org.smartmenu.project.ui.MetricsViewScreenRoute
import org.smartmenu.project.ui.SmartMenuTheme
import org.smartmenu.project.ui.SuppliersScreenRoute
import org.smartmenu.project.ui.UsersScreenRoute
import org.smartmenu.project.ui.screens.home.components.ActionCard
import org.smartmenu.project.ui.screens.home.components.GraficaCard
import org.smartmenu.project.ui.screens.home.components.Header
import org.smartmenu.project.ui.screens.home.components.charts.BarChart
import org.smartmenu.project.ui.screens.home.components.charts.LineChart
import org.smartmenu.project.ui.screens.home.components.charts.PieChart
import org.smartmenu.project.ui.viewmodels.AdmonViewModel
import org.smartmenu.project.ui.viewmodels.MetricsViewModel
import smartmenustatistics.composeapp.generated.resources.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, innerPadding: PaddingValues) {
    val colors = MaterialTheme.colorScheme
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )


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
    ){
        Header(
                navController = navController
        )

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
                .background(colors.background)
                .padding(horizontal = 15.dp, vertical = 15.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                ActionCard(
                    text = "Consultar usuarios",
                    icon = Icons.Default.Person,
                    onClick = {
                        navController.navigate(UsersScreenRoute) {
                            popUpTo(HomeScreenRoute) { inclusive = true }
                        }
                    }
                )
                ActionCard(
                    text = "Consultar proveedor",
                    icon = Icons.Default.PersonSearch,
                    onClick = {
                        scope.launch {
                            navController.navigate(SuppliersScreenRoute) {
                                popUpTo(HomeScreenRoute) { inclusive = true }
                            }
                        }
                    }
                )
                ActionCard(
                    text = "Clientes frecuentes",
                    icon = Icons.Default.PeopleAlt,
                    onClick = {
                        scope.launch {
                            navController.navigate(ClientsScreenRoute){
                                popUpTo(HomeScreenRoute) { inclusive = true}
                            }
                        }
                    }
                )

                ActionCard(
                    text = "Consultar métricas",
                    icon = Icons.Default.ShowChart,
                    onClick = {
                        scope.launch {
                            navController.navigate(MetricsViewScreenRoute){
                                popUpTo(HomeScreenRoute) { inclusive = true}
                            }
                        }
                    }
                )
            }

            Button(
                onClick = {
                    Preferences.clearPreferences()
                    navController.navigate(LoginScreenRoute) {
                        popUpTo(HomeScreenRoute) { inclusive = true }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .height(52.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colors.primary)
            ) {
                Text(
                    text = "Cerrar sesión",
                    color = colors.onPrimary,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    SmartMenuTheme {
        HomeScreen(navController = rememberNavController(), innerPadding = PaddingValues())
    }
}