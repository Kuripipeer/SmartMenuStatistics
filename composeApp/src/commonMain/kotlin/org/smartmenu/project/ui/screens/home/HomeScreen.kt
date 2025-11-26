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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.smartmenu.project.models.Grafica
import org.smartmenu.project.ui.AccentPurpleDark
import org.smartmenu.project.ui.SmartMenuTheme
import org.smartmenu.project.ui.screens.home.components.GraficaCard
import org.smartmenu.project.ui.screens.home.components.charts.BarChart
import org.smartmenu.project.ui.screens.home.components.charts.LineChart
import org.smartmenu.project.ui.screens.home.components.charts.PieChart
import org.smartmenu.project.ui.viewmodels.MetricsViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, innerPadding: PaddingValues) {
    val mVm: MetricsViewModel = viewModel()
    val colors = MaterialTheme.colorScheme
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    var tableTitle by remember {
        mutableStateOf("")
    }

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
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp)
            .padding(horizontal = 15.dp)
    ){
        item {
            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                IconButton(
                    onClick = {
                        navController.popBackStack()
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Regresar",
                        tint = colors.primary,
                        modifier =
                            Modifier
                                .size(30.dp)
                                .clip(CircleShape)
                                .background(color = Color.White.copy(alpha = 0.7f))
                    )
                }
                Text(
                    text = "Estadísticas",
                    style = MaterialTheme.typography.headlineSmall,
                    color = colors.onBackground,
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 10.dp),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
                IconButton(
                    onClick = {
                        navController.popBackStack()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Regresar",
                        tint = colors.primary,
                        modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape)
                            .background(color = Color.White.copy(alpha = 0.7f))
                    )
                }
            }
        }
        item {
            Button(
                onClick = {
                    scope.launch {
                        tableTitle = "Ventas diarias"
                        mVm.separateMetricsByTitle(tableTitle)
                        sheetState.expand()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSystemInDarkTheme()) Color.DarkGray else Color.LightGray
                ),
                shape = RoundedCornerShape(25.dp)
            ) {
                Text(text = "Ventas diarias", color = colors.onBackground)
            }
        }

        item {
            Button(
                onClick = {
                    scope.launch {
                        tableTitle = "Ventas por platillo"
                        mVm.separateMetricsByTitle(tableTitle)
                        sheetState.expand()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSystemInDarkTheme()) Color.DarkGray else Color.LightGray
                ),
                shape = RoundedCornerShape(25.dp)
            ) {
                Text(text = "Ventas por platillo", color = colors.onBackground)
            }
        }

        item {
            Button(
                onClick = {
                    scope.launch {
                        tableTitle = "Meseros con más pedidos"
                        mVm.separateMetricsByTitle(tableTitle)
                        sheetState.expand()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSystemInDarkTheme()) Color.DarkGray else Color.LightGray
                ),
                shape = RoundedCornerShape(25.dp)
            ) {
                Text(text = "Meseros con más pedidos", color = colors.onBackground)
            }
        }
    }

    if (mVm.showMetrics){
        ModalBottomSheet(
            onDismissRequest = {
                scope.launch {
                    mVm.showMetrics = false
                    sheetState.hide()
                }
            },
            sheetState = sheetState,
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                GraficaCard(grafica = mVm.separatedMetrics)
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